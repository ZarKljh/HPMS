package com.HPMS.HPMS.reference_personnel.reference_personnel_m;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ReferencePersonnelMRepository extends JpaRepository<ReferencePersonnelM, Integer> {
    //이승운 추가 - firstName과 lastName 과 cellPhone 으로 ReferencePersonnelM을 가져온다
    ReferencePersonnelM findByFirstNameAndLastNameAndCellPhone(String firstName, String lastName, String cellPhone);
}
