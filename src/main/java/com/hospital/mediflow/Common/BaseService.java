package com.hospital.mediflow.Common;

import com.hospital.mediflow.Common.Exceptions.ErrorCode;
import com.hospital.mediflow.Common.Exceptions.RecordNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;

@RequiredArgsConstructor
public abstract class BaseService<T,ID> {
    protected final JpaRepository<T, ID> repository;


    protected T findByIdOrThrow(ID id){
        return repository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(
                        String.format("%s not found with id: %s", getEntityName(), id),
                        ErrorCode.RECORD_NOT_FOUND
                ));
    }

    /**
     * Automatically infer entity name from service class.
     */
    private String getEntityName() {
        String className = this.getClass().getSimpleName();
        return className.replace("Service", "");
    }
}
