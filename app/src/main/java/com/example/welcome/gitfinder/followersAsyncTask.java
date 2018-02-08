package com.example.welcome.gitfinder;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by WELCOME on 05-10-2017.
 */

public class followersAsyncTask extends AsyncTask<String,Void,ArrayList<User>> {

    followerListener listener;

    public followersAsyncTask(followerListener listener) {
        this.listener = listener;
    }

    @Override
    protected ArrayList<User> doInBackground(String... params) {

        String urlString = params[0];

        try {
            URL url = new URL(urlString);
            HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            InputStream inputStream = urlConnection.getInputStream();
            Scanner scanner = new Scanner(inputStream);

            String response = "";

            while(scanner.hasNext()){
                response += scanner.nextLine();
            }

            ArrayList<User> followers = parseFollowers(response);

            return followers;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private ArrayList<User> parseFollowers(String response) {

        ArrayList<User> users = null;

        try {
            JSONArray rootArray = new JSONArray(response);

            if(rootArray != null){
                users = new ArrayList<>();
                for(int i = 0; i < rootArray.length(); i++){
                    JSONObject follower = rootArray.getJSONObject(i);

                    int id = follower.getInt("id");
                    String type = follower.getString("type");
                    String name = "";
                    String location = "";
                    int followers = 0;
                    int following = 0;
                    String login = follower.getString("login");

                    User user = new User(id,type,name,followers,following,location);
                    user.login = login;

                    users.add(user);
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return users;


    }

    @Override
    protected void onPostExecute(ArrayList<User> users) {
        super.onPostExecute(users);
        listener.followersDownload(users);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    static interface followerListener{
        void followersDownload(ArrayList<User> users);
    }
}
