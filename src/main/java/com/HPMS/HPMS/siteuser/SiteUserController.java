package com.HPMS.HPMS.siteuser;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
@Controller
public class SiteUserController {

    private final SiteUserService siteUserService;

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
}
