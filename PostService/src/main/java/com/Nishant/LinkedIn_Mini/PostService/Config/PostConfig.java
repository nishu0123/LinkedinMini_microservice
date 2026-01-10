package com.Nishant.LinkedIn_Mini.PostService.Config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PostConfig {
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
