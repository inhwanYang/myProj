package com.example.yang.saving_water;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity implements View.OnClickListener {

    final String TAG = "MainActivity";

    int mCurrentFragmentIndex;
    public final static int FRAGMENT_MAP = 0;
    public final static int FRAGMENT_HOME = 1;
    public final static int FRAGMENT_CONTROL = 2;
    public final static int FRAGMENT_NEWS = 3;
    public final static int FRAGMENT_TALK = 4;
    public final static int FRAGMENT_MY_INFO = 5;
    public final static int FRAGMENT_REGISTER = 6;

    private BackPressCloseHandler backPressCloseHandler;

    private Context mContext;

    private Toolbar toolbar;
    private DrawerLayout dlDrawer;
    private ActionBarDrawerToggle dtToggle;
    private String[] navItems = {"사용량 정보", "절약량 정보", "장치 제어", "뉴스&소식", "그룹톡"};
    private ListView lvNavList;
    private FrameLayout flContainer;
    private Toast toast;
    private DBManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //툴바와 서랍장 SET
        toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        dlDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        setSupportActionBar(toolbar);
        dtToggle = new ActionBarDrawerToggle(this, dlDrawer, R.string.app_name2, R.string.app_name2) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
        dlDrawer.setDrawerListener(dtToggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        lvNavList = (ListView) findViewById(R.id.lv_activity_main_nav_list);
        flContainer = (FrameLayout) findViewById(R.id.fl_activity_main_container);
        lvNavList.setAdapter(
                new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, navItems));
        lvNavList.setOnItemClickListener(new DrawerItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position,
                                    long id) {
                View view2 = findViewById(R.id.mainLayout);
                final TextView tv = (TextView) view2.findViewById(R.id.bar_text);
                switch (position) {
                    case 0:
                        mCurrentFragmentIndex = FRAGMENT_MAP;
                        tv.setText("사용량 정보");
                        fragmentReplace(mCurrentFragmentIndex);
                        break;
                    case 1:
                        mCurrentFragmentIndex = FRAGMENT_HOME;
                        tv.setText("절약량 정보");
                        fragmentReplace(mCurrentFragmentIndex);
                        break;
                    case 2:
                        mCurrentFragmentIndex = FRAGMENT_CONTROL;
                        tv.setText("장치 제어");
                        fragmentReplace(mCurrentFragmentIndex);
                        break;
                    case 3:
                        mCurrentFragmentIndex = FRAGMENT_NEWS;
                        tv.setText("뉴스와 소식");
                        fragmentReplace(mCurrentFragmentIndex);
                        break;
                    case 4:
                        mCurrentFragmentIndex = FRAGMENT_TALK;
                        tv.setText("그룹 톡");
                        fragmentReplace(mCurrentFragmentIndex);
                        break;
                }
                dlDrawer.closeDrawer(lvNavList); // 추가됨
            }
        });


        //DB 매니저 생성
        String DB = getResources().getString(R.string.DATABASE);
        dbManager = new DBManager(getApplicationContext(), DB, null, 1);

        //서버 역할 대신 sqlite에 DB 생성
        inputData(dbManager);


        Intent intent = getIntent();
        String IndexString = intent.getStringExtra("index");
        String nameString = intent.getStringExtra("name");
        View v = findViewById(R.id.mainLayout);
        final TextView tv = (TextView) v.findViewById(R.id.bar_text);
        tv.setText(nameString); // 상태 메시지 SET

        // 현재 접속 아이디를 전역변수처럼 사용하기 위한 SET
        TextView myID = (TextView)findViewById(R.id.my_id);
        myID.setText(intent.getStringExtra("ID"));

        int index = Integer.parseInt(IndexString);
        backPressCloseHandler = new BackPressCloseHandler(this);
        fragmentReplace(index);// 화면 초기화

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        View view2 = findViewById(R.id.mainLayout);
        final View view3 = findViewById(R.id.ll_fragment);
        final TextView tv = (TextView) view2.findViewById(R.id.bar_text);
        final TextView tv_my_info = (TextView) view2.findViewById(R.id.my_info_text);

        final Intent intent = getIntent();
        final String _ID = intent.getStringExtra("ID");

        final int my_Device_ID = dbManager.getDeviceID(_ID);

        MenuItem mItem1 = menu.findItem(R.id.showMyInfo);
        mItem1.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                tv.setText("내정보");
                tv_my_info.setText("\n\n현재 접속 아이디: \n" +_ID +"\n\n등록된 장치 ID: "+my_Device_ID+
                        "\n(장치번호 0은 미등록) \n 확인 후 글 풍선 클릭 후 사용");
                tv_my_info.setVisibility(View.VISIBLE);
                view3.setVisibility(View.INVISIBLE);
                tv_my_info.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        tv_my_info.setVisibility(View.INVISIBLE);
                        view3.setVisibility(View.VISIBLE);
                    }
                });
                return false;
            }
        });
        MenuItem mItem2 = menu.findItem(R.id.RegisterDevice);
        mItem2.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                tv.setText("장치 등록");
                fragmentReplace(6);
                return false;
            }
        });
        MenuItem mItem3 = menu.findItem(R.id.LogoutMenu);
        mItem3.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
                return false;
            }
        });
        return true;
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

