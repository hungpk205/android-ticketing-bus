package com.hungpk.ticket.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Booking {
    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("trip_id")
    @Expose
    public Integer tripId;
    @SerializedName("start_time")
    @Expose
    public String startTime;
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
    @SerializedName("created_at")
    @Expose
    public String createdAt;

    public Booking(Integer id, Integer tripId, String startTime, String startPlace, String endPlace, ArrayList<Employee> employees, Integer price, String status, String busLicensePlate, String createdAt) {
        this.id = id;
        this.tripId = tripId;
        this.startTime = startTime;
        this.startPlace = startPlace;
        this.endPlace = endPlace;
        this.employees = employees;
        this.price = price;
        this.status = status;
        this.busLicensePlate = busLicensePlate;
        this.createdAt = createdAt;
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

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}


