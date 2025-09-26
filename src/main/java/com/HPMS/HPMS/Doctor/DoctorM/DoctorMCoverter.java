package com.HPMS.HPMS.Doctor.DoctorM;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class DoctorMCoverter implements AttributeConverter<String, String> {

    @Override
    public String convertToDatabaseColumn(String attribute) {
        if (attribute == null) return null;
        attribute = attribute.trim();

        // 오직 "의과대학생"일 때만 앞 2글자 잘라냄
        if ("의과대학생".equals(attribute)) {
            return attribute.substring(2); // "대학생"
        }

        return attribute; // 나머지는 그대로
    }

    @Override
    public String convertToEntityAttribute(String dbData) {
        return dbData; // DB 값 그대로 반환
    }
}
