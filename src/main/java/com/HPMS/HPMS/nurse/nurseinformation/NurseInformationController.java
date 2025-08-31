package com.HPMS.HPMS.nurse.nurseinformation;

import com.HPMS.HPMS.nurse.nursemain.NurseMain;
import com.HPMS.HPMS.nurse.nursemain.NurseMainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/nurse/create/info")
@RequiredArgsConstructor
@Controller
public class NurseInformationController {

    private final NurseMainService nurseMainService;
    private final NurseInformationService nurseInformationService;

    @GetMapping("/{nurseId}")
    public String createForm(@PathVariable Integer nurseId, Model model) {
        NurseMain main = nurseMainService.getNurseMain(nurseId);
        model.addAttribute("nurseMain", main);
        model.addAttribute("nurseId", main.getId());
        model.addAttribute("nurseInformation", new NurseInformation());
        return "nurse/nurse_info_form";
    }

    @PostMapping("/{nurseId}")
    public String createInfo(@PathVariable Integer nurseId,
                             @ModelAttribute NurseInformation info) {
        NurseMain main = nurseMainService.getNurseMain(nurseId);

        // 양방향 연결
        info.setNurseMain(main);
        main.setNurseInformation(info);

        // main 저장 → cascade.ALL로 info도 저장
        nurseMainService.save(main);

        return "redirect:/nurse";
    }
}
