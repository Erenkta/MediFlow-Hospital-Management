package com.hospital.mediflow.Common.Authorization.Policy;

import com.hospital.mediflow.Common.Annotations.Access.AccessType;
import com.hospital.mediflow.Common.Authorization.Model.BillingAccessData;
import com.hospital.mediflow.Common.Authorization.Model.DoctorAccessData;
import com.hospital.mediflow.Security.Dtos.Entity.User;
import org.apache.commons.lang3.NotImplementedException;

public abstract class AuthorizationPolicyDeprecated {
    void assertCanAccessPatient(User subject,
                      Long resourceId,
                      AccessType action){
        throw new NotImplementedException("This access policy is not implemented yet");

    }
    void assertCanAccessMedicalRecord(User subject,
                      Long resourceId,
                      AccessType action){
        throw new NotImplementedException("This access policy is not implemented yet");

    }
    void assertCanAccessDoctorDepartment(User subject,
                      Long resourceId,
                      AccessType action){
        throw new NotImplementedException("This access policy is not implemented yet");

    }
    void assertCanAccessDoctor(User subject,
                       DoctorAccessData resourceId,
                      AccessType action){
        throw new NotImplementedException("This access policy is not implemented yet");

    }
    void assertCanAccessDepartment(User subject,
                      Long resourceId,
                      AccessType action){
        throw new NotImplementedException("This access policy is not implemented yet");

    }
    void assertCanAccessAppointment(User subject,
                       Long resourceId,
                      AccessType action){
        throw new NotImplementedException("This access policy is not implemented yet");

    }
    void assertCanAccessBilling(User subject,
                      BillingAccessData data,
                      AccessType action)
    {
        throw new NotImplementedException("This access policy is not implemented yet");

    }

     protected String generateAccessExceptionMessage(AccessType action,String subject){
        return String.format("Invalid access type for %s user. Access Type : %s",subject,action.name());
    }
    protected String generateRelationExceptionMessage(Long resourceId,String subject,String resource){
        return String.format("Access is Denied. Current %s user does not have access to %s with id : %s",subject,resourceId,resourceId);
    }
}
