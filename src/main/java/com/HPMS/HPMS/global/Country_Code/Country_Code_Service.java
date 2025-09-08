package com.HPMS.HPMS.global.Country_Code;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class Country_Code_Service {
    private final Country_Code_Repository repo;

    public Page<Country_Code> search(String kw, Pageable pageable) {
        String normKw = (kw == null || kw.isBlank()) ? null : kw.trim();
        Integer num = null;
        if (normKw != null) {
            try { num = Integer.valueOf(normKw); } catch (NumberFormatException ignore) {}
        }
        return repo.search(normKw, num, pageable);
    }
}
