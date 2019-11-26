package com.hungpk.ticket.model;

public class Customer {
    private String fullname;
    private String phone;

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Customer(String fullname, String phone) {
        this.fullname = fullname;
        this.phone = phone;
    }
}
