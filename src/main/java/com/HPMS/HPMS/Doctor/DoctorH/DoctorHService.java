package com.HPMS.HPMS.Doctor.DoctorH;

import com.HPMS.HPMS.Doctor.DoctorDTL.DoctorDTL;
import com.HPMS.HPMS.Doctor.DoctorM.DoctorM;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DoctorHService {
    private final DoctorHRepository HistRepo;

    public void snapshotBeforeUpdate(DoctorM m, DoctorDTL d) {
        HistRepo.save(DoctorH.snapshotOf(m, d));
    }

    public Page<DoctorH> search(DoctorHForm cond, Pageable pageable) {
        Specification<DoctorH> spec = Specification.where(doctorIdEq(cond.getDoctorId()))
                .and(createDateGoe(cond.fromDateTime()))
                .and(createDateLoe(cond.toDateTime()))
                .and(keywordLike(emptyToNull(cond.getKeyword())));

        return HistRepo.findAll(spec, pageable);
    }

    // ---- 아래부터 서비스 내부 Private Spec 메서드들 ----

    private Specification<DoctorH> doctorIdEq(Integer doctorId) {
        return (root, q, cb) -> (doctorId == null)
                ? null
                : cb.equal(root.join("doctorMain").get("id"), doctorId);
    }


    private Specification<DoctorH> createDateGoe(LocalDateTime from) {
        return (root, q, cb) -> from == null ? null : cb.greaterThanOrEqualTo(root.get("createDate"), from);
    }

    private Specification<DoctorH> createDateLoe(LocalDateTime to) {
        return (root, q, cb) -> to == null ? null : cb.lessThanOrEqualTo(root.get("createDate"), to);
    }

    private Specification<DoctorH> keywordLike(String kw) {
        return (root, q, cb) -> {
            if (isBlank(kw)) return null;
            String like = "%" + kw.toLowerCase() + "%";
            return cb.or(
                    cb.like(cb.lower(root.get("firstName")), like),
                    cb.like(cb.lower(root.get("lastName")), like),
                    cb.like(cb.lower(root.get("department")), like),
                    cb.like(cb.lower(root.get("medicalDepartment")), like),
                    cb.like(cb.lower(root.get("note")), like)
            );
        };
    }

    private boolean isBlank(String s) { return s == null || s.isBlank(); }
    private String emptyToNull(String s) { return isBlank(s) ? null : s; }
}
