package com.synergies.synergyv2.auth;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class CustomUserDetails implements UserDetails {
    @Getter
    private final UUID userId;
    @Getter
    private final String kakaoId;
    @Getter
    private final String name;

    @Getter
    private final String email;

    private String password;

    private final List<String> roles;

    @Getter
    private String profileImage;

    public CustomUserDetails(UUID userId, String kakaoId, String nickName, String email, String role, String profileImage) {
        this.userId = userId;
        this.kakaoId = kakaoId;
        this.name = nickName;
        this.email = email;
        this.roles = List.of(role);
        this.profileImage = profileImage;

    }


    @Override   //사용자의 권한 목록 리턴
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return kakaoId;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
