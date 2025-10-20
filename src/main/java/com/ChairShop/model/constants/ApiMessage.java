package com.ChairShop.model.constants;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum ApiMessage {
    TOKEN_CREATED_OR_UPDATED("User's token has been created pr updated");


    private final String message;
}
