package com.hospital.mediflow.Common;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public interface BaseRepository<T, ID> extends JpaRepository<T, ID>, JpaSpecificationExecutor<T> {
    default List<T> findAll(Specification<T> spec){
        return findAll(spec,Sort.by(Sort.Direction.DESC,"createdAt"));
    }

}
