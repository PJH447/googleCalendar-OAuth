package com.group.googleCalendar.web.googleCalendar.service;

import com.google.api.client.util.DateTime;
import com.group.googleCalendar.web.googleCalendar.dto.CalendarResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Slf4j
@Component
@RequiredArgsConstructor
public class GoogleCalendarServiceImpl implements GoogleCalendarService {

    @Value("${google.calendar.calendar-id}")
    private String calendarId;

    @Value("${google.calendar.holiday-calendar-id}")
    private String holidayCalendarId;

    private final GoogleCalendarFeignService googleCalendarFeignService;

    @Override
    public CalendarResponse getEvent(LocalDate localDate) {
        GoogleCalendarDateTimeRange result = getResult(localDate);
        return googleCalendarFeignService.getCalendarEvent(holidayCalendarId, result.startDateTime(), result.endDateTime());
    }

    @Override
    public CalendarResponse getHolidayEvent(LocalDate localDate) {
        GoogleCalendarDateTimeRange result = getResult(localDate);
        return googleCalendarFeignService.getCalendarEvent(holidayCalendarId, result.startDateTime(), result.endDateTime());
    }

    private static GoogleCalendarDateTimeRange getResult(LocalDate localDate) {
        int dayOfWeekValue = localDate.getDayOfWeek().getValue() % 7;
        LocalDate startDate = localDate.minusDays(dayOfWeekValue);

        LocalDateTime startAt = startDate.atTime(0, 0, 0);
        DateTime startDateTime = new DateTime(startAt.toInstant(ZoneOffset.UTC).toEpochMilli());

        LocalDateTime endAt = startDate.plusDays(6).atTime(23, 59, 59);
        DateTime endDateTime = new DateTime(endAt.toInstant(ZoneOffset.UTC).toEpochMilli());
        return new GoogleCalendarDateTimeRange(startDateTime, endDateTime);
    }

    private record GoogleCalendarDateTimeRange(
            DateTime startDateTime,
            DateTime endDateTime
    ) {
    }

}
