package com.fenchtose.daggertest.app.db;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import com.j256.ormlite.android.apptools.OpenHelperManager;

/**
 * Created by Jay Rambhia on 16/03/15.
 */
public class DatabaseManager {

    private DatabaseHelper databaseHelper = null;
    private Application app;

    public DatabaseManager(Application app) {
        this.app = app;
    }

    public void prepareHelper() {
        if (databaseHelper == null) {
            databaseHelper = OpenHelperManager.getHelper(app.getApplicationContext(), DatabaseHelper.class);
        }
        Toast.makeText(app.getApplicationContext(), "Database Helper prepared", Toast.LENGTH_SHORT).show();
    }

    public DatabaseHelper getHelper() {
        if (databaseHelper == null) {
            prepareHelper();
        }

        return databaseHelper;
    }

    public void releaseHelper() {
        if (databaseHelper != null) {
            OpenHelperManager.releaseHelper();
            databaseHelper = null;
        }

        Toast.makeText(app.getApplicationContext(), "Database Helper released", Toast.LENGTH_SHORT).show();
    }

    public void ping() {
        Toast.makeText(app.getApplicationContext(), "Ping", Toast.LENGTH_SHORT).show();
    }
}
