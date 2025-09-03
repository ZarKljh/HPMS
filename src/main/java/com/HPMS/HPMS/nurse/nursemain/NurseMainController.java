package com.HPMS.HPMS.nurse.nursemain;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RequestMapping("/nurse")
@RequiredArgsConstructor
@Controller
public class NurseMainController {

    private final NurseMainService nurseMainService;

    @GetMapping("")
    public String list(Model model, @RequestParam(value="page", defaultValue="0") int page, HttpSession session) {
        session.removeAttribute("tempNurseMain");
        Page<NurseMain> paging = this.nurseMainService.getList(page);
        model.addAttribute("paging", paging);
        return "nurse/nurse_main";
    }

    @GetMapping("/create")
    public String createMainForm(Model model) {
        model.addAttribute("nurseMain", new NurseMain());
        return "nurse/nurse_main_form";
    }

    @PostMapping("/create")
    public String createMain(@ModelAttribute NurseMain nurseMain, HttpSession session) {
        LocalDateTime now = LocalDateTime.now();
        nurseMain.setCreateDate(now);
        nurseMain.setModifyDate(now);

        session.setAttribute("tempNurseMain", nurseMain);
        return "redirect:/nurse/create/info";
    }
}
