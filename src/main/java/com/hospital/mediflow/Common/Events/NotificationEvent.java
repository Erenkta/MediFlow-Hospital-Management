package com.hospital.mediflow.Common.Events;

import java.util.Map;

public record NotificationEvent(
        Long receiverId,
        String eventType, // make it enum later
        Map<String,String> data
) {
}
