package com.trustpoint.cases.repository;

import com.trustpoint.cases.model.Case;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository("postgres")
public interface CaseRepository extends JpaRepository<Case, UUID> {
	List<Case> findAllByBusinessUnit(Long businessUnit);

	List<Case> findAllByBusinessUnitIn(List<Long> businessUnits);

	List<Case> findAllByBusinessUnitOrOwner(Long businessUnit, String owner);

	List<Case> findAllByBusinessUnitInOrOwner(List<Long> businessUnits, String owner);

	List<Case> findAllByOwner(String owner);

	Optional<Case> findByIdAndOwner(UUID id, String owner);

	Optional<Case> findByIdAndBusinessUnit(UUID id, Long businessUnit);

	Optional<Case> findByIdAndBusinessUnitInOrOwner(UUID id, List<Long> businessUnits, String owner);
}