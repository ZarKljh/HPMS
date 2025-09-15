package com.HPMS.HPMS.nurse.nursedto;

import com.HPMS.HPMS.nurse.license.License;
import com.HPMS.HPMS.nurse.license.LicenseService;
import com.HPMS.HPMS.nurse.nurseinformation.NurseInformationService;
import com.HPMS.HPMS.nurse.nursemain.NurseMain;
import com.HPMS.HPMS.nurse.nursemain.NurseMainService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.List;

@RequestMapping("/nurse")
@Controller
@RequiredArgsConstructor
public class NurseDTOController {

    private final NurseDTOService nurseDTOService;
    private final NurseMainService nurseMainService;
    private final NurseInformationService nurseInformationService;
    private final LicenseService licenseService;

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_SYSTEM','ROLE_DOCTOR','ROLE_NURSE')")
    @GetMapping("/info/{nurseId}")
    public String showNurse(@PathVariable Integer nurseId, Model model) {
        NurseMain nurseMain = nurseMainService.getNurseMain(nurseId);
        NurseInformationDTO infoDTO = nurseInformationService.getInfoByNurse(nurseMain);

        NurseDTO nurseDTO = new NurseDTO(
                new NurseMainDTO(nurseMain),
                infoDTO
        );

        List<License> licenses = licenseService.getByNurse(nurseMain);
        List<NurseLicenseDTO> licenseDTOs = licenses.stream()
                .map(NurseLicenseDTO::new)
                .toList();

        nurseDTO.setLicenseList(licenseDTOs);
        model.addAttribute("nurseDTO", nurseDTO);

        return "nurse/nurse_information"; // 또는 "nurse/detail" 원하는 뷰로
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_SYSTEM','ROLE_DOCTOR','ROLE_NURSE')")
    @GetMapping("/modify/{id}")
    public String showModifyForm(@PathVariable Integer id, Model model) {
        NurseDTO nurseDTO = nurseDTOService.getNurseDTO(id);
        if (nurseDTO.getNurseInformation() == null) {
            nurseDTO.setNurseInformation(new NurseInformationDTO());
        }
        model.addAttribute("nurseDTO", nurseDTO);
        return "nurse/nurse_modify_form";
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_SYSTEM','ROLE_DOCTOR','ROLE_NURSE')")
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

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_SYSTEM','ROLE_DOCTOR','ROLE_NURSE')")
    @GetMapping("/delete/{id}")
    public String nurseDelete(Principal principal, @PathVariable("id") Integer id) {
        NurseDTO nurseDTO = this.nurseDTOService.getNurseDTO(id);
        if (!nurseDTO.getNurseMain().getFirstName().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
        }
        this.nurseDTOService.delete(nurseDTO);
        return "redirect:/nurse";
    }
}
