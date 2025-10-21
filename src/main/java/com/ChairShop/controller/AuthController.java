package com.ChairShop.controller;

import com.ChairShop.model.constants.ApiLogMessage;
import com.ChairShop.model.dto.User.LoginRequest;
import com.ChairShop.model.dto.User.UserProfileDTO;
import com.ChairShop.model.response.IamResponse;
import com.ChairShop.service.AuthService;
import com.ChairShop.utils.ApiUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import jakarta.servlet.http.Cookie;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @PostMapping("/login")
    public ResponseEntity<?> login (
            @RequestBody @Valid LoginRequest request,
            HttpServletResponse response){
        log.trace(ApiLogMessage.NAME_OF_CURRENT_METHOD.getValue(), ApiUtils.getMethodName());
        IamResponse<UserProfileDTO> result = authService.loginUser(request);
        Cookie authCookie = ApiUtils.createAuthCookie(result.getPayload().getToken());
        return ResponseEntity.ok(result);
    }

    @GetMapping("/refresh/token")
    public ResponseEntity<IamResponse<UserProfileDTO>> refreshToken(
            @RequestParam(name = "token") String refreshToken,
            HttpServletResponse response){
        log.trace(ApiLogMessage.NAME_OF_CURRENT_METHOD.getValue(), ApiUtils.getMethodName());
        IamResponse<UserProfileDTO> result = authService.refreshAccessToken(refreshToken);
        Cookie authCookie = ApiUtils.createAuthCookie(result.getPayload().getToken());
        response.addCookie(authCookie);
        return ResponseEntity.ok(result);
    }

}
