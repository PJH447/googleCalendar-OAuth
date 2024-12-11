package com.group.googleCalendar.web.googleCalendar.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record GoogleCalendarDateDto(
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
        LocalDateTime dateTime,
        String timeZone,
        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate date
) {
}
