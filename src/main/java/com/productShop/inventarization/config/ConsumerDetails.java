package com.productShop.inventarization.config;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class ConsumerDetails extends User implements UserDetails {
    private String key;

    public ConsumerDetails(String username, String password, String key, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
