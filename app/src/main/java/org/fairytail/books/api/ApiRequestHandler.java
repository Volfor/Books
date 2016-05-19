package org.fairytail.books.api;

import android.util.Log;

import org.fairytail.books.Constants;
import org.fairytail.books.events.OnLoadedEvent;
import org.fairytail.books.events.OnLoadingErrorEvent;
import org.fairytail.books.events.OnLoadingMoreStartEvent;
import org.fairytail.books.events.OnLoadingStartEvent;
import org.fairytail.books.events.OnMoreLoadedEvent;
import org.fairytail.books.models.BookItems;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApiRequestHandler {
    private ApiClient apiClient;

    public ApiRequestHandler() {
        apiClient = ApiClient.getInstance();
    }

    @Subscribe
    public void onLoadingStart(OnLoadingStartEvent e) {
        apiClient.getBooksService().search(e.keywords, Constants.MAX_RESULTS)
                .enqueue(new Callback<BookItems>() {
                    @Override
                    public void onResponse(Call<BookItems> call, Response<BookItems> response) {
                        if (response.isSuccessful()) {
                            EventBus.getDefault().post(new OnLoadedEvent(response.body()));
                        } else {
                            int statusCode = response.code();
                            ResponseBody errorBody = response.errorBody();
                            EventBus.getDefault().post(new OnLoadingErrorEvent(response.errorBody().toString()));
                        }
                    }

                    @Override
                    public void onFailure(Call<BookItems> call, Throwable error) {
                        if (error != null && error.getMessage() != null) {
                            EventBus.getDefault().post(new OnLoadingErrorEvent(error.getMessage()));
                        } else {
                            EventBus.getDefault().post(new OnLoadingErrorEvent("Failed!"));
                            Log.e(Constants.LOG_TAG, "Failed!");
                        }
                    }
                });
    }

    @Subscribe
    public void onLoadingMoreStart(OnLoadingMoreStartEvent e) {
        apiClient.getBooksService().loadMore(e.keywords, e.startIndex,
                Constants.MAX_RESULTS).enqueue(new Callback<BookItems>() {
            @Override
            public void onResponse(Call<BookItems> call, Response<BookItems> response) {
                if (response.isSuccessful()) {
                    EventBus.getDefault().post(new OnMoreLoadedEvent(response.body()));
                } else {
                    int statusCode = response.code();
                    ResponseBody errorBody = response.errorBody();
                    EventBus.getDefault().post(new OnLoadingErrorEvent(response.errorBody().toString()));
                }
            }

            @Override
            public void onFailure(Call<BookItems> call, Throwable error) {
                if (error != null && error.getMessage() != null) {
                    EventBus.getDefault().post(new OnLoadingErrorEvent(error.getMessage()));
                } else {
                    EventBus.getDefault().post(new OnLoadingErrorEvent("Failed!"));
                    Log.e(Constants.LOG_TAG, "Failed!");
                }
            }
        });
    }

}