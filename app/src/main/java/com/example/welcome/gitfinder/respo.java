package com.example.welcome.gitfinder;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class respo extends AppCompatActivity {

    ListView listViewRespo;
    ProgressBar p;
    ArrayAdapter<String> arrayAdapter;
    ArrayList<String> titles = new ArrayList<>();

    ConstraintLayout respo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_respo);

        Intent i = getIntent();
        String login = i.getStringExtra("L");
        respo = (ConstraintLayout)findViewById(R.id.respo);
        listViewRespo = (ListView)findViewById(R.id.listViewRespo);
        p = (ProgressBar)findViewById(R.id.progressBar3);
        listViewRespo.setVisibility(View.GONE);
        p.setVisibility(View.VISIBLE);

        arrayAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,titles);

        listViewRespo.setAdapter(arrayAdapter);

        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://api.github.com/").addConverterFactory(GsonConverterFactory.create()).build();

        GitHubService service = retrofit.create(GitHubService.class);

        Call<ArrayList<REPOSITORY>> call = service.getRepoository(login);

        call.enqueue(new Callback<ArrayList<REPOSITORY>>() {
            @Override
            public void onResponse(Call<ArrayList<REPOSITORY>> call, Response<ArrayList<REPOSITORY>> response) {
                ArrayList<REPOSITORY> repositories = response.body();
                for(REPOSITORY r : repositories){
                    titles.add(r.name);
                }
                arrayAdapter.notifyDataSetChanged();

                listViewRespo.setVisibility(View.VISIBLE);
                p.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<ArrayList<REPOSITORY>> call, Throwable t) {

                Snackbar.make(respo,t.getMessage(),Snackbar.LENGTH_LONG).show();

            }
        });





    }
}
