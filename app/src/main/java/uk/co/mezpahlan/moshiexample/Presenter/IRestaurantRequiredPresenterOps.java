package uk.co.mezpahlan.moshiexample.Presenter;

import java.util.List;

import uk.co.mezpahlan.moshiexample.Model.Restaurant;

/**
 * Created by mpahlan on 10/07/16.
 */
public interface IRestaurantRequiredPresenterOps {
    void onFetchSuccess(List<Restaurant> restaurantList);
    void onFetchError(Throwable t);
}
