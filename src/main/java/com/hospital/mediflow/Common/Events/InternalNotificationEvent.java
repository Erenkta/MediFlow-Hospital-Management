package com.hospital.mediflow.Common.Events;

import com.hospital.mediflow.Security.Dtos.Entity.User;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.util.Map;

@Getter
public class InternalNotificationEvent extends ApplicationEvent {
    private final User user;
    private final EventType eventType;
    private final Map<String, String> data;

    public InternalNotificationEvent(Object source, User user, EventType eventType, Map<String, String> data) {
        super(source);
        this.user = user;
        this.eventType = eventType;
        this.data = data;
    }
}