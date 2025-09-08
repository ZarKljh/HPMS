package com.HPMS.HPMS.Patient.PatientM;

import jakarta.persistence.EntityManager;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientMRepository extends JpaRepository<PatientM, Integer> {
    Page<PatientM> findAll(Pageable pageable);
}


