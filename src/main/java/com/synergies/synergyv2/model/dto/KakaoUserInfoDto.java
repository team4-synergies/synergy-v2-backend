package com.synergies.synergyv2.model.dto;

import com.synergies.synergyv2.auth.Role;
import com.synergies.synergyv2.model.entity.UserEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class KakaoUserInfoDto {

    private String userKakaoId;

    private String userNickname;

    private String email;

    private String profileImage;

    private String githubEmail;

    public UserEntity toUserEntity(){
        return UserEntity.builder()
                .kakaoId(userKakaoId)
                .name(userNickname)
                .email(email)
                .role(Role.STUDENT)
                .profileImage(profileImage)
                .build();
    }

    @Builder
    public KakaoUserInfoDto(String userKakaoId, String userNickname, String email, String profileImage){
        this.userKakaoId = userKakaoId;
        this.userNickname = userNickname;
        this.email = email;
        this.profileImage = profileImage;
    }
}
