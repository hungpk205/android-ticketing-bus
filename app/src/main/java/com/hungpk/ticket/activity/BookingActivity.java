package com.hungpk.ticket.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.hungpk.ticket.R;
import com.hungpk.ticket.adapter.BookingAdapter;
import com.hungpk.ticket.data.remote.APIService;
import com.hungpk.ticket.data.remote.RetrofitClient;
import com.hungpk.ticket.model.Booking;
import com.hungpk.ticket.model.Customer;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookingActivity extends AppCompatActivity {
    private static final String EXTRA_PHONE = "EXTRA_PHONE";
    private static final String EXTRA_FULL_NAME = "EXTRA_FULL_NAME";

    Toolbar toolbarBooking;
    ListView listViewBooking;
    BookingAdapter bookingAdapter;
    ArrayList<Booking> listBooking;
    TextView txtNoData;
    private APIService mApiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);
        setup();
        ActionToolBar();
        GetDataBooking();

    }

    private void GetDataBooking() {
        Intent intent = getIntent();
        String phone = intent.getStringExtra(EXTRA_PHONE);
        String fullname = intent.getStringExtra(EXTRA_FULL_NAME);
        Customer customer = new Customer(fullname, phone);

        mApiService = RetrofitClient.getClient().create(APIService.class);
        Call<ArrayList<Booking>> call = mApiService.GetAllBookings(customer);

        call.enqueue(new Callback<ArrayList<Booking>>() {
            @Override
            public void onResponse(Call<ArrayList<Booking>> call, Response<ArrayList<Booking>> response) {
                listBooking.clear();
                assert response.body() != null;
                listBooking.addAll(response.body());

                Log.d("data", String.valueOf(listBooking.size()));
                bookingAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ArrayList<Booking>> call, Throwable t) {
                showNoData();
                Log.d("ERROR-GET-BOOKING:", "Response=" + t.toString());
            }
        });

    }

    private void showNoData() {
        txtNoData.setVisibility(View.VISIBLE);
//        CheckConnection.ShowToast_Short(this.getApplicationContext(), "Không có dữ liệu đặt vé");
    }

    private void ActionToolBar() {
        toolbarBooking.setTitle("Lịch sử đặt vé");
        setSupportActionBar(toolbarBooking);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarBooking.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void setup() {
        toolbarBooking = (Toolbar) findViewById(R.id.toolbar_booking);
        listViewBooking = (ListView) findViewById(R.id.list_view_booking);
        txtNoData = findViewById(R.id.textViewNoData);

        listBooking = new ArrayList<>();
        bookingAdapter = new BookingAdapter(getApplicationContext(), listBooking);

        listViewBooking.setAdapter(bookingAdapter);
    }

    public static Intent getBookingActivityIntent(Context context, String phone, String fullName) {
        Intent intent = new Intent(context, BookingActivity.class);
        intent.putExtra(EXTRA_PHONE, phone);
        intent.putExtra(EXTRA_FULL_NAME, fullName);
        return intent;
    }
}
