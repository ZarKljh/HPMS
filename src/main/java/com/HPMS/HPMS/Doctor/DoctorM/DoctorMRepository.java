package com.HPMS.HPMS.Doctor.DoctorM;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
    public interface DoctorMRepository extends JpaRepository<DoctorM, Integer> {
//    DoctorM findBySubject(String subject);
//
//    DoctorM findBySubjectAndContent(String subject, String content);
//
//    List<DoctorM> findBySubjectLike(String subject);
//
//    Page<DoctorM> findAll(Pageable pageable);
}
