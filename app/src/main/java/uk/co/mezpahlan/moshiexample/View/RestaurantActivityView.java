package uk.co.mezpahlan.moshiexample.View;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import uk.co.mezpahlan.moshiexample.Model.Adapters.RestaurantAdapter;
import uk.co.mezpahlan.moshiexample.Model.Restaurant;
import uk.co.mezpahlan.moshiexample.Presenter.IRestaurantPresenterOps;
import uk.co.mezpahlan.moshiexample.Presenter.RestaurantPresenter;
import uk.co.mezpahlan.moshiexample.R;

public class RestaurantActivityView extends AppCompatActivity implements IRestaurantRequiredViewOps {

    private Button gpsButton;
    private EditText searchText;
    private RecyclerView recyclerView;
    private RestaurantAdapter restaurantAdapter;
    private List<Restaurant> restaurantList = new ArrayList<>();
    private final String RESTUARANT_LIST_ID = "uk.co.mezpahlan.moshiexample.restaurantlist";
    private final String TAG = getClass().getSimpleName();

    private final StateMaintainer mStateMaintainer = new StateMaintainer(getFragmentManager(), TAG );

    // Presenter Operations
    private IRestaurantPresenterOps restaurantPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        gpsButton = (Button) findViewById(R.id.gpsButton);
        searchText = (EditText) findViewById(R.id.searchText);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        restaurantAdapter = new RestaurantAdapter(restaurantList);
        recyclerView.setAdapter(restaurantAdapter);

        startMVPOps();

        searchText.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE ||
                        (event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    gpsButton.performClick();
                    return true;
                }
                return false;
            }
        });

        gpsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v != null) {
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }

                String outCode = searchText.getText().toString().trim();
                restaurantPresenter.fetchRestaurants(outCode);
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // store the data in the fragment
        mStateMaintainer.put(IRestaurantPresenterOps.class.getSimpleName(), restaurantPresenter);
    }


    private void startMVPOps() {
        try {
            if (mStateMaintainer.firstTimeIn()) {
                Log.d(TAG, "onCreate() called for the first time");
                initialize(this);
            } else {
                Log.d(TAG, "onCreate() called more than once");
                reinitialize(this);
            }
        } catch (InstantiationException e) {
            Log.d(TAG, "onCreate() " + e );
            throw new RuntimeException( e );
        } catch (IllegalAccessException e) {
            Log.d(TAG, "onCreate() " + e );
            throw new RuntimeException( e );
        }
    }

    private void reinitialize(IRestaurantRequiredViewOps restaurantView) throws InstantiationException, IllegalAccessException {
        // Reattach the presenter reference from the state maintainer to the new view that has been created on configuration change.
        restaurantPresenter = mStateMaintainer.get(IRestaurantPresenterOps.class.getSimpleName());

        // Catch the case where we have lost the reference...... start again.
        if (restaurantPresenter == null) {
            Log.w(TAG, "recreating Presenter");
            initialize(restaurantView);
        } else {
            // If everything is OK try and restore from configuration change.
            restaurantPresenter.onConfigurationChanged(restaurantView);
        }
    }

    private void initialize(IRestaurantRequiredViewOps restaurantView) throws InstantiationException, IllegalAccessException {
        // View creates the presenter
        restaurantPresenter = new RestaurantPresenter(restaurantView);

        // And stores it in the state maintainer
        mStateMaintainer.put(IRestaurantPresenterOps.class.getSimpleName(), restaurantPresenter);
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showRestaurants(List<Restaurant> restaurantList) {
        restaurantAdapter.swap(restaurantList);
    }
}
