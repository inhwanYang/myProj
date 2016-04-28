package com.example.yang.saving_water;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class NewsFragment extends Fragment implements OnClickListener {
    private ArrayList<String> arrayList;
    private ArrayAdapter<String>    m_Adapter;
    TextView tv;
    String bodyText;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_news, container, false);
        String DB = getResources().getString(R.string.DATABASE);
        final DBManager dbManager = new DBManager(getActivity().getApplicationContext(), DB, null, 1);

        tv = (TextView)view.findViewById(R.id.TextinList);
        tv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                tv.setVisibility(View.INVISIBLE);
            }
        });
        Insert(dbManager);

        arrayList = new ArrayList<>();

        dbManager.setNews(arrayList);

        m_Adapter = new ArrayAdapter<>(getActivity(),android.R.layout.simple_list_item_1,arrayList);
        ListView listView=(ListView)view.findViewById(R.id.listview);
        listView.setAdapter(m_Adapter);
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String str = m_Adapter.getItem(position);
                bodyText = dbManager.getNewsBody(position);
                tv.setText(bodyText);
                tv.setVisibility(View.VISIBLE);
                //Toast.makeText(getActivity().getBaseContext(),str,Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

    @Override
    public void onClick(View v) {

    }


    public void Insert(final DBManager dbManager)
    {
        //서버 대신..
        dbManager.insert("insert into NEWS values(null,'물 부족국가 한국에서 물 절약하는 방법 7가지'," +
                "'아침에 일어나서 물 한잔 마시고 출근 준비를 위해 욕실로 가 샤워를 합니다. 회사에 도착해 커피를 한잔 타 자리에서 일을 하다 퇴근 후 밀린 설거지와 빨래를 하고 하루의 피곤을 씻어 내기 위해 샤워를 하고 잠에 듭니다. \n" +
                "보통 직장인들의 하루 일과 인데요, 공감하시나요? 직장인뿐만 아니라 학생 주부 등 거의 모든 사람이 비슷한 패턴으로 움직이며 하루에도 많은 양의 물을 마시고, 사용하고 있지요.')");
        dbManager.insert("insert into NEWS values(null,'화장실에서의 물절약','기존 변기 수조에 절수기 설치 또는 물 채운 병을 넣어 20% 절수\n" +
                "현재 가정에 많이 보급되어 있는 변기의 용량은 13리터 급이며 1일 평균 변기 이용 횟수를 7회(대변1회, 소변6회)라고 가정하면 4인 가족의 경우 1일 물 사용량은 255리터 수준입니다. 하지만 ‘대소변 구분형 절수부속’을 설치하면 기존의 13리터급 변기에 비해 67리터(30% 정도), ‘사용수량 조절형 절수부속’을 사용하면 40리터(15% 정도) 절수가 가능합니다. 물론 절수부속 대신 1.5리터 용량의 물병을 물탱크 안에 넣어두어도 변기의 물을 아낄 수 있습니다.\n" +
                "변수 수조를 절수형으로 설치하여 50% 절수')");
        dbManager.insert("insert into NEWS values(null,'변기 물탱크에 벽돌 넣으면 물 절약 효과','[가정에서 실천할 수 있는 절수방법]\n" +
                "극심한 가뭄에 시달리는 캘리포니아주가 주 역사 167년만에 처음으로 강제 절수명령을 내렸다.\n" +
                "제리 브라운 주지사가 전체 물 사용량의 25% 이상을 강제로 감축하도록 행정명령을 발표했고, 이에 따라 캘리포니아주 수자원 통제위원회는 내년 물 소비를 최대 35%까지 줄이는 절수계획을 7일 발표한 것. 위반 지역에 대해서는 하루 최고 1만달러의 벌금이 부과될 예정이다.\n" +
                "주 정부 역시 주민들의 물 사용량 감축을 위해 다양한 내용을 발표한 상태다.')");
        dbManager.insert("insert into NEWS values(null,'이제는 다함께 물 절약에 앞장서야 할 때 입니다.','모든 국민이 피부로 느낄 수 있는 물 절약 효과를 얻어야 하겠습니다.\n" +
                "정부는 국가 물 절약 기본 목표를 설정하여 2006년까지 7억 9천만 톤의 물을 절약할 계획입니다. 이와 같은 목표를 달성하기 위해 정책 추진의 수단별 목표를 구체적으로 설정하여 추진하겠습니다. 이 목표가 달성될 경우에는 섬진강댐의 저수용량이 3억 5천만 톤이므로 섬진강 댐 두 개를 건설하는 것보다 더 큰 효과를 거두게 됩니다. \n" +
                "이를 위해 절수 효과의 투입되는 예산규모에 따라 장·단기 대책으로 구분하여 절수 대책을 추진해야겠습니다. 단기적으로는 적은 예산으로도 물 절약 효과가 있는 절수기기 도입을 적극 추진하고, 중·장기적으로는 많은 예산이 소요되는 노후 수도관 교체 사업, 물 절약형 수도 요금 체제의 도입과 중수도 보급을 늘려나가겠습니다. \n" +
                "무엇보다도 공공 기관이 앞장서서 절수 기기를 설치하겠습니다. 과천정부청사에는 작년에 절수 기기를 설치하여 12.7%의 절수 효과를 보고 있고, 2000년에는 다른 정부 청사와 지방자치단체 등 모든 공공 기관에도 절수 기기를 설치할 계획입니다. \n" +
                "물 절약은 국민의 생활 습관과 직결된 문제이기 때문에 정부의 물 절약 시책만으로는 한계가 있습니다. 국민 여러분의 협조가 무엇보다도 중요합니다. 우리 모두가 공동의 이익을 위해 다함께 노력해야 할 때입니다.')");
    }

}
