package com.example.yang.saving_water;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

public class FragmentGroupTalk extends BaseFragment implements View.OnClickListener {
    ListView m_ListView;
    LinearLayout Linear;
    CustomAdapter m_Adapter;
    Button button1;
    EditText eText;
    private BackPressCloseHandler backPressCloseHandler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fragment_group_talk, container, false);
        String DB = getResources().getString(R.string.DATABASE);
        final DBManager dbManager = new DBManager(getActivity().getApplicationContext(), DB, null, 1);
// 커스텀 어댑터 생성
        m_Adapter = new CustomAdapter();
        int my_id = 1;
        // Xml에서 추가한 ListView 연결
        m_ListView = (ListView) view.findViewById(R.id.listView1);
        Linear = (LinearLayout) view.findViewById(R.id.im1);

        button1 = (Button) view.findViewById(R.id.but1);
        eText = (EditText) view.findViewById(R.id.editText1);
        // ListView에 어댑터 연결
        m_ListView.setAdapter(m_Adapter);
        dbManager.getTalk(my_id, m_Adapter);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                m_Adapter.add(eText.getText().toString(), 1);
                dbManager.insert("insert into GROUP_TALK values('1','" + eText.getText().toString() + "','1')");
                m_Adapter.notifyDataSetChanged();
            }
        });
        return view;
    }

    @Override
    public void onClick(View v) {
    }
}
