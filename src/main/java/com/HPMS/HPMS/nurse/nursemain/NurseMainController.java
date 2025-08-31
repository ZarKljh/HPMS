package com.HPMS.HPMS.nurse.nursemain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;

@RequestMapping("/nurse")
@RequiredArgsConstructor
@Controller
public class NurseMainController {

    private final NurseMainService nurseMainService;

    @GetMapping("")
    public String list(Model model) {
        model.addAttribute("nurseMainList", nurseMainService.getAll());
        return "nurse/nurse_main";
    }

    @GetMapping("/create")
    public String createMainForm(Model model) {
        model.addAttribute("nurseMain", new NurseMain());
        return "nurse/nurse_main_form";
    }

    @PostMapping("/create")
    public String createMain(@ModelAttribute NurseMain nurseMain) {
        LocalDateTime now = LocalDateTime.now();
        nurseMain.setCreateDate(now);
        nurseMain.setModifyDate(now);

        NurseMain savedMain = nurseMainService.save(nurseMain);
        return "redirect:/nurse/create/info/" + savedMain.getId();
    }
}
