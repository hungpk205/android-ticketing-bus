package com.hungpk.ticket.data.remote;

import com.hungpk.ticket.model.Booking;
import com.hungpk.ticket.model.Customer;
import com.hungpk.ticket.model.Trip;
import com.hungpk.ticket.request.SearchTrip;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface APIService {
    @GET("/api/trips")
    Call<ArrayList<Trip>> GetAllTrips();

    @POST("/api/history-bookings")
    Call<ArrayList<Booking>> GetAllBookings(@Body Customer customer);

    @POST("/api/trips/search")
    Call<ArrayList<Trip>> SearchTrips(@Body SearchTrip searchTrip);
}
