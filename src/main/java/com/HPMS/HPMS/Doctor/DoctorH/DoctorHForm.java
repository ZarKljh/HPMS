package com.HPMS.HPMS.Doctor.DoctorH;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class DoctorHForm {
    private Integer doctorId;           // 의사ID로 필터
    private String keyword;             // 통합 검색(이름, 부서, 메모 등)

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate fromDate;         // 생성일(시작)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate toDate;           // 생성일(끝)

    // 유틸: 날짜를 LocalDateTime 범위로 변환 (종료일 23:59:59 포함)
    public LocalDateTime fromDateTime() {
        return (fromDate == null) ? null : fromDate.atStartOfDay();
    }
    public LocalDateTime toDateTime() {
        return (toDate == null) ? null : toDate.atTime(23,59,59);
    }
}
