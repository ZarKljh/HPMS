// src/main/java/com/HPMS/HPMS/global/Country_Code/Country_Code_Service.java
package com.HPMS.HPMS.global.Country_Code;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class Country_Code_Service {
    private final Country_Code_Repository repository;

    public Page<Country_Code> search(String kw, Pageable pageable) {
        return repository.search(kw, pageable);
    }
}
