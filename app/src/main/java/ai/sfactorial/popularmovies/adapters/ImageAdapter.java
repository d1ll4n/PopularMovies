package ai.sfactorial.popularmovies.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.net.URL;
import java.util.List;

import ai.sfactorial.popularmovies.R;
import ai.sfactorial.popularmovies.customViews.MoviePosterImageView;
import ai.sfactorial.popularmovies.models.Movie;
import ai.sfactorial.popularmovies.utilities.MovieDb;
import ai.sfactorial.popularmovies.utilities.Network;

/**
 * Created by dillan on 2018/01/04.
 */

public class ImageAdapter extends BaseAdapter {
    private Context mContext;
    private List<Movie> mData;

    public ImageAdapter(Context context, List<Movie> data) {
        mContext = context;
        mData = data;
    }

    public int getCount() {
        return mData.size();
    }

    public Movie getItem(int position) {
        return mData.get(position);
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;

        try {

            Movie movie = getItem(position);
            URL posterUrl = Network.getFullPosterUrl(movie.getPosterPath(), MovieDb.Size_W185);

            if (convertView == null) {
                //imageView = new MoviePosterImageView(mContext);
                imageView = new ImageView(mContext);
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setAdjustViewBounds(true);
            } else {
                imageView = (ImageView) convertView;
            }

            Picasso.with(mContext).load(posterUrl.toString()).into(imageView);

        }
        catch (Exception e){
            imageView = new ImageView(mContext);
        }

        return imageView;
    }
}
