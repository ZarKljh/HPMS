package com.HPMS.HPMS.Patient.PatientM;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PatientMService {
    private final PatientMRepository patientMRepository;

    public List<PatientM> getAllPatientM(){
        return this.patientMRepository.findAll();
    }
    public PatientM getPatientM(Integer id){
        Optional<PatientM> patientM = this.patientMRepository.findById(id);
        if (patientM.isPresent()) {
            return patientM.get();
        } else {
            throw new NoSuchElementException("Patient detail not found for patientId: " + patientM.get().getId());
            //throw new DataNotFoundException("question not found");
        }
    }
}
