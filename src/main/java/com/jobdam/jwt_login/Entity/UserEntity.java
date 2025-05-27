package com.jobdam.jwt_login.Entity;

import com.jobdam.jwt_login.dto.UserDTO;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "USER")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    @Column(unique = true , nullable = false)
    private String userEmail;
    @Column(unique = true , nullable = false)
    private String userEmailHashCode;
    private String password;
    private String userName;

    @OneToOne(mappedBy = "user")
    private RefreshTokenEntity refreshToken;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<UserRoleEntity> userRoles = new HashSet<>();


}
