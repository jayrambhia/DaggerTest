package com.fenchtose.daggertest.app;

import android.app.Application;
import android.util.Log;

import com.fenchtose.daggertest.app.db.DatabaseManager;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import dagger.ObjectGraph;

/**
 * Created by Jay Rambhia on 14/03/15.
 */
public class App extends Application {

    private ObjectGraph objectGraph;

    @Inject
    DatabaseManager databaseManager;

    private static final String TAG = "App.App.App";

    @Override
    public void onCreate() {
        super.onCreate();
        objectGraph = ObjectGraph.create(getModules().toArray());
        objectGraph.inject(this);
        Log.i(TAG, "onCreate called");
        databaseManager.prepareHelper();
    }

    @Override
    public void onTerminate() {
        Log.i(TAG, "onTerminate called");
        databaseManager.releaseHelper();
    }

    public DatabaseManager getDatabaseManager() {
        return databaseManager;
    }

    private List<Object> getModules() {
        return Arrays.<Object>asList(new AppModule(this));
    }

    public ObjectGraph createScopedGraph(Object... modules) {
        return objectGraph.plus(modules);
    }
}
