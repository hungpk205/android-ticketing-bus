package com.hungpk.ticket.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hungpk.ticket.R;
import com.hungpk.ticket.model.Booking;
import com.hungpk.ticket.model.Employee;
import com.hungpk.ticket.model.Ticket;
import com.hungpk.ticket.util.CheckConnection;

import java.io.Serializable;
import java.text.DecimalFormat;

public class DetailBookingActivity extends AppCompatActivity {
    private TextView txtStartEndPlace, txtStartEndTime, txtTicketCode, txtTotalCost, txtStatus, txtCreatedAt, txtCompanyName, txtCompanyPhone;
    private TextView txtCompanyAddress, txtDriverMajorName, txtDriverMajorPhone, txtDriverMinorName, txtDriverMinorPhone, txtLicensePlateBus;
    private ImageView imgCallCompany, imgCallDriverMajor, imgCallDriverMinor, imgMapBusFloor1, imgMapBusFloor2;
    private String phone = "";
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_booking);
        setup();
        loadData();
        catchEventCall();
        ActionToolBar();
    }

    private void ActionToolBar() {
        toolbar.setTitle("Chi tiết");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


    private void catchEventCall() {
        imgCallDriverMajor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                phone = txtDriverMajorPhone.getText().toString().substring(5);
                makeCall(phone);
            }
        });
        imgCallDriverMinor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                phone = txtDriverMinorPhone.getText().toString().substring(5);
                makeCall(phone);
            }
        });
        imgCallCompany.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                phone = txtCompanyPhone.getText().toString().substring(5);
                makeCall(phone);
            }
        });
    }

    private void makeCall(String phone) {

        Intent intentCall = new Intent(Intent.ACTION_CALL);

        intentCall.setData(Uri.parse("tel:" + phone));
        if (checkSelfPermission(Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    Activity#requestPermissions
            ActivityCompat.requestPermissions(DetailBookingActivity.this, new String[]{Manifest.permission.CALL_PHONE}, 1);
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for Activity#requestPermissions for more details.
            return;
        } else {
            startActivity(intentCall);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                makeCall(phone);
            } else {
                CheckConnection.ShowToast_Short(this, "PERMISSION DENIED");
            }
        }
    }

    private void loadData() {
        Intent intent = getIntent();
        Booking booking = (Booking) intent.getSerializableExtra("detail_booking");
        txtStartEndPlace.setText("Lộ trình: " + booking.getStartPlace() + " -> " + booking.getEndPlace());
        txtStartEndTime.setText("Thời gian: " + booking.getStartTime() + " - " + booking.getEndTime());
        String ticket = "";
        for(Ticket t: booking.getTickets()){
            ticket += t.getCode().substring(3) + ",";
        }
        ticket = ticket.substring(0, ticket.lastIndexOf(","));
        txtTicketCode.setText("Số vé: " + booking.getTickets().size() + "(" + ticket + ")");
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        txtTotalCost.setText("Tổng tiền: " + decimalFormat.format(booking.getPrice()) + " Đ");
        if (booking.getStatus().equals("paying")){
            txtStatus.setText("Trạng thái: Chờ thanh toán");
            txtStatus.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.colorWarning));
        } else if (booking.getStatus().equals("paid")){
            txtStatus.setText("Trạng thái: Đã thanh toán");
            txtStatus.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.colorGreen));
        } else if (booking.getStatus().equals("canceled")) {
            txtStatus.setText("Trạng thái: Đã bị hủy");
            txtStatus.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.colorRed));
        }
        txtCreatedAt.setText("Đặt lúc: " + booking.getCreatedAt());
        txtCompanyName.setText("Hãng xe: " + booking.getCompany().getName());
        txtCompanyPhone.setText("SĐT: " + booking.getCompany().getPhone());
        txtCompanyAddress.setText("Địa chỉ: " + booking.getCompany().getAddress());
        for(Employee e: booking.getEmployees()){
            if (e.getRole().equals("major")){
                txtDriverMajorName.setText("Lái xe chính: " + e.getName());
                txtDriverMajorPhone.setText("SĐT: " + e.getPhone());
            } else if (e.getRole().equals("minor")){
                txtDriverMinorName.setText("Lái xe phụ: " + e.getName());
                txtDriverMinorPhone.setText("SĐT: " + e.getPhone());
            }
        }
        txtLicensePlateBus.setText("Biển số xe: " + booking.getBusLicensePlate());
        if (booking.getBusSeats() == 46){
            imgMapBusFloor1.setImageResource(R.drawable.floor_1_46);
            imgMapBusFloor2.setImageResource(R.drawable.floor_2_46);
        } else if (booking.getBusSeats() == 36 ){
            imgMapBusFloor1.setImageResource(R.drawable.floor_1_36);
            imgMapBusFloor2.setImageResource(R.drawable.floor_2_36);
        }

    }

    private void setup() {
        toolbar = findViewById(R.id.toolbar_detail_booking);
        txtStartEndPlace = findViewById(R.id.text_view_start_end_place_detail);
        txtStartEndTime = findViewById(R.id.text_view_start_end_time_detail);
        txtTicketCode = findViewById(R.id.text_view_ticket_detail);
        txtTotalCost = findViewById(R.id.text_view_price_detail);
        txtStatus = findViewById(R.id.text_view_status_detail);
        txtCreatedAt = findViewById(R.id.text_view_created_at_detail);
        txtCompanyName = findViewById(R.id.text_view_company_name_detail);
        txtCompanyAddress = findViewById(R.id.text_view_company_address_detail);
        txtCompanyPhone = findViewById(R.id.text_view_company_phone_detail);
        txtDriverMajorName = findViewById(R.id.text_view_driver_major_name);
        txtDriverMajorPhone = findViewById(R.id.text_view_driver_major_phone);
        txtDriverMinorName = findViewById(R.id.text_view_driver_minor_name);
        txtDriverMinorPhone = findViewById(R.id.text_view_driver_minor_phone);
        imgCallCompany = findViewById(R.id.call_company);
        imgCallDriverMajor = findViewById(R.id.icon_call_major);
        imgCallDriverMinor = findViewById(R.id.icon_call_minor);
        txtLicensePlateBus = findViewById(R.id.text_view_license_plate_bus_detail);
        imgMapBusFloor1 = findViewById(R.id.image_map_bus_floor_1);
        imgMapBusFloor2 = findViewById(R.id.image_map_bus_floor_2);
    }

    public static Intent getDetailBookingActivityIntent(Context context, Serializable detailBooking){
        Intent intent = new Intent(context, DetailBookingActivity.class);
        intent.putExtra("detail_booking", detailBooking);
        return intent;
    }
}
