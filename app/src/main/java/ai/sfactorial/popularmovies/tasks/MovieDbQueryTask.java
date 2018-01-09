package ai.sfactorial.popularmovies.tasks;

import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.AdapterView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import ai.sfactorial.popularmovies.DetailsActivity;
import ai.sfactorial.popularmovies.MainActivity;
import ai.sfactorial.popularmovies.adapters.ImageAdapter;
import ai.sfactorial.popularmovies.models.Movie;
import ai.sfactorial.popularmovies.utilities.Network;

/**
 * Created by dillan on 2018/01/09.
 */

public class MovieDbQueryTask extends AsyncTask<URL, Void, String> {

    MainActivity mCallingActivity;

    public MovieDbQueryTask(MainActivity callingActivity){
        mCallingActivity = callingActivity;
    }

    @Override
    protected void onPreExecute() {
        mCallingActivity.mProgress.setVisibility(View.VISIBLE);
    }

    @Override
    protected String doInBackground(URL... urls) {
        URL url = urls[0];
        String result = null;

        try{
            result = Network.getResponseFromUrl(url);
        }
        catch (Exception e){
            result = null;
        }

        return result;
    }

    @Override
    protected void onPostExecute(String s) {
        try{
            if(s != null && !s.equals("")){
                JSONObject data = new JSONObject(s);
                JSONArray movieResults = data.getJSONArray("results");

                List<Movie> movies = new ArrayList<Movie>();

                for(int i = 0; i < movieResults.length(); i++){
                    JSONObject jsonObject = movieResults.getJSONObject(i);

                    String title = jsonObject.getString("original_title");
                    String overview = jsonObject.getString("overview");
                    String releaseDate = jsonObject.getString("release_date");
                    double rating = jsonObject.getDouble("vote_average");
                    String posterPath = jsonObject.getString("poster_path");

                    movies.add(new Movie(title, overview, rating, releaseDate, posterPath));
                }

                final ImageAdapter imageAdapter = new ImageAdapter(mCallingActivity, movies);
                mCallingActivity.mMoviesGrid.setAdapter(imageAdapter);

                mCallingActivity.mMoviesGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        try{
                            Movie movie = imageAdapter.getItem(i);

                            Intent detailsIntent = new Intent(mCallingActivity, DetailsActivity.class);
                            detailsIntent.putExtra(Intent.ACTION_ATTACH_DATA, movie);
                            mCallingActivity.startActivity(detailsIntent);
                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                });
            }
            else{
                mCallingActivity.showErrorFeedback("No data received from The Movie DB. Please try again later");
            }

            mCallingActivity.mProgress.setVisibility(View.GONE);
        }
        catch (Exception e){

        }

    }

}
