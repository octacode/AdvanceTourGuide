/**
 * Filename: FoursquareService.java
 * Author: Matthew Huie
 *
 * FoursquareService provides a Retrofit interface for the Foursquare API.
 */

package life.afor.code.advancetourguide.app.refs;

import life.afor.code.advancetourguide.app.model.FoursquareJSON;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface FoursquareService {

    @GET("venues/search?v=20161101&limit=1")
    Call<FoursquareJSON> snapToPlace(@Query("client_id") String clientID,
                                     @Query("client_secret") String clientSecret,
                                     @Query("ll") String ll,
                                     @Query("llAcc") double llAcc);

    @GET("search/recommendations?v=20161101&intent=topPicks")
    Call<FoursquareJSON> searchTopPicks(@Query("client_id") String clientID,
                                        @Query("client_secret") String clientSecret,
                                        @Query("ll") String ll,
                                        @Query("llAcc") double llAcc);

    @GET("search/recommendations?v=20161101&intent=food")
    Call<FoursquareJSON> searchFood(@Query("client_id") String clientID,
                                      @Query("client_secret") String clientSecret,
                                      @Query("ll") String ll,
                                      @Query("llAcc") double llAcc);

    @GET("search/recommendations?v=20161101&intent=coffee")
    Call<FoursquareJSON> searchCoffee(@Query("client_id") String clientID,
                                      @Query("client_secret") String clientSecret,
                                      @Query("ll") String ll,
                                      @Query("llAcc") double llAcc);

    @GET("search/recommendations?v=20161101&intent=drinks")
    Call<FoursquareJSON> searchNightLife(@Query("client_id") String clientID,
                                         @Query("client_secret") String clientSecret,
                                         @Query("ll") String ll,
                                         @Query("llAcc") double llAcc);

    @GET("search/recommendations?v=20161101&intent=outdoors")
    Call<FoursquareJSON> searchFun(@Query("client_id") String clientID,
                                   @Query("client_secret") String clientSecret,
                                   @Query("ll") String ll,
                                   @Query("llAcc") double llAcc);

    @GET("search/recommendations?v=20161101&intent=shops")
    Call<FoursquareJSON> searchShopping(@Query("client_id") String clientID,
                                        @Query("client_secret") String clientSecret,
                                        @Query("ll") String ll,
                                        @Query("llAcc") double llAcc);

}