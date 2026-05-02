package com.hospital.mediflow.Common.Helpers.Notification;

import com.hospital.mediflow.Security.Dtos.Entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationContext {
    private User user;
    private ObjectType objectType;
    private Map<String,Object> data = new HashMap<>();
    private Object entity;

    public NotificationContext(Map<String,Object> additionalData){
        this.data = new HashMap<>(additionalData); // to make this mutable.
    }
}
