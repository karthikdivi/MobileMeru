package com.meru.yelp.yelpdata;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.Arrays;

public class ResultActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        String yelpUrl = intent.getStringExtra("yelp_url");
        String opentableUrl = intent.getStringExtra("opentable_url");
        new MyAsyncTask().execute(yelpUrl,opentableUrl);
    }

    private class MyAsyncTask extends AsyncTask<String, String, String> {
        TextView txtView;
        String message = "";
        protected void onPreExecute() {
            txtView = (TextView) findViewById(R.id.resultTextView);
        }

        protected String doInBackground(String... strings) {

            try {
                for(String url: strings){
                    Document doc = Jsoup.connect(url).get(); //connecting to external site
                    if(url.contains("yelp.com")){
                        Entity entity =  SourceScraping.scrapeYelp(doc);
                        message = message + getListingsString("yelp", entity);
                    }else if(url.contains("opentable.com")){
                        Entity entity =  SourceScraping.scrapeOpentable(doc);
                        message = message + getListingsString("opentable", entity);
                    }
                }
            } catch (IOException e) {
                Log.d("kd",e.toString());
                e.printStackTrace();
            }
            return message;
        }

        protected void onPostExecute(String result) {
            txtView.setText(message);
        }

        private String getListingsString(String sourceName, Entity entity){
            String entityString = sourceName + ":";
            entityString = entityString + "\n"+  "_________" + "\n";
            entityString = entityString + "Name: "+ entity.getName() + "\n";

            entityString = entityString + "StreetAddress: "+entity.getStreetAddress() + "\n";
            entityString = entityString + "Locality: "+entity.getLocality() + "\n";
            entityString = entityString + "Region: "+entity.getRegion() + "\n";
            entityString = entityString + "PostalCode: "+entity.getPostalCode() + "\n";

            entityString = entityString + "Phone: "+entity.getPhone() + "\n";
            entityString = entityString + "weburl: "+entity.getWebUrl() + "\n";
            entityString = entityString + "\n" + "\n";
            return  entityString;
        }
    }

}
