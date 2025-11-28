package org.example.cinemaservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum DisplayFormat {
    _2D("2D"),
    _3D("3D");

    private final String value;

    public static DisplayFormat getDisplayFormatByValue(String value) {
        for (DisplayFormat displayFormat : DisplayFormat.values()) {
            if (displayFormat.value.equals(value)) {
                return displayFormat;
            }
        }
        return null;
    }
}
