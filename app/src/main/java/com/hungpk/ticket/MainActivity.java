package com.hungpk.ticket;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.ViewFlipper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;
import com.hungpk.ticket.activity.BookingActivity;
import com.hungpk.ticket.activity.InformationActivity;
import com.hungpk.ticket.activity.SearchActivity;
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
    private ProgressDialog progressDialog;
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

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        getMenuInflater().inflate(R.menu.search_trip,menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);
        menuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                if (menuItem.getItemId() == R.id.action_search){
                    Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                    startActivity(intent);
                }
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    private void CatchOnClickMenu() {
        infoCustomer = this.getSharedPreferences(Constant.SHARED_CUSTOMER, Context.MODE_PRIVATE);
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
//                            if (infoCustomer.getString(Constant.SHARED_FULL_NAME,"").isEmpty()) {
//                                Intent intent = new Intent(MainActivity.this, InformationActivity.class);
//                                startActivity(intent);
//                            } else {
                                Intent intent = new Intent(MainActivity.this, InformationActivity.class);
                                startActivity(intent);
//                                startActivity(BookingActivity.getBookingActivityIntent(getApplicationContext(), infoCustomer.getString(Constant.SHARED_SDT,""), infoCustomer.getString(Constant.SHARED_FULL_NAME, "")));
//                            }
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
                progressDialog.dismiss();

                tripList.clear();
                assert response.body() != null;
                tripList.addAll(response.body());

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
        listSlide.add("https://0euaka.dm.files.1drv.com/y4mjIxiWpazLLRwqOAAtM3ruNShyJIB1rncwwSJ0jlHWysWzWBYM7P_SPsfGqm_M8arcRx96RA-UZTTy34LNnOBl5uK8u8pg7pfTk4-ZEyojHmflUQeUVZJbPj8FedSEK4JzBCoTUUz96on6F61xRJKS20pn_Ou8LwGQioWYO4kidwDeLEwSV3ca69vOz5jk3Qb4kcJfwvnryk7292AkYvOFw");
        listSlide.add("https://nngwtq.dm.files.1drv.com/y4mteN22m_uL8BrGtNP3r-kFLcgXQDRgsmaCWkdRu7iyBmGaMsRzjXC1TGGquksdezy30NSNBmbDceyaQ5CWcw1oTUA-sx3tI0YT3heTYwsCXMPQHW4PbpUlaIJQ0NlFsHVh3CGFJDnUd0PXNA0NDDz-SJiFkRc2xWxJoki64985NP1Of2qKLXe3EJYxFybAYpNDGHCyX5AHviZQMbwlgj-yw");
        listSlide.add("https://nnevqq.dm.files.1drv.com/y4m7K0v01_hU6HbGCP1EK2FG5CUV1BjTJ0mhhhpeuEphciNO0uPtixc3TTJ67dL0CqM6moKO-N09lagRmzzd0FVzWO28nLfxQQZG_fvSS5v72Y3tiO0u8DUx9JyfRzL2lve8wsU4JTZfammkVYFVjdsyrMTQc2Y-3tA_z0JuQdHBLMZjvUeeqEjyd63AqpgKSGFfVPP_dH3fhYwQRNwHQRwtg");
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
        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.show();
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
