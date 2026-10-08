package com.ChairShop.repositories;

import com.ChairShop.model.enteties.RefreshToken;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Ref;
import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Integer> {

    Optional<RefreshToken> findByToken(String token);
    Optional<RefreshToken> findByUserId(Integer userId);
}
