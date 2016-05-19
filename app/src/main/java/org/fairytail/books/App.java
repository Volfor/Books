package org.fairytail.books;

import android.app.Application;

import org.fairytail.books.api.ApiRequestHandler;
import org.greenrobot.eventbus.EventBus;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class App extends Application {
    public static ApiRequestHandler apiRequestHandler;

    @Override
    public void onCreate() {
        super.onCreate();
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(this).build();
        Realm.setDefaultConfiguration(realmConfiguration);

        apiRequestHandler = new ApiRequestHandler();
        EventBus.getDefault().register(apiRequestHandler);
    }

}
