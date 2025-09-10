// src/main/java/com/HPMS/HPMS/global/Address_Code/Address_Code_Controller.java
package com.HPMS.HPMS.global.Address_Code;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/global/road")

public class Address_Code_Controller {

    private final Address_Code_Service service;

    /** 팝업 페이지 (템플릿만 반환, 데이터는 AJAX) */
    @GetMapping("/popup")
    public String popup() {
        return "global/road_name_form"; // templates/global/road_popup.html
    }

    /** AJAX 검색 (JSON 반환) */

    @GetMapping("/search")
    @ResponseBody
    public ResponseEntity<Page<Address_Code>> search(
            @RequestParam(required = false) String kw,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("road_name_kor").ascending());
        return ResponseEntity.ok(service.search(kw, pageable));
    }
}