// Sync the toggle state after onRestoreInstanceState has occurred.
        dtToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        dtToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return dtToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);

    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        getFragmentManager().popBackStack();
        backPressCloseHandler.onBackPressed();
    }

    public void onBtnClicked(View view) {
        LinearLayout ll = (LinearLayout) findViewById(R.id.ll_fragment);
    }

    public void fragmentReplace(int reqNewFragmentIndex) {

        Fragment newFragment;

        Log.d(TAG, "fragmentReplace " + reqNewFragmentIndex);

        newFragment = getFragment(reqNewFragmentIndex);


        final FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction();

        transaction.replace(R.id.ll_fragment, newFragment);
        // Commit the transaction
        transaction.commit();
    }

    private Fragment getFragment(int idx) {
        Fragment newFragment = null;

        switch (idx) {

            case FRAGMENT_MAP:
                newFragment = new Fragment_mapmenu();
                break;
            case FRAGMENT_NEWS:
                newFragment = new NewsFragment();
                break;
            case FRAGMENT_HOME:
                newFragment = new Fragment_myHome();
                break;
            case FRAGMENT_TALK:
                newFragment = new FragmentGroupTalk();
                break;
            case FRAGMENT_CONTROL:
                newFragment = new DeviceControlFragment();
                break;
            case FRAGMENT_REGISTER:
                newFragment = new RegisterFragment();
                break;

            default:
                Log.d(TAG, "Unhandle case");
                break;
        }
        return newFragment;
    }


    void inputData(DBManager dbManager) {
        // 서버 대신 사용
        // 가정 시간대 별 평균 사용량
        //월별 데이터
        //"CREATE TABLE HOMEUSE_IN_MONTH( DEVICE_ID INTEGER, name TEXT, count INTEGER);")
        dbManager.insert("insert into HOMEUSE_IN_MONTH values('1','2','15','17','15','14','16','19')");
        dbManager.insert("insert into HOMEUSE_IN_MONTH values('1','3','18','19','21','13','18','15')");
        dbManager.insert("insert into HOMEUSE_IN_MONTH values('1','4','20','14','16','19','20','13')");

        //"CREATE TABLE HOMEUSE_IN_YEAR( DEVICE_ID INTEGER, name TEXT, count INTEGER);");
        //일 평균 데이터
        dbManager.insert("insert into HOMEUSE_IN_DAY values('1','1','6','6','2','7','6','6','4')");
        // 절약 데이터
        dbManager.insert("insert into WATERSAVED values(null,'1','5','6','4','6','5','4')");

    }
    @Override
    public void onClick(View v) {
    }

}
