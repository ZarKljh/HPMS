package com.HPMS.HPMS.Doctor.DoctorH;

import com.HPMS.HPMS.Doctor.DoctorDTL.DoctorDTL;
import com.HPMS.HPMS.Doctor.DoctorM.DoctorM;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DoctorHService {
    private final DoctorHRepository HistRepo;

    public void snapshotBeforeUpdate(DoctorM m, DoctorDTL d) {
        HistRepo.save(DoctorH.snapshotOf(m, d));
    }

}
