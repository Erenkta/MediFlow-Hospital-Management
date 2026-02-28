package com.hospital.mediflow.Common.Annotations;


import com.hospital.mediflow.Common.Annotations.Access.AccessType;
import com.hospital.mediflow.Common.Annotations.Access.ResourceType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ResourceAccess {
    ResourceType resource();
    AccessType action();

    String idParam() default "";
    String payloadParam() default "";
    String filterParam() default "";

}
