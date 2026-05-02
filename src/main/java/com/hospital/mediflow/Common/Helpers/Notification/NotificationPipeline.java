package com.hospital.mediflow.Common.Helpers.Notification;

import com.hospital.mediflow.Common.Events.EventType;
import com.hospital.mediflow.Common.Events.InternalNotificationEvent;
import com.hospital.mediflow.Common.Helpers.Notification.Enrichers.NotificationEnricher;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class NotificationPipeline {
    private final List<NotificationEnricher> enrichers;
    private final ApplicationEventPublisher eventPublisher;

    public void processAndNotify(Object entity, ObjectType type, EventType eventType){
        NotificationContext context = new NotificationContext();
        context.setEntity(entity);
        context.setObjectType(type);

        enrichers.stream()
                .filter(enricher -> enricher.supports(context))
                .forEach(e->e.enrich(context));

        eventPublisher.publishEvent(new InternalNotificationEvent(
                entity,
                context.getUser(),
                eventType,
                context.getData()
        ));
    }
    public void processAndNotify(Object entity, ObjectType type, EventType eventType, Map<String,Object> additionalData){
        NotificationContext context = new NotificationContext(additionalData);

        context.setEntity(entity);
        context.setObjectType(type);

        enrichers.stream()
                .filter(enricher -> enricher.supports(context))
                .forEach(e->e.enrich(context));

        eventPublisher.publishEvent(new InternalNotificationEvent(
                entity,
                context.getUser(),
                eventType,
                context.getData()
        ));
    }

}
