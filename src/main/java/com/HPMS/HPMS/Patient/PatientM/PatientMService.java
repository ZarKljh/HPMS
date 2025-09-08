package com.HPMS.HPMS.Patient.PatientM;

import com.HPMS.HPMS.Patient.PatientDTL.PatientDTL;
import com.HPMS.HPMS.Patient.PatientDTL.PatientDTLService;
import com.HPMS.HPMS.Patient.patientForm.PatientForm;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PatientMService {
    private final PatientMRepository patientMRepository;
    private final PatientDTLService patientDTLService;

    //모든 환자의 Main정보를 가져온다
    public List<PatientM> getAllPatientM(){
        return this.patientMRepository.findAll();
    }

    public Page<PatientM> getAllPatientM(Pageable pageable) {
        return this.patientMRepository.findAll(pageable);
    }

    public PatientM getPatientM(Integer id){
        Optional<PatientM> patientM = this.patientMRepository.findById(id);
        if (patientM.isPresent()) {
            return patientM.get();
        } else {
            throw new NoSuchElementException("Patient detail not found for patientId: " + patientM.get().getId());
            //throw new DataNotFoundException("question not found");
        }
    }

    public void deletePatientM(PatientM patientM){
        //환자종결구분자에 1을 입력한다
        patientM.setDelStatus(1);
        this.patientMRepository.save(patientM);
    }

    public void createPatientM(PatientForm pf){
        PatientM m = new PatientM();
        PatientDTL dtl = new PatientDTL();

        m.setFirstName(pf.getFirstName());
        m.setLastName(pf.getLastName());
        m.setMiddleName(pf.getMiddleName());
        m.setPassFirstName(pf.getPassFirstName());
        m.setPassLastName(pf.getLastName());
        m.setPassMiddleName(pf.getPassMiddleName());
        m.setGender(pf.getGender());
        m.setDayOfBirth(localDateToInteger(pf.getBirth()));
        m.setForeigner(pf.getForeigner());
        m.setPassport(pf.getPassport());
        m.setCreateDate(LocalDateTime.now());
        m.setUpdateDate(LocalDateTime.now());
        m.setDelStatus(0);

        this.patientMRepository.save(m);

        dtl.setPatientM(m);
        dtl.setMobilePhone(removeHyphen(pf.getMobilePhone()));
        dtl.setHomePhone(removeHyphen(pf.getHomePhone()));
        dtl.setFax(removeHyphen(pf.getFax()));
        dtl.setEmail(pf.getEmail());

        dtl.setGuardianFirstName(pf.getGuardianFirstName());
        dtl.setGuardianLastName(pf.getGuardianLastName());
        dtl.setGuardianMiddleName(pf.getGuardianMiddleName());
        dtl.setGuardianTel(removeHyphen(pf.getGuardianTel()));
        dtl.setGuardianRelation(pf.getGuardianRelation());

        dtl.setCurHomePCD(pf.getHomePcd());
        dtl.setCurHomeDefAdd(pf.getHomeDefAdd());
        dtl.setCurHomeDetAdd(pf.getHomeDetAdd());
        dtl.setRegHomePCD(pf.getRegPcd());
        dtl.setRegHomeDefAdd(pf.getRegDefAdd());
        dtl.setRegHomeDetAdd(pf.getRegDefAdd());

        dtl.setOccupation(pf.getOccupation());
        dtl.setLastVisitDate(LocalDateTime.now());

        dtl.setNatn(pf.getNatn());
        dtl.setNote(pf.getNote());

        this.patientDTLService.createPatientDTL(dtl);
    }

    public Integer modifyPatientM(PatientM m, PatientForm pf){
        //PatientM과 연결되어있는 PatientDTL 객체를 가져온다
        PatientDTL dtl = this.patientDTLService.getPatientDTLByPatientId(m);
        //정보수정을 위핸 PatientM 과 PatientDTL이 준비되었다

        //PatientForm에 들어있는 정보들을 PatientM 과 PatientDTL에 셋팅한다

        m.setFirstName(pf.getFirstName());
        m.setLastName(pf.getLastName());
        m.setMiddleName(pf.getMiddleName());
        m.setPassFirstName(pf.getPassFirstName());
        m.setPassLastName(pf.getLastName());
        m.setPassMiddleName(pf.getPassMiddleName());
        m.setGender(pf.getGender());
        m.setDayOfBirth(localDateToInteger(pf.getBirth()));
        m.setForeigner(pf.getForeigner());
        m.setPassport(pf.getPassport());
        //생성날짜는 변경할수 없다
        //m.setCreateDate(LocalDateTime.now());
        m.setUpdateDate(LocalDateTime.now());
        //환자삭제는 삭제기능에서 이루어진다
        //m.setDelStatus(0);

        this.patientMRepository.save(m);

        dtl.setPatientM(m);
        dtl.setMobilePhone(removeHyphen(pf.getMobilePhone()));
        dtl.setHomePhone(removeHyphen(pf.getHomePhone()));
        dtl.setOfficePhone(removeHyphen(pf.getOfficePhone()));
        dtl.setFax(removeHyphen(pf.getFax()));
        dtl.setEmail(pf.getEmail());

        dtl.setGuardianFirstName(pf.getGuardianFirstName());
        dtl.setGuardianLastName(pf.getGuardianLastName());
        dtl.setGuardianMiddleName(pf.getGuardianMiddleName());
        dtl.setGuardianTel(removeHyphen(pf.getGuardianTel()));
        dtl.setGuardianRelation(pf.getGuardianRelation());

        dtl.setCurHomePCD(pf.getHomePcd());
        dtl.setCurHomeDefAdd(pf.getHomeDefAdd());
        dtl.setCurHomeDetAdd(pf.getHomeDetAdd());
        dtl.setRegHomePCD(pf.getRegPcd());
        dtl.setRegHomeDefAdd(pf.getRegDefAdd());
        dtl.setRegHomeDetAdd(pf.getRegDefAdd());

        dtl.setOccupation(pf.getOccupation());
        dtl.setLastVisitDate(LocalDateTime.now());

        dtl.setNatn(pf.getNatn());
        dtl.setNote(pf.getNote());

        this.patientDTLService.modifyPatientDTL(dtl);

        return m.getId();
    }
    // LocalDate를 Integer YYYYMMDD 형태로 변환
    public Integer localDateToInteger(LocalDate date) {
        if (date == null) {
            return null;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String dateString = date.format(formatter);
        return Integer.valueOf(dateString);
    }
    public String removeHyphen(String phone) {
        if (phone == null) {
            return null; // null 체크
        }
        return phone.replace("-", "");
    }
}

