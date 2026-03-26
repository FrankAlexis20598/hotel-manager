package com.devfrank.hotelmanager.shared.config;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuditorProvider implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        //TODO Aquí puedes devolver el usuario autenticado, por ejemplo de Spring Security
        return Optional.of("SYSTEM");
    }
}