package com.HPMS.HPMS.nurse.nurseinformation;

import com.HPMS.HPMS.nurse.nursemain.NurseMain;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class NurseInformationService {

    private NurseInformationRepository nurseInformationRepository;

    public void create (NurseMain nurseMain, ) {
        NurseInformation nurseInformation = new NurseInformation();
        nurseInformation.setFirstName();
        nurseInformation.setLastName();
        nurseInformation.setMiddleName();
        nurseInformation.setTel();
        nurseInformation.setEmgcCntc();
        nurseInformation.setEmgcFName();
        nurseInformation.setEmgcLName();
        nurseInformation.setEmgcMName();
        nurseInformation.setEmgcRel();
        nurseInformation.setEmgcNote();
        nurseInformation.setEmail();
        nurseInformation.setPcd();
        nurseInformation.setDefAdd();
        nurseInformation.setDetAdd();
        nurseInformation.setRnNo();
        nurseInformation.setEdbc();
        nurseInformation.setGradDate();
        nurseInformation.setFl();
        nurseInformation.setMs();
        nurseInformation.setNatn();
        nurseInformation.setDss();
        nurseInformation.setCarr();
        nurseInformation.setPicture();
        nurseInformation.setNote();
        nurseInformation.setNurseMain();
        this.nurseInformationRepository.save(nurseInformation);
    }
}
