package com.HPMS.HPMS.Doctor.DoctorDTL;

import com.HPMS.HPMS.Doctor.DoctorM.DoctorM;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoctorDTLRepository extends JpaRepository<DoctorDTL, Integer> {
    /*
    DoctorDTL findBySubject(String subject);
    DoctorDTL findBySubjectAndContent(String subject, String content);
    List<DoctorDTL> findBySubjectLike(String subject);
    Page<DoctorDTL> findAll(Pageable pageable);

     */
}
