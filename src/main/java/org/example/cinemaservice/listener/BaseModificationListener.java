package org.example.cinemaservice.listener;

public interface BaseModificationListener {
    void handle(ModificationEvent event);

    ListenerStage getStage();
    Class getListenerClass(); // todo пояснить
    int getOrder();
}
