package com.HPMS.HPMS.nurse.license;

import com.HPMS.HPMS.nurse.nursedto.NurseDTO;
import com.HPMS.HPMS.nurse.nursemain.NurseMain;
import com.HPMS.HPMS.nurse.nursemain.NurseMainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/nurse/license")
public class LicenseController {

    private final NurseMainService nurseMainService;
    private final LicenseService licenseService;

    // 인라인 수정 저장
    @PostMapping("/updateAll/{nurseId}")
    public String updateAllLicenses(@PathVariable Integer nurseId,
                                    @ModelAttribute NurseDTO nurseDTO) {
        NurseMain nurse = nurseMainService.getNurseMain(nurseId);
        if (nurseDTO.getLicenseList() != null) {
            for (License license : nurseDTO.getLicenseList()) {
                license.setNurseId(nurse);
                licenseService.save(license);
            }
        }
        return "redirect:/nurse/info/" + nurseId;
    }

    // 인라인 추가
    @PostMapping("/{nurseId}")
    public String addLicense(@PathVariable Integer nurseId, License license) {
        NurseMain nurse = nurseMainService.getNurseMain(nurseId);
        license.setNurseId(nurse);
        licenseService.save(license);
        return "redirect:/nurse/info/" + nurseId;
    }

    // 삭제
    @GetMapping("/delete/{licenseId}/{nurseId}")
    public String deleteLicense(@PathVariable Integer licenseId, @PathVariable Integer nurseId) {
        licenseService.delete(licenseId);
        return "redirect:/nurse/info/" + nurseId;
    }
}
