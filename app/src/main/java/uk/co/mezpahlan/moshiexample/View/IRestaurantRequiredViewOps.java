package uk.co.mezpahlan.moshiexample.View;

import java.util.List;

import uk.co.mezpahlan.moshiexample.Model.Restaurant;

/**
 * Created by mpahlan on 10/07/16.
 */
public interface IRestaurantRequiredViewOps {
    void showToast(String message);
    void showRestaurants(List<Restaurant> restaurantList);
}
