package org.example.cinemaservice.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum DisplayFormat {
    _2D("2D"),
    _3D("3D");

    private final String value;

    @JsonValue
    public String getDisplayFormat() {
        return value;
    }

    @JsonCreator
    public static DisplayFormat getDisplayFormatByValue(String value) {
        for (DisplayFormat displayFormat : DisplayFormat.values()) {
            if (displayFormat.getDisplayFormat().equals(value)) {
                return displayFormat;
            }
        }
        return null;
    }
}
