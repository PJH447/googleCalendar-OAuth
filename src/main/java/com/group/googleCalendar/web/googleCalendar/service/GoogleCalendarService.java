package com.group.googleCalendar.web.googleCalendar.service;

import com.group.googleCalendar.web.googleCalendar.dto.CalendarResponse;

import java.time.LocalDate;

public interface GoogleCalendarService {

    CalendarResponse getEvent(LocalDate localDate);

    CalendarResponse getHolidayEvent(LocalDate localDate);

}
