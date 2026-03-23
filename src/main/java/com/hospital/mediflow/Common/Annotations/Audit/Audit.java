package com.hospital.mediflow.Common.Annotations.Audit;

import com.hospital.mediflow.Common.Annotations.Access.AccessType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Audit {
    AccessType action();
    Class<?> returns() default Object.class;
    String idParam() default "";
    int retentionDay() default 30;
}