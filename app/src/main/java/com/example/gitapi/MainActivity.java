package com.example.gitapi;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView listview;
    List<Commit> commitList = new ArrayList<>();
    String url = "https://api.github.com/repos/rails/rails/commits";
    CustomAdapter adp;
    int count =25;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listview = (ListView)findViewById(R.id.listView);
        if(isNetworkConnected())
            new GetCommits().execute(url);
        else
            Toast.makeText(getApplicationContext(),"Please enable internet connection",Toast.LENGTH_SHORT).show();
        adp = new CustomAdapter(MainActivity.this,commitList);
        listview.setAdapter(adp);



    }

    private List<Commit> convertStringtoList(String result){
        try {
            JSONArray jsonArray = new JSONArray(result);
            for (int i = 0; i < count; i++) {

                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String sha = jsonObject.getString("sha");
                JSONObject jsonObject1 = jsonObject.getJSONObject("commit");
                String message = jsonObject1.getString("message");
                JSONObject jsonObject3 = jsonObject1.getJSONObject("author");
                String name = jsonObject3.getString("name");


                commitList.add(new Commit(name, message, sha));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return commitList;


    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }

    public class GetCommits extends AsyncTask<String, Void, String> {

        HttpURLConnection connection = null;
        ProgressDialog pd;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(MainActivity.this);
            pd.setMessage("Please wait");
            pd.show();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            convertStringtoList(s);
            adp.notifyDataSetChanged();
            if(pd.isShowing())
                pd.dismiss();

        }

        @Override
        protected String doInBackground(String... params) {
            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                InputStream in = connection.getInputStream();
                String result = getStringFromInputStream(in);
                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        private String getStringFromInputStream(InputStream is) {

            BufferedReader br = null;
            StringBuilder sb = new StringBuilder();

            String line;
            try {

                br = new BufferedReader(new InputStreamReader(is));
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (br != null) {
                    try {
                        br.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return sb.toString();
        }
    }
}
