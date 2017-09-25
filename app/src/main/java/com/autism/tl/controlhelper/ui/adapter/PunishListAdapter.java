package com.autism.tl.controlhelper.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.autism.tl.controlhelper.R;
import com.autism.tl.controlhelper.model.PunishInfo;

import java.util.List;

/**
 * Created by 唐亮 on 2017/8/19.
 */

public class PunishListAdapter extends RecyclerView.Adapter<PunishListAdapter.ViewHolder> {

    private List<PunishInfo> punishList;

    public class ViewHolder extends RecyclerView.ViewHolder {
        View punishInfoView;
        TextView name;
        TextView person;
        TextView date;

        public ViewHolder(View view) {
            super(view);
            punishInfoView = view;
            name = (TextView) view.findViewById(R.id.punish_name);
            person = (TextView) view.findViewById(R.id.punish_person);
            date = (TextView) view.findViewById(R.id.punish_date);
        }
    }

    public PunishListAdapter(List<PunishInfo> punishList) {
        this.punishList = punishList;
    }

    @Override
    public PunishListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_punishlist, parent, false);
        final PunishListAdapter.ViewHolder holder = new PunishListAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(PunishListAdapter.ViewHolder holder, int position) {
        PunishInfo punishInfo = punishList.get(position);
        holder.name.setText(punishInfo.getName());
        holder.person.setText(punishInfo.getPerson());
        holder.date.setText(punishInfo.getDate());
    }

    @Override
    public int getItemCount() {
        return punishList.size();
    }
}
