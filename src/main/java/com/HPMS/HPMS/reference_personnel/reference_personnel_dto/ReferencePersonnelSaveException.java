package com.HPMS.HPMS.reference_personnel.reference_personnel_dto;

public class ReferencePersonnelSaveException extends RuntimeException {
    public ReferencePersonnelSaveException(String message) {
        super(message);
    }

    public ReferencePersonnelSaveException(String message, Throwable cause) {
        super(message, cause);
    }
}
