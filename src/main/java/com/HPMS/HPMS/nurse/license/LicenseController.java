package com.HPMS.HPMS.nurse.license;

import com.HPMS.HPMS.nurse.nursedto.NurseDTO;
import com.HPMS.HPMS.nurse.nursedto.NurseInformationDTO;
import com.HPMS.HPMS.nurse.nursedto.NurseLicenseDTO;
import com.HPMS.HPMS.nurse.nursedto.NurseMainDTO;
import com.HPMS.HPMS.nurse.nursemain.NurseMain;
import com.HPMS.HPMS.nurse.nursemain.NurseMainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/nurse/license")
public class LicenseController {

    private final NurseMainService nurseMainService;
    private final LicenseService licenseService;

    @GetMapping("/info/{nurseId}")
    public String getNurseInfo(@PathVariable Integer nurseId, Model model) {
        NurseMain nurseMain = nurseMainService.getNurseMain(nurseId);

        NurseDTO nurseDTO = new NurseDTO(
                new NurseMainDTO(nurseMain),                         // Entity → DTO
                new NurseInformationDTO(nurseMain.getNurseInformation())
        );

        List<License> licenses = licenseService.getByNurse(nurseMain);
        List<NurseLicenseDTO> licenseDTOList = licenses.stream().map(l -> {
            NurseLicenseDTO dto = new NurseLicenseDTO();
            dto.setId(l.getId());
            dto.setLi(l.getLi());
            dto.setLicenseNo(l.getLicenseNo());
            dto.setIssueDate(LocalDate.parse(l.getIssueDate()));      // LocalDate로 그대로 세팅
            dto.setExpiryDate(LocalDate.parse(l.getExpiryDate()));    // LocalDate로 그대로 세팅
            dto.setNote(l.getNote());
            return dto;
        }).toList();

        nurseDTO.setLicenseList(licenseDTOList);
        model.addAttribute("nurseDTO", nurseDTO);

        return "nurse/info";
    }

    // 인라인 수정 저장
    @PostMapping("/updateAll/{nurseId}")
    public String updateAllLicenses(@PathVariable Integer nurseId,
                                    @ModelAttribute NurseDTO nurseDTO) {
        // 1. NurseMain Entity 조회
        NurseMain nurse = nurseMainService.getNurseMain(nurseId);

        // 2. License 리스트 처리
        if (nurseDTO.getLicenseList() != null) {
            for (NurseLicenseDTO licenseDTO : nurseDTO.getLicenseList()) {
                License license;

                // 2-1. 기존 데이터 업데이트 / 신규 생성
                if (licenseDTO.getId() != null) {
                    license = licenseService.getById(licenseDTO.getId());
                } else {
                    license = new License();
                }

                // 2-2. DTO -> Entity 매핑
                license.setLi(licenseDTO.getLi());
                license.setLicenseNo(licenseDTO.getLicenseNo());
                license.setIssueDate(licenseDTO.getIssueDate().format(DateTimeFormatter.BASIC_ISO_DATE));
                license.setExpiryDate(licenseDTO.getExpiryDate().format(DateTimeFormatter.BASIC_ISO_DATE));
                license.setNote(licenseDTO.getNote());
                license.setNurse(nurse);

                // 2-3. 저장
                licenseService.save(license);
            }
        }
        return "redirect:/nurse/info/" + nurseId;
    }

    // 인라인 추가
    @PostMapping("/{nurseId}")
    public String addLicense(@PathVariable Integer nurseId, License license) {
        NurseMain nurse = nurseMainService.getNurseMain(nurseId);
        license.setNurse(nurse);
        licenseService.save(license);
        return "redirect:/nurse/info/" + nurseId;
    }

    // 삭제
    @GetMapping("/delete/{licenseId}/{nurseId}")
    public String deleteLicense(@PathVariable Integer licenseId, @PathVariable Integer nurseId) {
        licenseService.delete(licenseId);
        return "redirect:/nurse/info/" + nurseId;
    }
}
