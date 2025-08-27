package com.HPMS.HPMS.Patient.PatientM;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PatientMService {
    private final PatientMRepository patientMRepository;

    public List<PatientM> getAllPatientM(){
        return this.patientMRepository.findAll();
    }

}
