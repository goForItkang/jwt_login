package com.jobdam.jwt_login.repository;

import com.jobdam.jwt_login.Entity.UserEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;

@RestController
@Transactional
public class UserRepository {
    @PersistenceContext
    private EntityManager em;

    //유저 회원가입 로직
    public void insertUser(UserEntity userEntity) {
        em.persist(userEntity);
    }
}
