package com.HPMS.HPMS.global.Address_Code;


import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.checkerframework.checker.units.qual.C;

@Getter
@Setter
@Entity
@Table(name="ROAD_NAME")
public class Address_Code {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment
    @Column(name = "ID")
    private Integer id;

    @Size(max = 12)
    @Column(name = "road_code", length = 12, nullable = false, unique = true)
    private String road_code;

    @Size(max = 80)
    @Column(name = "road_name_kor", length = 80, nullable = false)
    private String road_name_kor;

    @Size(max = 80)
    @Column(name = "road_name_eng", length = 80, nullable = false)
    private String road_name_eng;

    @Size(max=2)
    @Column(name = "emd_seq_no", length = 2, nullable = false, unique = true)
    private String emd_seq_no;

    @Size(max = 20)
    @Column(name = "sido_kor", length = 20, nullable = false)
    private String sido_kor;

    @Size(max = 40)
    @Column(name = "sido_eng", length = 40, nullable = false)
    private String sido_eng;

    @Size(max = 20)
    @Column(name = "sgg_kor", length = 20, nullable = false)
    private String sgg_kor;

    @Size(max = 40)
    @Column(name = "sgg_eng", length = 40, nullable = false)
    private String sgg_eng;

    @Size(max = 20)
    @Column(name = "emd_kor", length = 20, nullable = false)
    private String emd_kor;

    @Size(max = 40)
    @Column(name = "emd_eng", length = 40, nullable = false)
    private String emd_eng;

    @Size(max = 1)
    @Column(name = "emd_gubun", length = 1, nullable = false)
    private String emd_gubun;

    @Size(max = 3)
    @Column(name = "emd_code", length = 3, nullable = false)
    private String emd_code;

    @Size(max = 1)
    @Column(name = "use_yn", length = 1, nullable = false)
    private String use_yn;

    @Size(max = 1)
    @Column(name = "change_reason", length = 1, nullable = false)
    private String change_reason;

    @Size(max = 14)
    @Column(name = "change_info", length = 14, nullable = false)
    private String change_info;

    @Size(max = 8)
    @Column(name = "notice_date", length = 8, nullable = false)
    private String notice_date;

    @Size(max = 8)
    @Column(name = "abolish_date", length = 8, nullable = false)
    private String abolish_date;
}
