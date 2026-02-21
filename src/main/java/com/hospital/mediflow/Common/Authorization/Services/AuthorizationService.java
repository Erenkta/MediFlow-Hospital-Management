package com.hospital.mediflow.Common.Authorization.Services;

import com.hospital.mediflow.Common.Authorization.Model.AuthorizationContext;
import com.hospital.mediflow.Common.Authorization.Model.RuleKey;
import com.hospital.mediflow.Common.Authorization.Rules.ActionRule;
import com.hospital.mediflow.Security.Roles.Role;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

import java.util.List;

import java.util.Map;
import java.util.stream.Collectors;

@Component
public class AuthorizationService {
    private final Map<RuleKey, ActionRule> ruleMap;

    public AuthorizationService(List<ActionRule> rules) {

        this.ruleMap = rules.stream()
                .collect(Collectors.toMap(
                        r -> new RuleKey(r.role(), r.resource(), r.action()),
                        r -> r
                ));
    }
    public void authorize(AuthorizationContext context) {
        if(context.getUser().getRole().equals(Role.ADMIN)){
            return;
        }
        RuleKey key = new RuleKey(
                context.getUser().getRole(),
                context.getResource(),
                context.getAction()
        );
        ActionRule rule = ruleMap.get(key);
        if (rule == null) {
            String message = String.format(
                    "No rule defined for the User Id: %s , User Role : %s , Resource : %s , ResourceId : %s and action : %s ."
                    ,context.getUser().getResourceId(),context.getUser().getRole().name(),context.getResource().name(),context.getResourceId(),context.getAction().name()
                    );
            throw new AccessDeniedException(message);
        }

        rule.check(context);
    }
}
