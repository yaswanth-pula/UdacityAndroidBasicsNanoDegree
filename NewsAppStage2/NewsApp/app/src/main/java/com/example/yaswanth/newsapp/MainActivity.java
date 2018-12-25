package com.example.yaswanth.newsapp;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<NewsItems>> {

    private static final String NEWS_DATA_URL = " http://content.guardianapis.com/search?&show-tags=contributor&";
    private static final String API_KEY = "api-key=e861f352-3b0b-4a5b-81e6-e32ceeb9f311";
    private static NewsItemsAdapter newsItemsAdapter;
    private static final int NEWS_LOAD_ID = 1;
    private TextView mEmptyTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mEmptyTextView = findViewById(R.id.emptyView);
        final ListView newsListView = findViewById(R.id.newsView);
        newsItemsAdapter = new NewsItemsAdapter(this, new ArrayList<NewsItems>());
        newsListView.setAdapter(newsItemsAdapter);
        newsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                NewsItems currentNewsItems = newsItemsAdapter.getItem(i);
                Uri newsUri = Uri.parse(currentNewsItems.getWebUrl());
                Intent webIntent = new Intent(Intent.ACTION_VIEW, newsUri);
                //check for Intent fallback issue
                PackageManager packageManager = getPackageManager();
                List<ResolveInfo> activity = packageManager.queryIntentActivities(webIntent, 0);
                boolean isIntentSafe = activity.size() > 0;
                if (isIntentSafe) {
                    startActivity(webIntent);
                } else {
                    Toast.makeText(getApplicationContext(), getString(R.string.intent_fallback_error), Toast.LENGTH_SHORT).show();
                }
            }
        });
        if (isConnected()) {
            LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(NEWS_LOAD_ID, null, this);

        } else {
            View loadView = findViewById(R.id.progressBar);
            loadView.setVisibility(View.GONE);
            mEmptyTextView.setText(R.string.no_internet);
            mEmptyTextView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_action_warning, 0, 0, 0);
        }
        newsListView.setEmptyView(mEmptyTextView);


    }


    @Override
    public android.content.Loader<List<NewsItems>> onCreateLoader(int i, Bundle bundle) {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String newsTopic = sharedPreferences.getString(getString(R.string.settings_topic_key), getString(R.string.settings_topic_default));

        String newsOrderBy = sharedPreferences.getString(getString(R.string.settings_order_by_key), getString(R.string.settings_order_by_default));

        Uri baseUri = Uri.parse(NEWS_DATA_URL + API_KEY);

        Uri.Builder builder = baseUri.buildUpon();

        builder.appendQueryParameter(getString(R.string.topic_query_parameter), newsTopic);
        builder.appendQueryParameter(getString(R.string.order_by_query_parameter), newsOrderBy);

        return new NewsAsyncLoader(this, builder.toString());
    }

    @Override
    public void onLoadFinished(Loader<List<NewsItems>> loader, List<NewsItems> newsItems) {
        View loadView = findViewById(R.id.progressBar);
        loadView.setVisibility(View.GONE);
        mEmptyTextView.setText(R.string.no_news);
        newsItemsAdapter.clear();
        if (newsItems != null && !newsItems.isEmpty()) {
            newsItemsAdapter.addAll(newsItems);
        }
    }

    @Override
    public void onLoaderReset(android.content.Loader<List<NewsItems>> loader) {
        newsItemsAdapter.clear();
    }

    private boolean isConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingsIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
