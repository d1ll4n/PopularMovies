package ai.sfactorial.popularmovies.utilities;

import android.net.Uri;
import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

import java.net.MalformedURLException;
import java.util.Scanner;

/**
 * Created by dillan on 2018/01/03.
 */

public class Network {

    private static final String MOVIEDb_BASE_URL = "http://api.themoviedb.org/3/";
    private static final String MOVIEDb_IMAGE_BASE_URL_TEMPLATE = "http://image.tmdb.org/t/p/";

    private static final String PARAM_API_KEY = "api_key";

    //TODO (1): Insert The Movie DB API key.
    private static final String MOVIEDb_API_KEY = "ENTER API KEY";

    public static URL getFullPosterUrl(String posterPath, String posterSize){

        Uri posterUri = Uri.parse(MOVIEDb_IMAGE_BASE_URL_TEMPLATE)
                .buildUpon()
                .appendPath(posterSize)
                .appendEncodedPath(posterPath)
                .build();

        URL url = null;

        try{
            url = new URL(posterUri.toString());
        }
        catch (MalformedURLException e){
            e.printStackTrace();
        }

        return url;
    }

    public static URL buildQueryUrl(String order){
        Uri queryUri = Uri.parse(MOVIEDb_BASE_URL)
                .buildUpon()
                .appendEncodedPath(order)
                .appendQueryParameter(PARAM_API_KEY, MOVIEDb_API_KEY)
                .build();

        URL url = null;

        try{
            url = new URL(queryUri.toString());
        }
        catch (MalformedURLException e){
            e.printStackTrace();
        }

        return url;
    }

    public static String getResponseFromUrl(URL url) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        try {
            InputStream inputStream = connection.getInputStream();

            Scanner scanner = new Scanner(inputStream);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();

            if(hasInput){
                return scanner.next();
            }
            else{
                return null;
            }
        }
        finally {
            connection.disconnect();
        }
    }
}
