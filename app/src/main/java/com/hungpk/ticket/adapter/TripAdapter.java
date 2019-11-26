package com.hungpk.ticket.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hungpk.ticket.R;
import com.hungpk.ticket.model.Trip;

import java.util.ArrayList;

public class TripAdapter extends RecyclerView.Adapter<TripAdapter.ItemHolder> {
    private Context context;
    private ArrayList<Trip> listTrip;

    public TripAdapter(Context context, ArrayList<Trip> listTrip) {
        this.context = context;
        this.listTrip = listTrip;
    }
    public void setListTrip(ArrayList<Trip> listTrip) {
        this.listTrip = listTrip;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TripAdapter.ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_trip_main, parent, false);
        return new ItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TripAdapter.ItemHolder holder, int position) {
        holder.txtName.setText(listTrip.get(position).getName());
        holder.txtStartPlace.setText(listTrip.get(position).getStartPlace());
        holder.txtEndPlace.setText(listTrip.get(position).getEndPlace());
        holder.txtStartTime.setText(listTrip.get(position).getStartTime());
        holder.txtPrice.setText(String.valueOf(listTrip.get(position).getPrice()));
        holder.txtTicket.setText(String.valueOf(listTrip.get(position).getTickets().size()));
    }

    @Override
    public int getItemCount() {
        return listTrip.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder {
        TextView txtName, txtStartPlace, txtEndPlace, txtStartTime, txtPrice, txtTicket;

        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            this.txtName = itemView.findViewById(R.id.text_view_name);
            this.txtStartPlace = itemView.findViewById(R.id.text_view_start_place);
            this.txtEndPlace = itemView.findViewById(R.id.text_view_end_place);
            this.txtStartTime = itemView.findViewById(R.id.text_view_start_time);
            this.txtPrice = itemView.findViewById(R.id.text_view_price);
            this.txtTicket = itemView.findViewById(R.id.text_view_slot);
        }
    }
}
