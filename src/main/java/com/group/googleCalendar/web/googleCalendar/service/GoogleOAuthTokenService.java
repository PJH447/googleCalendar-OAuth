package com.group.googleCalendar.web.googleCalendar.service;

import com.group.googleCalendar.config.feign.DefaultFeignConfig;
import com.group.googleCalendar.web.googleCalendar.dto.GoogleAccessTokenReq;
import com.group.googleCalendar.web.googleCalendar.dto.GoogleAccessTokenRes;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@FeignClient(name = "googleOAuthTokenService", url = "https://oauth2.googleapis.com", configuration = {DefaultFeignConfig.class})
public interface GoogleOAuthTokenService {

    @PostMapping(value = "/token", produces = "application/json", consumes = "application/json")
    GoogleAccessTokenRes getGoogleAccessToken(@RequestBody GoogleAccessTokenReq request);

}
