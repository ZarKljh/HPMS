package com.HPMS.HPMS.nurse.license;

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

    @GetMapping("/{nurseId}")
    public String licenseForm(@PathVariable Integer nurseId, Model model) {
        NurseMain nurseMain = nurseMainService.getNurseMain(nurseId);

        List<License> licenseList = licenseService.getByNurse(nurseMain);

        model.addAttribute("nurseMain", nurseMain);
        model.addAttribute("license", new License());
        model.addAttribute("licenseList", licenseList);

        return "nurse/license_form"; // Thymeleaf 폼 페이지
    }

    @PostMapping("/{nurseId}")
    public String addLicense(@PathVariable Integer nurseId, @ModelAttribute License license) {
        NurseMain nurse = nurseMainService.getNurseMain(nurseId); // 서비스 메서드 사용
        license.setNurseId(nurse); // 양방향 연결은 필요시 NurseMain에도 추가 가능
        licenseService.save(license);
        return "redirect:/nurse/info/" + nurseId; // 저장 후 상세페이지로 리다이렉트
    }
}
