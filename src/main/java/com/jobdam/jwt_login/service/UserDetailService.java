package com.jobdam.jwt_login.service;

import com.jobdam.jwt_login.Entity.UserEntity;
import com.jobdam.jwt_login.dto.UserDTO;
import com.jobdam.jwt_login.excetion.CustomException;
import com.jobdam.jwt_login.repository.UserRepository;
import com.jobdam.jwt_login.util.AESUtil;
import com.jobdam.jwt_login.util.HashUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserDetailService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AESUtil aesUtil;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("여기 까지는 데이터가 전송 된거야 ?");
        UserEntity loginUser = userRepository.selectByEmail(new HashUtil().sha256Encode(username));
        return entityToDTO(loginUser);

    }
    // 비밀번호는 암호화 한 상태랑 달라서 확인 해야한다.
    private UserDTO entityToDTO(UserEntity userEntity) {
        UserDTO userDTO = new UserDTO();
        userDTO.setUserId(userEntity.getUserId());
        userDTO.setUserEmail(aesUtil.decoding(userEntity.getUserEmail()));
        userDTO.setUserName(aesUtil.decoding(userEntity.getUserName()));
        userDTO.setPassword(userEntity.getPassword());
        return userDTO;
    }
}
