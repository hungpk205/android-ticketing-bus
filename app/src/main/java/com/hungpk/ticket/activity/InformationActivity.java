package com.hungpk.ticket.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.hungpk.ticket.R;
import com.hungpk.ticket.util.CheckConnection;
import com.hungpk.ticket.util.Constant;

public class InformationActivity extends AppCompatActivity {
 /// create shared -> save
    EditText edit_text_fullname, edit_text_phone;
    Button btnConfirm, btnCancel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
        setup();
        setData();
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        if (CheckConnection.haveNetworkConnection(getApplicationContext())){
            EventButton();
        } else {
            CheckConnection.ShowToast_Short(getApplicationContext(), "Kiểm tra lại kết nối!");
        }
    }

    private void setData() {
        SharedPreferences inforCustomer = getSharedPreferences(Constant.SHARED_CUSTOMER, Context.MODE_PRIVATE);
        String name = inforCustomer.getString(Constant.SHARED_FULL_NAME,"");
        String phone = inforCustomer.getString(Constant.SHARED_SDT,"");
        if (!name.isEmpty() && !phone.isEmpty()){
            edit_text_fullname.setText(name);
            edit_text_phone.setText(phone);
        }
    }

    private void EventButton() {
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fullname = edit_text_fullname.getText().toString().trim();
                String phone = edit_text_phone.getText().toString().trim();
                if (fullname.length()>0 && phone.length()> 0){
                    //Save full name and phone
                    SharedPreferences infoCustomer = getSharedPreferences(Constant.SHARED_CUSTOMER, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = infoCustomer.edit();
                    editor.putString(Constant.SHARED_SDT, phone);
                    editor.putString(Constant.SHARED_FULL_NAME, fullname);
                    editor.commit();

                    startActivity(BookingActivity.getBookingActivityIntent(getApplicationContext(), infoCustomer.getString(Constant.SHARED_SDT,""), infoCustomer.getString(Constant.SHARED_FULL_NAME, "")));

                } else {
                    CheckConnection.ShowToast_Short(getApplicationContext(), "Kiểm tra lại dữ liệu");
                }
            }
        });
    }

    private void setup() {
        edit_text_fullname = findViewById(R.id.edit_text_fullname);
        edit_text_phone = findViewById(R.id.edit_text_phone);
        btnConfirm = findViewById(R.id.button_confirm);
        btnCancel = findViewById(R.id.button_cancel);
    }
}
