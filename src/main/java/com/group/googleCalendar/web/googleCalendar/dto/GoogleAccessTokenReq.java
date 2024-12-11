package com.group.googleCalendar.web.googleCalendar.dto;

import lombok.Builder;

@Builder
public record GoogleAccessTokenReq(
        String grant_type,
        String assertion
) {
}
