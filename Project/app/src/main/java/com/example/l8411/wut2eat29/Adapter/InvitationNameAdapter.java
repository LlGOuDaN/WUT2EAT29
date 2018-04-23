package com.example.l8411.wut2eat29.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.l8411.wut2eat29.R;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by chenj11 on 3/23/2018.
 */

public class InvitationNameAdapter extends RecyclerView.Adapter<InvitationNameAdapter.ViewHolder> {
    private Context mContext;
    private RecyclerView mRecyclerView;
    final ArrayList<String> mNames = new ArrayList<>();
    private Random mRandom = new Random();
    public InvitationNameAdapter(Context context, RecyclerView RV) {
        mRecyclerView = RV;
        mContext = context;
        for (int i = 0; i < 2; i++) {
            mNames.add(getRandomName());

        }
    }


    private String getRandomName() {
        String[] names = new String[]{
                "Junhao Chen", "Yifei Li", "Yilun Wu", "Xin Xiao", "Yuankai Wang",
                "Coleman Weaver", "", "Hailey", "Alexis", "Elizabeth",
                "Michael", "Jacob", "Matthew", "Nicholas", "Christopher",
                "Joseph", "Zachary", "Joshua", "Andrew", "William"
        };
        return names[mRandom.nextInt(names.length)];
    }







    @Override
    public InvitationNameAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.invitation_view,parent,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(InvitationNameAdapter.ViewHolder holder, int position) {
        String name = mNames.get(position);
        holder.nameTextView.setText(String.format("%s send you an invitation",name));
    }


    @Override
    public int getItemCount() {
        return mNames.size();
    }




    public void addName(){
        mNames.add(0,getRandomName());
        notifyItemInserted(0);
        mRecyclerView.getLayoutManager().scrollToPosition(0);


    }
    public void deleteName(int position) {
        mNames.remove(position);

        notifyItemRemoved(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView nameTextView;

        public ViewHolder(View view){
            super(view);
            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    deleteName(getAdapterPosition());
                    return false;
                }
            });
            nameTextView = (TextView) view.findViewById(R.id.Iname_view);

        }
    }
}
