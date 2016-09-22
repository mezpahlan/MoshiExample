package uk.co.mezpahlan.moshiexample.Presenter;

import java.util.List;

import uk.co.mezpahlan.moshiexample.Model.Restaurant;
import uk.co.mezpahlan.moshiexample.View.IRestaurantRequiredViewOps;
import uk.co.mezpahlan.moshiexample.View.RestaurantActivityView;

/**
 * Created by mpahlan on 10/07/16.
 */
public interface IRestaurantPresenterOps {
    void fetchRestaurants(String outCode);
    void onConfigurationChanged(IRestaurantRequiredViewOps restaurantActivityView);
    void onDestroy(boolean isChangingConfig);
}
