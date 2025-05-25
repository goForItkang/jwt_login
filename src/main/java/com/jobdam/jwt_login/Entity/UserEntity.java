package com.jobdam.jwt_login.Entity;

import com.jobdam.jwt_login.dto.UserDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "USER")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    @Column(unique = true , nullable = false)
    private String userEmail;
    private String password;
    private String userName;

    @OneToOne(mappedBy = "user")
    private RefreshTokenEntity refreshToken;
}
