package uk.co.mezpahlan.moshiexample.Model;

import com.squareup.moshi.Moshi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;
import uk.co.mezpahlan.moshiexample.API.JustEatService;
import uk.co.mezpahlan.moshiexample.Model.Adapters.DateAdapter;
import uk.co.mezpahlan.moshiexample.Presenter.IRestaurantRequiredPresenterOps;

/**
 * Created by mpahlan on 10/07/16.
 */
public class RestaurantModel implements IRestaurantModelOps {

    // Presenter reference
    private IRestaurantRequiredPresenterOps presenter;

    public RestaurantModel(IRestaurantRequiredPresenterOps presenter) {
        this.presenter = presenter;
    }

    @Override
    public void fetchRestaurants(String outCode) {
        Moshi moshi = new Moshi.Builder()
                .add(new DateAdapter())
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://public.je-apis.com/")
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .build();

        JustEatService justEatService = retrofit.create(JustEatService.class);

        Call<Restaurants> restaurants = justEatService.getRestaurants(outCode);

        restaurants.enqueue(new Callback<Restaurants>() {
            @Override
            public void onResponse(Call<Restaurants> call, Response<Restaurants> response) {
                Restaurants restaurants = response.body();
                List<Restaurant> restaurantList = restaurants.getRestaurants();
                presenter.onFetchSuccess(restaurantList);
            }

            @Override
            public void onFailure(Call<Restaurants> call, Throwable t) {
                presenter.onFetchError(t);
            }
        });
    }

    @Override
    public void onDestroy() {
        // TODO: Finish clean up
    }
}
