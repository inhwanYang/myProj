package com.example.yang.saving_water;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.support.v7.app.AppCompatActivity;

public class MenuActivity extends AppCompatActivity implements View.OnClickListener {
    Intent intent1;
    String my_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        ImageButton bt_InfoFragment = (ImageButton) findViewById(R.id.map);
        bt_InfoFragment.setOnClickListener(this);
        ImageButton bt_newsFragment = (ImageButton) findViewById(R.id.news);
        bt_newsFragment.setOnClickListener(this);
        ImageButton bt_conrtolFragment = (ImageButton) findViewById(R.id.water_control);
        bt_conrtolFragment.setOnClickListener(this);
        ImageButton bt_talkFragment = (ImageButton) findViewById(R.id.GroupTalk);
        bt_talkFragment.setOnClickListener(this);
        ImageButton bt_homeFragment = (ImageButton) findViewById(R.id.home);
        bt_homeFragment.setOnClickListener(this);
        intent1 = getIntent();
        my_id = intent1.getStringExtra("ID");
    }
    @Override
    public void onClick(View v) {
        Intent intent = new Intent(MenuActivity.this, MainActivity.class);
        switch (v.getId()) {
            case R.id.map:
                intent.putExtra("index", "0");
                intent.putExtra("name", "사용량 정보");
                break;
            case R.id.home:
                intent.putExtra("index", "1");
                intent.putExtra("name", "절약량 정보");
                break;
            case R.id.water_control:
                intent.putExtra("index", "2");
                intent.putExtra("name", "장치제어");
                break;
            case R.id.news:
                intent.putExtra("index", "3");
                intent.putExtra("name", "뉴스와 소식");
                break;
            case R.id.GroupTalk:
                intent.putExtra("index", "4");
                intent.putExtra("name", "그룹 톡");
                break;

        }
        intent.putExtra("ID",my_id);
        startActivity(intent);
        finish();
    }
}
