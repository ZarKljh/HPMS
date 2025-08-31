package com.HPMS.HPMS.nurse.nurseinformation;

import com.HPMS.HPMS.nurse.nursemain.NurseMainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping("/nurse")
@RequiredArgsConstructor
@Controller
public class NurseInformationController {

    private final NurseMainService nurseMainService;
    private final NurseInformationService nurseInformationService;

    @GetMapping("/create/info/{nurseId}")
    public String createInfoForm(@PathVariable Integer nurseId, Model model) {
        model.addAttribute("nurseId", nurseId);
        model.addAttribute("nurseInformation", new NurseInformation());
        return "nurse/nurse_info_form";
    }

    @PostMapping("/create/info/{nurseId}")
    public String createInfo(@PathVariable Integer nurseId,
                             @ModelAttribute NurseInformation nurseInformation,
                             @RequestParam("pictureFile") MultipartFile pictureFile) throws Exception {

        nurseInformationService.saveWithMainAndFile(nurseId, nurseInformation, pictureFile);
        return "redirect:/nurse/info/" + nurseId; // 등록 후 상세페이지로 이동
    }
}
