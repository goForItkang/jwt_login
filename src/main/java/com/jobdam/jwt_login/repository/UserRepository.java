package com.jobdam.jwt_login.repository;

import com.jobdam.jwt_login.Entity.UserEntity;
import com.jobdam.jwt_login.Entity.UserRoleEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
@Repository
@Transactional
public class UserRepository {
    @PersistenceContext
    private EntityManager em;

    //유저 회원가입 로직
    public void insertUser(UserEntity userEntity) {
        em.persist(userEntity);
    }
    // 유저 로그인 로직
    @Transactional(readOnly = true)
    public UserEntity selectByEmail(String email) {
        return em.createQuery(
                        "select u from UserEntity u " +
                                "left join fetch u.userRoles " +
                                "where u.userEmailHashCode = :email", UserEntity.class)
                .setParameter("email", email)
                .getSingleResult();
    }

    public Set<UserRoleEntity> selectByRole(Long userId) {
        List<UserRoleEntity> list = em.createQuery(
                        "select r from UserRoleEntity r where r.user.userId = :userId", UserRoleEntity.class)
                .setParameter("userId", userId)
                .getResultList();

        return new HashSet<>(list);
    }
}
