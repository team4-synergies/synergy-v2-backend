package com.synergies.synergyv2.common.response.code;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum AuthErrorCode implements Code {
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "토큰이 유효하지 않습니다."), //401
    NOT_EXIST_USER(HttpStatus.UNAUTHORIZED, "이 uuid 에 해당하는 사용자가 존재하지 않습니다."), //401
    NO_PERMISSION(HttpStatus.FORBIDDEN, "권한이 없습니다."),
    ;

    private HttpStatus code;
    private String message;

    AuthErrorCode(HttpStatus code, String message) {
        this.code = code;
        this.message = message;
    }
}
