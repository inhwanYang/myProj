package com.example.yang.saving_water;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class JoinActivity extends Activity {

    EditText MAIL;
    EditText DEVICE_ID;
    EditText PASSWORD;
    Button button_join;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);
        String DB = getResources().getString(R.string.DATABASE);
        final DBManager dbManager = new DBManager(getApplicationContext(), DB, null, 1);

        button_join = (Button) findViewById(R.id.regbtn);
        MAIL = (EditText) findViewById(R.id.edit_mail);
        PASSWORD = (EditText) findViewById(R.id.edit_pass);
        DEVICE_ID = (EditText) findViewById(R.id.edit_dev);

        button_join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (MAIL.getText().toString().equals("") || DEVICE_ID.getText().toString().equals("")) {
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "빈칸이 없어야 합니다.", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                } else {
                    if (dbManager.select_user(MAIL.getText().toString(), DEVICE_ID.getText().toString())) {
                        Toast toast = Toast.makeText(getApplicationContext(),
                                "해당 메일 또는 등록된 장치 번호가 존재합니다.", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    } else {
                        dbManager.insert("insert into USER values('" + MAIL.getText().toString() +
                                "','" + PASSWORD.getText().toString() + "','" + DEVICE_ID.getText().toString() + "')");

                        Toast toast = Toast.makeText(getApplicationContext(),
                                "등록되었습니다. 로그인 해 주세요", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    }
                    Intent intent1 = new Intent(JoinActivity.this, LoginActivity.class);
                    startActivity(intent1);
                    finish();
                }
            }
        });


    }
}
