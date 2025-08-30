package com.HPMS.HPMS.nurse.nursemain;

import com.HPMS.HPMS.nurse.nurseinformation.NurseInformationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/nurse")
@RequiredArgsConstructor
@Controller
public class NurseMainController {

    private final NurseMainService nurseMainService;
    private final NurseInformationService nurseInformationService;

    @GetMapping("")
    public String list(Model model) {
        List<NurseMain> nurseMainList = this.nurseMainService.getAll();
        model.addAttribute("nurseMainList", nurseMainList);
        return "nurse/nurse_main";
    }
}
