package uk.co.mezpahlan.moshiexample.Model.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import uk.co.mezpahlan.moshiexample.Model.CuisineType;
import uk.co.mezpahlan.moshiexample.Model.Restaurant;
import uk.co.mezpahlan.moshiexample.R;

/**
 * Created by mpahlan on 03/07/16.
 */
public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.MyViewHolder> {

    private List<Restaurant> restaurantList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final ImageView image;
        private final TextView name;
        private final TextView cuisineType;
        private final RatingBar rating;

        public MyViewHolder(View itemView) {
            super(itemView);

            image = (ImageView) itemView.findViewById(R.id.imageView);
            name = (TextView) itemView.findViewById(R.id.nameView);
            cuisineType = (TextView) itemView.findViewById(R.id.cuisineTypeView);
            rating = (RatingBar) itemView.findViewById(R.id.ratingBar);
        }

        public void bindRestaurant(Restaurant restaurant) {
            Context context = image.getContext();
            String url = restaurant.getLogo().get(0).getStandardResolutionURL();

            rating.setRating(restaurant.getRatingStars());


            // TODO: Should this be here? Why not?
            Picasso.with(context)
                    .load(url)
                    .placeholder(R.drawable.je_logo)
                    .error(R.drawable.je_logo)
                    .into(image);

            name.setText(restaurant.getName());
            cuisineType.setText(cuisineTypesToString(restaurant.getCuisineTypes()));
//            rating.setText(restaurant.getRatingAverage());

        }

        private String cuisineTypesToString(List<CuisineType> cuisineTypes) {
            StringBuilder cuisinesBuilder = new StringBuilder();

            for(CuisineType cuisineType : cuisineTypes) {
                cuisinesBuilder.append(cuisineType.getName());
                cuisinesBuilder.append(", ");
            }

            return cuisinesBuilder.toString().replaceFirst(", $", "");
        }
    }

    public RestaurantAdapter(List<Restaurant> restaurantList) {
        this.restaurantList = restaurantList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.restaurant_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Restaurant restaurant = restaurantList.get(position);
        holder.bindRestaurant(restaurant);
    }

    @Override
    public int getItemCount() {
        return restaurantList.size();
    }

    public void swap(List<Restaurant> newData){
        restaurantList.clear();
        restaurantList.addAll(newData);
        notifyDataSetChanged();
    }

}
