package com.ChairShop.controller;

import com.ChairShop.model.constants.ApiLogMessage;
import com.ChairShop.model.dto.User.FullUserDTO;
import com.ChairShop.model.dto.User.UserDTO;
import com.ChairShop.model.request.user.NewUserRequest;
import com.ChairShop.model.response.IamResponse;
import com.ChairShop.service.UserService;
import com.ChairShop.utils.ApiUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;


    @GetMapping("/full/{id}")
    public ResponseEntity<IamResponse<FullUserDTO>> getFullUserById(@PathVariable("id") Integer id) {
        log.trace(ApiLogMessage.NAME_OF_CURRENT_METHOD.getValue(), ApiUtils.getMethodName());

        IamResponse<FullUserDTO> response = userService.getFullUserById(id);
        return ResponseEntity.ok(response);
    }


    @GetMapping("/{id}")
    public ResponseEntity<IamResponse<UserDTO>> getUserById(
            @PathVariable(name = "id") Integer id){
        log.trace(ApiLogMessage.NAME_OF_CURRENT_METHOD.getValue(), ApiUtils.getMethodName());

        IamResponse<UserDTO> response = userService.getUserById(id);
        return ResponseEntity.ok(response);
    }


    @PostMapping("/create")
    public ResponseEntity<IamResponse<UserDTO>> createUser(
            @RequestBody @Valid NewUserRequest newUserRequest) {
        log.trace(ApiLogMessage.NAME_OF_CURRENT_METHOD.getValue(), ApiUtils.getMethodName());

        IamResponse<UserDTO> response = userService.createUser(newUserRequest);
        return ResponseEntity.ok(response);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> softDeleteUserById(
            @PathVariable(name = "id") Integer id){
        log.trace(ApiLogMessage.NAME_OF_CURRENT_METHOD.getValue(), ApiUtils.getMethodName());

        userService.softDeleteUserById(id);
        return ResponseEntity.noContent().build();
    }


    @PutMapping("/{id}")
    public ResponseEntity<IamResponse<UserDTO>> updateUserById(
            @PathVariable(name = "id") Integer id,
            @RequestBody @Valid NewUserRequest request){
        log.trace(ApiLogMessage.NAME_OF_CURRENT_METHOD.getValue(), ApiUtils.getMethodName());

        IamResponse<UserDTO> response = userService.updateUserById(id, request);
        return ResponseEntity.ok(response);
    }

}
