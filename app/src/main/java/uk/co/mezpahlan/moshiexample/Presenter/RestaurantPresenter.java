package uk.co.mezpahlan.moshiexample.Presenter;

import android.util.Log;

import java.lang.ref.WeakReference;
import java.util.List;

import uk.co.mezpahlan.moshiexample.Model.IRestaurantModelOps;
import uk.co.mezpahlan.moshiexample.Model.Restaurant;
import uk.co.mezpahlan.moshiexample.Model.RestaurantModel;
import uk.co.mezpahlan.moshiexample.View.IRestaurantRequiredViewOps;

/**
 * Created by mpahlan on 10/07/16.
 */
public class RestaurantPresenter implements IRestaurantPresenterOps, IRestaurantRequiredPresenterOps {
    // Layer View reference
    private WeakReference<IRestaurantRequiredViewOps> restaurantView;
    // Layer Model reference
    private IRestaurantModelOps model;
    // Configuration change state
    private boolean isChangingConfig;
    private List<Restaurant> restaurantList;

    public RestaurantPresenter(IRestaurantRequiredViewOps restaurantView){
        this.restaurantView = new WeakReference<>(restaurantView);
        this.model = new RestaurantModel(this);
    }

    @Override
    public void fetchRestaurants(String outCode) {
        if (outCode.length() == 0 && restaurantView != null) {
            restaurantView.get().showToast("You must enter a post code");
            return;
        } else {
            model.fetchRestaurants(outCode);
        }
    }

    @Override
    public void onConfigurationChanged(IRestaurantRequiredViewOps restaurantView) {
        // Swap reference to view with new view that came from a config change.
        this.restaurantView = new WeakReference<>(restaurantView);

        // And if we have some data.... load that too!
        if (this.restaurantList != null) {
            restaurantView.showRestaurants(this.restaurantList);
        }

    }

    @Override
    public void onDestroy(boolean isChangingConfig) {
        this.restaurantView = null;
        this.isChangingConfig = isChangingConfig;
        if (!this.isChangingConfig) {
            model.onDestroy();
        }
    }

    @Override
    public void onFetchSuccess(List<Restaurant> restaurantList) {
        this.restaurantList = restaurantList;
        if (restaurantView != null) {
            restaurantView.get().showRestaurants(restaurantList);
        }
    }

    @Override
    public void onFetchError(Throwable t) {
        Log.e("MOSHI", "Bang!",  t);
    }
}
