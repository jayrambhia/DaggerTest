package com.fenchtose.daggertest.app.communication;

/**
 * Created by Jay Rambhia on 16/03/15.
 */
public class DataRetrieverEvent {

    private String event;
    private Object data;

    public DataRetrieverEvent(String event, Object data) {
        this.event = event;
        this.data = data;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public Object getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
