package com.hospital.mediflow.Common.Aspects;

import com.hospital.mediflow.Common.Annotations.ResourceAccess;
import com.hospital.mediflow.Common.Authorization.Model.AuthorizationContext;
import com.hospital.mediflow.Common.Authorization.Services.AuthorizationService;
import com.hospital.mediflow.Common.Providers.Abstracts.CurrentUserProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

@Aspect
@RequiredArgsConstructor
@Component
@Slf4j
public class AuthorizationAspect extends BaseAspect{
    private final CurrentUserProvider userProvider;
    private final AuthorizationService authorizationService;

    @Before("@annotation(auth)")
    public void authorize(JoinPoint jp, ResourceAccess auth) {

        MethodSignature signature = (MethodSignature) jp.getSignature();
        String[] paramNames = signature.getParameterNames();
        Object[] args = jp.getArgs();

        Long resourceId = null;
        Object payload = null;
        Object filter = null;

        for (int i = 0; i < paramNames.length; i++) {

            if (paramNames[i].equals(auth.idParam())) {
                resourceId = (Long) args[i];
            }

            if (paramNames[i].equals(auth.payloadParam())) {
                payload = args[i];
            }

            if (paramNames[i].equals(auth.filterParam())) {
                filter = args[i];
            }
        }

        AuthorizationContext context =
                new AuthorizationContext(
                        auth.resource(),
                        auth.action(),
                        resourceId,
                        userProvider.get(),
                        payload,
                        filter
                );

        authorizationService.authorize(context);
    }
}
