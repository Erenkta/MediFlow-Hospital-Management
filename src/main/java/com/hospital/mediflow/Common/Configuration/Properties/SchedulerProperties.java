package com.hospital.mediflow.Common.Configuration.Properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;


@Data
@Configuration
@ConfigurationProperties(prefix = "mediflow.scheduler")
@Component("schedulerProperties")
public class SchedulerProperties {
     String invoicePdf;
     String overduePayment;
     String cleanBackup;
}
