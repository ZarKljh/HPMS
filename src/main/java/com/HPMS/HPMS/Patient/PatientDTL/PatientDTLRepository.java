package com.HPMS.HPMS.Patient.PatientDTL;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientDTLRepository extends JpaRepository<PatientDTL, Integer> {
}
