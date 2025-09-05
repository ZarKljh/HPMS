package com.HPMS.HPMS.nurse.license;

import com.HPMS.HPMS.nurse.nursemain.NurseMain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class LicenseService {

    @Autowired
    private LicenseRepository licenseRepository;

    public List<License> getByNurse(NurseMain nurseMain) {
        return licenseRepository.findByNurseId(nurseMain);
    }

    public License getById(Integer id) {
        return licenseRepository.findById(id).orElse(null);
    }

    @Transactional
    public License save(License license) {
        return licenseRepository.save(license);
    }

    @Transactional
    public void delete(Integer id) {
        licenseRepository.deleteById(id);
    }
}
