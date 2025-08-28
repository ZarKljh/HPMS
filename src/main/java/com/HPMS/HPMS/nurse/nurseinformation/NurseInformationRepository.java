package com.HPMS.HPMS.nurse.nurseinformation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NurseInformationRepository extends JpaRepository<NurseInformation ,Integer> {
}
