package com.meru.yelp.yelpdata;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText yelpUrl;
    EditText openTableUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        yelpUrl = (EditText) findViewById(R.id.yelp_url);
        openTableUrl = (EditText) findViewById(R.id.opentable_url);

        // Setting defaults, user can change them later
        yelpUrl.setText("http://www.yelp.com/biz/the-fourth-new-york");
        openTableUrl.setText("http://www.opentable.com/the-fourth");

    }

    public void onShowListingsClick(View view) {
        Intent intent = new Intent(this, ResultActivity.class);
        Toast.makeText(getApplicationContext(), "Started scraping...",
                Toast.LENGTH_LONG).show();
        intent.putExtra("yelp_url", yelpUrl.getText().toString());
        intent.putExtra("opentable_url", openTableUrl.getText().toString());
        Log.d("kd", yelpUrl.getText().toString());
        Log.d("kd", openTableUrl.getText().toString());
        startActivity(intent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
