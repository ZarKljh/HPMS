package com.HPMS.HPMS.nurse.license;

import com.HPMS.HPMS.nurse.nursemain.NurseMain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LicenseRepository extends JpaRepository<License, Integer> {
    List<License> findByNurseId(NurseMain nurseMain); // NurseMain 객체 기준
}
