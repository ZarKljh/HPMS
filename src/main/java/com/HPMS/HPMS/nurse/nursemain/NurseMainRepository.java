package com.HPMS.HPMS.nurse.nursemain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NurseMainRepository extends JpaRepository<NurseMain, Integer> {
    NurseMain findByFirstName(String firstName);
    NurseMain findByFirstNameAndLastName(String firstName, String lastName);
    List<NurseMain> findByDeptLike(String dept);
}
