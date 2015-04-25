package com.fenchtose.daggertest.app.ui.main;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.support.v7.widget.RecyclerView;

import com.fenchtose.daggertest.app.App;
import com.fenchtose.daggertest.app.R;
import com.fenchtose.daggertest.app.ui.common.BaseActivity;
import com.fenchtose.daggertest.app.user.User;
import com.fenchtose.daggertest.app.user.UserAdapter;
import com.fenchtose.daggertest.app.user.UserList;
import com.fenchtose.daggertest.app.utils.SwipeDismissRecyclerViewTouchListener;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import jp.wasabeef.recyclerview.animators.LandingAnimator;

public class MainActivity extends BaseActivity implements MainView {

    @Inject
    MainPresenter presenter;
    private Button button;
    private EditText editText;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = (Button)findViewById(R.id.button);
        button.setVisibility(View.GONE);
        editText = (EditText)findViewById(R.id.edittext);
        presenter.setTextObservable(editText);
        presenter.setClickObservable(button);

        recyclerView = (RecyclerView)findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new LandingAnimator());
        recyclerView.getItemAnimator().setRemoveDuration(500);
        recyclerView.getItemAnimator().setAddDuration(600);
        recyclerView.setAdapter(new UserAdapter(this, R.layout.user_view_layout, null));

        setSwipeListener();
    }

    @Override
    protected List<Object> getModules() {
        return Arrays.<Object>asList(new MainModule(this));
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        presenter.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onPause();
    }

    @Override
    public void showMessage(final String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void clearViews() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (editText != null) {
                    editText.setText("");
                }

                UserAdapter userAdapter = (UserAdapter)recyclerView.getAdapter();

                int size = userAdapter.getItemCount();
                if (size > 0) {
                    userAdapter.clearUserList();
                    userAdapter.notifyItemRangeRemoved(0, size);
                }
            }
        });


    }

    @Override
    public void setItems(final UserList userList) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                UserAdapter userAdapter = (UserAdapter)recyclerView.getAdapter();
                userAdapter.setUserList(userList);
//                userAdapter.notifyDataSetChanged();
                userAdapter.notifyItemRangeInserted(0, userAdapter.getItemCount());
            }
        });
    }

    private void setSwipeListener() {
        SwipeDismissRecyclerViewTouchListener touchListener =
                new SwipeDismissRecyclerViewTouchListener(recyclerView,
                        new SwipeDismissRecyclerViewTouchListener.DismissCallbacks() {
                            @Override
                            public boolean canDismiss(int position) {
                                return true;
                            }

                            @Override
                            public void onDismiss(RecyclerView recyclerView, int[] reverseSortedPositions) {
                                UserAdapter userAdapter = (UserAdapter)recyclerView.getAdapter();
                                for (int position : reverseSortedPositions) {
                                    User user = userAdapter.removeItem(position);
                                    presenter.removeUser(user);
                                    userAdapter.notifyDataSetChanged();
                                }
                            }
                        }
                );
        touchListener.setEnabled(true);
        recyclerView.setOnTouchListener(touchListener);
    }
}
