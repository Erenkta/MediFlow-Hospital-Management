package com.hospital.mediflow.Common.Helpers.Notification.Enrichers;

import com.hospital.mediflow.Appointment.Domain.Entity.Appointment;
import com.hospital.mediflow.Billing.Domain.Entity.Billing;
import com.hospital.mediflow.Common.Helpers.Notification.NotificationContext;
import com.hospital.mediflow.Common.Helpers.Notification.ObjectType;
import com.hospital.mediflow.Security.Dtos.Entity.User;
import com.hospital.mediflow.Security.UserDetails.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@Slf4j
public class DefaultEnricher implements NotificationEnricher {
    private final UserRepository userRepository;
    @Override
    public boolean supports(NotificationContext context) {
        return true;
    }

    @Override
    public void enrich(NotificationContext context) {
        // Identities and default values.
        ObjectType type = context.getObjectType();
        Long userId = null;
        switch(type){
            case APPOINTMENT -> userId = ((Appointment)context.getEntity()).getPatient().getId();
            case BILLING -> userId = ((Billing)context.getEntity()).getPatient().getId();
        }
        if(userId == null){
            log.error("Default Enricher couldn't find the user. Object Name : "+type.getClazz().getSimpleName());
            return;
        }
        User relatedUser = userRepository.findByResourceId(userId);
        context.setUser(relatedUser);
    }
}
