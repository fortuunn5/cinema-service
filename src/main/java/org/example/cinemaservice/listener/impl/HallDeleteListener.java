package org.example.cinemaservice.listener.impl;

import org.example.cinemaservice.listener.BaseDeleteModificationListener;
import org.example.cinemaservice.listener.ModificationEvent;
import org.example.cinemaservice.model.Hall;
import org.springframework.stereotype.Service;

@Service
public class HallDeleteListener extends BaseDeleteModificationListener {

    @Override
    public void handle(ModificationEvent event) {
        Hall source = (Hall) event.getSource();
        if (source.getCapacity() < 0) {
            throw new RuntimeException("Hall capacity is less than zero");
        }
    }

    @Override
    public Class getListenerClass() {
        return Hall.class;
    }

    @Override
    public int getOrder() {
        return 5;
    }
}
