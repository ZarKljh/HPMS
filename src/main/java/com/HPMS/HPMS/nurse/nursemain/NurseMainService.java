package com.HPMS.HPMS.nurse.nursemain;

import com.HPMS.HPMS.nurse.NurseDataNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class NurseMainService {

    @Autowired
    private NurseMainRepository nurseMainRepository;

    public List<NurseMain> getAll() {
        return nurseMainRepository.findAll();
    }

    public NurseMain getNurseMain(Integer id) {
        Optional<NurseMain> nurseMain = this.nurseMainRepository.findById(id);
        if (nurseMain.isPresent()) {
            return nurseMain.get();
        } else {
            throw new NurseDataNotFoundException("nurse not found");
        }
    }

    public Page<NurseMain> getList(int page) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createDate"));
        Pageable pageable = PageRequest.of(page, 15, Sort.by(sorts));
        return this.nurseMainRepository.findAll(pageable);
    }

    public NurseMain save(NurseMain nurseMain) {
        return nurseMainRepository.save(nurseMain);
    }

    public List<NurseMain> getNurseMainByName(String firstName, String lastName){
        return this.nurseMainRepository.findByFirstNameAndLastName(firstName, lastName);
    }
}
