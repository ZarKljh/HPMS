package com.HPMS.HPMS.siteuser;

import com.HPMS.HPMS.nurse.nurseinformation.NurseInformation;
import com.HPMS.HPMS.nurse.nursemain.NurseMain;
import com.HPMS.HPMS.nurse.nursemain.NurseMainService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static com.HPMS.HPMS.Patient.PatientDTO.PatientDTOService.formatPhoneNumber;

@RequiredArgsConstructor
@Service
public class SiteUserService {
    private final SiteUserRepository siteUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final NurseMainService nurseMainService;

    public SiteUser createSiteUser(SiteUserForm suf){
        SiteUser newsu = new SiteUser();

        newsu.setUserId(suf.getUserId());
        //패스워드 암호화
        newsu.setPassword(passwordEncoder.encode(suf.getPassword1()));

        newsu.setLastName(suf.getLastName());
        newsu.setFirstName(suf.getFirstName());
        newsu.setMiddleName(suf.getMiddleName());

        //전화번호 내에 있는 '-' 을 제거
        String formatedPhoneNumber = formatPhoneNumber(suf.getMobilePhone());

        newsu.setMobilePhone(formatedPhoneNumber);
        newsu.setEmail(suf.getEmail());

        //성명과 전화번호를 이용해서, role 과 roleid를 가져오는 로직

        newsu.setRole(suf.getRole());
        newsu.setRoleId(suf.getRoleId());
        newsu.setStatus("active");
        newsu.setCreateDate(LocalDateTime.now());

        this.siteUserRepository.save(newsu);
        return newsu;
    }

    public RoleAndId getRoleAndID(SiteUserForm suf, String formatedPhoneNumber){
        String firstName = suf.getFirstName();
        String lastName = suf.getLastName();
        RoleAndId roleAndId = new RoleAndId();




        List<NurseMain> nurseMains = this.nurseMainService.getNurseMainByName(firstName, lastName);
        for(NurseMain nurseMain: nurseMains){
            if(nurseMain.getNurseInformation().getTel().equals(formatedPhoneNumber)){
                roleAndId.role = "ROLE_NURSE";
                roleAndId.roleId = nurseMain.getId();

            }
        }

        return roleAndId;
    }


    public class RoleAndId {
        String role;
        Integer roleId;

        RoleAndId(){
            this.role = "ROLE_USER";
            this.roleId = 0;
        }
    }

}

