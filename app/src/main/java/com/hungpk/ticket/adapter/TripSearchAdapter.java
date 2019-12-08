package com.hungpk.ticket.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hungpk.ticket.R;
import com.hungpk.ticket.model.Ticket;
import com.hungpk.ticket.model.Trip;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class TripSearchAdapter extends BaseAdapter {
    Context context;
    ArrayList<Trip> listTripSearch;

    public TripSearchAdapter(Context context, ArrayList<Trip> listTripSearch) {
        this.context = context;
        this.listTripSearch = listTripSearch;
    }

    @Override
    public int getCount() {
        return listTripSearch.size();
    }

    @Override
    public Object getItem(int i) {
        return listTripSearch.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public class ViewHolder{
        public TextView txtStartPlace, txtEndPlace, txtStartTime, txtPrice, txtSlot;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (view == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_trip_search, null);
            viewHolder.txtStartPlace = view.findViewById(R.id.text_view_start_place_search);
            viewHolder.txtEndPlace = view.findViewById(R.id.text_view_end_place_search);
            viewHolder.txtStartTime = view.findViewById(R.id.text_view_start_time_search);
            viewHolder.txtPrice = view.findViewById(R.id.text_view_price_search);
            viewHolder.txtSlot = view.findViewById(R.id.text_view_slot_search);

            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        Trip trip = (Trip) getItem(i);
        viewHolder.txtStartPlace.setText("Điểm đi: " + trip.getStartPlace());
        viewHolder.txtEndPlace.setText("Điểm đến: " + trip.getEndPlace());
        viewHolder.txtStartTime.setText("Giờ chạy: " + trip.getStartTime());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.txtPrice.setText("Giá: " + decimalFormat.format(trip.getPrice()) + " Đ");
        int count = 0;
        ArrayList<Ticket> listTicket = trip.getTickets();
        for (Ticket ticket: listTicket){
            if (ticket.getStatus().equals("empty")){
                count++;
            }
        }
        viewHolder.txtSlot.setText("Còn lại: " + String.valueOf(count) + " vé trên tổng " +  trip.getTickets().size() + " vé");
        return view;
    }


}
