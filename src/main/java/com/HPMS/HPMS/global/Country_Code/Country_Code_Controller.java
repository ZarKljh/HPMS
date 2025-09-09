package com.HPMS.HPMS.global.Country_Code;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("global")
public class Country_Code_Controller {

    private final Country_Code_Service service;

    // 팝업: /country/popup?kw=kor&page=0&size=10
    @GetMapping("/country_form")
    public String popup(@RequestParam(value = "kw", required = false) String kw,
                        @RequestParam(value = "page", defaultValue = "0") int page,
                        @RequestParam(value = "size", defaultValue = "10") int size,
                        Model model) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("countryKr").ascending());
        Page<Country_Code> result = service.search(kw, pageable);
        model.addAttribute("kw", kw);
        model.addAttribute("page", result);
        return "global/country_form";
    }
}