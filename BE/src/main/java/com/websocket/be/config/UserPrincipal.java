package com.websocket.be.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.security.Principal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserPrincipal implements Principal {
    private String name;

    @Override
    public String getName() {
        return name;
    }
}
