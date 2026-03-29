package com.hospital.mediflow.Common.Events;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hospital.mediflow.Common.Annotations.Access.AccessType;
import com.hospital.mediflow.Common.Annotations.Audit.Audit;
import com.hospital.mediflow.Common.Configuration.RabbitMQConfig;
import com.hospital.mediflow.Security.Dtos.UserPreference;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
@Slf4j
public class NotificationDispatcher {
    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Async
    public void handleIntervalEvent(InternalNotificationEvent event){
        try {
            UserPreference userPref = objectMapper.readValue(event.getUser().getPreferences(), UserPreference.class);
            NotificationType userPreference = userPref.notification();
            rabbitTemplate.convertAndSend(
                    RabbitMQConfig.EXCHANGE,
                    RabbitMQConfig.ROUTING_KEY,
                    convertToRabbitEvent(event,userPreference));
        }catch (Exception e){
            log.error("An error occured during the notification dispatch",e.getMessage());
        }
    }
    private NotificationEvent convertToRabbitEvent(InternalNotificationEvent event, NotificationType notificationType){
        return new NotificationEvent(
                event.getUser().getId(),
                event.getEventType().name(),
                notificationType,
                event.getData()
        );
    }
}
