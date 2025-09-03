package com.HPMS.HPMS.Doctor.DoctorH;


import com.HPMS.HPMS.Doctor.DoctorM.DoctorM;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorHRepository extends JpaRepository<DoctorH, Integer>, JpaSpecificationExecutor<DoctorH> {
}
