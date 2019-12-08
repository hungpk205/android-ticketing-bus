package com.hungpk.ticket.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Employee implements Serializable {
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("phone")
    @Expose
    public String phone;
    @SerializedName("role")
    @Expose
    public String role;

    public Employee(String name, String phone, String role) {
        this.name = name;
        this.phone = phone;
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
