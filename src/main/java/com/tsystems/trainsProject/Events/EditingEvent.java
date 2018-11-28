package com.tsystems.trainsProject.Events;

import org.springframework.context.ApplicationEvent;

public class EditingEvent extends ApplicationEvent {
    private String message;
    private int type;

    public EditingEvent(Object source, String message, int type) {
        super(source);
        this.message = message;
        this.type=type;
    }

    public String getMessage() {
        return message;
    }

    public int getType() {
        return type;
    }
}
