package com.HPMS.HPMS.Patient.PatientDTL;

import com.HPMS.HPMS.Patient.PatientM.PatientM;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PatientDTLRepository extends JpaRepository<PatientDTL, Integer> {
    Optional<PatientDTL> findByPatientM(PatientM patientM);
}
