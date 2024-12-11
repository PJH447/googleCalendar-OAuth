package com.group.googleCalendar.web.googleCalendar.dto;

import java.util.List;

public record CalendarResponse(
        String kind,
        String etag,
        String summary,
        String description,
        String timeZone,
        String accessRole,
        String nextSyncToken,
        List<ItemResponse> items
) {
}
