package com.hospital.mediflow.Security.Dtos;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.hospital.mediflow.Common.Events.NotificationType;

public record UserPreference(
        @JsonProperty("notification")
        NotificationType notification
) {
}
