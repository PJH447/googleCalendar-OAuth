package com.group.googleCalendar.exception;

import com.group.googleCalendar.web.common.dto.CustomFeignErrorForm;

import java.util.Collection;
import java.util.Map;

public class FeignCustomException extends RuntimeException {

    private final int status;
    private final String errorMessage;
    private final Map<String, Collection<String>> headers;
    private final CustomFeignErrorForm errorForm;

    public FeignCustomException(int status, String errorMessage, Map<String, Collection<String>> headers, CustomFeignErrorForm errorForm) {
        super(errorMessage);
        this.status = status;
        this.errorMessage = errorMessage;
        this.headers = headers;
        this.errorForm = errorForm;
    }

    public int getStatus() {
        return status;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public Map<String, Collection<String>> getHeaders() {
        return headers;
    }

    public CustomFeignErrorForm getErrorForm() {
        return errorForm;
    }
}