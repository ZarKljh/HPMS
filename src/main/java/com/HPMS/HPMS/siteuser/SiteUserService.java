package com.HPMS.HPMS.siteuser;

import com.HPMS.HPMS.Doctor.DoctorM.DoctorM;
import com.HPMS.HPMS.Doctor.DoctorM.DoctorMService;
import com.HPMS.HPMS.nurse.nurseinformation.NurseInformation;
import com.HPMS.HPMS.nurse.nursemain.NurseMain;
import com.HPMS.HPMS.nurse.nursemain.NurseMainService;
import com.HPMS.HPMS.reference_personnel.reference_personnel_m.ReferencePersonnelM;
import com.HPMS.HPMS.reference_personnel.reference_personnel_m.ReferencePersonnelMService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static com.HPMS.HPMS.Patient.PatientDTO.PatientDTOService.formatPhoneNumber;
import static com.HPMS.HPMS.Patient.PatientDTO.PatientDTOService.getOnlyDigitNumber;

@RequiredArgsConstructor
@Service
public class SiteUserService {
    private final SiteUserRepository siteUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final NurseMainService nurseMainService;
    private final DoctorMService doctorMService;
    private final ReferencePersonnelMService referencePersonnelMService;

    public SiteUser createSiteUser(SiteUserForm suf){
        SiteUser newsu = new SiteUser();

        newsu.setUserId(suf.getUserId());
        //패스워드 암호화
        newsu.setPassword(passwordEncoder.encode(suf.getPassword1()));

        newsu.setLastName(suf.getLastName());
        newsu.setFirstName(suf.getFirstName());
        newsu.setMiddleName(suf.getMiddleName());

        //전화번호 내에 있는 '-' 을 제거
        String formatedPhoneNumber = getOnlyDigitNumber(suf.getMobilePhone());

        newsu.setMobilePhone(formatedPhoneNumber);
        newsu.setEmail(suf.getEmail());

        //성명과 전화번호를 이용해서, role 과 roleid를 가져오는 로직
        RoleAndId roleAndId = getRoleAndID(suf, formatedPhoneNumber);

        newsu.setRole(roleAndId.getRole());
        newsu.setRoleId(roleAndId.getRoleId());
        newsu.setStatus("active");
        newsu.setCreateDate(LocalDateTime.now());

        this.siteUserRepository.save(newsu);

        return newsu;
    }

    //signupForm으로 부터 받아온 firstName 과 lastName 과 휴대전화번호로 Role/테이블ID를 가져오는 메소드
    public RoleAndId getRoleAndID(SiteUserForm suf, String formatedPhoneNumber){
        String firstName = suf.getFirstName();
        String lastName = suf.getLastName();
        RoleAndId roleAndId = new RoleAndId();

        //DoctorM doctorM = this.doctorMService.getDoctorMByNameAndTelephone(firstName, lastName, formatedPhoneNumber);
        DoctorM doctorM = this.doctorMService.getDoctorMByNameAndTelephone(firstName, lastName, formatPhoneNumber(formatedPhoneNumber));
        ReferencePersonnelM referencePersonnelM1 = this.referencePersonnelMService.getReferencePersonnelMByNameAndCellPhone(firstName, lastName, formatedPhoneNumber);
        if( doctorM!= null){
            roleAndId.role = "ROLE_DOCTOR";
            roleAndId.roleId = doctorM.getId();
        } else if( referencePersonnelM1 !=null ){
            roleAndId.role = "ROLE_SYSTEM";
            roleAndId.roleId = referencePersonnelM1.getId();
        } else {
            List<NurseMain> nurseMains = this.nurseMainService.getNurseMainByName(firstName, lastName);
            for (NurseMain nurseMain : nurseMains) {
                if (nurseMain.getNurseInformation().getTel().equals(formatedPhoneNumber)) {
                    roleAndId.role = "ROLE_NURSE";
                    roleAndId.roleId = nurseMain.getId();
                }
            }
        }
        return roleAndId;
    }

    //Role과 RoleId를 가져오기 위한 클래스
    @Getter
    @Setter
    public static class RoleAndId {
        String role;
        Integer roleId;

        //초기값-역할 아무권한이 없는 ROLE_USER로 설정
        RoleAndId(){
            this.role = "ROLE_USER";
            this.roleId = 0;
        }
    }

}

