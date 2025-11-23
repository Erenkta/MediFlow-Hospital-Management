package com.hospital.mediflow.Common.Configuration;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

@Component
@ConfigurationProperties(prefix="mediflow.appointment")
@Getter
@Setter
public class AppointmentProperties {
    private int duration;
    private LocalTime startTime;
    private LocalTime endTime;
}
