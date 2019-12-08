package com.hungpk.ticket.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.hungpk.ticket.R;
import com.hungpk.ticket.util.CheckConnection;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class SearchActivity extends AppCompatActivity {
    private EditText txtStartPlace, txtEndPlace;
    private TextView txtSelectDate, txtValueDate;
    private ImageView imgSelectDate;
    private Button btnSearch, btnCancel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        setup();
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

    private void EventButton() {
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String start_place = txtStartPlace.getText().toString().trim();
                String end_place = txtEndPlace.getText().toString().trim();
                String start_time = txtValueDate.getText().toString().trim();
                if (start_place.equals("") || end_place.equals("") || start_time.equals("")){
                    CheckConnection.ShowToast_Short(getApplicationContext(), "Vui lòng nhập đầy đủ thông tin");
                } else {
                    startActivity(TripSearchActivity.getTripSearchActivityIntent(getApplicationContext(), start_place, end_place, start_time));
                }
            }
        });
    }

    private void setup() {
        txtStartPlace = findViewById(R.id.edit_text_start_place);
        txtEndPlace = findViewById(R.id.edit_text_end_place);
        txtSelectDate = findViewById(R.id.edit_text_select_date);
        txtValueDate = findViewById(R.id.value_date);
        imgSelectDate = findViewById(R.id.img_select_date);
        btnSearch = findViewById(R.id.button_search);
        btnCancel = findViewById(R.id.button_cancel_search);

        imgSelectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SelectDatePicker();
            }
        });

    }
    private void SelectDatePicker(){
        final Calendar calendar = Calendar.getInstance();
        int ngay = calendar.get(Calendar.DATE);
        int thang = calendar.get(Calendar.MONTH);
        int nam = calendar.get(Calendar.YEAR);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                calendar.set(i,i1,i2);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                txtValueDate.setVisibility(View.VISIBLE);
                txtValueDate.setText(simpleDateFormat.format(calendar.getTime()));
            }
        }, nam, thang, ngay);
        datePickerDialog.show();
    }
}
