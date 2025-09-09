package com.HPMS.HPMS.Patient;

import com.HPMS.HPMS.Patient.PatientDTL.PatientDTLService;
import com.HPMS.HPMS.Patient.PatientDTO.PatientDTOService;
import com.HPMS.HPMS.Patient.PatientDTO.PatientDetailDTO.PatientDetailDTO;
import com.HPMS.HPMS.Patient.PatientDTO.PatientListDTO.PatientListDTO;
import com.HPMS.HPMS.Patient.PatientM.PatientM;
import com.HPMS.HPMS.Patient.PatientM.PatientMRepository;
import com.HPMS.HPMS.Patient.PatientM.PatientMService;
import com.HPMS.HPMS.Patient.patientForm.PatientForm;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class PatientController {
    private final PatientMRepository patientMRepository;
    private final PatientMService patientMService;
    private final PatientDTLService patientDTLService;
    private final PatientDTOService patientDTOService;

    //JPA와 DB 수동연결후 작동확인을 위한 테스트 메소드 입니다. ---> 정상작동 하였습니다
    @GetMapping("/patient")
    @ResponseBody
    public String test(){
        PatientM patientM= this.patientMRepository.getById(1);
        System.out.println(patientM.getFirstName());
        patientM.setFirstName("우진");
        this.patientMRepository.save(patientM);
        patientM= this.patientMRepository.getById(1);
        return patientM.getFirstName();
    }
    /*
    @GetMapping("/patient/list")
    public String list(Model model) {
        List<PatientListDTO> patients = this.patientDTOService.getPatientListDTO();
        model.addAttribute("patients", patients);
        return "patient/lsw_patient_list";
    }
    */

    //@PreAuthorize("isAuthenticated()")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_SYSTEM','ROLE_DOCTOR','ROLE_NURSE')")
    @GetMapping("/patient/list")
    public String list(Model model,
                       @RequestParam(value="page", defaultValue="0") int page,
                       @RequestParam(value="size", defaultValue="10") int size) {
        //id 칼럼을 기준으로 내림차순(descending())
        //오름차순으로 하고 싶을 때에는 (ascending()) 변경
        Pageable pageable = PageRequest.of(page, size, Sort.by("id"). descending());
        Page<PatientListDTO> patients = this.patientDTOService.getPatientListDTO(pageable);

        int totalPages = patients.getTotalPages();
        if (page >= totalPages) {
            page = 0;
        }
        int currentPage = page;

        int startPage = Math.max(currentPage - 2, 0);
        int endPage = Math.min(currentPage + 2, totalPages - 1);

        List<Integer> pageNumbers = new ArrayList<>();

        for (int i = startPage; i <= endPage; i++) {
            pageNumbers.add(i);
        }

        model.addAttribute("patients", patients);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("size", size);




        return "patient/lsw_patient_list";
    }




    @GetMapping("/patient/detail/{id}")
    public String patientDetial(Model model, @PathVariable("id") Integer id){
        PatientDetailDTO detailDTO = this.patientDTOService.getPatientDetailDTO(id);
        model.addAttribute("detailDTO", detailDTO);
        return "patient/lsw_patient_detail";
    }

    @GetMapping("/patient/create")
    public String patientCreate(PatientForm patientCreateForm){
        return "patient/lsw_patient_create";
    }

    @PostMapping("/patient/create")
    public String patientCreate(@Valid PatientForm patientForm, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            return "patient/lsw_patient_create";
        }
        this.patientMService.createPatientM(patientForm);
        return "redirect:/patient/list"; // 질문 저장후 질문목록으로 이동
    }

    @GetMapping("/patient/modify/{id}")
    public String patientModify(Model model, @PathVariable("id") Integer id){
        PatientDetailDTO detailDTO = this.patientDTOService.getPatientDetailDTO(id);
        model.addAttribute("patientForm", detailDTO);
        return "patient/lsw_patient_modify";
    }
    @PostMapping("/patient/modify/{id}")
    public String patientModify(@Valid PatientForm patientForm, BindingResult bindingResult, @PathVariable("id") Integer id){
        PatientM patientM = this.patientMService.getPatientM(id);
        if (bindingResult.hasErrors()) {
            return "patient/lsw_patient_modify";
        }
        Integer modifiedId = this.patientMService.modifyPatientM(patientM, patientForm);
        return String.format("redirect:/patient/detail/%s", modifiedId);
    }

    @GetMapping("/patient/delete/{id}")
    public String patientDelete(Model model, BindingResult bindingResult, @PathVariable("id") Integer id){
        PatientM patientM = this.patientMService.getPatientM(id);
        if (bindingResult.hasErrors()) {
            return "patient/lsw_patient_modify";
        }
        this.patientMService.deletePatientM(patientM);
        return "patient/lsw_patient_list";
    }

    @GetMapping("/patient/search")
    public String searchPatient(
            Model model,
            @RequestParam(value="page", defaultValue="0") int page,
            @RequestParam(value="size", defaultValue="10") int size,
            @RequestParam(value="column[]", required=false) List<String> columns,
            @RequestParam(value="operator[]", required=false) List<String> operators,
            @RequestParam(value="value[]", required=false) List<String> values,
            @RequestParam(value="logicalOperator[]", required=false) List<String> logicalOperators
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());

        // 🔍 검색 서비스 호출
        Page<PatientListDTO> patients = patientDTOService.searchPatients(columns, operators, values, logicalOperators, pageable);

        // 페이지네이션 처리
        int totalPages = patients.getTotalPages();
        int currentPage = Math.min(page, totalPages - 1);
        int startPage = Math.max(currentPage - 2, 0);
        int endPage = Math.min(currentPage + 2, totalPages - 1);
        List<Integer> pageNumbers = new ArrayList<>();
        for (int i = startPage; i <= endPage; i++) pageNumbers.add(i);

        // 모델에 담기
        model.addAttribute("patients", patients);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("size", size);

        return "patient/lsw_patient_list"; // ✅ 기존 리스트 화면 재활용
    }


}
