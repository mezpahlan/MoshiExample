package uk.co.mezpahlan.moshiexample.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mpahlan on 28/06/16.
 */
public class Restaurant implements Parcelable {
    private final Integer Id;
    private final String Name;
    private final List<CuisineType> CuisineTypes;
    private final Float RatingStars;
    private final List<Logo> Logo;

    public Restaurant(Integer id,
                      String name,
                      List<CuisineType> cuisineTypes,
                      Float ratingStars,
                      List<Logo> logo) {
        Id = id;
        Name = name;
        CuisineTypes = cuisineTypes;
        RatingStars = ratingStars;
        Logo = logo;
    }

    private Restaurant(Parcel in) {
        Id = in.readInt();
        Name = in.readString();
        CuisineTypes = new ArrayList<>();
        in.readTypedList(CuisineTypes, CuisineType.CREATOR);
        RatingStars = in.readFloat();
        Logo = new ArrayList<>();
        in.readTypedList(Logo, uk.co.mezpahlan.moshiexample.Model.Logo.CREATOR);
    }

    public List<uk.co.mezpahlan.moshiexample.Model.Logo> getLogo() {
        return Logo;
    }

    public Float getRatingStars() {
        return RatingStars;
    }

    public List<CuisineType> getCuisineTypes() {
        return CuisineTypes;
    }

    public String getName() {
        return Name;
    }

    public Integer getId() {
        return Id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(Id);
        dest.writeString(Name);
        dest.writeTypedList(CuisineTypes);
        dest.writeFloat(RatingStars);
        dest.writeTypedList(Logo);
    }


    public static final Parcelable.Creator<Restaurant> CREATOR
            = new Parcelable.Creator<Restaurant>() {

        @Override
        public Restaurant createFromParcel(Parcel in) {
            return new Restaurant(in);
        }

        @Override
        public Restaurant[] newArray(int size) {
            return new Restaurant[size];
        }
    };
}
