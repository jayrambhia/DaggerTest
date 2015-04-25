package com.fenchtose.daggertest.app.ui.main;

import android.util.Log;

import com.fenchtose.daggertest.app.communication.DataRetrieverCommEvent;
import com.fenchtose.daggertest.app.communication.DataRetrieverEvent;
import com.fenchtose.daggertest.app.db.DatabaseHelper;
import com.fenchtose.daggertest.app.db.DatabaseManager;
import com.fenchtose.daggertest.app.user.User;
import com.fenchtose.daggertest.app.user.UserList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;

import javax.inject.Inject;

import de.greenrobot.event.EventBus;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.http.Path;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;

/**
 * Created by Jay Rambhia on 15/03/15.
 */
public class DataRetrieverImpl {

    private static final String SERVER_END_POINT = "https://api.github.com/";

    private RestAdapter restAdapter;
    private DataRetriever dataRetriever;
    private EventBus eventBus;

    DatabaseManager dbManager;
    private DatabaseHelper dbHelper;

    private static final String TAG = "DataRetrieverImpl";

    public DataRetrieverImpl() {
        restAdapter = new RestAdapter.Builder().setEndpoint(SERVER_END_POINT).build();
        dataRetriever = restAdapter.create(DataRetriever.class);
        eventBus = EventBus.getDefault();
        eventBus.register(this);
//        this.dbManager = dbManager;
    }

    public void setDbManager(DatabaseManager dbManager) {
        this.dbManager = dbManager;
        dbHelper = this.dbManager.getHelper();
    }

    public void onResume() {
        if (!eventBus.isRegistered(this)) {
            eventBus.register(this);
        }
    }

    public void onPause() {
        if (eventBus.isRegistered(this)) {
            eventBus.unregister(this);
        }
    }

    public void getUserList(final String name) {
        Log.i(TAG, "getting user list for: " + name);

        /*
        Observable<UserList> observable = Observable.create(new Observable.OnSubscribe<UserList>() {
            @Override
            public void call(Subscriber<? super UserList> subscriber) {
                try {
                    subscriber.onNext(dataRetriever.getUserList(name));
                    subscriber.onCompleted();
                } catch (RetrofitError e) {
//                    e.printStackTrace();
                    Log.e(TAG, "error: " + e.getMessage());
                    Log.e(TAG, "url : " + e.getUrl());
                    subscriber.onError(e);
                }
            }
        });
        */
//        Observable<UserList> observable = dataRetriever.getUserList(name);
//        observable.

        Observable<UserList> observable = dataRetriever.getUserList(name);

        Log.i(TAG, "subscribe to get userlist");
        observable.subscribe(new Action1<UserList>() {
            @Override
            public void call(UserList userList) {

                eventBus.post(new DataRetrieverEvent("UserList", userList));

                // save to database
                for (User user : userList.getItems()) {
                    Log.i(TAG, user.getLogin());
                    try {
                        dbHelper.create(user);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }

            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                throwable.printStackTrace();
            }
        });

        Log.i(TAG, "retrun void");
        /*

        Callback<Response> responseCallback = new Callback<Response>() {
            @Override
            public void success(Response result, Response response2) {
                BufferedReader reader = null;
                StringBuilder sb = new StringBuilder();
                try {

                    reader = new BufferedReader(new InputStreamReader(result.getBody().in()));

                    String line;

                    try {
                        while ((line = reader.readLine()) != null) {
                            sb.append(line);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }


                String resultstr = sb.toString();
                Log.i(TAG, resultstr);
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e(TAG, error.toString());
                Log.e(TAG, error.getUrl());
            }
        };

        dataRetriever.getUserListWithCallback(name, responseCallback);
        */
    }

    public UserList getSavedUsers() {
        UserList userList = new UserList();
        try {
            userList.setItems(dbHelper.findAll());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return userList;
    }

    public void removeUser(final User user) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    dbHelper.remove(user);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void onEvent(DataRetrieverCommEvent event) {
        Log.i(TAG, "DataRetrieverCommEvent");
    }
}
