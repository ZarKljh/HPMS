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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/patient/list")
    public String list(Model model) {
        List<PatientListDTO> patients = this.patientDTOService.getPatientListDTO();
        model.addAttribute("patients", patients);
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
        model.addAttribute("modifyDTO", detailDTO);

        return "patient/lsw_patient_modify";
    }

    public String patientModify(@Valid PatientForm patientForm, BindingResult bindingResult, @PathVariable("id") Integer id){
        if (bindingResult.hasErrors()) {
            return "patient/lsw_patient_modify";
        }
        //this.patientMService.modifyPatientM(patientForm);
        return "patient/lsw_patient_detail";
    }



}
