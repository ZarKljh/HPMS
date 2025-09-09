package com.HPMS.HPMS.reference_personnel.reference_personnel_m;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import com.HPMS.HPMS.reference_personnel.reference_personnel_dtl.ReferencePersonnelDtl;
import com.HPMS.HPMS.reference_personnel.reference_personnel_dtl.ReferencePersonnelDtlRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReferencePersonnelMService {
    private final   ReferencePersonnelMRepository referencePersonnelMRepository;
    private final ReferencePersonnelDtlRepository referencePersonnelDtlRepository;

    public List<ReferencePersonnelM> getAllReferencePersonnelM(){
        return this.referencePersonnelMRepository.findAll();
    }

/*    public Optional<ReferencePersonnelM> optional_getAllReferencePersonnelM(){
        return this.referencePersonnelMRepository.findAll();
    }*/
    public ReferencePersonnelM getReferencePersonnelM(Integer id){
        Optional<ReferencePersonnelM> referencePersonnelM = this.referencePersonnelMRepository.findById(id);
        if (referencePersonnelM.isPresent()) {
            return referencePersonnelM.get();
        } else {
            throw new NoSuchElementException("Reference Personal main information is not found by ID: " + referencePersonnelM.get().getId());
            //throw new DataNotFoundException("question not found");
        }
    }

    public ReferencePersonnelM saveReferencePersonnelM(ReferencePersonnelM m) {
        return referencePersonnelMRepository.save(m);
    }
    public ReferencePersonnelDtl saveReferencePersonnelDtl(ReferencePersonnelDtl dtl) {
        return referencePersonnelDtlRepository.save(dtl);
    }

    //이승운추가 firstName 과 lastName 과 cellPhone 으로 referencePersonnelM 을 가져오는 메소드
    public ReferencePersonnelM getReferencePersonnelMByNameAndCellPhone(String firstName, String lastName, String cellPhone) {
        return this.referencePersonnelMRepository.findByFirstNameAndLastNameAndCellPhone(firstName, lastName, cellPhone);    
    }
    // 삭제를 위해 추가함
    // private final ReferencePersonnelMRepository referencePersonnelMRepository;
  
    @Transactional
    public void deletePersonnel(Integer id) {
        ReferencePersonnelM master = referencePersonnelMRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 마스터가 존재하지 않습니다: " + id));

        master.setDetail(null); // orphanRemoval 작동
        referencePersonnelMRepository.delete(master); // 마스터 삭제

    }

    // selectedIds 기반 다중삭제
    @Transactional
    public void deleteByIds(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            System.out.println("삭제할 ID가 없습니다.");
            return;
        }

        try {
            referencePersonnelDtlRepository.deleteByPersonnelIds(ids); // Dtl 먼저 삭제
            referencePersonnelMRepository.deleteByPersonnelMAllById(ids);          // M 삭제 //Long 방식
            // System.out.println("삭제 완료: " + ids);
        } catch (Exception e) {
            System.err.println("삭제 중 오류 발생: " + e.getMessage());
            // 필요 시 로깅 또는 사용자 안내 처리
        }

        // referencePersonnelDtlRepository.deleteByPersonnelIds(ids); // Dtl 먼저 삭제
        // referencePersonnelMRepository.deleteAllById(ids);
    }
}


