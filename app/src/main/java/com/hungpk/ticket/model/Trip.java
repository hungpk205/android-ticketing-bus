package com.hungpk.ticket.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Trip {
    @SerializedName("trip_id")
    @Expose
    private Integer tripId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("start_time")
    @Expose
    private String startTime;
    @SerializedName("start_place")
    @Expose
    private String startPlace;
    @SerializedName("end_place")
    @Expose
    private String endPlace;
    @SerializedName("driver_major")
    @Expose
    private String driverMajor;
    @SerializedName("driver_minor")
    @Expose
    private String driverMinor;
    @SerializedName("price")
    @Expose
    private Integer price;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("bus_license_plate")
    @Expose
    private String busLicensePlate;
    @SerializedName("tickets")
    @Expose
    private ArrayList<Ticket> tickets = null;

    public Trip(Integer tripId, String name, String startTime, String startPlace, String endPlace, String driverMajor, String driverMinor, Integer price, String status, String busLicensePlate, ArrayList<Ticket> tickets) {
        this.tripId = tripId;
        this.name = name;
        this.startTime = startTime;
        this.startPlace = startPlace;
        this.endPlace = endPlace;
        this.driverMajor = driverMajor;
        this.driverMinor = driverMinor;
        this.price = price;
        this.status = status;
        this.busLicensePlate = busLicensePlate;
        this.tickets = tickets;
    }

    public Integer getTripId() {
        return tripId;
    }

    public void setTripId(Integer tripId) {
        this.tripId = tripId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getDriverMajor() {
        return driverMajor;
    }

    public void setDriverMajor(String driverMajor) {
        this.driverMajor = driverMajor;
    }

    public String getDriverMinor() {
        return driverMinor;
    }

    public void setDriverMinor(String driverMinor) {
        this.driverMinor = driverMinor;
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

    public ArrayList<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(ArrayList<Ticket> tickets) {
        this.tickets = tickets;
    }
}
