package com.fenchtose.daggertest.app.communication;

/**
 * Created by Jay Rambhia on 16/03/15.
 */
public class DataRetrieverCommEvent {

    private String event;
    private String data;

    public DataRetrieverCommEvent(String event, String data) {
        this.event = event;
        this.data = data;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
