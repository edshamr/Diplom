package com.productShop.inventarization.service;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;

@Component
public class ConsumerTokenUtil {

    public static String getUserToken(HttpSession session) {
        return session.getAttribute("userToken").toString();
    }
}
