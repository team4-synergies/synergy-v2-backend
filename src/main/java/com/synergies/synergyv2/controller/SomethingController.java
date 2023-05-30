package com.synergies.synergyv2.controller;

import com.synergies.synergyv2.common.response.CommonResponse;
import com.synergies.synergyv2.common.response.code.CommonCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v2")
public class SomethingController {
    public ResponseEntity<CommonResponse> test() {
        // CommonResponse commonResponse = CommonResponse.toResponse(CommonCode.OK, "data");
        // return new ResponseEntity<>(commonResponse, HttpStatus.OK);

        return ResponseEntity.ok(CommonResponse.toResponse(CommonCode.OK, "data"));
    }
}
