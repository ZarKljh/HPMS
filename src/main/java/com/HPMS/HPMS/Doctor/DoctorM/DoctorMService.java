package com.HPMS.HPMS.Doctor.DoctorM;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.criteria.Predicate;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DoctorMService {


    private final DoctorMRepository repository;

    public DoctorM get(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("의사 정보가 없습니다."));
    }

    public Page<DoctorM> search(String q, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(Math.max(page,0), Math.max(size,1), Sort.by(Sort.Direction.DESC, "id"));
        if (q == null || q.isBlank()) {
            return repository.findAll(pageable);
        }
        Specification<DoctorM> spec = buildSearchSpec(q.trim());
        return repository.findAll(spec, pageable);
    }

    private Specification<DoctorM> buildSearchSpec(String q) {
        return (root, cq, cb) -> {
            ArrayList<Predicate> orList = new ArrayList<>();

            // 문자열 LIKE
            orList.add(cb.like(cb.lower(root.get("department")), "%" + q.toLowerCase() + "%"));
            orList.add(cb.like(cb.lower(root.get("medicalDepartment")), "%" + q.toLowerCase() + "%"));
            orList.add(cb.like(cb.lower(root.get("rank")), "%" + q.toLowerCase() + "%"));
            orList.add(cb.like(cb.lower(root.get("position")), "%" + q.toLowerCase() + "%"));
            orList.add(cb.like(cb.lower(root.get("firstName")), "%" + q.toLowerCase() + "%"));
            orList.add(cb.like(cb.lower(root.get("lastName")), "%" + q.toLowerCase() + "%"));
            orList.add(cb.like(cb.lower(root.get("middleName")), "%" + q.toLowerCase() + "%"));
            orList.add(cb.like(cb.lower(root.get("gender")), "%" + q.toLowerCase() + "%"));
            orList.add(cb.like(cb.lower(root.get("telephone")), "%" + q.toLowerCase() + "%"));
            orList.add(cb.like(cb.lower(root.get("status")), "%" + q.toLowerCase() + "%"));
            orList.add(cb.like(cb.lower(root.get("workType")), "%" + q.toLowerCase() + "%"));

            // 숫자(ID) 검색
            try {
                Integer id = Integer.parseInt(q);
                orList.add(cb.equal(root.get("id"), id));
            } catch (NumberFormatException ignore) {}

            // 날짜(YYYY-MM-DD 형식) 검색: 동일일자 매칭
            try {
                LocalDate d = LocalDate.parse(q);
                orList.add(cb.equal(root.get("dateOfBirth"), d));
                orList.add(cb.equal(root.get("hireDate"), d));
            } catch (DateTimeParseException ignore) {}

            return cb.or(orList.toArray(new Predicate[0]));
        };
    }

    @Transactional
    public DoctorM save(DoctorM entity) {
        return repository.save(entity);
    }

    @Transactional
    public void delete(Integer id) {
        if (!repository.existsById(id)) throw new IllegalArgumentException("이미 삭제되었거나 존재하지 않습니다.");
        repository.deleteById(id);
    }

}
