package com.fenchtose.daggertest.app.ui.main;

import android.view.View;
import android.widget.EditText;

import com.fenchtose.daggertest.app.communication.DataRetrieverEvent;
import com.fenchtose.daggertest.app.user.User;

/**
 * Created by Jay Rambhia on 14/03/15.
 */
public interface MainPresenter {

    public void onResume();
    public void onPause();
    public void onButtonClicked();
    public void setClickObservable(View v);
    public void setTextObservable(EditText editText);
    public void getUserList(String name);

    public void onEvent(DataRetrieverEvent event);

    public void removeUser(User user);
}
