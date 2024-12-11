package com.group.googleCalendar.web.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CustomFeignErrorForm {

    private Boolean success;
    private String message;
    private String title;
    private String errKey;
    private String field;
    private String[] constraintViolationFields;
    private String type;

}