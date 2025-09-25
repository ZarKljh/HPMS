package com.HPMS.HPMS.nurse.nurseinformation;

import com.HPMS.HPMS.nurse.nursemain.NurseMain;
import com.HPMS.HPMS.nurse.nursemain.NurseMainService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/nurse/create/info")
@RequiredArgsConstructor
@Controller
public class NurseInformationController {

    private final NurseMainService nurseMainService;

    // application.properties에서 업로드 경로 설정
    @Value("${file.upload.directory:uploads/nurse/pictures}")
    private String uploadDirectory;

    @Value("${file.upload.base-url:http://localhost:8080}")
    private String baseUrl;

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_SYSTEM','ROLE_DOCTOR','ROLE_NURSE')")
    @GetMapping("")
    public String createInfoForm(Model model, HttpSession session) {
        NurseMain nurseMain = (NurseMain) session.getAttribute("tempNurseMain");
        if (nurseMain == null) return "redirect:/nurse";

        model.addAttribute("nurseMain", nurseMain);
        model.addAttribute("nurseInformation", new NurseInformation());
        model.addAttribute("nurseId", nurseMain.getId());
        return "nurse/nurse_info_form";
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_SYSTEM','ROLE_DOCTOR','ROLE_NURSE')")
    @PostMapping("")
    public String createInfo(@ModelAttribute NurseInformation info, HttpSession session) {
        NurseMain nurseMain = (NurseMain) session.getAttribute("tempNurseMain");
        if (nurseMain == null) {
            return "redirect:/nurse";
        }

        // id가 null이면 신규 저장
        if (nurseMain.getId() == null) {
            nurseMainService.save(nurseMain); // insert
        } else {
            // 기존 NurseMain이면 DB에서 조회
            nurseMain = nurseMainService.findById(nurseMain.getId());
        }

        // 양방향 연결
        info.setNurseMain(nurseMain);
        nurseMain.setNurseInformation(info);

        nurseMainService.save(nurseMain);

        session.removeAttribute("tempNurseMain");

        return "redirect:/nurse/info/" + nurseMain.getId();
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_SYSTEM','ROLE_DOCTOR','ROLE_NURSE')")
    @GetMapping("/cancel")
    public String cancelCreation(HttpSession session) {
        session.removeAttribute("tempNurseMain");
        return "redirect:/nurse";
    }
}
