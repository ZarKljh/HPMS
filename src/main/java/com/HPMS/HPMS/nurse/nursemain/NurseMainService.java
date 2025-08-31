package com.HPMS.HPMS.nurse.nursemain;

import com.HPMS.HPMS.nurse.NurseDataNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NurseMainService {

    @Autowired
    private NurseMainRepository nurseMainRepository;

    public List<NurseMain> getAll() {
        return nurseMainRepository.findAll();
    }

    public NurseMain getNurseMain(Integer id) {
        Optional<NurseMain> nurseMain = this.nurseMainRepository.findById(id);
        if (nurseMain.isPresent()) {
            return nurseMain.get();
        } else {
            throw new NurseDataNotFoundException("nurse not found");
        }
    }

    public NurseMain save(NurseMain nurseMain) {
        return nurseMainRepository.save(nurseMain);
    }
}
