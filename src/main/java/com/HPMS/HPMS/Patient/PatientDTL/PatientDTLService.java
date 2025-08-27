package com.HPMS.HPMS.Patient.PatientDTL;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PatientDTLService {
    private final PatientDTLRepository patientDTLRepository;

    public PatientDTL getPatientDTLByPatientId(Integer id){
        Optional<PatientDTL> dtl = this.patientDTLRepository.findById(id);
        if (dtl.isPresent()) {
            return dtl.get();
        } else {

            throw new NoSuchElementException("Patient detail not found for patientId: " + dtl.get().getId());
            //throw new DataNotFoundException("question not found");

        }
    }
}
