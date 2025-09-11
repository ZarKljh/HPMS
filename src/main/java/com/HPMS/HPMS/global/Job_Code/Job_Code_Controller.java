// JobSimpleController.java
package com.HPMS.HPMS.global.Job_Code;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/global/jobcode")
public class Job_Code_Controller {
    private final Job_Code_Service service;

    @GetMapping("/popup")
    public String popup() { return "global/job_code"; }

    @GetMapping("/ranks")     @ResponseBody
    public List<Job_Code_DTO> ranks() { return service.ranks(); }

    @GetMapping("/positions") @ResponseBody
    public List<Job_Code_DTO> positions() { return service.positions(); }
}