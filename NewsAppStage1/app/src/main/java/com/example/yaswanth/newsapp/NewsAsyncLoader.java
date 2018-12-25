package com.example.yaswanth.newsapp;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.support.annotation.Nullable;

import java.util.List;

public class NewsAsyncLoader extends AsyncTaskLoader<List<NewsItems>> {

    private String mUrl;

    public NewsAsyncLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Nullable
    @Override
    public List<NewsItems> loadInBackground() {

        if (mUrl == null) {
            return null;
        }
        return NewsUtils.fetchJSon(mUrl);
    }
}
