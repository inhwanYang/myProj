package com.example.yang.saving_water;


import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

public class MapFragment extends BaseFragment implements OnMapReadyCallback {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private SupportMapFragment mapFragment;
    private GoogleMap mMap;
    private TextView text;
    private String nameString;
    private OnFragmentInteractionListener mListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        MapView mapview=(MapView)view.findViewById(R.id.map);
        mapview.onCreate(savedInstanceState);
        mapview.onResume();
        mapview.getMapAsync(this);

        final LatLng point = new LatLng(37.5435,127.0596);
        final ImageButton button = (ImageButton) view.findViewById(R.id.myposition);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(point, 15));
            }
        });
        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        final DBManager dbManager = new DBManager(getActivity().getApplicationContext(), "INFO_DB.db", null, 1);
        // 서버에서 값을 받아오는 대신 간략하게 보여주기 위해서..
        dbManager.insert("insert into MAPPOINT values(null,'30L','37.544','127.064','성수동1');");
        dbManager.insert("insert into MAPPOINT values(null,'31L','37.545','127.063','성수동2');");
        dbManager.insert("insert into MAPPOINT values(null,'29L','37.546','127.062','성수동3');");
        dbManager.insert("insert into MAPPOINT values(null,'28L','37.549','127.058','성수동4');");
        dbManager.insert("insert into MAPPOINT values(null,'28L','37.552','127.059','송정동5');");
        dbManager.insert("insert into MAPPOINT values(null,'28L','37.553','127.055','송정동6');");
        dbManager.insert("insert into MAPPOINT values(null,'28L','37.534','127.054','성수동7');");

        mMap = googleMap;
        // Add a marker in Sydney and move the camera
        dbManager.PointMapMarker(mMap);
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {

            public boolean onMarkerClick(Marker marker) {

                return false;
            }
        });
    }

    // TODO: Rename and change types and number of parameters
    public static MapFragment newInstance(String param1, String param2) {
        MapFragment fragment = new MapFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        //fragment.setArguments(args);
        return fragment;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    public void onClick(View v)
    {

    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
