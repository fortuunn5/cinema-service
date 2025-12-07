package org.example.cinemaservice.listener;

public abstract class BaseDeleteModificationListener implements BaseModificationListener {

    @Override
    public ListenerStage getStage() {
        return ListenerStage.DELETE;
    }
}
