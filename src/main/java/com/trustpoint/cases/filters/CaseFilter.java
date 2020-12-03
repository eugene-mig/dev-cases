package com.trustpoint.cases.filters;

import com.trustpoint.cases.dto.CaseFilterPayload;
import com.trustpoint.cases.model.Case;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class CaseFilter implements Specification<Case> {
  private CaseFilterPayload filters;

  public CaseFilter(CaseFilterPayload filters) {
    this.filters = filters;
  }

  @Override
  public Predicate toPredicate(Root<Case> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder builder) {
    List<Predicate> filterPredicates = new ArrayList<>();

    if (filters != null) {

      if (!StringUtils.isEmpty(filters.getCustomerId())) {
        Predicate customerIdFilter = builder.equal(root.get("customer"), filters.getCustomerId());
        filterPredicates.add(customerIdFilter);
      }

    }

    return builder.and(filterPredicates.toArray(new Predicate[filterPredicates.size()]));
  }
}
