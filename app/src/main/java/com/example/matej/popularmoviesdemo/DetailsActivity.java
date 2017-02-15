package com.example.matej.popularmoviesdemo;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;


/**
 * Created by matej on 6.2.2017.
 * details activity
 */

public class DetailsActivity extends AppCompatActivity {

    TextView tv_title, tv_release_date, tv_description, tv_users_rating;
    ImageView iv_poster;

    ArrayList<Review> reviewArrayList;
    ListView reviewListView;

    private String theMovieId;

    private String TAG = MainActivity.class.getSimpleName();

    private ProgressBar mProgressBar;

    private class GetReviews extends AsyncTask<Void, Void, Void> {


        @Override
        protected Void doInBackground(Void... params) {
            HttpHandler handler = new HttpHandler();

            Uri uri = Uri.parse(ApiRequest.API_URL + theMovieId + "/reviews").buildUpon()
                    .appendQueryParameter("api_key", ApiRequest.API_KEY)
                    .build();
            URL url;
            String jsonStr = null;
            try {
                url = new URL(uri.toString());
                jsonStr = handler.makeServiceCall(url);

            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            Log.i(TAG, "Response from url: " + jsonStr);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObject = new JSONObject(jsonStr);
                    JSONArray reviews = jsonObject.getJSONArray("results");

                    for (int i = 0; i < reviews.length(); i++) {
                        JSONObject r = reviews.getJSONObject(i);
                        reviewArrayList.add(new Review(
                                r.getString("id"),
                                r.getString("author"),
                                r.getString("content"),
                                r.getString("url")
                        ));
                    }
                    Log.i(TAG, "reviewArrayList: " + reviewArrayList.size());

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

            CustomReviewAdapter adapter = new CustomReviewAdapter(
                    getApplicationContext(), R.layout.review, reviewArrayList
            );
            reviewListView.setAdapter(adapter);


        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);

        }
        // TODO: request reviews and videos
        // TODO: put them to the view

        tv_title = (TextView)findViewById(R.id.tv_title);
        tv_description = (TextView)findViewById(R.id.tv_description);
        tv_release_date = (TextView)findViewById(R.id.tv_release_date);
        tv_users_rating = (TextView)findViewById(R.id.tv_users_rating);
        iv_poster = (ImageView)findViewById(R.id.iv_poster);
        theMovieId = "";

        reviewArrayList = new ArrayList<>();
        reviewListView = (ListView)findViewById(R.id.reviewListView);


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String title = extras.getString("title");
            tv_title.append(title);
            if (actionBar != null) actionBar.setTitle(title);

            theMovieId = extras.getString("id");

            String description = extras.getString("description");
            tv_description.append(description);

            String release_date = extras.getString("release_date");
            tv_release_date.append(release_date);

            String users_rating = extras.getString("users_rating");
            tv_users_rating.append(users_rating);

            String poster = extras.getString("poster");
            Picasso.with(DetailsActivity.this).load(poster).into(iv_poster);
        }

        new GetReviews().execute();

    }


}
