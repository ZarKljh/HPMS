package com.HPMS.HPMS.reference_personnel.reference_personnel_m;
import com.HPMS.HPMS.reference_personnel.reference_personnel_dtl.ReferencePersonnelDtl;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
// @SecondaryTable(name = "TBL_REL_PERSONNEL_DTL", pkJoinColumns = @PrimaryKeyJoinColumn(name = "TBL_ETC_MEM_ID"))
// PK를 공유하는 1:1 테이블 병합에만 사용해야 함
@Table(name = "TBL_REL_PERSONNEL_M")
public class ReferencePersonnelM {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "NATIONALITY")
    private String nationality;

    @Column(name = "FIRST_NAME")
    private String firstName;

    @Column(name = "LAST_NAME")
    private String lastName;

    @Column(name = "MIDDLE_NAME")
    private String middleName;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "CELL_PHONE")
    private String cellPhone;

    @Column(name = "CREATOR")
    private String creator;

    @Column(name = "CREATE_DATE")
    private LocalDateTime createDate;

    @OneToOne(mappedBy = "personnel", cascade = CascadeType.ALL, orphanRemoval = true)
    private ReferencePersonnelDtl detail;


    /*
    ReferencePersonnelM m = new ReferencePersonnelM();
    ReferencePersonnelDtl dtl = new ReferencePersonnelDtl();

    dtl.setPersonnel(m);     // FK 설정
    m.setDetail(dtl);        // 양방향 연결

    entityManager.persist(m);
    */

    public String getFormattedCellPhone() {
        if (cellPhone == null || cellPhone.length() < 10) return "관련정보없음";

        // 예: 01012345678 → 010-1234-5678
        return cellPhone.replaceAll("(\\d{3})(\\d{4})(\\d+)", "$1-$2-$3");
    }

}

