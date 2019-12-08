package com.hungpk.ticket.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.hungpk.ticket.R;
import com.hungpk.ticket.adapter.TicketAdapter;
import com.hungpk.ticket.data.remote.APIService;
import com.hungpk.ticket.data.remote.RetrofitClient;
import com.hungpk.ticket.model.Ticket;
import com.hungpk.ticket.model.Trip;
import com.hungpk.ticket.util.CheckConnection;


import java.text.DecimalFormat;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailTripActivity extends AppCompatActivity implements TicketAdapter.OnClickTicketListener {
    private TextView txtStartEndPlace, txtStartEndTime, txtPrice, txtSeat, txtListSeatSelected, txtDetailRoute, txtDetailRouteFixed, txtMapSeat, txtMapSeatFloor1, txtMapSeatFloor2;
    private TextView txtBus, txtNameCompany, txtPhoneCompany, txtAddressCompany;
    private Button btnTicketing;
    private ImageView imgFloor1, imgFloor2, imgCallCompany;
    private TicketAdapter ticketAdapter;
    private RecyclerView recyclerViewTicket;
    private Toolbar toolbarDetailTrip;
    private ArrayList<String> listTicketSelect;
    private ArrayList<Ticket> listTicket;
    private Trip trip;
    private String trip_id_booking;
    private APIService mApiService;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_trip);
        setup();
        ActionToolBar();
        getDataDetailTrip();
        ButtonTicketing();
        CallCompany();
    }

    private void CallCompany() {
        imgCallCompany.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_CALL);
                String phone = txtPhoneCompany.getText().toString().substring(5);
                intent.setData(Uri.parse("tel:" + phone));
                if (checkSelfPermission(Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    Activity#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for Activity#requestPermissions for more details.
                    ActivityCompat.requestPermissions(DetailTripActivity.this, new String[]{Manifest.permission.CALL_PHONE}, 1);
                    return;
                }
                startActivity(intent);
            }
        });
    }

    private void ActionToolBar() {
        toolbarDetailTrip.setTitle("Chi tiết chuyến đi");
        setSupportActionBar(toolbarDetailTrip);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarDetailTrip.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void ButtonTicketing() {
        btnTicketing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listTicketSelect.isEmpty()) {
                    CheckConnection.ShowToast_Short(getApplicationContext(), "Bạn chưa chọn vé nào");
                } else {
                    startActivity(TicketBookingActivity.getTicketBookingActivityIntent(getApplicationContext(),trip_id_booking, trip.getStartPlace(), trip.getEndPlace(), trip.getStartTime(), listTicketSelect));
                }
            }
        });
    }


    private void getDataDetailTrip() {
        Intent intent = getIntent();
        final String trip_id = intent.getStringExtra("trip_id");
        Log.d("trip_id", trip_id);
        mApiService = RetrofitClient.getClient().create(APIService.class);
        Call<Trip> call = mApiService.GetTrip(trip_id);
        call.enqueue(new Callback<Trip>() {
            @Override
            public void onResponse(Call<Trip> call, Response<Trip> response) {
                progressDialog.dismiss();
                txtSeat.setVisibility(View.VISIBLE);
                txtDetailRouteFixed.setVisibility(View.VISIBLE);
                btnTicketing.setVisibility(View.VISIBLE);
                assert response.body() != null;
                trip = response.body();
                txtStartEndPlace.setText("Lộ trình: " + trip.getStartPlace() + " - " + trip.getEndPlace());
                txtStartEndTime.setText("Thời gian: " + trip.getStartTime() + " - " + trip.getEndTime());
                txtDetailRoute.setText(Html.fromHtml(trip.getDetailRoute()).toString());
                trip_id_booking = trip_id;
                DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                txtPrice.setText("Giá vé: " + decimalFormat.format(trip.getPrice()) + " Đ");
                txtMapSeat.setText("Sơ đồ ghế");
                txtMapSeatFloor1.setText("Tầng 1");
                txtMapSeatFloor2.setText("Tầng 2");
                txtBus.setText("Xe: " + trip.getBusLicensePlate());
                txtNameCompany.setText("Hãng xe: " + trip.getNameCompany());
                txtPhoneCompany.setText("SĐT: " + trip.getPhoneCompany());
                txtAddressCompany.setText("Địa chỉ: " + trip.getAddressCompany());
                imgCallCompany.setVisibility(View.VISIBLE);
                if (trip.getTickets().size() == 36){
                    imgFloor1.setImageResource(R.drawable.floor_1_36);
                    imgFloor2.setImageResource(R.drawable.floor_2_36);
                } else if (trip.getTickets().size() == 46) {
                    imgFloor1.setImageResource(R.drawable.floor_1_46);
                    imgFloor2.setImageResource(R.drawable.floor_2_46);
                }
                listTicket.clear();
                listTicket.addAll(trip.getTickets());
                ticketAdapter.setListTicket(listTicket);
            }

            @Override
            public void onFailure(Call<Trip> call, Throwable t) {
                Log.d("error-detail-trip", t.toString());
            }
        });

    }

    private void setup() {
        progressDialog = new ProgressDialog(DetailTripActivity.this);
        progressDialog.show();
        toolbarDetailTrip = findViewById(R.id.toolbar_detail_trip);
        txtStartEndPlace = findViewById(R.id.text_view_detail_start_to_end_place);
        txtStartEndTime = findViewById(R.id.text_view_detail_start_to_end_time);
        txtPrice = findViewById(R.id.text_view_detail_price);
        txtSeat = findViewById(R.id.text_view_list_seat_selected);
        txtDetailRouteFixed = findViewById(R.id.text_view_detail_route_fixed);
        txtDetailRoute = findViewById(R.id.text_view_detail_route);
        txtListSeatSelected = findViewById(R.id.list_ticket_selected);
        btnTicketing = findViewById(R.id.btn_submit);
        recyclerViewTicket = findViewById(R.id.recycler_view_ticket_floor_1);
        txtMapSeat = findViewById(R.id.text_view_map_seat);
        txtMapSeatFloor1 = findViewById(R.id.text_view_map_seat_floor_1);
        txtMapSeatFloor2 = findViewById(R.id.text_view_map_seat_floor_2);
        imgFloor1 = findViewById(R.id.image_map_bus_1);
        imgFloor2 = findViewById(R.id.image_map_bus_2);
        imgCallCompany = findViewById(R.id.image_detail_call_company);
        txtBus = findViewById(R.id.text_view_detail_bus);
        txtNameCompany = findViewById(R.id.text_view_detail_name_company);
        txtPhoneCompany = findViewById(R.id.text_view_detail_phone_company);
        txtAddressCompany = findViewById(R.id.text_view_detail_address_company);

        listTicket = new ArrayList<>();
        listTicketSelect = new ArrayList<>();

        ticketAdapter = new TicketAdapter(getApplicationContext(), listTicket, this);
        recyclerViewTicket.setHasFixedSize(true);
        recyclerViewTicket.setLayoutManager(new GridLayoutManager(getApplicationContext(),6));
        recyclerViewTicket.setAdapter(ticketAdapter);

    }

    @Override
    public void onClickTicket(int position) {
        Ticket ticket = listTicket.get(position);
        Log.d("ticket-code", listTicket.get(position).getCode());
        View v = recyclerViewTicket.getChildAt(position);
        ImageView img = v.findViewById(R.id.img_seat);
        String tag = (String) img.getTag();
        if (tag.equals("booked")){
            CheckConnection.ShowToast_Short(getApplicationContext(),"Ghế này đã được đặt");
        }
        if (tag.equals("selected")){
            img.setImageResource(R.drawable.seat_normal_24dp);
            img.setTag("normal");
            listTicketSelect.remove(ticket.getCode());
            txtListSeatSelected.setText(listTicketSelect.toString());
        }
        if (tag.equals("normal")){
            img.setImageResource(R.drawable.seat_selected_24dp);
            img.setTag("selected");
            listTicketSelect.add(ticket.getCode());
            txtListSeatSelected.setText(listTicketSelect.toString());
        }

    }
}
