package com.jobdam.jwt_login.repository;

import com.jobdam.jwt_login.Entity.RefreshTokenEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Repository
@Transactional
public class TokenRepository {
    @PersistenceContext
    private EntityManager em;

    @Transactional(readOnly = true)
    public LocalDateTime selectByRefreshToken(Long userEmail) {

        return null;
    }

    public void saveRefreshToken(RefreshTokenEntity refreshTokenEntity) {
        em.persist(refreshTokenEntity);
    }
}
