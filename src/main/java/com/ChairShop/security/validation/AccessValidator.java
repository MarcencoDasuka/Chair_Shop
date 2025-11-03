package com.ChairShop.security.validation;

import com.ChairShop.model.constants.ApiErrorMessage;
import com.ChairShop.model.enteties.Role;
import com.ChairShop.model.exception.DataExistException;
import com.ChairShop.model.exception.InvalidPasswordException;
import com.ChairShop.model.exception.NotFoundException;
import com.ChairShop.repositories.UserRepository;
import com.ChairShop.service.model.IamServiceUserRole;
import com.ChairShop.utils.PasswordUtils;
import lombok.RequiredArgsConstructor;
import org.hibernate.ScrollableResults;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.CompositeTypeRegistration;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AccessValidator {
    private final UserRepository userRepository;

    public void validateNewUser(String username, String email, String password, String confirmPassword){
        userRepository.findByUsername(username).ifPresent(existingUser->{
            throw new DataExistException(ApiErrorMessage.USER_WITH_NAME_ALREADY_EXISTS.getMessage(username));
        });
        userRepository.findUserByEmail(email).ifPresent(existingEmail-> {
            throw new DataExistException(ApiErrorMessage.USER_WITH_EMAIL_ALREADY_EXISTS.getMessage(email));
        });
        
        if (!password.equals(confirmPassword)){
            throw new InvalidDataAccessApiUsageException(ApiErrorMessage.MISMATCH_PASSWORDS.getMessage());
        }

        if (PasswordUtils.isNotValidPassword(password)) {
            throw new InvalidPasswordException(ApiErrorMessage.INVALID_PASSWORD.getMessage());
        }
    }

}
