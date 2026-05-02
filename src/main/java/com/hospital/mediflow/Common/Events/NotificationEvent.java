package com.hospital.mediflow.Common.Events;

import java.util.Map;

public record NotificationEvent(
        Long receiverId,
        String eventType,
        NotificationType type,
        Map<String,Object> data
) {
}
