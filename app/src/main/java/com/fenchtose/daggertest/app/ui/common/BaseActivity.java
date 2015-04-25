package com.fenchtose.daggertest.app.ui.common;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.fenchtose.daggertest.app.App;

import java.util.List;

import dagger.ObjectGraph;

/**
 * Created by Jay Rambhia on 14/03/15.
 */
public abstract class BaseActivity extends ActionBarActivity {

    private ObjectGraph activityGraph;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityGraph = ((App)getApplication()).createScopedGraph(getModules().toArray());
        activityGraph.inject(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        activityGraph = null;
    }

    protected abstract List<Object> getModules();
}
