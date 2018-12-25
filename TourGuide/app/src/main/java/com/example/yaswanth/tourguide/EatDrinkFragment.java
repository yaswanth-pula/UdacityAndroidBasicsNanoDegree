package com.example.yaswanth.tourguide;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class EatDrinkFragment extends Fragment {


    //creating a Fragment View
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //inflating the layout to fragment
        View rootView = inflater.inflate(R.layout.list_view, container, false);
        //Creating Array list of Places
        final ArrayList<Places> places = new ArrayList<>();
        places.add(new Places(R.drawable.paradise, getString(R.string.paradise)));
        places.add(new Places(R.drawable.chutneys, getString(R.string.chutneys)));
        places.add(new Places(R.drawable.eatstreet, getString(R.string.eat_street)));
        places.add(new Places(R.drawable.hardrockcafe, getString(R.string.rockcafe)));
        places.add(new Places(R.drawable.vapour, getString(R.string.vapour)));
        places.add(new Places(R.drawable.repete, getString(R.string.repete)));
        //creating Adapter Instance
        PlaceAdapter placeAdapter = new PlaceAdapter(getActivity(), places);
        // Creating ListView Instance
        ListView listView = rootView.findViewById(R.id.placesList);
        listView.setAdapter(placeAdapter);
        return rootView;
    }

}
