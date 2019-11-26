package com.hungpk.ticket.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class CompanyList {
    @SerializedName("company")
    @Expose
    private ArrayList<Company> company = null;

    public ArrayList<Company> getCompany() {
        return company;
    }

    public void setCompany(ArrayList<Company> company) {
        this.company = company;
    }
}
