package uk.co.mezpahlan.moshiexample.Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by mpahlan on 28/06/16.
 */
public class CuisineType implements Parcelable {
    private final Integer Id;
    private final String Name;


    public CuisineType(Integer id, String name) {
        this.Id = id;
        this.Name = name;
    }


    protected CuisineType(Parcel in) {
        Id = in.readInt();
        Name = in.readString();
    }

    public static final Creator<CuisineType> CREATOR = new Creator<CuisineType>() {
        @Override
        public CuisineType createFromParcel(Parcel in) {
            return new CuisineType(in);
        }

        @Override
        public CuisineType[] newArray(int size) {
            return new CuisineType[size];
        }
    };

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
    }
}
