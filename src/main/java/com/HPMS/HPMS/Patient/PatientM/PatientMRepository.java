package com.HPMS.HPMS.Patient.PatientM;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientMRepository extends JpaRepository<PatientM, Integer> {

}
