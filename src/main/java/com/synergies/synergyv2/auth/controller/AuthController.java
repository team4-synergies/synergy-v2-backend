package com.synergies.synergyv2.auth.controller;

import com.synergies.synergyv2.auth.JwtTokenProvider;
import com.synergies.synergyv2.auth.service.LoginService;
import com.synergies.synergyv2.auth.service.UserService;
import com.synergies.synergyv2.common.response.CommonResponse;
import com.synergies.synergyv2.common.response.code.CommonCode;
import com.synergies.synergyv2.model.dto.KakaoTokenInfoDto;
import com.synergies.synergyv2.model.dto.KakaoUserInfoDto;
import com.synergies.synergyv2.model.dto.LoginResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
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
    public ResponseEntity<CommonResponse> kakaoLogin(@RequestParam String code) throws IOException {
        KakaoTokenInfoDto kakaoTokenInfoDto = loginService.getToken(code);
        KakaoUserInfoDto kakaoUserInfoDto = loginService.getKakaoUserInfo(kakaoTokenInfoDto.getAccessToken());

        userService.createUser(kakaoUserInfoDto);

        loginService.expiredKakaoAccessToken(kakaoTokenInfoDto.getAccessToken());
        String curUserRole = userService.getUserRole(kakaoUserInfoDto.getUserKakaoId()).name();
        String accessToken = jwtTokenProvider.createToken(String.valueOf(kakaoUserInfoDto.getUserKakaoId()), List.of(curUserRole));
        return ResponseEntity.ok(CommonResponse.toResponse(CommonCode.OK, new LoginResponse(accessToken, curUserRole)));
    }
}
