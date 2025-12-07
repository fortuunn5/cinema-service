package org.example.cinemaservice.listener;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Date;

@RequiredArgsConstructor
@Getter
public class ModificationEvent {
    private final Object source;
    private final Long sourceId;
    private final Date date;
}
