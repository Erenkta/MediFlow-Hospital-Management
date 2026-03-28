package com.hospital.mediflow.Common.Events;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.util.Map;

@Getter
public class InternalNotificationEvent extends ApplicationEvent {
    private final Long userId;
    private final EventType eventType;
    private final Map<String, String> data;

    public InternalNotificationEvent(Object source, Long userId, EventType eventType, Map<String, String> data) {
        super(source);
        this.userId = userId;
        this.eventType = eventType;
        this.data = data;
    }
}