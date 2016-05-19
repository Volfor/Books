package org.fairytail.books.api;

import org.fairytail.books.models.BookItems;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface BooksService {

    @GET("volumes")
    Call<BookItems> search(@Query("q") String keywords, @Query("maxResults") int maxResults);

    @GET("volumes")
    Call<BookItems> loadMore(@Query("q") String keywords, @Query("startIndex") int startIndex,
                             @Query("maxResults") int maxResults);

}
