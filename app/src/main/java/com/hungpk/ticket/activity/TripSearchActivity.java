package com.hungpk.ticket.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.hungpk.ticket.MainActivity;
import com.hungpk.ticket.R;
import com.hungpk.ticket.adapter.TripSearchAdapter;
import com.hungpk.ticket.data.remote.APIService;
import com.hungpk.ticket.data.remote.RetrofitClient;
import com.hungpk.ticket.model.Trip;
import com.hungpk.ticket.request.SearchTrip;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TripSearchActivity extends AppCompatActivity {
    Toolbar toolbar;
    ListView listViewTripSearch;
    ArrayList<Trip> listTripSearch;
    TripSearchAdapter tripSearchAdapter;
    private APIService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_search);
        setup();
        ActionToolBar();
        getDataTrip();

    }

    private void ActionToolBar() {
        toolbar.setTitle("Danh sách tìm kiếm");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void getDataTrip() {
        Intent intent = getIntent();
        String start_place = intent.getStringExtra("start_place");
        String end_place = intent.getStringExtra("end_place");
        String start_time = intent.getStringExtra("start_time");

        SearchTrip searchTrip = new SearchTrip(start_place, end_place, start_time);
        apiService = RetrofitClient.getClient().create(APIService.class);
        Call<ArrayList<Trip>> call = apiService.SearchTrips(searchTrip);
        call.enqueue(new Callback<ArrayList<Trip>>() {
            @Override
            public void onResponse(Call<ArrayList<Trip>> call, Response<ArrayList<Trip>> response) {
                listTripSearch.clear();
                assert response.body() != null;
                listTripSearch.addAll(response.body());
                Log.d("Data-trip", String.valueOf(listTripSearch.size()));
                tripSearchAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ArrayList<Trip>> call, Throwable t) {
                Log.d("Error-search", t.toString());
            }
        });

    }

    private void setup() {
        toolbar = findViewById(R.id.toolbar_search_trip);
        listViewTripSearch = findViewById(R.id.list_view_trip_search);
        listTripSearch = new ArrayList<>();
        tripSearchAdapter = new TripSearchAdapter(getApplicationContext(), listTripSearch);
        listViewTripSearch.setAdapter(tripSearchAdapter);
    }

    public static Intent getTripSearchActivityIntent(Context context, String start_place, String end_place, String start_time){
        Intent intent = new Intent(context, TripSearchActivity.class);
        intent.putExtra("start_place", start_place);
        intent.putExtra("end_place", end_place);
        intent.putExtra("start_time", start_time);
        return intent;
    }
}
