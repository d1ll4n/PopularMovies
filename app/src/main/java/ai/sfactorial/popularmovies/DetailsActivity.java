package ai.sfactorial.popularmovies;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.net.URL;

import ai.sfactorial.popularmovies.models.Movie;
import ai.sfactorial.popularmovies.utilities.MovieDb;
import ai.sfactorial.popularmovies.utilities.Network;

public class DetailsActivity extends AppCompatActivity {

    TextView mTitle;
    ImageView mPoster;
    TextView mPlot;
    TextView mRating;
    TextView mReleaseDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        try{
            Intent detailsIntent = getIntent();

            if(detailsIntent.hasExtra(Intent.ACTION_ATTACH_DATA)){
                mTitle = (TextView) findViewById(R.id.tv_details_title);
                mPlot = (TextView) findViewById(R.id.tv_details_plot);
                mReleaseDate = (TextView) findViewById(R.id.tv_details_release_date);
                mRating = (TextView) findViewById(R.id.tv_details_rating);
                mPoster  = (ImageView) findViewById(R.id.iv_details_poster);

                Movie movie = (Movie) detailsIntent.getParcelableExtra(Intent.ACTION_ATTACH_DATA);

                URL posterUrl = Network.getFullPosterUrl(movie.getPosterPath(), MovieDb.Size_W500);

                mTitle.setText(movie.getTitle());
                mPlot.setText(movie.getOverview());
                mReleaseDate.setText(movie.getReleaseDate());
                mRating.setText(String.valueOf(movie.getRating()));
                Picasso.with(this).load(posterUrl.toString()).into(mPoster);

            }
            else{
                throw new Exception("Movie data not found.");
            }
        }
        catch (Exception e){
            Intent mainIntent = new Intent(this, MainActivity.class);
            startActivity(mainIntent);
        }
    }
}
