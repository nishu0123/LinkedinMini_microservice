package com.Nishant.LinkedIn_Mini.PostService.Config;

import feign.codec.Encoder;
import feign.form.spring.SpringFormEncoder;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.http.converter.autoconfigure.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.FeignHttpMessageConverters;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignMultipartConfig {

    private final ObjectProvider<FeignHttpMessageConverters> messageConverters;

    public FeignMultipartConfig(ObjectProvider<FeignHttpMessageConverters> messageConverters) {
        this.messageConverters = messageConverters;
    }

    @Bean
    public Encoder feignFormEncoder() {
        // We wrap the standard SpringEncoder inside the SpringFormEncoder
        // This allows the client to handle BOTH JSON and Multipart files
        return new SpringFormEncoder(new SpringEncoder(messageConverters));
    }
    @Bean
    public feign.Logger.Level feignLoggerLevel() {
        return feign.Logger.Level.FULL; // Logs the full body and headers
    }
}