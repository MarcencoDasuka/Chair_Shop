package com.ChairShop.service;


import com.ChairShop.model.dto.User.LoginRequest;
import com.ChairShop.model.dto.User.UserProfileDTO;
import com.ChairShop.model.response.IamResponse;
import jakarta.validation.constraints.NotNull;

public interface AuthService {

    IamResponse<UserProfileDTO> loginUser (@NotNull LoginRequest request);

    IamResponse<UserProfileDTO> refreshAccessToken (@NotNull String refreshToken);
}
