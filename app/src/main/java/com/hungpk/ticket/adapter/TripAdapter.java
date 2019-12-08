package com.hungpk.ticket.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hungpk.ticket.R;
import com.hungpk.ticket.activity.DetailTripActivity;
import com.hungpk.ticket.model.Trip;

import java.text.DecimalFormat;
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
        Trip trip = listTrip.get(position);
        holder.txtStartEndPlace.setText(trip.getStartPlace() + " -> " + trip.getEndPlace());
        holder.txtStartEndTime.setText(trip.getStartTime() + " -> " + trip.getEndTime());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.txtPrice.setText("Giá vé: "+ decimalFormat.format(listTrip.get(position).getPrice()) + " Đ");
        if (listTrip.get(position).getTickets().size() == 36){
            holder.txtTicket.setText("VIP/Limousine 36 chỗ");
        } else if (listTrip.get(position).getTickets().size() == 46) {
            holder.txtTicket.setText("Giường nằm 46 chỗ");
        }

        holder.txtBus.setText("Biển số xe: " + trip.getBusLicensePlate());
        holder.txtCompany.setText("Hãng xe " + trip.getNameCompany() + " - SĐT: " + trip.getPhoneCompany());

    }

    @Override
    public int getItemCount() {
        return listTrip.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder {
        TextView txtStartEndPlace, txtStartEndTime, txtPrice, txtTicket, txtBus, txtCompany;

        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            this.txtStartEndPlace = itemView.findViewById(R.id.text_view_start_to_end_place);
            this.txtStartEndTime = itemView.findViewById(R.id.text_view_start_to_end_time);
            this.txtPrice = itemView.findViewById(R.id.text_view_price);
            this.txtTicket = itemView.findViewById(R.id.text_view_slot);
            this.txtBus = itemView.findViewById(R.id.text_view_license_plate_bus);
            this.txtCompany = itemView.findViewById(R.id.text_view_name_company);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, DetailTripActivity.class);
                    intent.putExtra("trip_id", listTrip.get(getPosition()).getTripId().toString());
                    context.startActivity(intent);
                }
            });
        }
    }
}
