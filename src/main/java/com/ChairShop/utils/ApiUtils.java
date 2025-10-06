package com.ChairShop.utils;

import com.ChairShop.model.constants.ApiConstants;

public class ApiUtils {
    public static String getMethodName() {
        try {
            return new Throwable().getStackTrace()[1].getMethodName();
        } catch (Exception cause) {
            return ApiConstants.UNDEFINED;
        }
    }
}
