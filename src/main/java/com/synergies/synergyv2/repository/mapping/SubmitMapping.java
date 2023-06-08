package com.synergies.synergyv2.repository.mapping;

import java.time.LocalDateTime;
import java.util.UUID;

public interface SubmitMapping {
    Integer getId();
    LocalDateTime getUpdateDate();
    String getNickname();
    UUID getUserId();
}
