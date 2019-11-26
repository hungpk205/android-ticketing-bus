package com.hungpk.ticket.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hungpk.ticket.R;
import com.hungpk.ticket.model.Booking;
import com.hungpk.ticket.util.Constant;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class BookingAdapter extends BaseAdapter {
    Context context;
    ArrayList<Booking> listBooking;

    public BookingAdapter(Context context, ArrayList<Booking> listBooking) {
        this.context = context;
        this.listBooking = listBooking;
    }

    @Override
    public int getCount() {
        return listBooking.size();
    }

    @Override
    public Object getItem(int i) {
        return listBooking.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public class ViewHolder {
        public TextView txtStartPlace, txtEndPlace, txtStartTime, txtPrice, txtStatus, txtCreateAt;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (view == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_booking, null);
            viewHolder.txtStartPlace = (TextView) view.findViewById(R.id.text_view_start_place);
            viewHolder.txtEndPlace = (TextView) view.findViewById(R.id.text_view_end_place);
            viewHolder.txtStartTime = (TextView) view.findViewById(R.id.text_view_start_time);
            viewHolder.txtPrice = (TextView) view.findViewById(R.id.text_view_price);
            viewHolder.txtStatus = (TextView) view.findViewById(R.id.text_view_status);
            viewHolder.txtCreateAt = (TextView) view.findViewById(R.id.text_view_created_at);

            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        Booking booking = (Booking) getItem(i);
        viewHolder.txtStartPlace.setText(booking.getStartPlace());
        viewHolder.txtEndPlace.setText(booking.getEndPlace());
        viewHolder.txtStartTime.setText(booking.getStartTime());
        viewHolder.txtPrice.setText(booking.getPrice());
        viewHolder.txtStatus.setText(booking.getStatus());
        viewHolder.txtCreateAt.setText(booking.getCreatedAt());

        return  view;
    }
}
