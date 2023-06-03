package com.synergies.synergyv2.auth.service;

import com.synergies.synergyv2.model.dto.KakaoUserInfoDto;
import com.synergies.synergyv2.model.entity.UserEntity;
import com.synergies.synergyv2.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public void createUser(KakaoUserInfoDto kakaoUserInfoDto) {
        System.out.println(isDupulicateUser(kakaoUserInfoDto));
        System.out.println(kakaoUserInfoDto);
        if (!isDupulicateUser(kakaoUserInfoDto)) {
            UserEntity userEntity = kakaoUserInfoDto.toUserEntity();
            userRepository.save(userEntity);
        }
    }

    private boolean isDupulicateUser(KakaoUserInfoDto kakaoUserInfoDto) {
        return userRepository.findByKakaoId(kakaoUserInfoDto.getUserKakaoId()).isPresent();
    }
}
