package com.fenchtose.daggertest.app;

import android.app.Application;

import com.fenchtose.daggertest.app.db.DatabaseModule;
import com.fenchtose.daggertest.app.interactors.DataRetrieverModule;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Jay Rambhia on 14/03/15.
 */

@Module(injects = {App.class},
        includes = {DatabaseModule.class,
                    DataRetrieverModule.class})

public class AppModule {

    private App app;

    public AppModule(App app) {
        this.app = app;
    }

    @Provides public Application provideApplication() {
        return app;
    }
}
