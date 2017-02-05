package com.example.matej.popularmoviesdemo;

import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    ArrayList<Movie> arrayList;

    GridView gridView;

    private ProgressBar mProgressBar;

    private String TAG = MainActivity.class.getSimpleName();

    // ArrayList<HashMap<String, String>> moviesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        arrayList = new ArrayList<>();
        gridView = (GridView)findViewById(R.id.gridView);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);

        mProgressBar = (ProgressBar)findViewById(R.id.progressBar);

        // moviesList = new ArrayList<>();

        new GetMovies().execute();

    }


    /**
     * Async task class to get json by making HTTP call
     */
    private class GetMovies extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            mProgressBar.setVisibility(View.VISIBLE);

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();

            // build url
            Uri uri = Uri.parse(ApiRequest.API_URL).buildUpon()
                    .appendQueryParameter("sort_by", "popular")
                    .appendQueryParameter("api_key", ApiRequest.API_KEY)
                    .build();

            URL url;
            String jsonStr = null;
            try {
                url = new URL(uri.toString());
                // Making a request to url and getting response
                jsonStr = sh.makeServiceCall(url);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }


            Log.i(TAG, "Response from url: " + jsonStr);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObject = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    JSONArray movies = jsonObject.getJSONArray("results");

                    // looping through all movies
                    for (int i = 0; i < movies.length(); i++) {
                        JSONObject m = movies.getJSONObject(i);
                        arrayList.add(new Movie(
                                m.getString("poster_path"),
                                m.getString("title"),
                                m.getString("id")
                        ));

//                        String id = m.getString("id");
//                        String title = m.getString("title");
//                        String poster = m.getString("poster_path");
//
//                        // tmp hash map for single movie
//                        HashMap<String, String> movie = new HashMap<>();
//
//                        // adding each child node to HashMap array key => value
//                        movie.put("id", id);
//                        movie.put("title", title);
//                        movie.put("poster", poster);

                        // adding movie to movies list
                        // moviesList.add(movie);
                    }

                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    });
                }


            } else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG).show();
                    }
                });
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            // Dismiss the progress dialog
            mProgressBar.setVisibility(View.INVISIBLE);

            /**
             * Updating parsed JSON data into Grid View
             */
            // Log.i("Movies", moviesList.toString());
            CustomGridAdapter adapter = new CustomGridAdapter(
                    getApplicationContext(), R.layout.grid_item, arrayList
            );
            gridView.setAdapter(adapter);

        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_sort_by_popularity:
                // User chose the "Sort by popularity action" show items sorted by popularity...
                return true;

            case R.id.action_sort_by_rate:
                // User chose the "Sort by rate" show items sorted by rate
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.items, menu);
        return true;

    }

}
