package com.iss.service;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import java.util.ArrayList;
import java.util.List;

public abstract class ObservableService<T,K> implements ICrudService<T,K> {

    protected final ICrudService<T,K> target;
    private List<EventHandler<ActionEvent>> eventHandlerList = new ArrayList<>();

    public ObservableService(ICrudService<T,K> target) {
        this.target = target;
    }

    @Override
    public Iterable<T> getAll() {
        return target.getAll();
    }

    public void triggerEvent() {
        ActionEvent event = new ActionEvent();
        for (EventHandler<ActionEvent> eventHandler : eventHandlerList)
            eventHandler.handle(event);
    }

    public void addHandler(EventHandler<ActionEvent> handler) {
        eventHandlerList.add(handler);
    }

    @SuppressWarnings("unused")
    public void removeHandler(EventHandler<ActionEvent> handler) {
        eventHandlerList.remove(handler);
    }

    public ICrudService<T,K> getTarget() {
        return target;
    }

}
