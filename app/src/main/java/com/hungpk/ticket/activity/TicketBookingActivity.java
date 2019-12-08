package com.hungpk.ticket.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.hungpk.ticket.R;
import com.hungpk.ticket.data.remote.APIService;
import com.hungpk.ticket.data.remote.RetrofitClient;
import com.hungpk.ticket.request.TicketBooking;
import com.hungpk.ticket.response.BookingResponse;
import com.hungpk.ticket.util.Constant;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TicketBookingActivity extends AppCompatActivity {
    TextView txtStartEndPlace, txtStartTime, txtListTicket;
    EditText editTextFullname, editTextPhone;
    Button btnConfirm, btnCancel;
    private APIService mAPIService;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_booking);
        setup();
        loadData();
        bookingTicket();
        CancelBookingTicket();
    }

    private void CancelBookingTicket() {
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void bookingTicket() {
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog = new ProgressDialog(TicketBookingActivity.this);
                progressDialog.show();

                mAPIService = RetrofitClient.getClient().create(APIService.class);
                Intent intent = getIntent();
                int trip_id = Integer.valueOf(intent.getStringExtra("trip_id"));
                String fullname = editTextFullname.getText().toString();
                String phone = editTextPhone.getText().toString();

                // Save infoCustomer
                SharedPreferences infoCustomer = getSharedPreferences(Constant.SHARED_CUSTOMER, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = infoCustomer.edit();
                editor.putString(Constant.SHARED_SDT, phone);
                editor.putString(Constant.SHARED_FULL_NAME, fullname);
                editor.commit();

                ArrayList<String> listTickets = intent.getStringArrayListExtra("list_ticket");
                TicketBooking obj = new TicketBooking(trip_id, fullname, phone, listTickets);
                Call<BookingResponse> call = mAPIService.BookingTickets(obj);
                call.enqueue(new Callback<BookingResponse>() {
                    @Override
                    public void onResponse(Call<BookingResponse> call, Response<BookingResponse> response) {
                        BookingResponse obj = response.body();
                        String status = "error";
                        if (obj.getSuccess()){
                            status = "success";
                        }
                        startActivity(ResponseBookingActivity.getResponseBookingActivityIntent(getApplicationContext(),status));
                    }

                    @Override
                    public void onFailure(Call<BookingResponse> call, Throwable t) {

                    }
                });
            }
        });
    }

    private void loadData() {
        SharedPreferences inforCustomer = getSharedPreferences(Constant.SHARED_CUSTOMER, Context.MODE_PRIVATE);
        String name = inforCustomer.getString(Constant.SHARED_FULL_NAME,"");
        String phone = inforCustomer.getString(Constant.SHARED_SDT,"");
        if (!name.equals("") && !phone.equals("")){
            editTextFullname.setText(name);
            editTextPhone.setText(phone);
        }
        Intent intent = getIntent();
        txtStartEndPlace.setText(String.format("%s - %s", intent.getStringExtra("start_place"), intent.getStringExtra("end_place")));
        txtStartTime.setText(String.format("%s", intent.getStringExtra("start_time")));
        txtListTicket.setText(String.format("%s", intent.getStringArrayListExtra("list_ticket")));
    }

    private void setup() {
        txtStartEndPlace = findViewById(R.id.text_view_start_end_place);
        txtStartTime = findViewById(R.id.text_view_time);
        txtListTicket = findViewById(R.id.text_view_list_ticket_selected);
        editTextFullname = findViewById(R.id.edit_text_fullname_booking);
        editTextPhone = findViewById(R.id.edit_text_phone_booking);
        btnConfirm = findViewById(R.id.button_submit_booking);
        btnCancel = findViewById(R.id.button_cancel_booking);
    }

    public static Intent getTicketBookingActivityIntent(Context context, String trip_id, String start_place, String end_place, String start_time, ArrayList<String> listTicket){
        Intent intent = new Intent(context, TicketBookingActivity.class);
        intent.putExtra("trip_id", trip_id);
        intent.putExtra("start_time", start_time);
        intent.putExtra("start_place", start_place);
        intent.putExtra("end_place", end_place);
        intent.putExtra("list_ticket", listTicket);
        return intent;
    }
}
