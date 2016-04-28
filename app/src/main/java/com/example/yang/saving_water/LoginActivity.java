package com.example.yang.saving_water;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    EditText eT;
    EditText PASS;
    DBManager dbManager;
    Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ImageButton bt_login = (ImageButton) findViewById(R.id.btn1);
        ImageButton bt_join = (ImageButton) findViewById(R.id.btn2);

        bt_login.setOnClickListener(this);
        bt_join.setOnClickListener(this);

        eT = (EditText) findViewById(R.id.mail);
        PASS = (EditText) findViewById(R.id.passwordText);
        String DB = getResources().getString(R.string.DATABASE);
        dbManager = new DBManager(getApplicationContext(), DB, null, 1);

    }

    @Override
    public void onClick(View v) {
        boolean check;
        switch (v.getId()) {
            case R.id.btn1://login
                if (!eT.getText().toString().equals(""))
                {//PASS.getText().toString() != "") {
                    check = dbManager.check_ID_PASSWORD(eT.getText().toString(), PASS.getText().toString());
                    if (check) {
                        Intent intent1 = new Intent(LoginActivity.this, MenuActivity.class);
                        intent1.putExtra("ID",eT.getText().toString());
                        startActivity(intent1);
                        finish();
                        // 서버와 통신이 없어서 세션은 설정 안함

                    } else {
                        toast = Toast.makeText(getApplicationContext(),
                                "메일 또는 비밀번호가 일치하지 않습니다.", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    }
                }
                else
                {
                    toast = Toast.makeText(getApplicationContext(),
                            "메일 또는 비밀번호가 입력되지 않았습니다.", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
                break;
            case R.id.btn2://join
                Intent intent2 = new Intent(LoginActivity.this, JoinActivity.class);
                startActivity(intent2);
                finish();
                break;
            default:
        }
    }
}