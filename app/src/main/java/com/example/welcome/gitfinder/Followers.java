package com.example.welcome.gitfinder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.util.ArrayList;

import static com.example.welcome.gitfinder.Constants.LOGIN;

public class Followers extends AppCompatActivity implements followersAsyncTask.followerListener {

    ListView listView;
    ArrayAdapter<String> arrayAdapter;
    ArrayList<String> titles = new ArrayList<>();

    ProgressBar p;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_followers);

        listView = (ListView)findViewById(R.id.listView);
        Intent i = getIntent();
        String login = i.getStringExtra("L");
        p = (ProgressBar) findViewById(R.id.progressBar2);

        fetchFollowers(login);
         arrayAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,titles);

        listView.setAdapter(arrayAdapter);

        p.setVisibility(View.VISIBLE);
        listView.setVisibility(View.GONE);




    }

    private void fetchFollowers(String login) {

        String urlString = "https://api.github.com/users/" + login + "/followers";
        followersAsyncTask asyncTask = new followersAsyncTask(this);
        asyncTask.execute(urlString);
    }

    @Override
    public void followersDownload(final ArrayList<User> users) {

        titles.clear();
        for(User user : users){
            titles.add(user.login);
        }

        arrayAdapter.notifyDataSetChanged();

        p.setVisibility(View.GONE);
        listView.setVisibility(View.VISIBLE);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                User u = users.get(position);
                Intent intent = new Intent(Followers.this,SecondActivity.class);
                intent.putExtra(LOGIN,u.login);

                startActivity(intent);

            }
        });



    }
}
