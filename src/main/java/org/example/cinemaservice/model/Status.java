package org.example.cinemaservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Status {
    RESERVED("забронировано"),
    PAID("оплачено"),
    CANCELLED("отменено");

    private final String value;

    public static Status getStatusByValue(String value) {
        for (Status status : Status.values()) {
            if (status.getValue().equals(value)) {
                return status;
            }
        }
        return null;
    }
}
