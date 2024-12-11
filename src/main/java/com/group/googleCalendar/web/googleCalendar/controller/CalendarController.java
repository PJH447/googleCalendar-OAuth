package com.group.googleCalendar.web.googleCalendar.controller;

import com.group.googleCalendar.web.googleCalendar.dto.CalendarResponse;
import com.group.googleCalendar.web.googleCalendar.service.GoogleCalendarService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
public class CalendarController {

    private final GoogleCalendarService googleCalendarService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/api/calendar")
    public CalendarResponse getCalendar(
            @RequestParam(value = "localDate", defaultValue = "#{T(java.time.LocalDate).now()}") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate localDate
    ) {
        return googleCalendarService.getEvent(localDate);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/api/calendar/holiday")
    public CalendarResponse getHolidayCalendar(
            @RequestParam(value = "localDate", defaultValue = "#{T(java.time.LocalDate).now()}") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate localDate
    ) {
        return googleCalendarService.getHolidayEvent(localDate);
    }

}
