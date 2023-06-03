package com.synergies.synergyv2.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@RequiredArgsConstructor
public class SecurityRole implements GrantedAuthority {

    private final Role role;

    public static final String STUDENT = "ROLE_STUDENT";
    public static final String ADMIN = "ROLE_ADMIN";

    @Override
    public String getAuthority() {
        return "ROLE_" + role.name();
    }
}
