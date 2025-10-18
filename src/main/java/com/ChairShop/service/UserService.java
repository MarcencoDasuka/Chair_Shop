package com.ChairShop.service;

import com.ChairShop.model.dto.User.FullUserDTO;
import com.ChairShop.model.dto.User.UserDTO;
import com.ChairShop.model.request.user.NewUserRequest;
import com.ChairShop.model.response.IamResponse;
import jakarta.validation.constraints.NotNull;

public interface UserService {

    IamResponse<FullUserDTO> getFullUserById(@NotNull Integer id);
    IamResponse<UserDTO> createUser(@NotNull NewUserRequest newUserRequest);
    IamResponse<UserDTO> getUserById(@NotNull Integer id);
    void softDeleteUserById(@NotNull Integer id);
    IamResponse<UserDTO> updateUserById(@NotNull Integer id, @NotNull NewUserRequest newUserRequest);
}
