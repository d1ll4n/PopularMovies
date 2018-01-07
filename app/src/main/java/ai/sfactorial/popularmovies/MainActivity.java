package ai.sfactorial.popularmovies;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import ai.sfactorial.popularmovies.adapters.ImageAdapter;
import ai.sfactorial.popularmovies.models.Settings;
import ai.sfactorial.popularmovies.utilities.MovieDb;
import ai.sfactorial.popularmovies.utilities.Network;

public class MainActivity extends AppCompatActivity{
    MovieAdapter mAdapter;
    ListView mMovieList;
    GridView mMoviesGrid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //mMovieList = (ListView) findViewById(R.id.lv_movies);
        mMoviesGrid = (GridView) findViewById(R.id.gv_movies);

        try{
            URL url = Network.buildQueryUrl(Settings.SortOrder);
            MovieDbQueryTask movieDbQueryTask = new MovieDbQueryTask(this);
            movieDbQueryTask.execute(url);
        }
        catch (Exception e){
            //Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    public void onSearchClick(View view){


    }

    public void onListItemClick(AdapterView<?> adapterView, View view, int position, long blah){
        Toast.makeText(this, "Item " + String.valueOf(position) + " clicked.", Toast.LENGTH_LONG).show();
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

    class MovieDbQueryTask extends AsyncTask<URL, Void, String> {

        Activity mContext;

        MovieDbQueryTask(Activity context){
            mContext = context;
        }

        @Override
        protected String doInBackground(URL... urls) {
            URL url = urls[0];
            String result = null;

            try{
                result = Network.getResponseFromUrl(url);
            }
            catch (Exception e){
                e.printStackTrace();
                mErrorFeedback.setText(R.string.network_error);

                mProgress.setVisibility(View.INVISIBLE);
                mMoviesGrid.setVisibility(View.INVISIBLE);
                mErrorFeedback.setVisibility(View.VISIBLE);
            }

            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            try{
                if(s != null && !s.equals("")){
                    JSONObject jsonObject = new JSONObject(s);
                    JSONArray resultsArray = jsonObject.getJSONArray("results");

                    List<JSONObject> jsonList = new ArrayList<JSONObject>();

                    for(int i = 0; i < resultsArray.length(); i++){
                        jsonList.add(resultsArray.getJSONObject(i));
                    }

                    mAdapter = new MovieAdapter(mContext, jsonList);
                    mMoviesGrid.setAdapter(new ImageAdapter(mContext, jsonList));

                    mMovieList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            try{
                                JSONObject movieJson = mAdapter.getItem(i);

                                Intent detailsIntent = new Intent(mContext, DetailsActivity.class);
                                detailsIntent.putExtra(Intent.ACTION_ATTACH_DATA, movieJson.toString());
                                startActivity(detailsIntent);
                            }
                            catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }

        }
    }
}
