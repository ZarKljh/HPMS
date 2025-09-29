package com.HPMS.HPMS.Patient.PatientM;

import jakarta.persistence.EntityManager;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PatientMRepository extends JpaRepository<PatientM, Integer> {
    Page<PatientM> findAll(Pageable pageable);
    Page<PatientM> findAll(Specification<PatientM> spec, Pageable pageable);
    Optional<PatientM> findByFirstNameAndLastName(String firstName, String lastName);
    @Query("select "
            + "distinct m "
            + "from PatientM m "
            + "left outer join PatientDTL d on d.patientM = m "
            + "where m.delStatus != 1"
            + "	and m.firstName like %:kw% "
            + "	or d.mobilePhone like %:kw% "
            + "	or d.guardianTel like %:kw% "
            )
    Page<PatientM> findAllByKeyword(@Param("kw") String kw, Pageable pageable);
}


