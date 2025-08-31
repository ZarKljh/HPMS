package com.HPMS.HPMS.nurse.NurseDTO;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RequestMapping("/nurse")
@Controller
@RequiredArgsConstructor
public class NurseDTOController {

    private final NurseDTOService nurseDTOService;

    @GetMapping("/info/{nurseId}")
    public String getNurse(@PathVariable Integer nurseId, Model model) {
        NurseDTO nurseDTO = nurseDTOService.getNurseDTO(nurseId);
        model.addAttribute("nurseDTO", nurseDTO);
        return "nurse/nurse_information"; // thymeleaf 뷰 이름
    }

    @PostMapping("/modify/{id}")
    public String modifyNurse(@PathVariable Integer id, @ModelAttribute NurseDTO nurseDTO, Model model) {
        String modifier = nurseDTO.getNurseMain().getModifier();

        if (modifier == null || modifier.trim().isEmpty()) {
            model.addAttribute("errorMessage", "수정자 이름을 입력해야 합니다.");
            model.addAttribute("nurseDTO", nurseDTO);
            return "nurse/nurse_modify_form"; // 입력 폼 다시 보여주기
        }

        nurseDTOService.updateNurse(id, nurseDTO);
        return "redirect:/nurse/info/" + id;
    }

    @GetMapping("/modify/{id}")
    public String showModifyForm(@PathVariable Integer id, Model model) {
        NurseDTO nurseDTO = nurseDTOService.getNurseDTO(id);
        model.addAttribute("nurseDTO", nurseDTO);
        return "nurse/nurse_modify_form";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/delete/{id}")
    public String nurseDelete(Principal principal, @PathVariable("id") Integer id) {
        NurseDTO nurseDTO = this.nurseDTOService.getNurseDTO(id);
//        if (!nurseDTO.getNurseMain().getFirstName().equals(principal.getName())) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
//        }
        this.nurseDTOService.delete(nurseDTO);
        return "redirect:/nurse";
    }
}
