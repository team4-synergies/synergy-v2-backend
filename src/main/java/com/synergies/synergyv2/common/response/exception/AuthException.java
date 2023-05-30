package com.synergies.synergyv2.common.response.exception;

import com.synergies.synergyv2.common.response.code.Code;

public class AuthException extends DefaultException {
    public AuthException(Code code) {
        super(code);
    }
}
