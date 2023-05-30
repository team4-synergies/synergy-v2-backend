package com.synergies.synergyv2.common.response.code;

import org.springframework.http.HttpStatus;

public interface Code {
    String name();
    HttpStatus getCode();
    String getMessage();
}