package com.hungpk.ticket.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hungpk.ticket.R;
import com.hungpk.ticket.model.Menu;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MenuAdapter extends BaseAdapter {
    ArrayList<Menu> arrayListMenu;
    Context context;

    public MenuAdapter(ArrayList<Menu> arrayListMenu, Context context) {
        this.arrayListMenu = arrayListMenu;
        this.context = context;
    }

    @Override
    public int getCount() {
        return arrayListMenu.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayListMenu.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public  class ViewHolder {
        TextView txtMenu;
        ImageView imgMenu;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (view == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_menu, null);
            viewHolder.txtMenu = view.findViewById(R.id.text_view_trip);
            viewHolder.imgMenu = view.findViewById(R.id.image_trip);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        Menu menu = (Menu) getItem(i);
        viewHolder.txtMenu.setText(menu.getText());
        Picasso.get().load(menu.getImg())
                .placeholder(R.drawable.noimage)
                .error(R.drawable.error)
                .into(viewHolder.imgMenu);

        return view;
    }
}
