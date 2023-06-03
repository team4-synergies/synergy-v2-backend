package com.synergies.synergyv2.model.dto;

import com.synergies.synergyv2.model.entity.UserEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
public class KakaoUserInfoDto {

    private String userKakaoId;

    private String userNickname;

    private String email;

    public UserEntity toUserEntity(){
        return UserEntity.builder()
                .kakaoId(userKakaoId)
                .name(userNickname)
                .email(email)
                .build();
    }
    public KakaoUserInfoDto(String userKakaoId, String userNickname, String email){
        this.userKakaoId = userKakaoId;
        this.userNickname = userNickname;
        this.email = email;
    }
}
