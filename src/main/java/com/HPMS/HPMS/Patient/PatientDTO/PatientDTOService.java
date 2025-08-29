package com.HPMS.HPMS.Patient.PatientDTO;

import com.HPMS.HPMS.Patient.PatientDTL.PatientDTL;
import com.HPMS.HPMS.Patient.PatientDTL.PatientDTLService;
import com.HPMS.HPMS.Patient.PatientDTO.PatientDetailDTO.PatientDetailDTO;
import com.HPMS.HPMS.Patient.PatientDTO.PatientListDTO.PatientListDTO;
import com.HPMS.HPMS.Patient.PatientM.PatientM;
import com.HPMS.HPMS.Patient.PatientM.PatientMService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PatientDTOService {

    private final PatientMService patientMService;
    private final PatientDTLService patientDTLService;

    public List<PatientListDTO> getPatientListDTO(){

        //환자메인정보 모든 리스트를 service를 통해 가져온다
        List<PatientM> patientMs = this.patientMService.getAllPatientM();
        //환자리스트html전용 DTO를 담아놓을 신규 List를 생성한다
        List<PatientListDTO> dtoList = new ArrayList<>();
        //날짜출력용 포멧을 정해놓았다
        //DateTimeFormatter birthFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        for( PatientM m : patientMs){
            //환자상세정보를 id를 통해 가져온다
            PatientDTL dtl = this.patientDTLService.getPatientDTLByPatientId(m);

            //환자리스트html 전용 DTO 객체를 선언한다
            PatientListDTO dto = new PatientListDTO();

            dto.setId(m.getId());
            dto.setName(m.getLastName() + " " + m.getFirstName());
            dto.setGender(m.getGender());
            dto.setBirth(stringToLocalDate(m.getDayOfBirth()));
            dto.setForeigner(m.getForeigner());
            dto.setMobilePhone(dtl.getMobilePhone());
            dto.setGuardianTel(dtl.getGuardianTel());
            dto.setLastVisitDate(dtl.getLastVisitDate());
            dto.setCreateDate(m.getCreateDate());

            dtoList.add(dto);
        }
        return dtoList;
    }


    public PatientDetailDTO getPatientDetailDTO(Integer id){

        //환자 1명의 main정보를 가져온다
        PatientM m = this.patientMService.getPatientM(id);
        //환자 1명의 상세정보를 화면에 띄우기 위해 비어있는 DTO객체를 준비한다
        PatientDetailDTO detailDTO = new PatientDetailDTO();

        //환자 1명의 main정보를 바탕으로 환자상세정보를 가져온다.
        PatientDTL dtl = this.patientDTLService.getPatientDTLByPatientId(m);

        detailDTO.setId(m.getId());
        if(m.getDelStatus() == 1){
            detailDTO.setDelStatus("종결");
        } else detailDTO.setDelStatus("");
        detailDTO.setCreateDate(m.getCreateDate());
        detailDTO.setUpdateDate(m.getUpdateDate());

        detailDTO.setBirth(stringToLocalDate(m.getDayOfBirth()));
        detailDTO.setGender(m.getGender());
        detailDTO.setForeigner(m.getForeigner());
        detailDTO.setName(m.getLastName() + " " + m.getFirstName());
        detailDTO.setMiddleName(m.getMiddleName());
        detailDTO.setPassName(m.getPassLastName() + " " + m.getPassFirstName());
        detailDTO.setMiddleName(m.getPassMiddleName());

        detailDTO.setMobilePhone(dtl.getMobilePhone());
        detailDTO.setHomePhone(dtl.getHomePhone());
        detailDTO.setOfficePhone(dtl.getOfficePhone());
        detailDTO.setEmail(dtl.getEmail());
        detailDTO.setFax(dtl.getFax());

        detailDTO.setGuardianName(dtl.getGuardianLastName() + " " + dtl.getGuardianFirstName());
        detailDTO.setGuardianMiddleName(dtl.getGuardianMiddleName());
        detailDTO.setGuardianTel(dtl.getGuardianTel());
        detailDTO.setGuardianRelation(dtl.getGuardianRelation());

        detailDTO.setHomePcd(dtl.getCurHomePCD());
        detailDTO.setHomeDefAdd(dtl.getCurHomeDefAdd());
        detailDTO.setHomeDetAdd(dtl.getCurHomeDetAdd());

        detailDTO.setRegPcd(dtl.getRegHomePCD());
        detailDTO.setRegDefAdd(dtl.getRegHomeDefAdd());
        detailDTO.setRegDetAdd(dtl.getRegHomeDetAdd());

        detailDTO.setOccupation(dtl.getOccupation());
        detailDTO.setNatn(dtl.getNatn());
        detailDTO.setLastVisitDate(dtl.getLastVisitDate());
        detailDTO.setNote(dtl.getNote());

        return detailDTO;
    }

    //Integer 형으로 되어있는 날짜를 LocalDate로 변환하는 메소드
    public LocalDate stringToLocalDate(Integer dayOfbirth){
        //날짜출력용 포멧을 정해놓았다
        DateTimeFormatter birthFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");

        String birthDateString = String.valueOf(dayOfbirth);

        return LocalDate.parse(birthDateString, birthFormatter);
    }
}
