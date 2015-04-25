package com.fenchtose.daggertest.app.ui.main;

import android.view.View;

import com.fenchtose.daggertest.app.user.UserList;

/**
 * Created by Jay Rambhia on 14/03/15.
 */
public interface MainView {

    public void showMessage(String message);
    public void clearViews();
    public void setItems(UserList userList);
}
