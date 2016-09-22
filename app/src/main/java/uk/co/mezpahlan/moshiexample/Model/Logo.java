package uk.co.mezpahlan.moshiexample.Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by mpahlan on 28/06/16.
 */
public class Logo implements Parcelable {
    private final String StandardResolutionURL;

    public Logo(String standardResolutionURL) {
        this.StandardResolutionURL = standardResolutionURL;
    }

    protected Logo(Parcel in) {
        StandardResolutionURL = in.readString();
    }

    public static final Creator<Logo> CREATOR = new Creator<Logo>() {
        @Override
        public Logo createFromParcel(Parcel in) {
            return new Logo(in);
        }

        @Override
        public Logo[] newArray(int size) {
            return new Logo[size];
        }
    };

    public String getStandardResolutionURL() {
        return StandardResolutionURL;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(StandardResolutionURL);
    }
}
