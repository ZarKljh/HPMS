// src/main/java/com/HPMS/HPMS/global/Address_Code/Address_Code_Service.java
package com.HPMS.HPMS.global.Address_Code;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class Address_Code_Service {
    private final Address_Code_Repository repository;

    public Page<Address_Code> search(String kw, Pageable pageable) {
        return repository.search(kw, pageable);
    }
}

