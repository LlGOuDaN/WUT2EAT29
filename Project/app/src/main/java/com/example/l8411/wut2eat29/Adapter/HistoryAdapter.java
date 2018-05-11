package com.example.l8411.wut2eat29.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.l8411.wut2eat29.Model.History;
import com.example.l8411.wut2eat29.R;
import com.example.l8411.wut2eat29.Utils.utils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {
    List<HashMap> historyList;
    DatabaseReference mRef;
    FirebaseAuth mAuth;

    public HistoryAdapter(List<HashMap> historyList) {
        this.historyList = historyList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.histroy_list_view, parent,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        HashMap restaurant = (HashMap) historyList.get(position).get("resturant");
        String name = (String) restaurant.get("name");
        holder.ResName.setText(name);
        holder.DateText.setText((String) historyList.get(position).get("dateFormated"));
    }

    @Override
    public int getItemCount() {
        return historyList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView ResName;
        private TextView DateText;

        public ViewHolder(View itemView) {
            super(itemView);
            ResName = itemView.findViewById(R.id.resName_view);
            DateText = itemView.findViewById(R.id.date_view);
        }
    }
}
