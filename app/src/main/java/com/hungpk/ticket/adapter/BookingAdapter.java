package com.hungpk.ticket.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.hungpk.ticket.R;
import com.hungpk.ticket.model.Booking;
import com.hungpk.ticket.model.Ticket;

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
        public TextView txtStartEndPlace, txtStartEndTime, txtPrice, txtStatus, txtCreateAt, txtTickets, txtCompanyInfo;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (view == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_booking, null);
            viewHolder.txtStartEndPlace = (TextView) view.findViewById(R.id.text_view_start_end_place);
            viewHolder.txtStartEndTime = (TextView) view.findViewById(R.id.text_view_start_end_time);
            viewHolder.txtPrice = (TextView) view.findViewById(R.id.text_view_price);
            viewHolder.txtTickets = view.findViewById(R.id.text_view_ticket);
            viewHolder.txtStatus = (TextView) view.findViewById(R.id.text_view_status);
            viewHolder.txtCreateAt = (TextView) view.findViewById(R.id.text_view_created_at);
            viewHolder.txtCompanyInfo = view.findViewById(R.id.text_view_company_info);


            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        Booking booking = (Booking) getItem(i);
        viewHolder.txtStartEndPlace.setText("Lộ trình: " + booking.getStartPlace() + " -> " + booking.getEndPlace());
        viewHolder.txtStartEndTime.setText("Giờ chạy: " + booking.getStartTime());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.txtPrice.setText("Tổng tiền: " + decimalFormat.format(booking.getPrice()) + " Đ");
        String textTicket = "";
        ArrayList<Ticket> listTicket = booking.getTickets();
        for (Ticket ticket: listTicket) {
            textTicket += ticket.getCode().substring(3) + ",";
        }
        textTicket = textTicket.substring(0, textTicket.lastIndexOf(","));
        viewHolder.txtTickets.setText("Số vé đặt: " + String.valueOf(booking.getTickets().size()) + "(" + textTicket + ")");
        if (booking.getStatus().equals("paying")){
            viewHolder.txtStatus.setTextColor(ContextCompat.getColor(context,R.color.colorWarning));
            viewHolder.txtStatus.setText("Trạng thái: Chờ thanh toán");
        } else if (booking.getStatus().equals("canceled")){
            viewHolder.txtStatus.setTextColor(ContextCompat.getColor(context,R.color.colorRed));
            viewHolder.txtStatus.setText("Trạng thái: Đã bị hủy");
        } else if (booking.getStatus().equals("paid")) {
            viewHolder.txtStatus.setTextColor(ContextCompat.getColor(context,R.color.colorGreen));
            viewHolder.txtStatus.setText("Trạng thái: Đã thanh toán");
        }

        viewHolder.txtCreateAt.setText("Đặt lúc: " + booking.getCreatedAt());
        viewHolder.txtCompanyInfo.setText("Hãng xe: " + booking.getCompany().getName() + " - SĐT:" + booking.getCompany().getPhone());

        return  view;
    }
}
