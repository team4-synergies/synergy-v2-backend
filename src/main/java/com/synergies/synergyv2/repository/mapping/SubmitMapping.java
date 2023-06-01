package com.synergies.synergyv2.repository.mapping;

import java.time.LocalDateTime;

public interface SubmitMapping {
    Integer getId();
    LocalDateTime getUpdateDate();
    String getNickname();
    Integer getUserId();
}
