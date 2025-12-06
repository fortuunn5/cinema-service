package org.example.cinemaservice.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum Genre {
    BIOGRAPHY("биография"),
    ACTION("боевик"),
    DETECTIVE("детектив"),
    DOCUMENTARY("документальный"),
    DRAMA("драма"),
    HISTORICAL("исторический"),
    COMEDY("комедия"),
    CRIME("криминал"),
    ROMANCE("мелодрама"),
    MYSTERY("мистика"),
    ANIMATION("мультфильм"),
    MUSICAL("мюзикл"),
    ADVENTURE("приключение"),
    THRILLER("триллер"),
    HORROR("ужасы"),
    SCIENCE_FICTION("фантастика"),
    FANTASY("фэнтези");

    private final String value;

    @JsonValue
    public String getGenre() {
        return value;
    }

    @JsonCreator
    public static Genre getGenreByValue(String genre) {
        for (Genre g : Genre.values()) {
            if (g.getGenre().equals(genre)) {
                return g;
            }
        }
        return null;
    }
}
