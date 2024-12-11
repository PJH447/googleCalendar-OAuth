package com.group.googleCalendar.web.googleCalendar.dto;

public record ItemResponse(
        String summary,
        String eventType,
        String htmlLink,
        GoogleCalendarDateDto start,
        GoogleCalendarDateDto end
) {
}
