package com.example.yang.saving_water;


import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Fragment_mapmenu extends BaseFragment implements OnClickListener {
    Drawable drawable;
    protected static volatile int instanceCount;

    public Fragment_mapmenu() {
        // TODO Auto-generated constructor stub
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_fragment_mapmenu, container, false);

        drawable = getResources().getDrawable(R.drawable.sw_status3);
        String strColor = "#FFFFFFFF";

        Button btnMonthGraph = (Button)view.findViewById(R.id.butt1);
        btnMonthGraph.setOnClickListener(this);
        Button btnHourGraph = (Button)view.findViewById(R.id.butt2);
        btnHourGraph.setOnClickListener(this);
        Button btnMap = (Button)view.findViewById(R.id.butt3);
        btnMap.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.butt1:
                startFragment(getFragmentManager(), Monthly_graph_Fragment.class);
                break;
            case R.id.butt2:
                startFragment(getFragmentManager(), HourGraphFragment.class);
                break;
            case R.id.butt3:
                startFragment(getFragmentManager(), MapFragment.class);
                break;
            default:
                break;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        instanceCount--;
    }

}
