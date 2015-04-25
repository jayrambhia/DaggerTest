package com.fenchtose.daggertest.app.user;

import java.util.List;

/**
 * Created by Jay Rambhia on 15/03/15.
 */
public class UserList {

    private int total_count;
    private List<User> items;

    public int getTotal_count() {
        return total_count;
    }

    public void setTotal_count(int total_count) {
        this.total_count = total_count;
    }

    public List<User> getItems() {
        return items;
    }

    public void setItems(List<User> items) {
        this.items = items;
    }
}
