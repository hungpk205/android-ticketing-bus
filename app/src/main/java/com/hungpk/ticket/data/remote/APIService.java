package com.hungpk.ticket.data.remote;

import com.hungpk.ticket.model.Booking;
import com.hungpk.ticket.model.Customer;
import com.hungpk.ticket.model.Trip;
import com.hungpk.ticket.request.SearchTrip;
import com.hungpk.ticket.request.TicketBooking;
import com.hungpk.ticket.response.BookingResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface APIService {
    @GET("/api/trips")
    Call<ArrayList<Trip>> GetAllTrips();

    @GET("/api/trips/{id}")
    Call<Trip> GetTrip(@Path("id") String trip_id);

    @POST("/api/history-bookings")
    Call<ArrayList<Booking>> GetAllBookings(@Body Customer customer);

    @POST("/api/trips/search")
    Call<ArrayList<Trip>> SearchTrips(@Body SearchTrip searchTrip);

    @POST("/api/bookings")
    Call<BookingResponse> BookingTickets(@Body TicketBooking obj);
}
