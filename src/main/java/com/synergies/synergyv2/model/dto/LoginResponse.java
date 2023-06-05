package com.synergies.synergyv2.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginResponse {
    String accessToken;
    String userRole;
}
