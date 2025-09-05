package com.HPMS.HPMS.nurse.nursemain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NurseMainRepository extends JpaRepository<NurseMain, Integer> {
    NurseMain findByFirstName(String firstName);
    List<NurseMain> findByFirstNameAndLastName(String firstName, String lastName);
    List<NurseMain> findByDeptLike(String dept);
    Page<NurseMain> findAll(Pageable pageable);

}
