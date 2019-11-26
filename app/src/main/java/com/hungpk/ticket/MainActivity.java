package com.hungpk.ticket;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ViewFlipper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;
import com.hungpk.ticket.activity.BookingActivity;
import com.hungpk.ticket.activity.InformationActivity;
import com.hungpk.ticket.adapter.MenuAdapter;
import com.hungpk.ticket.adapter.TripAdapter;
import com.hungpk.ticket.data.remote.APIService;
import com.hungpk.ticket.data.remote.RetrofitClient;
import com.hungpk.ticket.model.Menu;
import com.hungpk.ticket.model.Trip;
import com.hungpk.ticket.util.CheckConnection;
import com.hungpk.ticket.util.Constant;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    ViewFlipper viewFlipper;
    RecyclerView recyclerView;
    NavigationView navigationView;
    ListView listViewHome;
    DrawerLayout drawerLayout;
    ArrayList<Menu> listMenu;
    ArrayList<String> listSlide;
    MenuAdapter menuAdapter;
    private APIService mApiService;
    ArrayList<Trip> tripList;
    private TripAdapter tripAdapter;
    public SharedPreferences infoCustomer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setup();
//        Check connection
        if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
            ActionViewFlipper();
            ActionBar();
            GetDataTrips();
            CatchOnClickMenu();
        } else {
            CheckConnection.ShowToast_Short(getApplicationContext(), "Vui lòng kiểm tra kết nối mạng");
            finish();
        }
    }

    private void CatchOnClickMenu() {
        infoCustomer = this.getSharedPreferences(Constant.SHARED_NAME, Context.MODE_PRIVATE);
        listViewHome.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0:
                        if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
                            Intent intent = new Intent(MainActivity.this, MainActivity.class);
                            startActivity(intent);
                        } else {
                            CheckConnection.ShowToast_Short(getApplicationContext(), "Bạn hãy kiểm tra lại kết nối");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 1:
                        if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
                            if (infoCustomer.getString(Constant.SHARED_FULL_NAME,"").isEmpty()) {
                                Intent intent = new Intent(MainActivity.this, InformationActivity.class);
                                startActivity(intent);
                            } else {
                                startActivity(BookingActivity.getBookingActivityIntent(getApplicationContext(), infoCustomer.getString(Constant.SHARED_SDT,""), infoCustomer.getString(Constant.SHARED_FULL_NAME, "")));
                            }
                        } else {
                            CheckConnection.ShowToast_Short(getApplicationContext(), "Bạn hãy kiểm tra lại kết nối");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                }
            }
        });
    }

    private void GetDataTrips() {
        mApiService = RetrofitClient.getClient().create(APIService.class);
        Call<ArrayList<Trip>> call = mApiService.GetAllTrips();
        call.enqueue(new Callback<ArrayList<Trip>>() {
            @Override
            public void onResponse(Call<ArrayList<Trip>> call, Response<ArrayList<Trip>> response) {
                tripList = response.body();
                tripAdapter.setListTrip(tripList);
            }

            @Override
            public void onFailure(Call<ArrayList<Trip>> call, Throwable t) {
                Log.d("ERROR:", "Response=" + t.toString());
            }
        });
    }

    private void ActionViewFlipper() {
        listSlide = new ArrayList<>();
        listSlide.add("https://i.pinimg.com/564x/39/2d/25/392d2562cc0e8b17248462fe429cd12b.jpg");
        listSlide.add("https://i.pinimg.com/236x/1c/e6/a3/1ce6a34f55d5898c763c7c24b5ccd155.jpg");
        listSlide.add("https://i.pinimg.com/564x/f6/43/e0/f643e069db90b3ac9d307ed59669b1a2.jpg");
        for (int j = 0; j < listSlide.size(); j++) {
            ImageView imageView = new ImageView(getApplicationContext());
            Picasso.get().load(listSlide.get(j)).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(imageView);
        }
        viewFlipper.setFlipInterval(5000);
        viewFlipper.setAutoStart(true);
        Animation animation_slide_in = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_in_night);
        Animation animation_slide_out = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_out_night);
        viewFlipper.setInAnimation(animation_slide_in);
        viewFlipper.setOutAnimation(animation_slide_out);
    }


    private void ActionBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

    private void setup() {
        toolbar = (Toolbar) findViewById(R.id.toolbar_home);
        viewFlipper = (ViewFlipper) findViewById(R.id.view_flipper);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_home);
        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        listViewHome = (ListView) findViewById(R.id.list_view_navigation);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        listMenu = new ArrayList<>();
        menuAdapter = new MenuAdapter(listMenu, getApplicationContext());
        listViewHome.setAdapter(menuAdapter);

        listMenu.add(0, new Menu("Trang chủ", R.drawable.home));
        listMenu.add(1, new Menu("Lịch sử đặt vé", R.drawable.history));

        tripList = new ArrayList<>();
        tripAdapter = new TripAdapter(getApplicationContext(), tripList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 1));
        recyclerView.setAdapter(tripAdapter);

    }
}
