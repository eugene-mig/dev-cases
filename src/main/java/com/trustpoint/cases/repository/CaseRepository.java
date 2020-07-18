package com.trustpoint.cases.repository;

import com.trustpoint.cases.model.Case;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository("postgres")
public interface CaseRepository extends JpaRepository<Case, UUID> {

}
