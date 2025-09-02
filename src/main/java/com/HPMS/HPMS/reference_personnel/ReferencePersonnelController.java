package com.HPMS.HPMS.reference_personnel;

import com.HPMS.HPMS.reference_personnel.reference_personnel_dtl.ReferencePersonnelDtlRepository;
import com.HPMS.HPMS.reference_personnel.reference_personnel_dto.ReferencePersonnelDTOService;
import com.HPMS.HPMS.reference_personnel.reference_personnel_dto.personnel_dtl_dto.ReferencePersonnelDtlDTO;
import com.HPMS.HPMS.reference_personnel.reference_personnel_dto.personnel_m_dto.ReferencePersonnelMDTO;
import com.HPMS.HPMS.reference_personnel.reference_personnel_m.ReferencePersonnelM;
import com.HPMS.HPMS.reference_personnel.reference_personnel_m.ReferencePersonnelMRepository;
import org.springframework.ui.Model;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

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
    @GetMapping("/user/list")
    /*@ResponseBody*/
    public String referencePersonnelList(Model model){
        List<ReferencePersonnelMDTO> referencePersonnels = this.referencePersonnelDTOService.getReferencePersonelMDTO();
        model.addAttribute("referencePersonnels", referencePersonnels);
        return "personnel/personnel_list";
    }

    @GetMapping("/user/detail/{id}")
    public String referencePersonnelDtl(Model model, @PathVariable("id") Integer id){
        // PatientDetailDTO detailDTO = this.patientDTOService.getPatientDetailDTO(id);
        ReferencePersonnelDtlDTO referencePersonnelDtlDTO = this.referencePersonnelDTOService.getReferencePersonnelDtlDTO(id);
        model.addAttribute("referencePersonnelDtlDTO",referencePersonnelDtlDTO);
        return "personnel/personnel_detail";
    }

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
