package com.hospital.mediflow.Common.Configuration.Properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "mediflow.billing")
public class BillingProperties {
    private int percentage;
    private double amount;
    private long paymentDateAfterTreatment;


    public int getRemainedAmount(){
        return ((100-percentage)*(int)amount)/percentage;
    }
}
