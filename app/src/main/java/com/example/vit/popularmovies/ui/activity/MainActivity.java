package com.example.vit.popularmovies.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.vit.popularmovies.MovieApplication;
import com.example.vit.popularmovies.R;
import com.example.vit.popularmovies.ui.adapter.ViewPagerAdapter;
import com.example.vit.popularmovies.ui.fragment.FragmentTag;
import com.example.vit.popularmovies.ui.fragment.MoviesFragment;


public class MainActivity extends AppCompatActivity {

    static final String CLASS = MainActivity.class.getSimpleName() + ": ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(MovieApplication.TAG, CLASS + "onCreate()");
        setContentView(R.layout.activity_main);
        initToolbar();
        initViews();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                startActivity(new Intent(this, SettingsActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
    }

    private void initViews(){
        ViewPager viewPager = (ViewPager) findViewById(R.id.main_viewpager);
        setupViewPager(viewPager);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.main_tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager){
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        MoviesFragment f1 = MoviesFragment.newInstance(FragmentTag.FR_POPULAR);
        MoviesFragment f2 = MoviesFragment.newInstance(FragmentTag.FR_MOST_VOTED);
        MoviesFragment f3 = MoviesFragment.newInstance(FragmentTag.FR_TOP_RATED);

        adapter.addFragment(f1, getString(R.string.popular).toUpperCase());
        adapter.addFragment(f2, getString(R.string.most_voted).toUpperCase());
        adapter.addFragment(f3, getString(R.string.top_rated).toUpperCase());

        viewPager.setAdapter(adapter);
    }
}
