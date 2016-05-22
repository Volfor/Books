package org.fairytail.books.api;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import org.fairytail.books.Constants;
import org.fairytail.books.models.RealmString;

import java.io.IOException;
import java.lang.reflect.Type;

import io.realm.RealmList;
import io.realm.RealmObject;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private BooksService booksService;
    private static volatile ApiClient instance;

    private ApiClient() {
        init();
    }

    public static ApiClient getInstance() {
        if (instance == null) {
            synchronized (ApiClient.class) {
                if (instance == null) {
                    instance = new ApiClient();
                }
            }
        }
        return instance;
    }

    public void init() {
        Type type = new TypeToken<RealmList<RealmString>>() {
        }.getType();
        Gson gson = new GsonBuilder()
                .setExclusionStrategies(new ExclusionStrategy() {
                    @Override
                    public boolean shouldSkipField(FieldAttributes f) {
                        return f.getDeclaringClass().equals(RealmObject.class);
                    }

                    @Override
                    public boolean shouldSkipClass(Class<?> clazz) {
                        return false;
                    }
                })
                .registerTypeAdapter(type, new TypeAdapter<RealmList<RealmString>>() {
                    @Override
                    public void write(JsonWriter out, RealmList<RealmString> value) throws IOException {
                        //Ignore
                    }

                    @Override
                    public RealmList<RealmString> read(JsonReader in) throws IOException {
                        RealmList<RealmString> list = new RealmList<>();
                        in.beginArray();
                        while (in.hasNext()) {
                            list.add(new RealmString(in.nextString()));
                        }
                        in.endArray();
                        return list;
                    }
                })
                .create();

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .addInterceptor(chain -> {
                    Request request = chain.request();
                    HttpUrl url = request.url()
                            .newBuilder()
                            .addQueryParameter("maxResults", String.valueOf(Constants.MAX_RESULTS))
                            .build();
                    request = request.newBuilder().url(url).build();

                    return chain.proceed(request);
                })
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.API_ROOT)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build();

        booksService = retrofit.create(BooksService.class);
    }

    public BooksService getBooksService() {
        return booksService;
    }

}
