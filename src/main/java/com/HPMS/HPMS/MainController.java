package com.HPMS.HPMS;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
//    @GetMapping("/hpms/login")
//    public String hmpsroot(){
//        return "lsw_login_form";
//    }

    @GetMapping("/")
    public String root(){
        return "main_menu";
    }
}
