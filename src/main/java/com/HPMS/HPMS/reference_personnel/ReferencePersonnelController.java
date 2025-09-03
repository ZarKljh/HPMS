package com.HPMS.HPMS.reference_personnel;

import com.HPMS.HPMS.reference_personnel.reference_personnel_dtl.ReferencePersonnelDtlRepository;
import com.HPMS.HPMS.reference_personnel.reference_personnel_dto.ReferencePersonnelDTOService;
import com.HPMS.HPMS.reference_personnel.reference_personnel_dto.personnel_dtl_dto.ReferencePersonnelDtlDTO;
import com.HPMS.HPMS.reference_personnel.reference_personnel_dto.personnel_m_dto.ReferencePersonnelMDTO;
import com.HPMS.HPMS.reference_personnel.reference_personnel_dto.ReferencePersonnelDTO;
import com.HPMS.HPMS.reference_personnel.reference_personnel_m.ReferencePersonnelM;
import com.HPMS.HPMS.reference_personnel.reference_personnel_m.ReferencePersonnelMRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.ui.Model;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RequiredArgsConstructor
@Controller
public class ReferencePersonnelController {
/*
  // 정상작동확인
    @GetMapping("/user")
    @ResponseBody
    public String index(){
        String reMessage = "<br><br><br>방가방가";
        System.out.println(reMessage);
        return reMessage;
    }*/
    private final ReferencePersonnelMRepository referencePersonnelMRepository;
    private final ReferencePersonnelDTOService referencePersonnelDTOService;
    // private final ReferencePersonnelDtlRepository;

    // @GetMapping("/main/personnel/List")

/*    @GetMapping("/user")
    *//*@ResponseBody*//*
    public String personnelList(Model model){
        List<ReferencePersonnelM> referencePersonnelM = this.referencePersonnelMRepository.findAll();
        model.addAttribute("personnelList",referencePersonnelM);
        return "personnel/personnel_list";
    }*/
/*
    // 아래 내용으로 대체
    @GetMapping("/user/list")
    public String referencePersonnelList(Model model){
        List<ReferencePersonnelMDTO> referencePersonnels = this.referencePersonnelDTOService.getReferencePersonelMDTO();
        model.addAttribute("referencePersonnels", referencePersonnels);
        return "personnel/personnel_list";
    }
*/

    @GetMapping("/user/list")
    public String referencePersonnelList(@RequestParam(defaultValue = "0") int page,
                                         @RequestParam(defaultValue = "10") int size,
                                         Model model) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("id").ascending());
        Page<ReferencePersonnelDTO> pagedList = referencePersonnelDTOService.getPagedReferencePersonnel(pageRequest);

        int totalPages = pagedList.getTotalPages();
        if (page >= totalPages) {
            page = 0;
        }
        int currentPage = page;

        int startPage = Math.max(currentPage - 2, 0);
        int endPage = Math.min(currentPage + 2, totalPages - 1);

        List<Integer> pageNumbers = IntStream.rangeClosed(startPage, endPage)
                .boxed()
                .collect(Collectors.toList());

        model.addAttribute("pageNumbers", pageNumbers);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", totalPages);

        model.addAttribute("referencePersonnels", pagedList.getContent());
        model.addAttribute("totalPages", pagedList.getTotalPages());
        model.addAttribute("currentPage", page);
        model.addAttribute("size", size);
        model.addAttribute("personnelPage", pagedList);
        // model.addAttribute("pageNumbers", pageNumbers); // 페이지 번호

        return "personnel/personnel_list";
    }





    @GetMapping("/user/detail/{id}")
    public String referencePersonnelDtl(Model model, @PathVariable("id") Integer id){
        // PatientDetailDTO detailDTO = this.patientDTOService.getPatientDetailDTO(id);
        ReferencePersonnelDtlDTO referencePersonnelDtlDTO = this.referencePersonnelDTOService.getReferencePersonnelDtlDTO(id);
        model.addAttribute("referencePersonnelDtlDTO",referencePersonnelDtlDTO);
        return "personnel/personnel_detail";
    }



    @GetMapping("/user/personnel_registration")
    public String personnelRegisration() {
        return "personnel/personnel_registration";
    }


    @PostMapping("/create/reference_personal")
    public String createReferencePersonnel(@ModelAttribute ReferencePersonnelDTO dto, RedirectAttributes redirectAttributes) {
        try {
            // referencePersonnelDTOService.saveReferencePersonnel(dto);
            Integer id = referencePersonnelDTOService.saveReferencePersonnel(dto);
            redirectAttributes.addFlashAttribute("successMessage", "Registration Result.");
            return "redirect:/user/detail/" + id;
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "저장 중 오류가 발생했습니다.");
            return "redirect:/user/personnel_registration";
        }
    }
    /*
    @PostMapping("/create/reference_personal")
    public String createReferencePersonnel(@ModelAttribute ReferencePersonnelDTO dto) {
        referencePersonnelDTOService.saveReferencePersonnel(dto);
        // return "redirect:/user/detail/{id}"; // 결과 페이지로 이동
        return "redirect:/user/list"; // 임시 저장이 완료되면 목록 화면으로 이동
    }
*/

    /* 기존
    @PostMapping("/create/reference_personal")
    public String createReferencePersonnel(
        Model model,
        @RequestParam(value="firstName") String firstName,
        @RequestParam(value="lastName") String lastName,
        @RequestParam(value="middleName") String middleName,
        @RequestParam(value="nationality") String nationality,
        @RequestParam(value="email") String email,
        @RequestParam(value="cellPhone") String cellPhone,
        @RequestParam(value="companyName") String companyName,
        @RequestParam(value="deptName") String deptName,
        @RequestParam(value="officeAddress") String officeAddress,
        @RequestParam(value="officeDetailAddress") String officeDetailAddress,
        @RequestParam(value="officeTel") String officeTel,
        @RequestParam(value="officeFax") String officeFax,
        @RequestParam(value="companyWebsiteUrl") String companyWebsiteUrl,
        @RequestParam(value="note") String note
        ) {

        // Question question = this.questionService.getQuestion(id);
        // TODO: registrate a reference personnel
        // ReferencePersonalM and ReferencePersonalDtl 엔터티 동시에
        // return "personnel/personnel_list";
        return "redirect:/user/list";
    }
*/


    /*

    */
/*@ResponseBody*//*

    public String referencePersonnelDtl(Model model, @PathVariable("id") Integer id){

        List<ReferencePersonnelM> referencePersonnelM = this.referencePersonnelMRepository.findAll();
        model.addAttribute("personnelList",referencePersonnelM);
        return "personnel/personnel_list";

    }
*/


}
