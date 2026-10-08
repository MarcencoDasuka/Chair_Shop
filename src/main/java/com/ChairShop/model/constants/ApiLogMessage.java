package com.ChairShop.model.constants;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum ApiLogMessage {
    CHAIR_INFO_BY_ID ("Receeving chair with id: {}"),
    NAME_OF_CURRENT_METHOD("Current method: {}"),
    ;

    private final String value;

}
