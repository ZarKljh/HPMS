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


    //환자 1명의 상세정보 id값으로 찾아서 가져옵니다
    @GetMapping("/patient/detail/{id}")
    public String patientDetial(Model model, @PathVariable("id") Integer id){
        PatientDetailDTO detailDTO = this.patientDTOService.getPatientDetailDTO(id);
        model.addAttribute("detailDTO", detailDTO);
        return "patient/lsw_patient_detail";
    }
    //신규환자등록 페이지로 이동합니다
    @GetMapping("/patient/create")
    public String patientCreate(PatientForm patientCreateForm){
        return "patient/lsw_patient_create";
    }

    //신규환자를 저장합니다
    @PostMapping("/patient/create")
    public String patientCreate(@Valid PatientForm patientForm, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            return "patient/lsw_patient_create";
        }
        this.patientMService.createPatientM(patientForm);
        return "redirect:/patient/list"; // 질문 저장후 질문목록으로 이동
    }
    //환자정보 수정페이지로 이동하기 위해 수정대상 정보를 가져옵니다
    @GetMapping("/patient/modify/{id}")
    public String patientModify(Model model, @PathVariable("id") Integer id){
        PatientDetailDTO detailDTO = this.patientDTOService.getPatientDetailDTO(id);
        model.addAttribute("patientForm", detailDTO);
        return "patient/lsw_patient_modify";
    }
    //수정된 환자정보를 저장합니다
    @PostMapping("/patient/modify/{id}")
    public String patientModify(@Valid PatientForm patientForm, BindingResult bindingResult, @PathVariable("id") Integer id){
        PatientM patientM = this.patientMService.getPatientM(id);
        if (bindingResult.hasErrors()) {
            return "patient/lsw_patient_modify";
        }
        Integer modifiedId = this.patientMService.modifyPatientM(patientM, patientForm);
        return String.format("redirect:/patient/detail/%s", modifiedId);
    }
    //환자를 종결(삭제)처리합니다
    @GetMapping("/patient/delete/{id}")
    public String patientDelete(Model model, BindingResult bindingResult, @PathVariable("id") Integer id){
        PatientM patientM = this.patientMService.getPatientM(id);
        if (bindingResult.hasErrors()) {
            return "patient/lsw_patient_modify";
        }
        this.patientMService.deletePatientM(patientM);
        return "patient/lsw_patient_list";
    }

    //다중컨디션으로 환자를 검색합니다
    // columns 칼럼명, operator 비교연산자, value 값, logicalOperator 논리연산자 4개의 변수가 1셋트입니다
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
        //페이징처리를 위한 pageRequest 생성
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());

        //변수들을 통해 데이터를 가져옵니다
        Page<PatientListDTO> patients = patientDTOService.searchPatients(columns, operators, values, logicalOperators, pageable);

        // 페이지네이션 처리
        // patient/list 의 페이지처리와 동일합니다
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

        //검색 조건을 다시 모델에 넣습니다.
        model.addAttribute("columns", columns);
        model.addAttribute("operators", operators);
        model.addAttribute("values", values);
        model.addAttribute("logicalOperators", logicalOperators);

        // 기존 patient/list 의 화면 폼을 그대로 사용합니다
        return "patient/lsw_patient_list";
    }


}
