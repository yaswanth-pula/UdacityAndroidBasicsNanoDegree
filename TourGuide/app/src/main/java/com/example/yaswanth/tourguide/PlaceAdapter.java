package com.example.yaswanth.tourguide;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class PlaceAdapter extends ArrayAdapter<Places> {
    public PlaceAdapter(Activity context, ArrayList<Places> places) {
        super(context, 0, places);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.place_layout, parent, false);

        }

        Places mPlaces = getItem(position);

        ImageView mPlaceImage = listItemView.findViewById(R.id.placeImage);
        mPlaceImage.setImageResource(mPlaces.getmPlaceImageId());

        TextView mPLaceText = listItemView.findViewById(R.id.placeText);
        mPLaceText.setText(mPlaces.getmPlaceText());

        return listItemView;
    }
}
