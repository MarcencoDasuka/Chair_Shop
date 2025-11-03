package com.ChairShop.service.impl;

import com.ChairShop.mapper.UserMapper;
import com.ChairShop.model.constants.ApiErrorMessage;
import com.ChairShop.model.enteties.Role;
import com.ChairShop.model.exception.DataExistException;
import com.ChairShop.model.exception.InvalidPasswordException;
import com.ChairShop.model.exception.NotFoundException;
import com.ChairShop.model.request.user.LoginRequest;
import com.ChairShop.model.dto.User.UserProfileDTO;
import com.ChairShop.model.enteties.RefreshToken;
import com.ChairShop.model.enteties.User;
import com.ChairShop.model.exception.iInvalidException;
import com.ChairShop.model.request.user.RegistrationUserRequest;
import com.ChairShop.model.response.IamResponse;
import com.ChairShop.repositories.RoleRepository;
import com.ChairShop.repositories.UserRepository;
import com.ChairShop.security.JwtTokenProvider;
import com.ChairShop.security.validation.AccessValidator;
import com.ChairShop.service.AuthService;
import com.ChairShop.service.RefreshTokenService;
import com.ChairShop.service.model.IamServiceUserRole;
import com.ChairShop.utils.PasswordUtils;
import jakarta.persistence.Access;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.internal.constraintvalidators.hv.ParameterScriptAssertValidator;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Slf4j
@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private  final UserMapper userMapper;
    private  final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;
    private final RefreshTokenService refreshTokenService;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AccessValidator accessValidator;

 //   private final ParameterScriptAssertValidator parameterScriptAssertValidator;

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
        RefreshToken refreshToken = refreshTokenService.generateOrUpdateRefreshToken(user);
        UserProfileDTO userProfileDTO = userMapper.toUserProfileDTO(user, token, refreshToken.getToken());
        userProfileDTO.setToken(token);

        return IamResponse.createSuccessfulWithNewToken(userProfileDTO);
    }

    @Override
    public IamResponse<UserProfileDTO> refreshAccessToken(@NotNull String refreshTokenValue) {
        RefreshToken refreshToken= refreshTokenService.validateOrUpdateRefreshToken(refreshTokenValue);
        User user = refreshToken.getUser();
        String token = jwtTokenProvider.generateToken(user);
        return IamResponse.createSuccessfulWithNewToken(
                userMapper.toUserProfileDTO(user, token, refreshToken.getToken())
        );
    }

    @Override
    public IamResponse<UserProfileDTO> registrationUser(@NotNull RegistrationUserRequest request) {
        accessValidator.validateNewUser(
                request.getUsername(),
                request.getEmail(),
                request.getPassword(),
                request.getConfirmPassword()
        );


    Role userRole = roleRepository.findByName(IamServiceUserRole.USER.getRole())
            .orElseThrow(()-> new NotFoundException(ApiErrorMessage.USER_ROLE_NOT_FOUND.getMessage()));



    User newUser = userMapper.fromDTO(request);
    newUser.setPassword(passwordEncoder.encode(request.getPassword()));
        Set<Role> roles = new HashSet<>();
        roles.add(userRole);
        newUser.setRoles(roles);
        userRepository.save(newUser);

         RefreshToken refreshToken = refreshTokenService.generateOrUpdateRefreshToken(newUser);
         String token = jwtTokenProvider.generateToken(newUser);
         UserProfileDTO userProfileDTO = userMapper.toUserProfileDTO(newUser, token, refreshToken.getToken());
         userProfileDTO.setToken(token);

         return IamResponse.createSuccessfulWithNewToken(userProfileDTO);



    }


}
