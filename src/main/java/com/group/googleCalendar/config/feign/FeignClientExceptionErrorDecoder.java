package com.group.googleCalendar.config.feign;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.group.googleCalendar.exception.FeignCustomException;
import com.group.googleCalendar.web.common.dto.CustomFeignErrorForm;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;

@Slf4j
public class FeignClientExceptionErrorDecoder implements ErrorDecoder {

    private ObjectMapper objectMapper = new ObjectMapper();

    public FeignCustomException decode(String methodKey, Response response) {

        CustomFeignErrorForm errorResponse = null;
        try (InputStream bodyIs = response.body().asInputStream()) {
            errorResponse = objectMapper.readValue(bodyIs, CustomFeignErrorForm.class);
            log.warn("[{}] {}", methodKey, errorResponse.getMessage());
            return new FeignCustomException(response.status(), errorResponse.getMessage(), response.headers(), errorResponse);
        } catch (IOException e) {
            log.error("[{}] Error Deserializing response body from failed feign request response.", methodKey);
            return new FeignCustomException(response.status(), "Error Deserializing response body from failed feign request response.", response.headers(), errorResponse);
        }
    }
}