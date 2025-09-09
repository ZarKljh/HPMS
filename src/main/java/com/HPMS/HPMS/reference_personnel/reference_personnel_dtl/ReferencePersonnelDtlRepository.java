package com.HPMS.HPMS.reference_personnel.reference_personnel_dtl;

import com.HPMS.HPMS.reference_personnel.reference_personnel_m.ReferencePersonnelM;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReferencePersonnelDtlRepository extends JpaRepository<ReferencePersonnelDtl, Integer> {
    // ReferencePersonnelDtl findByFirstName(String firstName);
    Optional<ReferencePersonnelDtl> findByPersonnel(ReferencePersonnelM referencePersonnelM);

    // selectedIds 기반 삭제용
    // DTL 삭제 전용 (이후, M 삭제)
    @Modifying  // JPA가 이 쿼리가 데이터 변경용임을 인식하게 함
    @Transactional  //삭제는 트랜잭션이 필요하므로 명시적으로 선언
    @Query("DELETE FROM ReferencePersonnelDtl d WHERE d.personnel.id IN :ids")
    void deleteByPersonnelIds(@Param("ids") List<Long> ids);
}
