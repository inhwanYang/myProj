package com.example.yang.saving_water;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.TextView;
import com.handstudio.android.hzgrapherlib.animation.GraphAnimation;
import com.handstudio.android.hzgrapherlib.graphview.LineGraphView;
import com.handstudio.android.hzgrapherlib.vo.GraphNameBox;
import com.handstudio.android.hzgrapherlib.vo.linegraph.LineGraph;
import com.handstudio.android.hzgrapherlib.vo.linegraph.LineGraphVO;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class Fragment_myHome extends BaseFragment implements OnClickListener {
    private ViewGroup layoutGraphView;
    private TextView Textv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_fragment_my_home, container, false);
        layoutGraphView = (ViewGroup) view.findViewById(R.id.GraphView);
        String DB = getResources().getString(R.string.DATABASE);
        final DBManager dbManager = new DBManager(getActivity().getApplicationContext(), DB, null, 1);
        float[] data1 = dbManager.getSavedData(1);
        setLineGraph(data1);
        float total = 0;
        float avr;
        for (int t = 0; t < 6; t++) {
            total += data1[t];
        }
        avr = total/30;
        Textv = (TextView)view.findViewById(R.id.gText);
        Textv.setText("이 달의 총 저장(절약)량은 "+total+"L이며 하루 평균 "+avr +"L 가 저장되었습니다.");

        return view;
    }
    @Override
    public void onClick(View v) {
    }

    private void setLineGraph(float[] data1) {

        LineGraphVO vo = makeLineGraphAllSetting(data1);
        layoutGraphView.addView(new LineGraphView(this.getActivity(), vo));
    }

    private LineGraphVO makeLineGraphAllSetting(float[] data1) {
        //BASIC LAYOUT SETTING
        //padding
        int paddingBottom = LineGraphVO.DEFAULT_PADDING;
        int paddingTop = LineGraphVO.DEFAULT_PADDING;
        int paddingLeft = LineGraphVO.DEFAULT_PADDING;
        int paddingRight = LineGraphVO.DEFAULT_PADDING;
        //graph margin
        int marginTop = LineGraphVO.DEFAULT_MARGIN_TOP;
        int marginRight = LineGraphVO.DEFAULT_MARGIN_RIGHT;
        //max value
        int maxValue = LineGraphVO.DEFAULT_MAX_VALUE;
        //increment
        int increment = LineGraphVO.DEFAULT_INCREMENT;

        //GRAPH SETTING
        String[] legendArr = {"1", "5", "10", "15", "20", "25", "일(Y축-리터(L))"};

        List<LineGraph> arrGraph = new ArrayList<>();
        arrGraph.add(new LineGraph("이달의 절약량", 0xaa66ff33, data1));
        LineGraphVO vo = new LineGraphVO(
                paddingBottom, paddingTop, 70, 80,
                marginTop, marginRight, 8, 1, legendArr, arrGraph);

        //set animation
        vo.setAnimation(new GraphAnimation(GraphAnimation.LINEAR_ANIMATION, GraphAnimation.DEFAULT_DURATION));
        //set graph name box
        vo.setGraphNameBox(new GraphNameBox());
        return vo;
    }


}
