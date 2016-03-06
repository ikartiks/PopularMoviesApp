package udacity.popularmoviesapp.utility;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;
import udacity.popularmoviesapp.pojos.MovieWrapper;
import udacity.popularmoviesapp.pojos.ReviewWrapper;
import udacity.popularmoviesapp.pojos.TrailerWrapper;

/**
 * Created by kartikshah on 25/12/15.
 */
public interface ApiCalls {

//    @POST("user/user_mobile")
//    Call<VUser> createUser(@Body VUser user);
//
//    @POST("basic/bulk_contacts_create")
//    Call<Void> createBulkContacts(@Header("Authorization") String authorization, @Body VUserContactsList vSales);
//
//    @POST("basic/sales")
//    Call<VSales> createSale(@Header("Authorization") String authorization, @Body VSales vSales);
//
//    @GET("basic/salesCount/{id}/{all}")
//    Call<VSalesList> fetchSales(@Header("Authorization") String authorization, @Path("id") Long id, @Path("all") Boolean all);

//    @GET("basic/salesBreakup/")
//    Call<VSalesList> getPersonWiseBreakup(@Header("Authorization") String authorization);

    @GET("discover/movie?")
    Call<MovieWrapper> getMovieList(@Query("sort_by") String sortBy,
                                            @Query("api_key") String apiKey);

//    =popularity.desc&api_key=[YOUR API KEY]

    @GET("movie/{id}/videos?")
    Call<TrailerWrapper> getTrailers(@Path("id") Long id,
                                    @Query("api_key") String apiKey);

    @GET("movie/{id}/reviews")
    Call<ReviewWrapper> getReviews(@Path("id") Long id,
                                   @Query("api_key") String apiKey);

}
