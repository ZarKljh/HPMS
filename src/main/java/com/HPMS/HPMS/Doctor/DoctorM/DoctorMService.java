package com.HPMS.HPMS.Doctor.DoctorM;

import com.HPMS.HPMS.Doctor.DataNotFoundException;
import com.sun.jna.platform.win32.OaIdl;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class DoctorMService {
    /*
    private final DoctorMRepository doctorMRepository;

    public List<DoctorM> getList() {
        return doctorMRepository.findAll();
    }

    public DoctorM getDoctorM(Integer id) {
        Optional<DoctorM> od = this.doctorMRepository.findById(id);

        if (od.isPresent()) {
            return od.get();
        } else {
            throw new DataNotFoundException("question not found");
        }
    }

    public void create(String department, String medicalDepartment, String rank, String position, String firstName, String lastName, String middleName,
                       String gender, LocalDate dateOfBirth, String telephone, LocalDate hireDate, String status, String workType) {
        DoctorM m = new DoctorM();
        m.setDepartment(department);
        m.setMedicalDepartment(medicalDepartment);
        m.setRank(rank);
        m.setPosition(position);
        m.setFirstName(firstName);
        m.setLastName(lastName);
        m.setMiddleName(middleName);
        m.setGender(gender);
        m.setDateOfBirth(dateOfBirth);
        m.setTelephone(telephone);
        m.setHireDate(hireDate);
        m.setStatus(status);
        m.setWorkType(workType);
        this.doctorMRepository.save(m);
    }

    public Page<DoctorM> getList(int page, String kw) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createDate"));
        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
        return this.doctorMRepository.findAllByKeyword(kw, pageable);
    }

    public void modify(DoctorM doctorM, String department, String medicalDepartment, String rank, String position, String firstName, String lastName, String middleName,
                       String gender, LocalDate dateOfBirth, String telephone, LocalDate hireDate, String status, String workType) {
    }

     */
}
