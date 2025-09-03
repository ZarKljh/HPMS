package com.HPMS.HPMS.reference_personnel.reference_personnel_dtl;

import com.HPMS.HPMS.reference_personnel.reference_personnel_m.ReferencePersonnelM;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReferencePersonnelDtlRepository extends JpaRepository<ReferencePersonnelDtl, Integer> {
    // ReferencePersonnelDtl findByFirstName(String firstName);
    Optional<ReferencePersonnelDtl> findByPersonnel(ReferencePersonnelM referencePersonnelM);
}
