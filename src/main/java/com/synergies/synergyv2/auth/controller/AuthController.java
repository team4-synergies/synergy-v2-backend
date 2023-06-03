package com.synergies.synergyv2.auth.controller;
import com.synergies.synergyv2.auth.JwtTokenProvider;
import com.synergies.synergyv2.auth.Role;
import com.synergies.synergyv2.auth.service.LoginService;
import com.synergies.synergyv2.auth.service.UserService;
import com.synergies.synergyv2.model.dto.KakaoTokenInfoDto;
import com.synergies.synergyv2.model.dto.KakaoUserInfoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:8081")
@RequestMapping("/kakao")
public class AuthController {

    private final LoginService loginService;

    private final UserService userService;

    private final JwtTokenProvider jwtTokenProvider;

    @GetMapping("/oauth/redirect")
    public String sendRedirectUrl(){
        return loginService.getAuthorizationUrl();
    }

    @GetMapping("/login")
    public String kakaoLogin(@RequestParam String code) throws IOException {
        KakaoTokenInfoDto kakaoTokenInfoDto = loginService.getToken(code);
        KakaoUserInfoDto kakaoUserInfoDto = loginService.getKakaoUserInfo(kakaoTokenInfoDto.getAccessToken());

        userService.createUser(kakaoUserInfoDto);

        String jwt = jwtTokenProvider.createToken(String.valueOf(kakaoUserInfoDto.getUserKakaoId()), List.of(Role.STUDENT.name()));

        return jwt;
    }
}
