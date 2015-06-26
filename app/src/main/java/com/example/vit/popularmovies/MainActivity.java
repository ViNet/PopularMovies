package com.example.vit.popularmovies;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.vit.popularmovies.rest.RestClient;
import com.example.vit.popularmovies.rest.conf.Constants;
import com.example.vit.popularmovies.rest.model.Page;

import retrofit.Callback;
import retrofit.RetrofitError;


public class MainActivity extends ActionBarActivity {

    static final String TAG = "PoMo";
    static final String CLASS = MainActivity.class.getSimpleName() + ": ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RestClient client = new RestClient();
        client.getApiService().getMovie("popularity.desc", Constants.API_KEY, new Callback<Page>() {
            @Override
            public void success(Page page, retrofit.client.Response response) {
                Log.d(TAG, CLASS + "success size = " + page.getMovies().size());
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d(TAG, CLASS + "failure error = " + error.getMessage());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
