package com.HPMS.HPMS.nurse.nursemain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NurseMainService {

    @Autowired
    private NurseMainRepository nurseMainRepository;

    public List<NurseMain> getAll() {
        return nurseMainRepository.findAll();
    }
}
