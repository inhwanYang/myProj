package com.example.yang.saving_water;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterFragment extends BaseFragment implements View.OnClickListener {

    private Toast toast;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_register, container, false);

        String DB = getResources().getString(R.string.DATABASE);
        final DBManager dbManager = new DBManager(getActivity().getApplicationContext(), DB, null, 1);

        View v = getActivity().findViewById(R.id.mainLayout);
        TextView tv = (TextView) v.findViewById(R.id.my_id);
        final String ID = tv.getText().toString();

        TextView tv2 = (TextView)view.findViewById(R.id.edit_mail2);
        tv2.setText(ID);

        final EditText password = (EditText) view.findViewById(R.id.edit_pass2);
        final EditText device_id = (EditText) view.findViewById(R.id.edit_dev2);

        Button modBtn = (Button)view.findViewById(R.id.modbtn2);
        modBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(password.getText().toString().equals("") || device_id.getText().toString().equals(""))
                {
                    toast = Toast.makeText(getActivity().getApplicationContext(),
                            "모든 칸을 채워야 합니다.", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();

                }
                else
                {
                    //수정
                    dbManager.update("UPDATE USER SET PASSWORD = '"+password.getText().toString() +"', DEVICE_ID = "+device_id.getText().toString()+
                    " WHERE USER_EMAIL = '"+ID+"'");
                    toast = Toast.makeText(getActivity().getApplicationContext(),
                            "수정 되었습니다.", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    startFragment(getFragmentManager(), Fragment_mapmenu.class);
                }
            }
        });
        return view;
    }

    @Override
    public void onClick(View v) {
    }
}
