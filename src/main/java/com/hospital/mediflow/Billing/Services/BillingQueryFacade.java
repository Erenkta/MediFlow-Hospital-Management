package com.hospital.mediflow.Billing.Services;

import com.hospital.mediflow.Billing.Domain.Dtos.BillingFilterDto;
import com.hospital.mediflow.Billing.Domain.Dtos.BillingRequestDto;
import com.hospital.mediflow.Billing.Domain.Dtos.BillingResponseDto;
import com.hospital.mediflow.Billing.Services.Abstracts.BillingService;
import com.hospital.mediflow.Common.Queries.Manager.ManagerBillingQuery;
import com.hospital.mediflow.Security.Roles.Role;
import com.hospital.mediflow.Security.UserDetails.MediflowUserDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class BillingQueryFacade {
    private final BillingService billingService;
    private final ManagerBillingQuery managerQuery;

    public List<BillingResponseDto> getBillings(BillingFilterDto filter){
        Role role = MediflowUserDetailsService.currentUserRole();
        return switch (role) {
            case ADMIN   -> billingService.findAllBillings(filter);
            case MANAGER -> managerQuery.findAllBillings(filter);
            case PATIENT -> billingService.findAllBillings(filter);        // TODO Patients can get their billings
            default -> throw new AccessDeniedException("Unsupported role for the method");
        };
    }

    public Page<BillingResponseDto> getBillings(Pageable pageable, BillingFilterDto filter){
        Role role = MediflowUserDetailsService.currentUserRole();
        return switch (role) {
            case ADMIN   -> billingService.findAllBillings(pageable,filter);
            case MANAGER -> managerQuery.findAllBillings(pageable,filter);        // TODO Managers can get the billings of their department
            case PATIENT -> billingService.findAllBillings(pageable,filter);        // TODO Patients can get their billings
            default -> throw new AccessDeniedException("Unsupported role for the method");
        };
    }

    public BillingResponseDto getBillingById(Long id) {
        Role role = MediflowUserDetailsService.currentUserRole();
        return switch (role) {
            case ADMIN   -> billingService.findBillingById(id);
            case MANAGER -> managerQuery.findBillingById(id);
            case PATIENT -> billingService.findBillingById(id);  // TODO Patient can get their billings

            default -> throw new AccessDeniedException("Unsupported role for the method");
        };
    }

    public BillingResponseDto createBilling(BillingRequestDto requestDto) {
        Role role = MediflowUserDetailsService.currentUserRole();
        return switch (role) {
            case ADMIN   -> billingService.createBilling(requestDto);
            case MANAGER -> managerQuery.createBilling(requestDto);
            default -> throw new AccessDeniedException("Unsupported role for the method");
        };
    }

    public BillingResponseDto updateBilling(Long id, BillingRequestDto requestDto) {
        Role role = MediflowUserDetailsService.currentUserRole();
        return switch (role) {
            case ADMIN   -> billingService.updateBilling(id, requestDto);
            case MANAGER -> managerQuery.updateBilling(id, requestDto);
            default -> throw new AccessDeniedException("Unsupported role for the method");
        };
    }

    public void deleteBilling(Long id) {
        Role role = MediflowUserDetailsService.currentUserRole();
         switch (role) {
            case ADMIN   -> billingService.deleteBilling(id);
            case MANAGER -> managerQuery.deleteBilling(id);
            default -> throw new AccessDeniedException("Unsupported role for the method");
        }
    }
}
