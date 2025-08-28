package com.HPMS.HPMS.nurse.nursemain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NurseMainRepository extends JpaRepository<NurseMain, Integer> {
}
