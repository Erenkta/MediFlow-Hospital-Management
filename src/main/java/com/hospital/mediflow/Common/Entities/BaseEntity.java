package com.hospital.mediflow.Common.Entities;

import jakarta.persistence.*;
import lombok.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.LocalDateTime;

@MappedSuperclass
@Getter
@Setter
@ToString
@EntityListeners(AuditingEntityListener.class)
@RequiredArgsConstructor
public class BaseEntity implements Serializable {
    @CreatedDate
    @Setter(AccessLevel.NONE)
    private LocalDateTime createdAt;
    @LastModifiedDate
    @Setter(AccessLevel.NONE)
    private LocalDateTime updatedAt;
    @Version
    @Setter(AccessLevel.NONE)
    private Long version;

    protected static final Logger log =  LoggerFactory.getLogger(BaseEntity.class);

    @PostPersist
    public void afterPersist(){
        String createdClassName = this.getClass().getSimpleName();
        try{
            Object entityId = this.getClass().getMethod("getId").invoke(this);
            log.info("{} with id {} is created at {}",createdClassName,entityId,this.createdAt);
        }catch (Exception ex){
            log.error("An error occurred while getting the id of {}", createdClassName, ex);
        }
    }
}
