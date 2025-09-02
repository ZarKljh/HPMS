package com.HPMS.HPMS.reference_personnel.reference_personnel_dtl;

import com.HPMS.HPMS.reference_personnel.reference_personnel_m.ReferencePersonnelM;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReferencePersonnelDtlService {
    private final ReferencePersonnelDtlRepository referencePersonnelDtlRepository;

    public List<ReferencePersonnelDtl> getAllReferencePersonnelDtl(){
        return this.referencePersonnelDtlRepository.findAll();
    }

    public ReferencePersonnelDtl getReferencePersonnelDtlByPersonnel(ReferencePersonnelM referencePersonnelM){
        Optional<ReferencePersonnelDtl> referencePersonnelDtl = this.referencePersonnelDtlRepository.findByPersonnel(referencePersonnelM);
        if(referencePersonnelDtl.isPresent()){
            return referencePersonnelDtl.get();
        }else{
            throw new NoSuchElementException("Reference Personnel details is not found: " + referencePersonnelDtl.get().getId());
        }
    }
}
