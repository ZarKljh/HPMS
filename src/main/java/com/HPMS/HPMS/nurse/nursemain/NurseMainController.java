package com.HPMS.HPMS.nurse.nursemain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class NurseMainController {

    @Autowired
    private NurseMainRepository nurseMainRepository;

    @GetMapping("/nurse/list")
    private String list(Model model) {
        List<NurseMain> nurseMainList = this.nurseMainRepository.findAll();
        model.addAttribute("nurseMainList", nurseMainList);
        return "nurse/nurse_main";
    }
}
