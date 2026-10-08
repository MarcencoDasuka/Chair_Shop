package com.ChairShop.service.impl;
import com.ChairShop.model.constants.ApiErrorMessage;
import com.ChairShop.model.enteties.RefreshToken;
import com.ChairShop.model.enteties.User;
import com.ChairShop.model.exception.NotFoundException;
import com.ChairShop.repositories.RefreshTokenRepository;
import com.ChairShop.service.RefreshTokenService;
import com.ChairShop.utils.ApiUtils;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.query.sql.internal.ParameterRecognizerImpl;
import org.springframework.stereotype.Service;

import java.sql.Ref;
import java.time.LocalDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    public RefreshToken generateOrUpdateRefreshToken(User user) {
        return refreshTokenRepository.findByUserId(user.getId())
                .map(refreshToken -> {
                    refreshToken.setCreated(LocalDateTime.now());
                    refreshToken.setToken(ApiUtils.generateUuidWithoutDash());
                    return refreshTokenRepository.save(refreshToken);
                })
                .orElseGet(()->{
                    RefreshToken refreshToken = new RefreshToken();
                    refreshToken.setCreated(LocalDateTime.now());
                    refreshToken.setToken(ApiUtils.generateUuidWithoutDash());
                    refreshToken.setUser(user);
                    return refreshTokenRepository.save(refreshToken);
                });
    }


    @Override
    public RefreshToken validateOrUpdateRefreshToken(@NotNull String requestRefreshToken) {
        RefreshToken refreshToken = refreshTokenRepository.findByToken(requestRefreshToken)
                .orElseThrow(()-> new NotFoundException(ApiErrorMessage.NOT_FOUND_REFRESH_TOKEN.getMessage()));

        refreshToken.setCreated(LocalDateTime.now());
        refreshToken.setToken(ApiUtils.generateUuidWithoutDash());
        return refreshTokenRepository.save(refreshToken);
    }
}
