package com.group.googleCalendar.web.googleCalendar.dto;

public record GoogleAccessTokenRes(
        String access_token,
        Long expires_in,
        String token_type
) {
}
