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
            dto.setMobilePhone(formatPhoneNumber(dtl.getMobilePhone()));
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
        detailDTO.setFirstName(m.getFirstName());
        detailDTO.setLastName(m.getLastName());
        detailDTO.setMiddleName(m.getMiddleName());
        detailDTO.setPassFirstName(m.getPassFirstName());
        detailDTO.setPassLastName(m.getPassLastName());
        detailDTO.setMiddleName(m.getPassMiddleName());

        detailDTO.setMobilePhone(formatPhoneNumber(dtl.getMobilePhone()));
        detailDTO.setHomePhone(dtl.getHomePhone());
        detailDTO.setOfficePhone(formatPhoneNumber(dtl.getOfficePhone()));
        detailDTO.setEmail(dtl.getEmail());
        detailDTO.setFax(formatPhoneNumber(dtl.getFax()));

        detailDTO.setGuardianFirstName(dtl.getGuardianFirstName());
        detailDTO.setGuardianLastName(dtl.getGuardianLastName());
        detailDTO.setGuardianMiddleName(dtl.getGuardianMiddleName());
        detailDTO.setGuardianTel(formatPhoneNumber(dtl.getGuardianTel()));
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

    /**
     * 전화번호를 포맷팅하는 메소드
     * @param phoneNumber DB에서 가져온 원본 전화번호 (숫자 문자열, 0으로 시작)
     * @return 하이픈(-)이 포함된 전화번호 문자열
     */
    public static String formatPhoneNumber(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.length() < 4) {
            return phoneNumber; // 잘못된 데이터는 그대로 반환
        }

        // 마지막 4자리 분리
        String lastFour = phoneNumber.substring(phoneNumber.length() - 4);
        String remain = phoneNumber.substring(0, phoneNumber.length() - 4);

        int remainLength = remain.length();
        String formatted = null;

        switch (remainLength) {
            case 5:
                // xx-xxx-xxxx
                formatted = remain.substring(0, 2) + "-" +
                        remain.substring(2) + "-" +
                        lastFour;
                break;

            case 6:
                // xx-xxxx-xxxx
                if (remain.startsWith("02")) {
                    // 02-xxxx-xxxx
                    formatted = remain.substring(0, 2) + "-" +
                            remain.substring(2) + "-" +
                            lastFour;
                } else {
                    // xxx-xxx-xxxx
                    formatted = remain.substring(0, 3) + "-" +
                            remain.substring(3) + "-" +
                            lastFour;
                }
                break;

            case 7:
                // xxx-xxxx-xxxx
                formatted = remain.substring(0, 3) + "-" +
                        remain.substring(3) + "-" +
                        lastFour;
                break;

            case 8:
                // xxxx-xxxx-xxxx
                formatted = remain.substring(0, 4) + "-" +
                        remain.substring(4) + "-" +
                        lastFour;
                break;

            default:
                // 규칙에 없는 경우는 그대로 반환
                formatted = phoneNumber;
        }
        return formatted;
    }
}
