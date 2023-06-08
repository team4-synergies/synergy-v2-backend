package com.synergies.synergyv2.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class MyPageResponseDto {
    String name;

    String email;

    String profileImage;

    String githubEmail;
}
