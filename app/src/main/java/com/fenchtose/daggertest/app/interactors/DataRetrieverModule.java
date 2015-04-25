package com.fenchtose.daggertest.app.interactors;

import android.app.Application;

import com.fenchtose.daggertest.app.App;
import com.fenchtose.daggertest.app.db.DatabaseManager;
import com.fenchtose.daggertest.app.db.DatabaseModule;
import com.fenchtose.daggertest.app.ui.main.DataRetrieverImpl;

import javax.inject.Inject;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Jay Rambhia on 15/03/15.
 */

@Module(library = true)
public class DataRetrieverModule {

    @Provides public DataRetrieverImpl provideDataRetriever() {
        return new DataRetrieverImpl();
    }
}
