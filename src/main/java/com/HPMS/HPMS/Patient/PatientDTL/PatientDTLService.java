package com.HPMS.HPMS.Patient.PatientDTL;

import com.HPMS.HPMS.Patient.PatientM.PatientM;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PatientDTLService {
    private final PatientDTLRepository patientDTLRepository;

    public PatientDTL getPatientDTLByPatientId(PatientM m){
        Optional<PatientDTL> dtl = this.patientDTLRepository.findByPatientM(m);
        if (dtl.isPresent()) {
            return dtl.get();
        } else {

            throw new NoSuchElementException("Patient detail not found for patientId: " + dtl.get().getId());
            //throw new DataNotFoundException("question not found");

        }
    }
}
