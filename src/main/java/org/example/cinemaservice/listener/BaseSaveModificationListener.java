package org.example.cinemaservice.listener;

public abstract class BaseSaveModificationListener implements BaseModificationListener {

    @Override
    public ListenerStage getStage() {
        return ListenerStage.SAVE;
    }
}
