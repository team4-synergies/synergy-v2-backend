package com.synergies.synergyv2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableJpaAuditing
@EnableScheduling
@SpringBootApplication
public class SynergyV2Application {

    public static void main(String[] args) {
        SpringApplication.run(SynergyV2Application.class, args);
    }

}
