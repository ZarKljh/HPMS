package com.HPMS.HPMS.nurse.license;

import com.HPMS.HPMS.nurse.nursedto.NurseDTO;
import com.HPMS.HPMS.nurse.nursedto.NurseInformationDTO;
import com.HPMS.HPMS.nurse.nursedto.NurseLicenseDTO;
import com.HPMS.HPMS.nurse.nursedto.NurseMainDTO;
import com.HPMS.HPMS.nurse.nursemain.NurseMain;
import com.HPMS.HPMS.nurse.nursemain.NurseMainService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Controller
@RequestMapping("/nurse/license")
public class LicenseController {

    private final NurseMainService nurseMainService;
    private final LicenseService licenseService;

    //@PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_SYSTEM','ROLE_DOCTOR','ROLE_NURSE')")
    @GetMapping("/info/{nurseId}")
    public String getNurseInfo(@PathVariable Integer nurseId, Model model) {
        NurseMain nurseMain = nurseMainService.getNurseMain(nurseId);

        NurseDTO nurseDTO = new NurseDTO(
                new NurseMainDTO(nurseMain),                         // Entity → DTO
                new NurseInformationDTO(nurseMain.getNurseInformation())
        );

        List<License> licenses = licenseService.getByNurse(nurseMain);
        List<NurseLicenseDTO> licenseDTOList = licenses.stream()
                .map(NurseLicenseDTO::new) // License 객체를 직접 생성자에 전달
                .collect(Collectors.toList()); // Java 8~11 호환

        nurseDTO.setLicenseList(licenseDTOList);
        model.addAttribute("nurseDTO", nurseDTO);

        return "nurse/info";
    }

    // 인라인 수정 저장
    //@PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_SYSTEM','ROLE_DOCTOR','ROLE_NURSE')")
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
                license.setIssueDate(licenseDTO.getIssueDate());
                license.setExpiryDate(licenseDTO.getExpiryDate());
                license.setNote(licenseDTO.getNote());
                license.setNurse(nurse);

                // 2-3. 저장
                licenseService.save(license);
            }
        }
        return "redirect:/nurse/info/" + nurseId;
    }

    // 인라인 추가
    //@PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_SYSTEM','ROLE_DOCTOR','ROLE_NURSE')")
    @PostMapping("/{nurseId}")
    public String addLicense(@PathVariable Integer nurseId, License license) {
        NurseMain nurse = nurseMainService.getNurseMain(nurseId);
        license.setNurse(nurse);
        licenseService.save(license);
        return "redirect:/nurse/info/" + nurseId;
    }

    // 삭제
    //@PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_SYSTEM','ROLE_DOCTOR','ROLE_NURSE')")
    @GetMapping("/delete/{licenseId}/{nurseId}")
    public String deleteLicense(@PathVariable Integer licenseId, @PathVariable Integer nurseId) {
        licenseService.delete(licenseId);

        // 삭제 후 남은 자격증 리스트 가져오기
        NurseMain nurseMain = nurseMainService.getById(nurseId);
        List<License> remainingLicenses = licenseService.getByNurse(nurseMain);

        // 다음 자격증 ID 찾기
        Integer scrollTargetId = null;
        for (License license : remainingLicenses) {
            if (license.getId() > licenseId) {
                scrollTargetId = license.getId();
                break;
            }
        }

        // 없으면 마지막 자격증으로
        if (scrollTargetId == null && !remainingLicenses.isEmpty()) {
            scrollTargetId = remainingLicenses.get(remainingLicenses.size() - 1).getId();
        }

        // 리다이렉트
        if (scrollTargetId != null) {
            return "redirect:/nurse/info/" + nurseId + "#license-" + scrollTargetId;
        } else {
            return "redirect:/nurse/info/" + nurseId;
        }
    }
}
