package ai.sfactorial.popularmovies;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextClock;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.List;

import ai.sfactorial.popularmovies.utilities.MovieDb;
import ai.sfactorial.popularmovies.utilities.Network;

/**
 * Created by dillan on 2018/01/03.
 */

public class MovieAdapter extends ArrayAdapter<JSONObject> {

    private int mNumberOfItems;
    private JSONArray mData;
    private Activity mContext;

    public MovieAdapter(Activity context, List<JSONObject> data){
        super(context,0, data);
        mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        try{
            JSONObject jsonObject = getItem(position);

            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.movie_list_item, parent, false);
            }

            String movieTitle = jsonObject.getString("title");
            TextView textView = (TextView) convertView.findViewById(R.id.tv_movie_title);
            textView.setText(movieTitle);

            ImageView moviePoster = (ImageView) convertView.findViewById(R.id.iv_movie_poster);
            String posterPath = jsonObject.getString("poster_path");
            URL posterUrl = Network.getFullPosterUrl(posterPath, MovieDb.Size_W185);
            Picasso.with(mContext).load(posterUrl.toString()).into(moviePoster);
        }
        catch (Exception e){

        }

        return convertView;
    }
}
