package com.HPMS.HPMS.nurse.nurseinformation;

import com.HPMS.HPMS.nurse.file.FileService;
import com.HPMS.HPMS.nurse.nursemain.NurseMain;
import com.HPMS.HPMS.nurse.nursemain.NurseMainService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequestMapping("/nurse/create/info")
@RequiredArgsConstructor
@Controller
public class NurseInformationController {

    private final NurseMainService nurseMainService;
    private final FileService fileService;

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
    public String createInfo(@ModelAttribute NurseInformation info, HttpSession session, @RequestParam("pictureFile") MultipartFile pictureFile) {
        NurseMain nurseMain = (NurseMain) session.getAttribute("tempNurseMain");
        if (nurseMain == null) {
            return "redirect:/nurse";
        }

        //파일 저장 처리
        if (pictureFile != null && !pictureFile.isEmpty()) {
            try {
                String fileUrl = fileService.saveUploadedFile(pictureFile);
                info.setPicture(fileUrl);
            } catch (IOException e) {
                e.printStackTrace();
                // 예외 처리: 메시지 반환 또는 기본 이미지 처리
                info.setPicture("/images/default.png");
            }
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
