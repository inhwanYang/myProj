package com.example.yang.saving_water;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.TextView;


import com.handstudio.android.hzgrapherlib.animation.GraphAnimation;
import com.handstudio.android.hzgrapherlib.graphview.CurveGraphView;
import com.handstudio.android.hzgrapherlib.vo.GraphNameBox;
import com.handstudio.android.hzgrapherlib.vo.curvegraph.CurveGraph;
import com.handstudio.android.hzgrapherlib.vo.curvegraph.CurveGraphVO;
import com.handstudio.android.hzgrapherlib.vo.linegraph.LineGraphVO;

import java.util.ArrayList;
import java.util.List;

public class HourGraphFragment extends BaseFragment implements OnClickListener {
    private ViewGroup layoutGraphView;
    private TextView Textv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hour_graph, container, false);
        layoutGraphView = (ViewGroup) view.findViewById(R.id.GraphView);
        String DB = getResources().getString(R.string.DATABASE);
        final DBManager dbManager = new DBManager(getActivity().getApplicationContext(), DB, null, 1);

        float[] graph1 = dbManager.getData_In_Day(1);

        setCurveGraph(graph1);

        float total = 0;
        for (int t = 0; t < 6; t++) {
            total += graph1[t];
        }
        Textv = (TextView) view.findViewById(R.id.gText);
        Textv.setText("하루 평균 " + total + "L의 물이 사용되었습니다.");

        return view;
    }

    private void setCurveGraph(float[] graph1) {
        //all setting

        CurveGraphVO vo = makeLineGraphAllSetting(graph1);
        layoutGraphView.addView(new CurveGraphView(this.getActivity(), vo));
    }

    private CurveGraphVO makeLineGraphAllSetting(float[] graph1) {
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

        String[] legendArr = {"0", "4", "8", "12", "16", "20", "24", "   HOUR(Y축-리터(L))"};

        List<CurveGraph> arrGraph = new ArrayList<>();
        arrGraph.add(new CurveGraph("우리집 시간 당 평균 물 사용량", 0xaa66ff33, graph1));
        CurveGraphVO vo = new CurveGraphVO(paddingBottom, paddingTop, 70, 60,
                marginTop, 150, 8, 1, legendArr, arrGraph);

        //set animation
        vo.setAnimation(new GraphAnimation(GraphAnimation.LINEAR_ANIMATION, GraphAnimation.DEFAULT_DURATION));
        //set graph name box
        vo.setGraphNameBox(new GraphNameBox());
        return vo;
    }

    @Override
    public void onClick(View v) {

    }

}
