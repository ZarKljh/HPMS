package com.HPMS.HPMS.Doctor.DoctorM;

import org.springframework.data.domain.Page;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

public class DoctorMController {
    @GetMapping("/doctor/list")
    public String list(Model Model, @RequestParam(value = "page", defaultValue = "0") int page,
                       @RequestParam(value = "kw", defaultValue = "") String kw){
        Page<DoctorM> paging = this.DoctorMService.getList(page,kw);
        model.addA
    }
}
