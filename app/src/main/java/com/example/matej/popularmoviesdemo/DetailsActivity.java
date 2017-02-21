package com.example.matej.popularmoviesdemo;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

    Menu menu;
    private Boolean are_reviews_shown = false;

    TextView tv_title, tv_release_date, tv_description, tv_users_rating;
    ImageView iv_poster;
    private ProgressBar mProgressBar;

    ArrayList<Review> reviewArrayList;
    //ListView reviewListView;

    DatabaseHandler db = new DatabaseHandler(this);

    private String theMovieId;

    private String theMovieTitle, theMovieDescription, theMovieReleaseDate, theMovieUsersRating, theMoviePosterPath;

    private String TAG = MainActivity.class.getSimpleName();


    private class GetReviews extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            mProgressBar.setVisibility(View.VISIBLE);

        }
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
                }
            } else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get reviews from server. You have probably problem with the internet!",
                                Toast.LENGTH_LONG).show();
                    }
                });
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            show_reviews();

        }
    }

    public boolean show_reviews(){
        mProgressBar.setVisibility(View.GONE);
/*
        CustomReviewAdapter adapter = new CustomReviewAdapter(
                getApplicationContext(), review, reviewArrayList
        );
        reviewListView.setAdapter(adapter);
        */

        LinearLayout r_ll = (LinearLayout) findViewById(R.id.reviewLinearLayout);
          //  for (int i = 0; i < reviewArrayList.size(); i++) {
              for (final Review review : reviewArrayList) {
                TextView text = new TextView(this);
                text.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                text.setText(review.getAuthor());
                  text.setOnClickListener(new View.OnClickListener() {
                      @Override
                      public void onClick(View v) {
                          Intent intent = new Intent("android.intent.action.REVIEWACTIVITY");

                          intent.putExtra("name", review.getAuthor());
                          intent.putExtra("content", review.getContent());
                          intent.putExtra("title", theMovieTitle);

                          startActivity(intent);
                      }
                  });
                r_ll.addView(text);
            }

//        reviewListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Review review = reviewArrayList.get(position);
//                Intent intent = new Intent("android.intent.action.REVIEWACTIVITY");
//
//                intent.putExtra("name", review.getAuthor());
//                intent.putExtra("content", review.getContent());
//                intent.putExtra("title", theMovieTitle);
//
//                startActivity(intent);
//            }
//        });

        return true;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        ActionBar actionBar = getSupportActionBar();

        // TODO: request reviews and videos
        // TODO: put them to the view

        tv_title = (TextView)findViewById(R.id.tv_title);
        tv_description = (TextView)findViewById(R.id.tv_description);
        tv_release_date = (TextView)findViewById(R.id.tv_release_date);
        tv_users_rating = (TextView)findViewById(R.id.tv_users_rating);
        iv_poster = (ImageView)findViewById(R.id.iv_poster);
        theMovieId = "";

        reviewArrayList = new ArrayList<>();
        //reviewListView = (ListView)findViewById(R.id.reviewListView);
        mProgressBar = (ProgressBar)findViewById(R.id.progressBar2);


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            theMovieTitle = extras.getString("title");
            tv_title.append(theMovieTitle);
            if (actionBar != null) actionBar.setTitle(theMovieTitle);

            theMovieId = extras.getString("id");

            theMovieDescription = extras.getString("description");
            tv_description.append(theMovieDescription);

            theMovieReleaseDate = extras.getString("release_date");
            tv_release_date.append(theMovieReleaseDate);

            theMovieUsersRating = extras.getString("users_rating");
            tv_users_rating.append(theMovieUsersRating);

            theMoviePosterPath = extras.getString("poster");
            Picasso.with(DetailsActivity.this).load(ApiRequest.IMAGE_URL + ApiRequest.IMAGE_SIZE + theMoviePosterPath).into(iv_poster);
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_add_to_favourites) {
            Toast.makeText(getApplicationContext(),
                    "movie has been added to favourites",
                    Toast.LENGTH_LONG).show();

            db.addMovie(new Movie(theMoviePosterPath, theMovieTitle, theMovieId, theMovieReleaseDate, theMovieUsersRating, theMovieDescription));

            for (Review review : reviewArrayList) {
                review.setId(theMovieId);
                db.addReview(review);
            }

            this.menu.clear();
            this.onCreateOptionsMenu(this.menu);

            return true;
        }else if(item.getItemId() == R.id.action_delete_from_favourites) {
            Toast.makeText(getApplicationContext(),
                    "movie has been deleted from favourites",
                    Toast.LENGTH_LONG).show();
            db.deleteMovie(theMovieId);
            db.deleteReview(theMovieId);

            this.menu.clear();
            this.onCreateOptionsMenu(this.menu);

        } else {
            return super.onOptionsItemSelected(item);
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        try{
            Movie m = db.getMovie(Integer.valueOf(theMovieId));
            String log = "Id: " + m.getId() + ", Title: " + m.getTitle() + ", Release Date: " + m.getRelease_date()
                    + ", Users Rating: " + m.getUsers_rating() + ", Description " + m.getDescription() + ", Poster Path: "
                    + m.getPoster_path();
            Log.i("Movie: ", log);
            Log.i("Movie id " ,theMovieId);

            if (!are_reviews_shown) {
                reviewArrayList = db.getReviews(Integer.valueOf(theMovieId));
                show_reviews();
                are_reviews_shown = true;
            }

            getMenuInflater().inflate(R.menu.delete_from_fav, menu);

        }catch (Exception e) {
            getMenuInflater().inflate(R.menu.fav, menu);
            if (!are_reviews_shown) {
                new GetReviews().execute();
                are_reviews_shown = true;
            }
        }


        return true;
    }


}
