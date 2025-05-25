package com.jobdam.jwt_login.service;

import com.jobdam.jwt_login.Entity.UserEntity;
import com.jobdam.jwt_login.dto.UserDTO;
import com.jobdam.jwt_login.excetion.CustomException;
import com.jobdam.jwt_login.excetion.ErrorCode;
import com.jobdam.jwt_login.repository.UserRepository;
import com.jobdam.jwt_login.util.AESUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AESUtil aesUtil;
    public void userSave(UserDTO userDTO) {
        try {
            userRepository.insertUser(entityBuilder(userDTO));
        }catch (Exception e) {
            throw new CustomException(ErrorCode.DUPLICATE_USERID);
        }
    }
    private UserEntity entityBuilder(UserDTO userDTO){
        return UserEntity.builder()
                .userId(userDTO.getUserId())
                .userEmail(aesUtil.encoding(userDTO.getUserEmail()))
                .password(passwordEncoder.encode(userDTO.getPassword()))
                .userName(aesUtil.encoding(userDTO.getUsername()))
                .build();
    }

}
