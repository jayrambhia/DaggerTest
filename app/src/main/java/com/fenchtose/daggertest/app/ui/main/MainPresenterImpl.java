package com.fenchtose.daggertest.app.ui.main;

import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.fenchtose.daggertest.app.communication.DataRetrieverEvent;
import com.fenchtose.daggertest.app.db.DatabaseManager;
import com.fenchtose.daggertest.app.user.User;
import com.fenchtose.daggertest.app.user.UserList;

import java.util.List;
import java.util.concurrent.TimeUnit;

import de.greenrobot.event.EventBus;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by Jay Rambhia on 14/03/15.
 */
public class MainPresenterImpl implements MainPresenter {

    private MainView mainView;
    private Subscription subscription;
    private Subscription editTextSubscription;
    private DataRetrieverImpl dataRetriever;
    private EventBus eventBus;

    private static final String TAG = "MainPresenterImpl";

    public MainPresenterImpl(MainView view, DataRetrieverImpl dRetriever, DatabaseManager dbManager) {
        this.mainView = view;
        this.dataRetriever = dRetriever;
        eventBus = EventBus.getDefault();
        dataRetriever.setDbManager(dbManager);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mainView.setItems(dataRetriever.getSavedUsers());
            }
        }, 500);
    }

    @Override
    public void onResume() {
        mainView.showMessage("onResume");
        dataRetriever.onResume();
        if (!eventBus.isRegistered(this)) {
            eventBus.register(this);
        }
    }

    @Override
    public void onPause() {
        if (subscription != null) {
            subscription.unsubscribe();
        }

        if (editTextSubscription != null) {
            editTextSubscription.unsubscribe();
        }

        dataRetriever.onPause();

        if (eventBus.isRegistered(this)) {
            eventBus.unregister(this);
        }
    }

    @Override
    public void onButtonClicked() {
        mainView.showMessage("Button Clicked");
    }

    @Override
    public void getUserList(String name) {
        dataRetriever.getUserList(name);
    }

    @Override
    public void onEvent(DataRetrieverEvent event) {
        Log.i(TAG, "DataRetrieverEvent");
        Log.i(TAG, event.getEvent());
        UserList userList = (UserList)event.getData();
        Log.i(TAG, "size: " + String.valueOf(userList.getItems().size()));
        mainView.setItems(userList);
    }

    @Override
    public void setClickObservable(final View v) {
        subscription = Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(final Subscriber<? super Integer> subscriber) {
                v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        subscriber.onNext(1);
                    }
                });
            }
        }).buffer(500, TimeUnit.MILLISECONDS)
          .flatMap(new Func1<List<Integer>, Observable<Boolean>>() {
              @Override
              public Observable<Boolean> call(final List<Integer> integers) {

                  return Observable.create(new Observable.OnSubscribe<Boolean>() {
                      @Override
                      public void call(Subscriber<? super Boolean> subscriber) {
                          if (integers.size() > 1) {
                              subscriber.onNext(true);
                          } else {
//                              Log.i(TAG, "subscriber onnext false");
                              subscriber.onNext(false);
                          }
                      }
                  });
              }
          })
          .subscribe(new Action1<Object>() {
              @Override
              public void call(Object o) {
                  if ((Boolean) o) {
                      mainView.showMessage("Clicked more than once");
                  } else {
//                      Log.i(TAG, "clicked only once");
                  }
              }
          });

    }

    @Override
    public void setTextObservable(final EditText editText) {

        editTextSubscription = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(final Subscriber<? super String> subscriber) {

                editText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        subscriber.onNext(s.toString());
                    }
                });
            }
        }).buffer(1200, TimeUnit.MILLISECONDS)
          .flatMap(new Func1<List<String>, Observable<Boolean>>() {
              @Override
              public Observable<Boolean> call(final List<String> strings) {
                  return Observable.create(new Observable.OnSubscribe<Boolean>() {
                      @Override
                      public void call(Subscriber<? super Boolean> subscriber) {
                          if (strings.size() > 1) {
//                              Log.i(TAG, "strings size: " + String.valueOf(strings.size()));
                              subscriber.onNext(false);
                          } else {
                              if (editText.getText().length() > 0) {
//                                  Log.i(TAG, "sending to subscriber");
                                  subscriber.onNext(true);
                                  return;
                              }
//                              Log.e(TAG, "string size is 0");

                          }
                      }
                  });
              }
          })
          .subscribe(new Action1<Object>() {
              @Override
              public void call(Object o) {
                  if ((Boolean) o) {
                      mainView.showMessage(editText.getText().toString());
                      mainView.clearViews();
                      dataRetriever.getUserList(editText.getText().toString());
                  } else {
//                      Log.e(TAG, "editText unchanged");
                  }
              }
          });
    }

    @Override
    public void removeUser(User user) {
        dataRetriever.removeUser(user);
    }
}
