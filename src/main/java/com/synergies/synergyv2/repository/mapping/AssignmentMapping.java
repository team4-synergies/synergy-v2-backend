package com.synergies.synergyv2.repository.mapping;

import java.time.LocalDateTime;

public interface AssignmentMapping {
    Integer getId();
    String getTitle();
    LocalDateTime getRegDate();
    LocalDateTime getUpdateDate();

}
