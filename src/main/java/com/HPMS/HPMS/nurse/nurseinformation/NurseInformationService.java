package com.HPMS.HPMS.nurse.nurseinformation;

import com.HPMS.HPMS.nurse.nursemain.NurseMain;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class NurseInformationService {

    private NurseInformationRepository nurseInformationRepository;

    public NurseInformation create (NurseMain nurseMain, String firstName, String lastName, String middleName, String tel, String emgcCntc, String emgcFName, String emgcLName, String emgcMName, String emgcRel, String emgcNote, String email, Integer pcd, String defAdd, String detAdd, String rnNo, String edbc, Integer gradDate, String fl, String ms, String natn, String dss, String carr, String picture, String note) {
        NurseInformation nurseInformation = new NurseInformation();
        nurseInformation.setFirstName(firstName);
        nurseInformation.setLastName(lastName);
        nurseInformation.setMiddleName(middleName);
        nurseInformation.setTel(tel);
        nurseInformation.setEmgcCntc(emgcCntc);
        nurseInformation.setEmgcFName(emgcFName);
        nurseInformation.setEmgcLName(emgcLName);
        nurseInformation.setEmgcMName(emgcMName);
        nurseInformation.setEmgcRel(emgcRel);
        nurseInformation.setEmgcNote(emgcNote);
        nurseInformation.setEmail(email);
        nurseInformation.setPcd(pcd);
        nurseInformation.setDefAdd(defAdd);
        nurseInformation.setDetAdd(detAdd);
        nurseInformation.setRnNo(rnNo);
        nurseInformation.setEdbc(edbc);
        nurseInformation.setGradDate(gradDate);
        nurseInformation.setFl(fl);
        nurseInformation.setMs(ms);
        nurseInformation.setNatn(natn);
        nurseInformation.setDss(dss);
        nurseInformation.setCarr(carr);
        nurseInformation.setPicture(picture);
        nurseInformation.setNote(note);
        nurseInformation.setNurseMain(nurseMain);
        return this.nurseInformationRepository.save(nurseInformation);
    }

    public NurseInformation findByNurseMainId(Integer id) {
        return nurseInformationRepository.findByNurseMain_Id(id)
                .orElse(null);
    }
}
