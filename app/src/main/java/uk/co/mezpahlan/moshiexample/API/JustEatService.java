package uk.co.mezpahlan.moshiexample.API;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;
import uk.co.mezpahlan.moshiexample.Model.Restaurants;

/**
 * Created by mpahlan on 27/06/16.
 */
public interface JustEatService {

    @Headers({
            "Accept-Tenant: uk",
            "Accept-Language: en-GB",
            "Authorization: Basic VGVjaFRlc3RBUEk6dXNlcjI=",
            "Host: public.je-apis.com"
    })
    @GET("restaurants")
    Call<Restaurants> getRestaurants(@Query("q") String outcode);
}
