package com.ChairShop.security.validation;

import com.ChairShop.model.constants.ApiErrorMessage;
import com.ChairShop.model.enteties.Role;
import com.ChairShop.model.enteties.User;
import com.ChairShop.model.exception.DataExistException;
import com.ChairShop.model.exception.InvalidPasswordException;
import com.ChairShop.model.exception.NotFoundException;
import com.ChairShop.repositories.UserRepository;
import com.ChairShop.service.model.IamServiceUserRole;
import com.ChairShop.utils.ApiUtils;
import com.ChairShop.utils.PasswordUtils;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.hibernate.ScrollableResults;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.CompositeTypeRegistration;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.stereotype.Component;

import java.nio.file.AccessDeniedException;

@Component
@RequiredArgsConstructor
public class AccessValidator {

    private final UserRepository userRepository;
    private final ApiUtils apiUtils;

    public void validateNewUser(String username, String email, String password, String confirmPassword) {
        userRepository.findByUsername(username).ifPresent(existingUser -> {
            throw new DataExistException(ApiErrorMessage.USER_WITH_NAME_ALREADY_EXISTS.getMessage(username));
        });

        userRepository.findUserByEmail(email).ifPresent(existingUser -> {
            throw new DataExistException(ApiErrorMessage.USER_WITH_EMAIL_ALREADY_EXISTS.getMessage(email));
        });


        if (!password.equals(confirmPassword)) {
            throw new InvalidPasswordException(ApiErrorMessage.MISMATCH_PASSWORDS.getMessage());
        }

        if (PasswordUtils.isNotValidPassword(password)) {
            throw new InvalidPasswordException(ApiErrorMessage.INVALID_PASSWORD.getMessage());
        }
    }


    public boolean isAdminOrSuperAdmin(Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(ApiErrorMessage.USER_WITH_ID_NOT_FOUND.getMessage(userId)));

        return user.getRoles().stream()
                .map(role -> IamServiceUserRole.fromName(role.getName()))
                .anyMatch(role -> role == IamServiceUserRole.ADMIN || role == IamServiceUserRole.SUPER_ADMIN);
    }

    @SneakyThrows
    public void validateAdminOrOwnerAccess(Integer ownerUserId) {
        Integer currentUserId = apiUtils.getUserIdFromAuthentication();

        if(!currentUserId.equals(ownerUserId) &&
                !isAdminOrSuperAdmin(currentUserId)) {
            throw new AccessDeniedException(ApiErrorMessage.HAVE_NO_ACCESS.getMessage());
        }
    }



}
