package com.HPMS.HPMS.Doctor.DoctorM;

import com.HPMS.HPMS.nurse.nursemain.NurseMain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorMRepository extends JpaRepository<DoctorM, Integer>, JpaSpecificationExecutor<DoctorM> {
    // 검색은 Service에서 Specification으로 구현 (모든 컬럼 안전검색)
    DoctorM findByFirstNameAndLastNameAndTelephone(String firstName, String lastName, String telephone);
}
