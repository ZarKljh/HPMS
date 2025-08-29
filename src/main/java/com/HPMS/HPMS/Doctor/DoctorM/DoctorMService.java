package com.HPMS.HPMS.Doctor.DoctorM;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.print.Doc;
import java.util.List;

@RequiredArgsConstructor
@Service
public class DoctorMService {
    private final DoctorMRepository doctorMRepository;

    public List<DoctorM> getList(){
        return DoctorMRepository.findAll();
    }
}
