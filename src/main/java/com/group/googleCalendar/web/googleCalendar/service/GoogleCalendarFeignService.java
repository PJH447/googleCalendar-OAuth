package com.group.googleCalendar.web.googleCalendar.service;

import com.google.api.client.util.DateTime;
import com.group.googleCalendar.config.feign.GoogleCalendarFeignConfig;
import com.group.googleCalendar.web.googleCalendar.dto.CalendarResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "googleCalendarFeignService", url = "https://www.googleapis.com/calendar", configuration = {GoogleCalendarFeignConfig.class})
public interface GoogleCalendarFeignService {

    @GetMapping(value = "/v3/calendars/{calendarId}/events", produces = "application/json", consumes = "application/json")
    CalendarResponse getCalendarEvent(
            @PathVariable(value = "calendarId") String calendarId,
            @RequestParam("timeMin") DateTime timeMin,
            @RequestParam("timeMax") DateTime timeMax);

}
