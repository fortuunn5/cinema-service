package org.example.cinemaservice.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Status {
    RESERVED("забронировано"),
    PAID("оплачено"),
    CANCELLED("отменено");

    private final String value;

    @JsonValue
    public String getStatus() {
        return value;
    }

    @JsonCreator
    public static Status getStatusByValue(String value) {
        for (Status status : Status.values()) {
            if (status.getStatus().equals(value)) {
                return status;
            }
        }
        return null;
    }
}
