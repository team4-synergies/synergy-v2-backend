package com.synergies.synergyv2.auth.service;

import com.synergies.synergyv2.auth.Role;
import com.synergies.synergyv2.model.dto.KakaoUserInfoDto;
import com.synergies.synergyv2.model.entity.UserEntity;
import com.synergies.synergyv2.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public void createUser(KakaoUserInfoDto kakaoUserInfoDto) {
        UserEntity user;
        if (!isDupulicateUser(kakaoUserInfoDto)) {
            user = kakaoUserInfoDto.toUserEntity();
        }
        else{
            user = userRepository.findByKakaoId(kakaoUserInfoDto.getUserKakaoId()).orElse(kakaoUserInfoDto.toUserEntity());
            user.update(kakaoUserInfoDto.getUserNickname(), kakaoUserInfoDto.getEmail());
        }
        userRepository.save(user);
    }

    private boolean isDupulicateUser(KakaoUserInfoDto kakaoUserInfoDto) {
        return userRepository.findByKakaoId(kakaoUserInfoDto.getUserKakaoId()).isPresent();
    }
    public Role getUserRole(String kakaoId) {
        UserEntity user = userRepository.findByKakaoId(kakaoId).orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 사용자입니다."));
        return user.getRole();
    }


}
