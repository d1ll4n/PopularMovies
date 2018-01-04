package ai.sfactorial.popularmovies;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_settings){
            Intent openSettingsIntent = new Intent(this, SettingsActivity.class);
            startActivity(openSettingsIntent);
            return true;
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
