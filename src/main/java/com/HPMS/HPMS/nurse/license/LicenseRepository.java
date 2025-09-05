package com.HPMS.HPMS.nurse.license;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LicenseRepository extends JpaRepository<License, Integer> {
    List<License> findByNurse_Id(Integer nurseId); // id로 조회
}
