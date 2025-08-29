package com.HPMS.HPMS.nurse.nursemain;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/nurse")
@RequiredArgsConstructor
@Controller
public class NurseMainController {

    @Autowired
    private final NurseMainService nurseMainService;

    @GetMapping("")
    private String list(Model model) {
        List<NurseMain> nurseMainList = this.nurseMainService.getAll();
        model.addAttribute("nurseMainList", nurseMainList);
        return "nurse/nurse_main";
    }

    @GetMapping(value = "/information/{id}")
    public String information(Model model, @PathVariable("id") Integer id) {
        NurseMain nurseMain = this.nurseMainService.getNurseMain(id);
        model.addAttribute("nurseMain", nurseMain);
        return "nurse/nurse_information";
    }
}
