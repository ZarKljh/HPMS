package com.HPMS.HPMS.Patient.PatientH;

import com.HPMS.HPMS.Patient.PatientDTL.PatientDTL;
import com.HPMS.HPMS.Patient.PatientM.PatientM;
import jakarta.persistence.PostPersist;
import jakarta.persistence.PostUpdate;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;

@RequiredArgsConstructor
public class PatientEntityListener {
    private final PatientHRepository patientHRepository;

    public void saveHistory(Object entity){
        PatientM patientM = null;

        if(entity instanceof PatientM m){
            patientM = m;
        } else if(entity instanceof PatientDTL dtl){
            patientM = dtl.getPatientM();
        }
        if(patientM != null){
            PatientH patientH = createPatientHistory(patientM);
            this.patientHRepository.save(patientH);
        }
    }

    private PatientH createPatientHistory(PatientM patientM){
        PatientH patientH = new PatientH();

        patientH.setPatientM(patientM);
        patientH.setFirstName(patientM.getFirstName());
        patientH.setLastName(patientM.getLastName());
        patientH.setMiddleName(patientM.getMiddleName());
        patientH.setPassFirstName(patientM.getPassFirstName());
        patientH.setPassLastName(patientM.getPassLastName());
        patientH.setPassMiddleName(patientM.getPassMiddleName());
        patientH.setGender(patientM.getGender());
        patientH.setDayOfBirth(patientM.getDayOfBirth());
        patientH.setForeigner(Integer.valueOf(patientM.getForeigner()));
        patientH.setPassport(patientM.getPassport());
        patientH.setCreateDate(patientM.getCreateDate());
        patientH.setUpdateDate(patientM.getUpdateDate());
        patientH.setDelStatus(patientM.getDelStatus());

        if (patientM.getPatientDTL() != null) {
            PatientDTL dtl = patientM.getPatientDTL();
            patientH.setMobilePhone(dtl.getMobilePhone());
            patientH.setHomePhone(dtl.getHomePhone());
            patientH.setOfficePhone(dtl.getOfficePhone());
            patientH.setEmail(dtl.getEmail());
            patientH.setFax(dtl.getFax());
            patientH.setGuradianTel(dtl.getGuardianTel());
            patientH.setGuradianRelation(dtl.getGuardianRelation());
            patientH.setGuradianFirstName(dtl.getGuardianFirstName());
            patientH.setGuradianLastName(dtl.getGuardianLastName());
            patientH.setGuradianMiddleName(dtl.getGuardianMiddleName());
            patientH.setCurHomePcd(dtl.getCurHomePCD());
            patientH.setCurHomeDefAdd(dtl.getCurHomeDefAdd());
            patientH.setCurHomeDetAdd(dtl.getCurHomeDetAdd());
            patientH.setRegHomePcd(dtl.getRegHomePCD());
            patientH.setRegHomeDefAdd(dtl.getRegHomeDefAdd());
            patientH.setRegHomeDetAdd(dtl.getRegHomeDetAdd());
            patientH.setOccupation(dtl.getOccupation());
            patientH.setLastVisitDate(dtl.getLastVisitDate());
            patientH.setNatn(dtl.getNatn());
            patientH.setNote(dtl.getNote());
        }
        return patientH;
    }


}
