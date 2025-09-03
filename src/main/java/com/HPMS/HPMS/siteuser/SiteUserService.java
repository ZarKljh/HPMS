package com.HPMS.HPMS.siteuser;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static com.HPMS.HPMS.Patient.PatientDTO.PatientDTOService.formatPhoneNumber;

@RequiredArgsConstructor
@Service
public class SiteUserService {
    private final SiteUserRepository siteUserRepository;
    private final PasswordEncoder passwordEncoder;

    public SiteUser createSiteUser(SiteUserForm suf){
        SiteUser newsu = new SiteUser();

        newsu.setUserId(suf.getUserId());
        //패스워드 암호화
        newsu.setPassword(passwordEncoder.encode(suf.getPassword1()));

        newsu.setLastName(suf.getLastName());
        newsu.setFirstName(suf.getFirstName());
        newsu.setMiddleName(suf.getMiddleName());
        newsu.setMobilePhone((formatPhoneNumber(suf.getMobilePhone())));
        newsu.setEmail(suf.getEmail());
        newsu.setRole(suf.getRole());
        newsu.setRoleId(suf.getRoleId());
        newsu.setStatus("active");
        newsu.setCreateDate(LocalDateTime.now());

        this.siteUserRepository.save(newsu);
        return newsu;
    }
}
