package com.example.yang.saving_water;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class DeviceControlFragment extends Fragment implements View.OnClickListener {
    TextView tv;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_device_control, container, false);
        String DB = getResources().getString(R.string.DATABASE);
        final DBManager dbManager = new DBManager(getActivity().getApplicationContext(), DB, null, 1);

        final Drawable drawableON = getResources().getDrawable(R.drawable.button_on);
        final Drawable drawableOFF = getResources().getDrawable(R.drawable.button_off);
        final ImageView iview = (ImageView)view.findViewById(R.id.ONOFF);

        Button OnButton = (Button) view.findViewById(R.id.ON);

        OnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iview.setBackground(drawableON);
                Toast toast = Toast.makeText(getActivity().getApplicationContext(),
                        "장치 ON", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
        });
        Button OFFButton = (Button) view.findViewById(R.id.OFF);
        OFFButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(getActivity().getApplicationContext(),
                        "장치 OFF", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                iview.setBackground(drawableOFF); }
        });
        return view;
    }

    @Override
    public void onClick(View v) {

    }

}
