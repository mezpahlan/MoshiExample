package uk.co.mezpahlan.moshiexample.Model;

import java.util.List;

/**
 * Created by mpahlan on 27/06/16.
 */
public class Restaurants {
    private final List<Restaurant> Restaurants;
    private final String ShortResultText;
    private final String Area;
    private final String Errors;
    private final Boolean HasErrors;


    public Restaurants(List<Restaurant> restaurants, String shortResultText, String area, String errors, Boolean hasErrors) {
        this.Restaurants = restaurants;
        this.ShortResultText = shortResultText;
        this.Area = area;
        this.Errors = errors;
        this.HasErrors = hasErrors;
    }

    public List<Restaurant> getRestaurants() {
        return Restaurants;
    }

    public String getShortResultText() {
        return ShortResultText;
    }

    public String getArea() {
        return Area;
    }

    public String getErrors() {
        return Errors;
    }

    public Boolean getHasErrors() {
        return HasErrors;
    }
}
