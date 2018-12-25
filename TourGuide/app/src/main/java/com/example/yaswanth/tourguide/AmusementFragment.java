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
public class AmusementFragment extends Fragment {

    //creating a Fragment View
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //inflating the layout to fragment
        View rootView = inflater.inflate(R.layout.list_view, container, false);
        //Creating Array list of Places
        final ArrayList<Places> places = new ArrayList<>();
        places.add(new Places(R.drawable.zoo, getString(R.string.zoo)));
        places.add(new Places(R.drawable.shilparamam, getString(R.string.shilparamam)));
        places.add(new Places(R.drawable.wonderla, getString(R.string.wonderla)));
        places.add(new Places(R.drawable.leonia, getString(R.string.leonia)));
        //creating Adapter Instance
        PlaceAdapter placeAdapter = new PlaceAdapter(getActivity(), places);
        // Creating ListView Instance
        ListView listView = rootView.findViewById(R.id.placesList);
        listView.setAdapter(placeAdapter);
        return rootView;
    }

}
