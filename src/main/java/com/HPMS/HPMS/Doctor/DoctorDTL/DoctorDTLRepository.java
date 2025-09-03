package com.HPMS.HPMS.Doctor.DoctorDTL;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DoctorDTLRepository extends JpaRepository<DoctorDTL, Integer> {
    Optional<DoctorDTL> findByDoctorMainId(Integer doctorId);
    boolean existsByDoctorMainId(Integer doctorId);
}
