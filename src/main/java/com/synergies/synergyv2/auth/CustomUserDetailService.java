package com.synergies.synergyv2.auth;

import com.synergies.synergyv2.model.entity.UserEntity;
import com.synergies.synergyv2.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String kakaoId) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByKakaoId(kakaoId).orElseThrow(() -> new UsernameNotFoundException("찾을 수 없는 사용자입니다."));
        return user.toCustomUserDetails();
    }
}
