package com.ChairShop.service.impl;

import com.ChairShop.mapper.UserMapper;
import com.ChairShop.model.constants.ApiErrorMessage;
import com.ChairShop.model.dto.User.FullUserDTO;
import com.ChairShop.model.dto.User.UserDTO;
import com.ChairShop.model.enteties.Role;
import com.ChairShop.model.enteties.ShoppingCart;
import com.ChairShop.model.enteties.User;
import com.ChairShop.model.exception.DataExistException;
import com.ChairShop.model.exception.NotFoundException;
import com.ChairShop.model.request.user.NewUserRequest;
import com.ChairShop.model.response.IamResponse;
import com.ChairShop.repositories.RoleRepository;
import com.ChairShop.repositories.ShoppingCartRepository;
import com.ChairShop.repositories.UserRepository;
import com.ChairShop.service.UserService;
import com.ChairShop.service.model.IamServiceUserRole;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ShoppingCartRepository shoppingCartRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @Override
    public IamResponse<FullUserDTO> getFullUserById(@NotNull Integer id) {
        User user = userRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new NotFoundException(ApiErrorMessage.USER_WITH_ID_NOT_FOUND.getMessage(id)));

        FullUserDTO fullUserDTO = userMapper.toFullDTO(user);
        return IamResponse.createSuccessful(fullUserDTO);
    }

    @Override
    public IamResponse<UserDTO> createUser(@NotNull NewUserRequest newUserRequest) {
        if(userRepository.existsByUsername(newUserRequest.getUsername())) {
            throw new DataExistException(ApiErrorMessage.USER_WITH_NAME_ALREADY_EXISTS.getMessage(newUserRequest.getUsername()));
        }

        if(userRepository.existsByEmail(newUserRequest.getEmail())) {
            throw new DataExistException(ApiErrorMessage.USER_WITH_EMAIL_ALREADY_EXISTS.getMessage(newUserRequest.getEmail()));
        }



        User user = userMapper.createUser(newUserRequest);
        user.setLast_login(LocalDateTime.now());

        ShoppingCart cart = new ShoppingCart();
        cart.setUser(user);
        user.setShoppingCart(cart);
        user.setPassword(passwordEncoder.encode(newUserRequest.getPassword()));

        Role role = roleRepository.findByName(IamServiceUserRole.USER.getRole())
                        .orElseThrow(() -> new NotFoundException(ApiErrorMessage.ROLE_WITH_NAME_NOT_FOUND.getMessage(IamServiceUserRole.USER.getRole())));

        Set<Role> roles = new HashSet<>();
        roles.add(role);
        user.setRoles(roles);
        userRepository.save(user);

        UserDTO userDTO = userMapper.toDTO(user);

        return IamResponse.createSuccessful(userDTO);

    }



    @Override
    public IamResponse<UserDTO> getUserById(@NotNull Integer id) {
        User user = userRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new NotFoundException(ApiErrorMessage.USER_WITH_ID_NOT_FOUND.getMessage(id)));

        UserDTO userDTO = userMapper.toDTO(user);
        return IamResponse.createSuccessful(userDTO);
    }


    @Override
    public void softDeleteUserById(Integer id) {
        User user = userRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new NotFoundException(ApiErrorMessage.USER_WITH_ID_NOT_FOUND.getMessage(id)));

        user.setDeleted(true);
        userRepository.save(user);
    }



    @Override
    public IamResponse<UserDTO> updateUserById(Integer id, NewUserRequest newUserRequest) {
        User user = userRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new NotFoundException(ApiErrorMessage.USER_WITH_ID_NOT_FOUND.getMessage(id)));

        if(newUserRequest.getEmail() != null && !newUserRequest.getEmail().isBlank()) {
            if(userRepository.existsByEmailAndIdNot(newUserRequest.getEmail(), id)){
                throw new DataExistException(ApiErrorMessage.USER_WITH_EMAIL_ALREADY_EXISTS.getMessage());
            }
            user.setEmail(newUserRequest.getEmail());
        }

        if(newUserRequest.getUsername() != null && !newUserRequest.getUsername().isBlank()) {
            if(userRepository.existsByUsernameAndIdNot(newUserRequest.getUsername(), id)){
                throw new DataExistException(ApiErrorMessage.USER_WITH_NAME_ALREADY_EXISTS.getMessage());
            }
            user.setUsername(newUserRequest.getUsername());
        }

        if(newUserRequest.getPassword() != null && !newUserRequest.getPassword().isEmpty()) {
            user.setPassword(newUserRequest.getPassword());
        }

        UserDTO userDTO = userMapper.toDTO(user);
        userRepository.save(user);


        return IamResponse.createSuccessful(userDTO);
    }

    @Override
    public UserDetails loadUserByUsername(String Email) throws UsernameNotFoundException {
        return getUserDetails(Email, userRepository);
    }

    static  UserDetails getUserDetails(String email, UserRepository userRepository) {
        User user = userRepository.findUserByEmail(email)
                .orElseThrow(() -> new NotFoundException(ApiErrorMessage.USER_WITH_EMAIL_NOT_FOUND.getMessage()));

        user.setLast_login(LocalDateTime.now());
        userRepository.save(user);

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                user.getRoles().stream()
                        .map(role -> new SimpleGrantedAuthority(role.getName()))
                        .collect(Collectors.toList())
        );
    }
}
