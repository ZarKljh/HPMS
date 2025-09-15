package com.HPMS.HPMS.siteuser;

import com.HPMS.HPMS.Patient.PatientDTO.PatientDTOService;
import com.HPMS.HPMS.Patient.PatientDTO.PatientDetailDTO.PatientDetailDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;

@RequiredArgsConstructor
@Controller
public class SiteUserController {

    private final SiteUserService siteUserService;
    private final PatientDTOService patientDTOService;

    @GetMapping("/hpms/signup")
    public String signup(SiteUserForm siteUserForm) {
        return "global/lsw_signup_form";
    }

    @PostMapping("/hpms/signup")
    public String signup(@Valid SiteUserForm siteUserForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "global/lsw_signup_form";
        }
        if (!siteUserForm.getPassword1().equals(siteUserForm.getPassword2())) {
            bindingResult.rejectValue("password2", "passwordInCorrect",
                    "2개의 패스워드가 일치하지 않습니다.");
            return "global/lsw_signup_form";
        }

        try {
            siteUserService.createSiteUser(siteUserForm);
        }catch(DataIntegrityViolationException e) {
            e.printStackTrace();
            bindingResult.reject("signupFailed", "이미 등록된 사용자입니다.");
            return "global/lsw_signup_form";
        }catch(Exception e) {
            e.printStackTrace();
            bindingResult.reject("signupFailed", e.getMessage());
            return "global/lsw_signup_form";
        }

        return "redirect:/";
    }

    @GetMapping("/hpms/login")
    public String login() {
        return "global/lsw_login_form";
    }

    //게스트 로그인한 환자정보를 처리를 위한 메소드입니다
    @GetMapping("/hpms/guest")
    public String loginGuest(Model model, Principal principal){
        PatientDetailDTO detailDTO = this.patientDTOService.getPatientDetailDTOByUserName(principal.getName());
        model.addAttribute("detailDTO", detailDTO);

        return "patient/lsw_patient_guest";
    }



    /*
    // 게스트로그인 테스트 메소드입니다
    // 로그인한 게스트의 user 아이디를 pincipal 을 통해 정상적으로 받았습니다
    @GetMapping("/hpms/guest")
    @ResponseBody
    public String loginGuest(Model model, Principal principal) {
        return principal.getName();
    }
     */
}
