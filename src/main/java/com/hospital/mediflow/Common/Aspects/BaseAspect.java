package com.hospital.mediflow.Common.Aspects;

import org.aspectj.lang.JoinPoint;

public abstract class BaseAspect {
    protected <T> T extract(JoinPoint joinPoint, Class<T> expectedType) {
        Object[] args = joinPoint.getArgs();
        T found = null;
        for (Object arg : args) {
            if (expectedType.isInstance(arg)) {
                if (found != null) {
                    throw new IllegalStateException(
                            "Multiple arguments of type " + expectedType.getSimpleName()
                    );
                }

                found = expectedType.cast(arg);
            }
        }
        if (found == null) {
            throw new IllegalStateException(
                    "No argument of type " + expectedType.getSimpleName() + " found"
            );
        }
        return found;
    }
}
