package com.example.welcome.gitfinder;

import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by WELCOME on 05-10-2017.
 */

public class UserAsyncTask extends AsyncTask<String,Void,User> {

    clickListener listener;

    public UserAsyncTask(clickListener listener) {
        this.listener = listener;
    }

    @Override
    protected User doInBackground(String... params) {

        String urlString = params[0];

        try {
            URL url = new URL(urlString);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            InputStream inputStream = urlConnection.getInputStream();

            Scanner scanner = new Scanner(inputStream);

            String response ="";

            while (scanner.hasNext()){
                response += scanner.nextLine();
            }

            User user = parseUser(response);

            return user;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private User parseUser(String response) {

        User user = null;

        try {
            JSONObject rootObject = new JSONObject(response);

            int id = rootObject.getInt("id");
            String type = rootObject.getString("type");
            String name = rootObject.getString("name");
            String location = rootObject.getString("location");
            int followers = rootObject.getInt("followers");
            int following = rootObject.getInt("following");

            user = new User(id,type,name,followers,following,location);


        } catch (JSONException e) {
            e.printStackTrace();
        }

        return user;

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(User user) {
        super.onPostExecute(user);
        listener.onDownload(user);
    }

    static interface clickListener{
        void onDownload(User user);
    }
}
