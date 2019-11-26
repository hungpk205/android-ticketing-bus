package com.hungpk.ticket.data.remote;

import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.hungpk.ticket.model.Booking;
import com.hungpk.ticket.model.Customer;
import com.hungpk.ticket.model.Trip;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface APIService {
    @GET("/api/trips")
    Call<ArrayList<Trip>> GetAllTrips();

    @POST("/api/history-bookings")
    Call<ArrayList<Booking>> GetAllBookings(@Body Customer customer);
}
