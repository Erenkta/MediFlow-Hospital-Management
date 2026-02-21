package com.hospital.mediflow.Common.Annotations.Access.Manager;

import com.hospital.mediflow.Common.Annotations.Access.AccessType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ManagerBillingAccess {
    AccessType type();
}
