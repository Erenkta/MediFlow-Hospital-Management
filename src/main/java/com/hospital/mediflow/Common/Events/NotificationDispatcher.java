package com.hospital.mediflow.Common.Events;

import com.hospital.mediflow.Common.Configuration.RabbitMQConfig;
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

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Async
    public void handleIntervalEvent(InternalNotificationEvent event){

        rabbitTemplate.convertAndSend(
                RabbitMQConfig.EXCHANGE,
                RabbitMQConfig.ROUTING_KEY,
                convertToRabbitEvent(event)
        );
    }

    private NotificationEvent convertToRabbitEvent(InternalNotificationEvent event){
        return new NotificationEvent(
                event.getUserId(),
                event.getEventType().name(),
                event.getData()
        );
    }
}
