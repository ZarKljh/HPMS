package com.HPMS.HPMS.nurse.nurseinformation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NurseInformationRepository extends JpaRepository<NurseInformation ,Integer> {
    Optional<NurseInformation> findByNurseMainId(Integer nurseId);
}
