package com.synergies.synergyv2.common.response.exception;

import com.synergies.synergyv2.common.response.code.Code;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class DefaultException extends RuntimeException {
    private final Code code;
}