package com.fenchtose.daggertest.app.ui.main;

import com.fenchtose.daggertest.app.user.UserList;

import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;
import rx.Observable;

/**
 * Created by Jay Rambhia on 15/03/15.
 */
public interface DataRetriever {

    @GET("/search/users")
    public Observable<UserList> getUserList(@Query("q") String name);

    @GET("/search/users")
    public void getUserListWithCallback(@Query("q") String name, Callback<Response> callback);
}
