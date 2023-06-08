package com.synergies.synergyv2.controller;

import com.synergies.synergyv2.auth.CustomUserDetails;
import com.synergies.synergyv2.common.response.CommonResponse;
import com.synergies.synergyv2.common.response.code.CommonCode;
import com.synergies.synergyv2.model.dto.MyPageResponseDto;
import com.synergies.synergyv2.service.MyPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/mypage")
public class MyPageController {

    private final MyPageService myPageService;

    @GetMapping
    public ResponseEntity<CommonResponse> getMyPageInfo(@AuthenticationPrincipal CustomUserDetails userDetails){
        return ResponseEntity.ok(CommonResponse.toResponse(CommonCode.OK, MyPageResponseDto.builder()
                .name(userDetails.getName())
                .email(userDetails.getEmail())
                .profileImage(userDetails.getProfileImage())
                .githubEmail(userDetails.getGithubEmail())
                .build()));
    }

    @PutMapping
    public ResponseEntity<CommonResponse> updateGithubNickname(@RequestBody Map<String, String> githubEmail, @AuthenticationPrincipal CustomUserDetails userDetails){
        myPageService.updateUserGitNickname(userDetails.getUserId(), githubEmail.get("githubEmail"));
        return ResponseEntity.ok(CommonResponse.toResponse(CommonCode.CREATED));
    }
}
