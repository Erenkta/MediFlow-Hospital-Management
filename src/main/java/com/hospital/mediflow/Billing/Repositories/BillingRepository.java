package com.hospital.mediflow.Billing.Repositories;

import com.hospital.mediflow.Billing.Domain.Entity.Billing;
import com.hospital.mediflow.Common.BaseRepository;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BillingRepository extends BaseRepository<Billing,Long>, QuerydslPredicateExecutor<Billing> {

    List<Billing> findAll(Predicate predicate);
    Page<Billing> findAll(Predicate predicate, Pageable pageable);
}
