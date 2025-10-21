package com.ChairShop.service;

import com.ChairShop.model.enteties.RefreshToken;
import com.ChairShop.model.enteties.User;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


public interface RefreshTokenService {
    RefreshToken generateOrUpdateRefreshToken(@NotNull User user);

    RefreshToken validateOrUpdateRefreshToken(@NotNull String refreshToken);
}
