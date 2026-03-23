package com.hospital.mediflow.Audit.Event;

import com.hospital.mediflow.Common.Annotations.Access.AccessType;

public record DomainEvent<T>(T entity, AccessType action, Long primaryKey,Integer retentionDay) {}