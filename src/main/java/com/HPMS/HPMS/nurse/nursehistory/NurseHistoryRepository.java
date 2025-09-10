package com.HPMS.HPMS.nurse.nursehistory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NurseHistoryRepository extends JpaRepository<com.HPMS.HPMS.nurse.history.NurseHistory, Integer> {
    List<com.HPMS.HPMS.nurse.history.NurseHistory> findByNurseMain_Id(Integer nurseId);
}
