package com.ChairShop.repositories.criterial;

import com.ChairShop.model.enteties.Chair;
import com.ChairShop.model.request.chair.ChairSearchRequest;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.util.Predicates;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@AllArgsConstructor

public class ChairSearchCriteria implements Specification<Chair> {

    private final ChairSearchRequest request;
    @Override
    public Predicate toPredicate(
            @NotNull Root<Chair> root,
            CriteriaQuery<?> query,
            @NotNull CriteriaBuilder criteriaBuilder) {

        List<Predicate> predicates = new ArrayList<>();
        if(Objects.nonNull(request.getName())){
            predicates.add(criteriaBuilder.like(root.get(Chair.NAME_FIELD),'%' + request.getName()+ '%'));
        }

        if(Objects.nonNull(request.getPrice())){
            predicates.add(criteriaBuilder.equal(root.get(Chair.PRICE_FIELD), request.getPrice()));
        }

        if(Objects.nonNull(request.getDeleted())){
            predicates.add(criteriaBuilder.equal(root.get(Chair.DELETED_FIELD), request.getDeleted()));
        }

        if (Objects.nonNull(request.getKeyword())){
            Predicate KeyWordPredicate = criteriaBuilder.or(
                    criteriaBuilder.like(root.get(Chair.NAME_FIELD),'%' + request.getKeyword()+ '%')
            );
            predicates.add(KeyWordPredicate);
        }

        sort(root, criteriaBuilder, query);
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }

    private void sort(Root<Chair> root,
                      CriteriaBuilder criteriaBuilder,
                      CriteriaQuery<?> query){
        if (Objects.nonNull(request.getChairSortField())){
            switch (request.getChairSortField()){
                case NAME -> criteriaBuilder.desc(root.get(Chair.NAME_FIELD));
                case PRICE -> criteriaBuilder.desc(root.get(Chair.PRICE_FIELD));
                default ->  criteriaBuilder.desc(root.get(Chair.ID_FIELD));
            }
        } else {
            query.orderBy(criteriaBuilder.desc(root.get(Chair.ID_FIELD)));
        }
    }

}
