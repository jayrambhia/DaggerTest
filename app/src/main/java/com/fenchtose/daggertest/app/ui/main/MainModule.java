package com.fenchtose.daggertest.app.ui.main;

import com.fenchtose.daggertest.app.AppModule;
import com.fenchtose.daggertest.app.db.DatabaseManager;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Jay Rambhia on 14/03/15.
 */

@Module(injects = MainActivity.class,
        addsTo = AppModule.class)
public class MainModule {

    private MainView view;

    public MainModule(MainView view) {
        this.view = view;
    }

    @Provides
    @Singleton
    public MainView provideView() {
        return view;
    }

    @Provides
    @Singleton
    public MainPresenter providePresenter(MainView mainView, DataRetrieverImpl dataRetriever, DatabaseManager dbManager) {
        return new MainPresenterImpl(mainView, dataRetriever, dbManager);
    }
}
