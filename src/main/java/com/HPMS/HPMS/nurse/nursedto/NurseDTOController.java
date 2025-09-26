package com.HPMS.HPMS.nurse.nursedto;

import com.HPMS.HPMS.nurse.file.FileService;
import com.HPMS.HPMS.nurse.license.License;
import com.HPMS.HPMS.nurse.license.LicenseService;
import com.HPMS.HPMS.nurse.nurseinformation.NurseInformationService;
import com.HPMS.HPMS.nurse.nursemain.NurseMain;
import com.HPMS.HPMS.nurse.nursemain.NurseMainService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RequestMapping("/nurse")
@Controller
@RequiredArgsConstructor
public class NurseDTOController {

    private final NurseDTOService nurseDTOService;
    private final NurseMainService nurseMainService;
    private final NurseInformationService nurseInformationService;
    private final LicenseService licenseService;
    private final FileService fileService;

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

        // 간호사 정보 없으면 새로 생성
        if (nurseDTO.getNurseInformation() == null) {
            nurseDTO.setNurseInformation(new NurseInformationDTO());
        }

        // 라이선스 리스트도 같이 가져오기
        List<License> licenses = licenseService.getByNurse(
                nurseMainService.getNurseMain(id)
        );
        List<NurseLicenseDTO> licenseDTOs = licenses.stream()
                .map(NurseLicenseDTO::new)
                .toList();
        nurseDTO.setLicenseList(licenseDTOs);

        model.addAttribute("nurseDTO", nurseDTO);
        return "nurse/nurse_modify_form";
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_SYSTEM','ROLE_DOCTOR','ROLE_NURSE')")
    @PostMapping("/modify/{id}")
    public String modifyNurse(@PathVariable Integer id, @ModelAttribute NurseDTO nurseDTO, @RequestParam(value = "pictureFile", required = false) MultipartFile pictureFile, Model model) throws IOException {
        // 업로드 파일 처리
        if (pictureFile != null && !pictureFile.isEmpty()) {
            String pictureUrl = fileService.saveUploadedFile(pictureFile);
            nurseDTO.getNurseInformation().setPicture(pictureUrl);
        }

        // 수정자 체크
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
    public String nurseDelete(@PathVariable("id") Integer id) {
        NurseDTO nurseDTO = this.nurseDTOService.getNurseDTO(id);
        this.nurseDTOService.delete(nurseDTO);
        return "redirect:/nurse";
    }
}
