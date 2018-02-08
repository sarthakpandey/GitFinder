package com.example.welcome.gitfinder;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.welcome.gitfinder.Constants.LOGIN;


public class SecondActivity extends AppCompatActivity{

    ProgressBar progressBar ;
    Button followerButton;
    ImageView imageView;
    Button respoButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        Intent i = getIntent();
        final String login = i.getStringExtra(LOGIN);

        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        followerButton = (Button)findViewById(R.id.followersButton);
        imageView = (ImageView)findViewById(R.id.imageView);
        followerButton.setVisibility(View.GONE);
        respoButton = (Button)findViewById(R.id.respoButton);

        followerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(SecondActivity.this,Followers.class);
                intent.putExtra("L",login);
                startActivity(intent);

            }
        });

        respoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SecondActivity.this,respo.class);
                intent.putExtra("L",login);
                startActivity(intent);
            }
        });




        progressBar.setVisibility(View.VISIBLE);

        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl("https://api.github.com/")
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();

        GitHubService service = retrofit.create(GitHubService.class);
        Call<User> call = service.getUser(login);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                User user = response.body();

                TextView name = (TextView)findViewById(R.id.tname);
                TextView id = (TextView)findViewById(R.id.tid);
                TextView type = (TextView)findViewById(R.id.ttype);
                TextView followers = (TextView)findViewById(R.id.tfollowers);
                TextView following = (TextView)findViewById(R.id.tfollowing);
                TextView location = (TextView)findViewById(R.id.tlocation);

        Picasso.with(SecondActivity.this).load("https://avatars3.githubusercontent.com/u/" + user.id + "" +"?v=4").placeholder(android.R.drawable.editbox_background).into(imageView);

        name.setText(user.name);
        id.setText(user.id + "");
        type.setText(user.type);
        followers.setText(user.followers + "");
        following.setText(user.following + "");
        location.setText(user.location);

        progressBar.setVisibility(View.GONE);
        name.setVisibility(View.VISIBLE);
        id.setVisibility(View.VISIBLE);
        type.setVisibility(View.VISIBLE);
        followers.setVisibility(View.VISIBLE);
        following.setVisibility(View.VISIBLE);
        location.setVisibility(View.VISIBLE);



        if(user.followers != 0){
            followerButton.setVisibility(View.VISIBLE);
        }

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });



       // fetchUser(login);


    }



//    private void fetchUser(String login) {
//
//        String urlString = "https://api.github.com/users/" + login;
//        UserAsyncTask asyncTask = new UserAsyncTask(this);
//        asyncTask.execute(urlString);
//
//
//    }

//    @Override
//    public void onDownload(User user) {
//
//        TextView name = (TextView)findViewById(R.id.tname);
//        TextView id = (TextView)findViewById(R.id.tid);
//        TextView type = (TextView)findViewById(R.id.ttype);
//        TextView followers = (TextView)findViewById(R.id.tfollowers);
//        TextView following = (TextView)findViewById(R.id.tfollowing);
//        TextView location = (TextView)findViewById(R.id.tlocation);
//
//        Picasso.with(this).load("https://avatars3.githubusercontent.com/u/" + user.id + "" +"?v=4").placeholder(R.drawable.explorer).into(imageView);
//
//        name.setText(user.name);
//        id.setText(user.id + "");
//        type.setText(user.type);
//        followers.setText(user.followers + "");
//        following.setText(user.following + "");
//        location.setText(user.location);
//
//        progressBar.setVisibility(View.GONE);
//        name.setVisibility(View.VISIBLE);
//        id.setVisibility(View.VISIBLE);
//        type.setVisibility(View.VISIBLE);
//        followers.setVisibility(View.VISIBLE);
//        following.setVisibility(View.VISIBLE);
//        location.setVisibility(View.VISIBLE);
//
//
//
//        if(user.followers != 0){
//            followerButton.setVisibility(View.VISIBLE);
//        }
//    }



}
