package com.group.googleCalendar.config.feign;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Bean;

public class DefaultFeignConfig implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate requestTemplate) {

    }

    @Bean
    public FeignClientExceptionErrorDecoder commonFeignErrorDecoder() {
        return new FeignClientExceptionErrorDecoder();
    }
}
