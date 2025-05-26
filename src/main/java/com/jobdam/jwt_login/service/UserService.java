package com.jobdam.jwt_login.service;

import com.jobdam.jwt_login.Entity.UserEntity;
import com.jobdam.jwt_login.dto.UserDTO;
import com.jobdam.jwt_login.excetion.CustomException;
import com.jobdam.jwt_login.excetion.ErrorCode;
import com.jobdam.jwt_login.repository.UserRepository;
import com.jobdam.jwt_login.util.AESUtil;
import com.jobdam.jwt_login.util.HashUtil;
import com.jobdam.jwt_login.util.JwtUtil;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AESUtil aesUtil;
    private final UserDetailsService userDetailsService;
    private JwtUtil jwtUtil;
    public void userSave(UserDTO userDTO) {
        try {
            userRepository.insertUser(entityBuilder(userDTO));
        }catch (Exception e) {
            e.printStackTrace();
            throw new CustomException(ErrorCode.DUPLICATE_USERID);
        }
    }
    // 로그인할때 사용하는 메서드
    private UserEntity entityBuilder(UserDTO userDTO){
        return UserEntity.builder()
                .userEmail(aesUtil.encoding(userDTO.getUserEmail()))
                .userEmailHashCode(new HashUtil().sha256Encode(userDTO.getUserEmail()))
                .password(passwordEncoder.encode(userDTO.getPassword()))
                .userName(aesUtil.encoding(userDTO.getUsername()))
                .refreshToken(null)
                .build();
    }
    private String OneDataDecoding(String planText){
        return aesUtil.decoding(planText);
    }

    public UserDTO login(UserDTO userDTO) {
        System.out.println("값 받아와 진거임 ?");
        UserDTO user= (UserDTO)userDetailsService.loadUserByUsername(userDTO.getUserEmail());

        if(passwordEncoder.matches(userDTO.getPassword(),user.getPassword())){
            System.out.println(user);
            return user;
        }
        throw new CustomException(ErrorCode.USER_NOT_FOUND);
    }
    //toekn 생성
    public String tokenGenerate(UserDTO userDTO) {

        String token = jwtUtil.createToken(userDTO.getUserEmail(),userDTO.getAuthorities().toString());
    return token;
    }
}
