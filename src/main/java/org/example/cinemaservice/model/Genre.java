package org.example.cinemaservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

//todo: сделать геттер и навесить аннотацию @JsonValue
@AllArgsConstructor
@Getter
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

    public static Genre getGenreByValue(String genre) {
        for (Genre g : Genre.values()) {
            if (g.value.equals(genre)) {
                return g;
            }
        }
        return null;
    }
}
