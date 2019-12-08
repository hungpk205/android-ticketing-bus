package com.hungpk.ticket.model;

import android.content.Intent;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class Booking implements Serializable {
    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("trip_id")
    @Expose
    public Integer tripId;
    @SerializedName("start_time")
    @Expose
    public String startTime;
    @SerializedName("end_time")
    @Expose
    public String endTime;
    @SerializedName("start_place")
    @Expose
    public String startPlace;
    @SerializedName("end_place")
    @Expose
    public String endPlace;
    @SerializedName("employees")
    @Expose
    public ArrayList<Employee> employees = null;
    @SerializedName("price")
    @Expose
    public Integer price;
    @SerializedName("status")
    @Expose
    public String status;
    @SerializedName("bus_license_plate")
    @Expose
    public String busLicensePlate;
    @SerializedName("bus_seats")
    @Expose
    public Integer busSeats;
    @SerializedName("created_at")
    @Expose
    public String createdAt;
    @SerializedName("company")
    @Expose
    public Company company;
    @SerializedName("tickets")
    @Expose
    public ArrayList<Ticket> tickets = null;

    public Booking(Integer id, Integer tripId, String startTime, String endTime, String startPlace, String endPlace, ArrayList<Employee> employees, Integer price, String status, String busLicensePlate, Integer bus_seats, String createdAt, Company company, ArrayList<Ticket> tickets) {
        this.id = id;
        this.tripId = tripId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.startPlace = startPlace;
        this.endPlace = endPlace;
        this.employees = employees;
        this.price = price;
        this.status = status;
        this.busLicensePlate = busLicensePlate;
        this.busSeats = bus_seats;
        this.createdAt = createdAt;
        this.company = company;
        this.tickets = tickets;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTripId() {
        return tripId;
    }

    public void setTripId(Integer tripId) {
        this.tripId = tripId;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getStartPlace() {
        return startPlace;
    }

    public void setStartPlace(String startPlace) {
        this.startPlace = startPlace;
    }

    public String getEndPlace() {
        return endPlace;
    }

    public void setEndPlace(String endPlace) {
        this.endPlace = endPlace;
    }

    public ArrayList<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(ArrayList<Employee> employees) {
        this.employees = employees;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBusLicensePlate() {
        return busLicensePlate;
    }

    public void setBusLicensePlate(String busLicensePlate) {
        this.busLicensePlate = busLicensePlate;
    }
    public Integer getBusSeats() {
        return busSeats;
    }

    public void setBusSeats(Integer busSeats) {
        this.busSeats = busSeats;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public ArrayList<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(ArrayList<Ticket> tickets) {
        this.tickets = tickets;
    }
}


