package com.hungpk.ticket.adapter;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hungpk.ticket.R;
import com.hungpk.ticket.model.Ticket;

import java.util.ArrayList;

public class TicketAdapter extends RecyclerView.Adapter<TicketAdapter.ItemHolder> {
    private Context context;
    private ArrayList<Ticket> listTicket;
    private OnClickTicketListener mOnClickTicketListener;

    public TicketAdapter(Context context, ArrayList<Ticket> listTicket, OnClickTicketListener onClickTicketListener) {
        this.context = context;
        this.listTicket = listTicket;
        this.mOnClickTicketListener = onClickTicketListener;
    }

    public void setListTicket(ArrayList<Ticket> listTicket) {
        this.listTicket = listTicket;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_ticket,parent,false);
        return new ItemHolder(view, mOnClickTicketListener );
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        Ticket ticket = listTicket.get(position);
        holder.txtNameSeat.setText(ticket.getCode().substring(3));
        if (ticket.getStatus().equals("empty")){
            holder.imgSeat.setImageResource(R.drawable.seat_normal_24dp);
            holder.imgSeat.setTag("normal");
        } else {
            if (ticket.getStatus().equals("booked")){
                holder.imgSeat.setImageResource(R.drawable.seat_booked_24dp);
                holder.imgSeat.setTag("booked");
            }
        }
    }

    @Override
    public int getItemCount() {
        return listTicket.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txtNameSeat;
        ImageView imgSeat;

        OnClickTicketListener onClickTicketListener;

        public ItemHolder(@NonNull View itemView, OnClickTicketListener onClickTicketLisener) {
            super(itemView);
            this.imgSeat = itemView.findViewById(R.id.img_seat);
            this.txtNameSeat = itemView.findViewById(R.id.text_view_name_seat);
            this.onClickTicketListener = onClickTicketLisener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onClickTicketListener.onClickTicket(getAdapterPosition());
        }
    }
    public interface OnClickTicketListener{
        void onClickTicket(int position);
    }
}
