package com.example.matej.popularmoviesdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;


/**
 * Created by matej on 6.2.2017.
 * details activity
 */

public class DetailsActivity extends AppCompatActivity {

    TextView tv_title, tv_release_date, tv_description, tv_users_rating;
    ImageView iv_poster;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);

        tv_title = (TextView)findViewById(R.id.tv_title);
        tv_description = (TextView)findViewById(R.id.tv_description);
        tv_release_date = (TextView)findViewById(R.id.tv_release_date);
        tv_users_rating = (TextView)findViewById(R.id.tv_users_rating);
        iv_poster = (ImageView)findViewById(R.id.iv_poster);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String title = extras.getString("title");
            tv_title.append(title);
            getSupportActionBar().setTitle(title);

            String description = extras.getString("description");
            tv_description.append(description);

            String release_date = extras.getString("release_date");
            tv_release_date.append(release_date);

            String users_rating = extras.getString("users_rating");
            tv_users_rating.append(users_rating);

            String poster = extras.getString("poster");
            Picasso.with(DetailsActivity.this).load(poster).into(iv_poster);

        }



    }


}
