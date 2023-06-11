package com.synergies.synergyv2.controller;

import com.synergies.synergyv2.auth.CustomUserDetails;
import com.synergies.synergyv2.common.response.CommonResponse;
import com.synergies.synergyv2.common.response.code.CommonCode;
import com.synergies.synergyv2.model.dto.MyPageResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v2/mypage")
@Slf4j
public class MyPageController {

    @GetMapping
    public ResponseEntity<CommonResponse> getMyPageInfo(@AuthenticationPrincipal CustomUserDetails userDetails){
        log.info("마이페이지 정보 조회 : " + userDetails.getName());
        return ResponseEntity.ok(CommonResponse.toResponse(CommonCode.OK, MyPageResponseDto.builder()
                .name(userDetails.getName())
                .email(userDetails.getEmail())
                .profileImage(userDetails.getProfileImage())
                .build()));
    }
}
