package com.example.yaswanth.tourguide;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class SightsFragment extends Fragment {


    //creating a Fragment View
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //inflating the layout to fragment
        View rootView = inflater.inflate(R.layout.list_view, container, false);
        //Creating Array list of Places
        final ArrayList<Places> places = new ArrayList<>();
        places.add(new Places(R.drawable.charminar, getString(R.string.charminar)));
        places.add(new Places(R.drawable.golkondafort, getString(R.string.golkonda)));
        places.add(new Places(R.drawable.budda, getString(R.string.buddha)));
        places.add(new Places(R.drawable.birlatemple, getString(R.string.birlamandir)));
        places.add(new Places(R.drawable.meccamasjid, getString(R.string.meccamasjid)));
        //creating Adapter Instance
        PlaceAdapter placeAdapter = new PlaceAdapter(getActivity(), places);
        // Creating ListView Instance
        ListView listView = rootView.findViewById(R.id.placesList);
        listView.setAdapter(placeAdapter);
        return rootView;
    }

}
