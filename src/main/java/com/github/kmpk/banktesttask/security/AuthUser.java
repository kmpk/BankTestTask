package com.github.kmpk.banktesttask.security;

import com.github.kmpk.banktesttask.model.User;
import lombok.Getter;
import org.springframework.lang.NonNull;

import java.util.Collections;

@Getter
public class AuthUser extends org.springframework.security.core.userdetails.User {
    private final User user;

    public AuthUser(@NonNull User user) {
        super(user.getLogin(), user.getPassword(), Collections.emptyList());
        this.user = user;
    }

    public int id() {
        return user.getId();
    }

    @Override
    public String toString() {
        return "AuthUser:" + user.getId() + '[' + user.getLogin() + ']';
    }
}
