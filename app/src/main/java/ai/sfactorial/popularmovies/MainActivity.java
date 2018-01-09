package ai.sfactorial.popularmovies;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.opengl.Visibility;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import ai.sfactorial.popularmovies.adapters.ImageAdapter;
import ai.sfactorial.popularmovies.tasks.MovieDbQueryTask;
import ai.sfactorial.popularmovies.utilities.MovieDb;
import ai.sfactorial.popularmovies.utilities.Network;

public class MainActivity extends AppCompatActivity{

    public GridView mMoviesGrid;
    public ProgressBar mProgress;
    public TextView mErrorFeedback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMoviesGrid = (GridView) findViewById(R.id.gv_movies);
        mProgress = (ProgressBar) findViewById(R.id.pb_loading);
        mErrorFeedback = (TextView) findViewById(R.id.tv_error_feedback);

        mProgress.setVisibility(View.GONE);
        mErrorFeedback.setVisibility(View.GONE);

        try{
            ConnectivityManager cm = (ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

            boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

            if(isConnected){
                URL url = Network.buildQueryUrl(MovieDb.Order_Popular);
                MovieDbQueryTask movieDbQueryTask = new MovieDbQueryTask(this);
                movieDbQueryTask.execute(url);
            }
            else{
                showErrorFeedback("No internet connection. Please try again later.");
            }

        }
        catch (Exception e){

        }
    }

    public void showErrorFeedback(String errorMessage){
        mErrorFeedback.setText(errorMessage);

        mProgress.setVisibility(View.GONE);
        mMoviesGrid.setVisibility(View.GONE);
        mErrorFeedback.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        URL url = null;

        if(item.getItemId() == R.id.action_top_rated){
            url = Network.buildQueryUrl(MovieDb.Order_Top_Rated);
        }
        else if(item.getItemId() == R.id.action_popular){
            url = Network.buildQueryUrl(MovieDb.Order_Popular);
        }

        if(url != null){
            MovieDbQueryTask task = new MovieDbQueryTask(this);
            task.execute(url);
        }

        return super.onOptionsItemSelected(item);
    }


}
