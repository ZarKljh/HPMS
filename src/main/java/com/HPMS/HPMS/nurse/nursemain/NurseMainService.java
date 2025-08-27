package com.HPMS.HPMS.nurse.nursemain;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class NurseMainService {

    @Autowired
    private NurseMainRepository nurseMainRepository;

    public List<NurseMain> getAll() {
        return nurseMainRepository.findAll();
    }
}
