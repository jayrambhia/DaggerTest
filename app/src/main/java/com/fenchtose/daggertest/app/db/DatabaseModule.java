package com.fenchtose.daggertest.app.db;

import android.app.Application;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Jay Rambhia on 16/03/15.
 */
@Module(complete = false, library = true)
public class DatabaseModule {


    @Provides
    @Singleton
    public DatabaseManager provideDatabaseManager(Application app) {
        return new DatabaseManager(app);
    }
}
