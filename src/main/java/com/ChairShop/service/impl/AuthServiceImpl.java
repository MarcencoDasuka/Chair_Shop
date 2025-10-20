package com.ChairShop.service.impl;

import com.ChairShop.mapper.UserMapper;
import com.ChairShop.model.constants.ApiErrorMessage;
import com.ChairShop.model.dto.User.LoginRequest;
import com.ChairShop.model.dto.User.UserProfileDTO;
import com.ChairShop.model.enteties.User;
import com.ChairShop.model.exception.NotFoundException;
import com.ChairShop.model.exception.iInvalidException;
import com.ChairShop.model.response.IamResponse;
import com.ChairShop.repositories.UserRepository;
import com.ChairShop.security.JwtTokenProvider;
import com.ChairShop.service.AuthService;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private  final UserMapper userMapper;
    private  final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;


    @Override
    public IamResponse<UserProfileDTO> loginUser(@NotNull LoginRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
                    );
        } catch (BadCredentialsException e){
            throw new iInvalidException(ApiErrorMessage.INVALID_USER_OR_PASSWORD.getMessage());
        }

        User user = userRepository.findUserByEmailAndDeletedFalse(request.getEmail())
                .orElseThrow(()-> new iInvalidException(ApiErrorMessage.USER_WITH_EMAIL_NOT_FOUND.getMessage(request.getEmail())));

        String token = jwtTokenProvider.generateToken(user);
        UserProfileDTO userProfileDTO = userMapper.toUserProfileDTO(user, token);
        userProfileDTO.setToken(token);

        return IamResponse.createSuccessfulWithNewToken(userProfileDTO);
    }


}
