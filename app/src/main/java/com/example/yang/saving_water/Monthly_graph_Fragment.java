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

import java.util.ArrayList;
import java.util.List;

public class Monthly_graph_Fragment extends BaseFragment implements OnClickListener {
    private ViewGroup layoutGraphView;
    private TextView Textv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_monthly_graph_, container, false);
        layoutGraphView = (ViewGroup) view.findViewById(R.id.layoutGraphView);
        String DB = getResources().getString(R.string.DATABASE);
        final DBManager dbManager = new DBManager(getActivity().getApplicationContext(), DB, null, 1);

        float[][] data = new float[3][6];

        for (int i = 2; i < 5; i++) {
            data[i - 2] = dbManager.getData(i);
        }
        setLineGraph(dbManager);

        float[] total_i = new float[3];
        float total = 0;

        for (int t = 0; t < 3; t++) {
            total_i[t] = 0;
            for (int u = 0; u < 6; u++) {
                total_i[t] += data[t][u];
            }
            total += total_i[t];
        }
        float max = total_i[0];
        int q = 1;
        for (q = 1; q < 3; q++) {
            if (max < total_i[q]) max = q;
        }
        int avr = (int) total / 3;
        Textv = (TextView) view.findViewById(R.id.gText);
        int max_month = (int) max + 2;
        Textv.setText("지난 3달 간 월 평균" + avr + "L의 물이 사용되었으며 가장 많은 양의 물을 사용한 달은" + max_month + "월 입니다.");

        return view;
    }


    private void setLineGraph(DBManager dbManager) {
        //all setting

        LineGraphVO vo = makeLineGraphAllSetting(dbManager);
        layoutGraphView.addView(new LineGraphView(this.getActivity(), vo));
    }

    private LineGraphVO makeLineGraphAllSetting(DBManager dbManager) {
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

        float[] graph1 = dbManager.getData(4);
        float[] graph2 = dbManager.getData(3);
        float[] graph3 = dbManager.getData(2);

        List<LineGraph> arrGraph = new ArrayList<>();
        arrGraph.add(new LineGraph("4월", 0xaa66ff33, graph1));
        arrGraph.add(new LineGraph("3월", 0xaa00ffff, graph2));
        arrGraph.add(new LineGraph("2월", 0xaaff0066, graph3));

        LineGraphVO vo = new LineGraphVO(
                paddingBottom, paddingTop, 70, 80,
                marginTop, marginRight, 25, 2, legendArr, arrGraph);

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
