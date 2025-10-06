package com.ChairShop.model.constants;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor (access = AccessLevel.PRIVATE)
public enum ApiErrorMessage {
    CHAIR_WITH_ID_NOT_FOUND("Chair with Id %s was not found"),
    ;

    private final String message;

    public String getMessage(Object... arg){
        return String.format(message, arg);
    }

}
