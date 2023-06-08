package com.synergies.synergyv2.service;

import com.synergies.synergyv2.model.entity.UserEntity;
import com.synergies.synergyv2.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MyPageService {

    private final UserRepository userRepository;

    @Transactional
    public void updateUserGitNickname(UUID userId, String githubNickname){
        UserEntity user = userRepository.findById(userId).orElseThrow(()->new UsernameNotFoundException("존재하지 않는 유저입니다"));
        user.updateGithubNickname(githubNickname);
    }
}