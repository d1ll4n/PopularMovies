package ai.sfactorial.popularmovies.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by dillan on 2018/01/09.
 */

public class Movie implements Parcelable {
    String mTitle;
    String mOverview;
    double mRating;
    String mReleaseDate;
    String mPosterPath;

    public Movie(){

    }

    public Movie(String title, String overview, double rating, String releaseDate, String posterPath){
        mTitle = title;
        mOverview = overview;
        mRating = rating;
        mReleaseDate = releaseDate;
        mPosterPath = posterPath;
    }

    private  Movie(Parcel in){
        mTitle = in.readString();
        mOverview = in.readString();
        mRating = in.readDouble();
        mReleaseDate = in.readString();
        mPosterPath = in.readString();
    }

    public String getTitle(){
        return mTitle;
    }

    public String getOverview(){
        return mOverview;
    }

    public double getRating(){
        return mRating;
    }

    public String getReleaseDate(){
        return mReleaseDate;
    }

    public String getPosterPath(){
        return mPosterPath;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mTitle);
        parcel.writeString(mOverview);
        parcel.writeDouble(mRating);
        parcel.writeString(mReleaseDate);
        parcel.writeString(mPosterPath);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {

        // This simply calls our new constructor (typically private) and
        // passes along the unmarshalled `Parcel`, and then returns the new object!
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        // We just need to copy this and change the type to match our class.
        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}
