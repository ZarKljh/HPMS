package com.HPMS.HPMS.Doctor.DoctorM;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/*
@RequestMapping("/doctor")
@RequiredArgsConstructor
@Controller
public class DoctorMController {
    private final DoctorMService DoctorMService;

    @GetMapping("/doctor/list")
    public String list(Model model, @RequestParam(value = "page", defaultValue = "0") int page,
                       @RequestParam(value = "kw", defaultValue = "") String kw){
        Page<DoctorM> paging = this.DoctorMService.getList(page,kw);
        model.addAttribute("paging", paging);
        model.addAttribute("kw", kw);
        return "doctorM_list";
    }
}

 */
