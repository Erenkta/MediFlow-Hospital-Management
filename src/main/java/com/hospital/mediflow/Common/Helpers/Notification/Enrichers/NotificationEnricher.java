package com.hospital.mediflow.Common.Helpers.Notification.Enrichers;

import com.hospital.mediflow.Common.Helpers.Notification.NotificationContext;

public interface NotificationEnricher {
    boolean supports(NotificationContext context);
    void enrich(NotificationContext context);
}
