package com.example.l8411.wut2eat29.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.l8411.wut2eat29.Model.History;
import com.example.l8411.wut2eat29.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {
    List<History> historyList;

    public HistoryAdapter() {
        this.historyList = new ArrayList<>();
        historyList.add(new History("BK", new GregorianCalendar().getTime()));
        historyList.add(new History("KK", new GregorianCalendar(2018,1,1).getTime()));
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.histroy_list_view, parent,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String format = "MM/dd/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        String name = historyList.get(position).getResturantName();
        String date = sdf.format(historyList.get(position).getDate());

        holder.ResNmae.setText(name);
        holder.DateText.setText(date);

    }

    @Override
    public int getItemCount() {
        return historyList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView ResNmae;
        private TextView DateText;

        public ViewHolder(View itemView) {
            super(itemView);
            ResNmae = itemView.findViewById(R.id.resName_view);
            DateText = itemView.findViewById(R.id.date_view);
        }
    }
}
