package com.HPMS.HPMS.global;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="COUNTRY_CODE")
public class CountryCode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment
    @Column(name = "ID")
    private Integer id;

    @Column(name = "ISO_ALPHA2", nullable = false, length = 2)
    private String isoAlpha2;

    @Column(name = "ISO_ALPHA3", nullable = false, length = 3)
    private String isoAlpha3;

    @Column(name = "ISO_NUMERIC")
    private Integer numeric;

    @Column(name = "CONTINENT_COMMON", length = 50)
    private String contientCommon;

    @Column(name = "CONTINENT_ADMIN", length = 50)
    private String continentAdmin;

    @Column(name = "CONTINENT_MOFA", length = 50)
    private String continentMofa;

    @Column(name = "COUNTRY_EN", nullable = false, length = 100)
    private String countryEn;

    @Column(name = "COUNTRY_KR", nullable = false, length = 100)
    private String countryKr;
}
