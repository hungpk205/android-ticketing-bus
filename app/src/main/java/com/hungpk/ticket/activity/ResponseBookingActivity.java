package com.hungpk.ticket.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.hungpk.ticket.MainActivity;
import com.hungpk.ticket.R;
import com.hungpk.ticket.util.Constant;

public class ResponseBookingActivity extends AppCompatActivity {
    private TextView txtStatus;
    Button btnOK;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_response_booking);
        setup();
        EventButtonOK();
    }

    private void EventButtonOK() {
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ResponseBookingActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setup() {
        txtStatus = findViewById(R.id.text_view_status);
        btnOK = findViewById(R.id.button_ok);

        Intent intent = getIntent();
        if (intent.getStringExtra("status").equals("success")){
            txtStatus.setText("Vé đã đặt thành công!");
        } else {
           txtStatus.setText("Có lỗi xảy ra. Vui lòng thử lại sau!");
        }

    }

    public static Intent getResponseBookingActivityIntent(Context context, String status){
        Intent intent = new Intent(context, ResponseBookingActivity.class);
        intent.putExtra("status", status);
        return intent;
    }
}
