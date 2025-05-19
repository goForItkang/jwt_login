package com.jobdam.jwt_login.service;

import com.jobdam.jwt_login.Entity.UserEntity;
import com.jobdam.jwt_login.dto.UserDTO;
import com.jobdam.jwt_login.excetion.CustomException;
import com.jobdam.jwt_login.excetion.ErrorCode;
import com.jobdam.jwt_login.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
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
                .userEmail(userDTO.getUserEmail())
                .password(userDTO.getPassword())
                .userName(userDTO.getUsername())
                .build();
    }

}
