package com.HPMS.HPMS.reference_personnel.reference_personnel_m;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import com.HPMS.HPMS.reference_personnel.reference_personnel_dtl.ReferencePersonnelDtl;
import com.HPMS.HPMS.reference_personnel.reference_personnel_dtl.ReferencePersonnelDtlRepository;
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
}
