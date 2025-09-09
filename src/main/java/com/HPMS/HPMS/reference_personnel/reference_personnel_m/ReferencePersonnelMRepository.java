package com.HPMS.HPMS.reference_personnel.reference_personnel_m;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

//  원래 Integer 로 선언하여 사용했으나 selectedIds를 이용한 삭제를 적용하면서 Long 으로 변경함
public interface ReferencePersonnelMRepository extends JpaRepository<ReferencePersonnelM, Integer> {

    //이승운 추가 - firstName과 lastName 과 cellPhone 으로 ReferencePersonnelM을 가져온다
    ReferencePersonnelM findByFirstNameAndLastNameAndCellPhone(String firstName, String lastName, String cellPhone);  
  
    //다중삭제용으로 추가됨
    @Modifying  // JPA가 이 쿼리가 데이터 변경용임을 인식하게 함
    @Transactional  //삭제는 트랜잭션이 필요하므로 명시적으로 선언
    @Query("DELETE FROM ReferencePersonnelM d WHERE d.id IN :ids")
    void deleteByPersonnelMAllById(@Param("ids") List<Long> ids);
    
}

