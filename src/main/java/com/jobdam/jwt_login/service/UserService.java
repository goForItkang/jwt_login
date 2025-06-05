package com.jobdam.jwt_login.service;

import com.jobdam.jwt_login.Entity.RefreshTokenEntity;
import com.jobdam.jwt_login.Entity.UserEntity;
import com.jobdam.jwt_login.Entity.UserRoleEntity;
import com.jobdam.jwt_login.dto.ReqSignupUser;
import com.jobdam.jwt_login.dto.UserDTO;
import com.jobdam.jwt_login.dto.UserRoleDTO;
import com.jobdam.jwt_login.excetion.CustomException;
import com.jobdam.jwt_login.excetion.ErrorCode;
import com.jobdam.jwt_login.repository.TokenRepository;
import com.jobdam.jwt_login.repository.UserRepository;
import com.jobdam.jwt_login.util.AESUtil;
import com.jobdam.jwt_login.util.HashUtil;
import com.jobdam.jwt_login.util.JwtUtil;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AESUtil aesUtil;
    private final UserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;
    private final TokenRepository tokenRepository;
   // 회원가입진행 할때
    public void userSave(UserDTO userDTO) {
        try {
            userRepository.insertUser(entityBuilder(userDTO));
        }catch (Exception e) {
            e.printStackTrace();
            throw new CustomException(ErrorCode.DUPLICATE_USERID);
        }
    }
    // 회원가입 할때 사용하는 메서드
    private UserEntity entityBuilder(UserDTO userDTO){
        UserEntity userEntity = UserEntity.builder()
                .userEmail(aesUtil.encoding(userDTO.getUserEmail()))
                .userEmailHashCode(new HashUtil().sha256Encode(userDTO.getUserEmail()))
                .password(passwordEncoder.encode(userDTO.getPassword()))
                .userName(aesUtil.encoding(userDTO.getUsername()))
                .userRoles(new HashSet<>())
                .refreshToken(null)
                .build();
        UserRoleEntity roles= new UserRoleEntity();
        roles.setUser(userEntity);
        roles.setRoleName("ADMIN");
        userEntity.getUserRoles().add(roles);
        return userEntity;
    }
    private String OneDataDecoding(String planText){
        return aesUtil.decoding(planText);
    }
    public UserDTO entityToDTO(UserEntity entity) {
        return new UserDTO(
                entity.getUserId(),
                entity.getUserEmail(),
                entity.getPassword(),
                entity.getUserName(),
                entity.getUserRoles().stream()
                        .map(role -> new UserRoleDTO(role.getRoleName()))
                        .collect(Collectors.toList())
        );
    }
    // 로그인 로직
    public UserDTO login(UserDTO userDTO) {
        System.out.println("값 받아와 진거임 ?");
        UserDTO user= (UserDTO)userDetailsService.loadUserByUsername(userDTO.getUserEmail());
        System.out.println(user);
        if(passwordEncoder.matches(userDTO.getPassword(),user.getPassword())){
            return user;
        }
        throw new CustomException(ErrorCode.USER_NOT_FOUND);
    }
    //toekn 생성
    public String tokenGenerate(UserDTO userDTO) {

        String token = jwtUtil.createToken(userDTO.getUserEmail(),userDTO.getAuthorities().toString());


    return token;
    }
    //refreshToken 확인

    public void saveRefreshToken(Long userId) {
        try {
            String result = jwtUtil.createRefreshToken(userId);
            RefreshTokenEntity refreshTokenEntity = RefreshTokenEntity.builder()
                    .id(userId)
                    .refreshToken(result)
                    .build();
            tokenRepository.saveRefreshToken(refreshTokenEntity);
        }catch (Exception e){
           throw new  CustomException(ErrorCode.ENCODING_ERROR);
        }
    }
    public void passwordMach(ReqSignupUser reqSignupUser){
        if(!reqSignupUser.getPassword().equals(reqSignupUser.getReCheckPassword())){
            throw new CustomException(ErrorCode.PASSWORD_NOT_MATCH);
        }else{
            // DB에 저장하는 로직
        }

    }



}
