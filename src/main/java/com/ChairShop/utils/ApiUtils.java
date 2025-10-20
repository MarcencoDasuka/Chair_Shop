package com.ChairShop.utils;

import com.ChairShop.model.constants.ApiConstants;
import jakarta.servlet.http.HttpServletResponse;

import java.net.http.HttpHeaders;
import jakarta.servlet.http.Cookie;

public class ApiUtils {
    public static String getMethodName() {
        try {
            return new Throwable().getStackTrace()[1].getMethodName();
        } catch (Exception cause) {
            return ApiConstants.UNDEFINED;
        }
    }

    public static Cookie createAuthCookie(String value) {
        Cookie authCookie = new Cookie("AuthToken", value);
        authCookie.setHttpOnly(true);
        authCookie.setSecure(true);
        authCookie.setPath("/");
        authCookie.setMaxAge(300);

        return authCookie;
    }

}
