package com.HPMS.HPMS.Doctor.DoctorM;

import com.HPMS.HPMS.Doctor.DoctorDTL.DoctorDTL;
import com.HPMS.HPMS.Doctor.DoctorDTL.DoctorDTLForm;
import com.HPMS.HPMS.Doctor.DoctorDTL.DoctorDTLService;
import jakarta.persistence.PreRemove;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class DoctorMController {


    private final DoctorMService service;
    private final DoctorDTLService doctorDTLService;

    /** 목록/검색 (메인만) */

    @GetMapping("/doctor")
   // @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_SYSTEM','ROLE_DOCTOR','ROLE_NURSE')")
    public String listPage(@RequestParam(value = "kw", required = false) String q,
                           @RequestParam(defaultValue = "0") Integer page,
                           @RequestParam(defaultValue = "10") Integer size,
                           Model model) {
        Page<DoctorM> result = service.search(q, page, size);
        model.addAttribute("q", q);
        model.addAttribute("page", result);
        model.addAttribute("currentPage", result.getNumber());
        model.addAttribute("totalPages", Math.max(result.getTotalPages(), 1));
        model.addAttribute("totalElements", result.getTotalElements());
        // ✅ 템플릿이 실제로 쓰는 키들 추가
        model.addAttribute("paging", result);                             // 템플릿에서 paging.* 참조
        model.addAttribute("size", result.getSize());                     // select selected 체크에 사용
        model.addAttribute("totalCount", result.getTotalElements());      // "등록 건수"
        model.addAttribute("kw", q == null ? "" : q);                     // 템플릿은 kw를 씀

        // 페이지 번호 리스트(0-based)
        List<Integer> pageNumbers = java.util.stream.IntStream
                .range(0, Math.max(result.getTotalPages(), 1))            // 0건일 때도 1 페이지 보이게 하려면 Math.max 유지
                .boxed()
                .toList();
        model.addAttribute("pageNumbers", pageNumbers);
        return "doctor/doctor";
    }

    /** 신규: 메인+디테일 통합 입력 폼 */
    @GetMapping("/doctor/new")
   // @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_SYSTEM','ROLE_DOCTOR','ROLE_NURSE')")
    public String newAllForm(Model model) {
        if (!model.containsAttribute("mForm")) model.addAttribute("mForm", new DoctorMForm());
        if (!model.containsAttribute("dForm")) model.addAttribute("dForm", new DoctorDTLForm());
        return "doctor/doctor_join";
    }

    /** 신규 처리: 메인+디테일 */
    @PostMapping("/doctor/new")
   // @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_SYSTEM','ROLE_DOCTOR','ROLE_NURSE')")
    public String newAllSubmit(@Valid @ModelAttribute("mForm") DoctorMForm mForm,
                               BindingResult mBinding,
                               @Valid @ModelAttribute("dForm") DoctorDTLForm dForm,
                               BindingResult dBinding,
                               RedirectAttributes redirect) {
        if (mBinding.hasErrors() || dBinding.hasErrors()) {
            redirect.addFlashAttribute("org.springframework.validation.BindingResult.mForm", mBinding);
            redirect.addFlashAttribute("org.springframework.validation.BindingResult.dForm", dBinding);
            redirect.addFlashAttribute("mForm", mForm);
            redirect.addFlashAttribute("dForm", dForm);
            return "redirect:/doctor/new";
        }
        Integer id = doctorDTLService.createMainAndDetail(mForm, dForm);
        return "redirect:/doctor/dtl/detail?id=" + id;
    }

    /** 수정: 메인+디테일 통합 폼 */
    @GetMapping("/doctor/edit/{id}")
   // @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_SYSTEM','ROLE_DOCTOR','ROLE_NURSE')")
    public String editAllForm(@PathVariable Integer id, Model model, RedirectAttributes redirect) {
        try {
            DoctorM main = service.get(id);
            if (!model.containsAttribute("mForm")) {
                DoctorMForm mForm = DoctorMForm.fromEntity(main);
                model.addAttribute("mForm", mForm);
            }

            DoctorDTL detail = null;
            try { detail = doctorDTLService.getByDoctorId(id); } catch (Exception ignore) {}
            if (!model.containsAttribute("dForm")) {
                DoctorDTLForm dForm = (detail != null) ? DoctorDTLForm.fromEntity(detail) : new DoctorDTLForm();
                dForm.setDoctorId(id);
                model.addAttribute("dForm", dForm);
            }

            model.addAttribute("doctorId", id);
            return "doctor/doctor_edit";
        } catch (IllegalArgumentException e) {
            redirect.addFlashAttribute("error", "해당 의사 정보가 없습니다.");
            return "redirect:/doctor";
        }
    }

    /** 수정 처리: 메인+디테일 */
    @PostMapping("/doctor/edit/{id}")
   // @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_SYSTEM','ROLE_DOCTOR','ROLE_NURSE')")
    public String editAllSubmit(@PathVariable Integer id,
                                @Valid @ModelAttribute("mForm") DoctorMForm mForm,
                                BindingResult mBinding,
                                @Valid @ModelAttribute("dForm") DoctorDTLForm dForm,
                                BindingResult dBinding,
                                RedirectAttributes redirect) {
        if (mBinding.hasErrors() || dBinding.hasErrors()) {
            redirect.addFlashAttribute("org.springframework.validation.BindingResult.mForm", mBinding);
            redirect.addFlashAttribute("org.springframework.validation.BindingResult.dForm", dBinding);
            redirect.addFlashAttribute("mForm", mForm);
            redirect.addFlashAttribute("dForm", dForm);
            return "redirect:/doctor/edit/" + id;
        }
        doctorDTLService.updateMainAndDetail(id, mForm, dForm);
        return "redirect:/doctor/dtl/detail?id=" + id;
    }

    /** 삭제: 상세 하단 버튼에서 사용 */

    @GetMapping("/doctor/delete/{id}")
   // @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_SYSTEM','ROLE_DOCTOR','ROLE_NURSE')")
    public String delete(@PathVariable Integer id, RedirectAttributes redirect) {
        service.delete(id);
        redirect.addFlashAttribute("msg", "삭제되었습니다.");
        return "redirect:/doctor";
    }
    @PostMapping("/doctor/deleteSelected")
   // @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_SYSTEM','ROLE_DOCTOR','ROLE_NURSE')")
    public String deleteSelected(@RequestParam("ids") java.util.List<Integer> ids,
                                 RedirectAttributes redirect) {
        if (ids != null && !ids.isEmpty()) {
            service.deleteAll(ids); // ✅ 서비스에 다중 삭제 메서드 필요
            redirect.addFlashAttribute("msg", ids.size() + "건 삭제되었습니다.");
        } else {
            redirect.addFlashAttribute("error", "선택된 항목이 없습니다.");
        }
        return "redirect:/doctor";
    }
}
