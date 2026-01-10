package com.Nishant.LinkedIn_Mini.ConnectionService.Config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConnectionConfig {
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
