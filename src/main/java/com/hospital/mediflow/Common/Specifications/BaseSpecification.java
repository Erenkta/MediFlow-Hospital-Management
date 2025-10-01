package com.hospital.mediflow.Common.Specifications;


import org.springframework.data.jpa.domain.Specification;

public abstract class BaseSpecification<T>{
    protected Specification<T> like(String attribute,String value){
        String likeQuery = "%"+value+"%";
         return (root, query, criteriaBuilder) ->
            value == null ? null : criteriaBuilder.like(root.get(attribute),likeQuery);

    }

    protected Specification<T> equals(String attribute,String value){
        return (root,query,criteriaBuilder) ->
                value == null ? null : criteriaBuilder.equal(root.get(attribute),value);
    }

}
