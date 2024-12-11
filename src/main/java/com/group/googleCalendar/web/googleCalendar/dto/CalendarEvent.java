package com.group.googleCalendar.web.googleCalendar.dto;

import lombok.*;

import java.time.LocalDateTime;

@Builder
public record CalendarEvent(
        String title,
        LocalDateTime startAt,
        LocalDateTime endAt,
        Boolean isHoliday,
        Integer dayOfWeekIndex,
        String htmlLink,
        Boolean isAllDay
        ) {

}
