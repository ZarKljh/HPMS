package com.HPMS.HPMS.nurse.NurseDTO;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class NurseDTOController {

    private final NurseDTOService nurseDTOService;

    @GetMapping("/nurse/info/{nurseId}")
    public String getNurse(@PathVariable Integer nurseId, Model model) {
        NurseDTO nurseDTO = nurseDTOService.getNurseDTO(nurseId);
        model.addAttribute("nurseDTO", nurseDTO);
        return "nurse/nurse_information"; // thymeleaf 뷰 이름
    }
}
