package com.example.yaswanth.newsapp;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class NewsItemsAdapter extends ArrayAdapter<NewsItems> {
    public NewsItemsAdapter(Activity context, ArrayList<NewsItems> newsItems) {
        super(context, 0, newsItems);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listView = convertView;
        if (listView == null) {
            listView = LayoutInflater.from(getContext()).inflate(R.layout.news_view, parent, false);
        }
        NewsItems newsItems = getItem(position);
        //for the Main News Title
        TextView mMainNewsTextView = listView.findViewById(R.id.mainNews);
        //to overcome the NullPointerException (newsItem!=null)
        mMainNewsTextView.setText(newsItems != null ? newsItems.getWebTitle() : null);
        //for the AuthorName
        TextView mAuthorNameTextView = listView.findViewById(R.id.authorName);
        mAuthorNameTextView.setText(newsItems != null ? newsItems.getAuthorName() : null);
        //for the category of the news
        TextView mCategoryTextView = listView.findViewById(R.id.category);
        mCategoryTextView.setText(newsItems != null ? newsItems.getSecName() : null);
        //for the time and date of the news
        TextView mTimeOfPublishTextView = listView.findViewById(R.id.time_date);
        mTimeOfPublishTextView.setText(newsItems != null ? newsItems.getPublishDate() : null);
        return listView;
    }
}
