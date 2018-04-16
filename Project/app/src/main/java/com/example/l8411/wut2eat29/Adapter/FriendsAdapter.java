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

public class FriendsAdapter extends RecyclerView.Adapter<FriendsAdapter.ViewHolder> {
    private Context mContext;
    private RecyclerView mRecyclerView;
    final ArrayList<String> mNames = new ArrayList<>();
    final ArrayList<String> mPlaces = new ArrayList<>();
    private Random mRandom = new Random();
    public FriendsAdapter(Context context, RecyclerView RV) {
        mRecyclerView = RV;
        mContext = context;
        for (int i = 0; i < 10; i++) {
            mNames.add(getRandomName());
            mPlaces.add(getRandomPlaces());
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

    private String getRandomPlaces() {
        String[] places = new String[]{
                "McDonald", "Burger King", "Chauncey", "Texas RoadHouse", "Zeng",
                "Umi", "Tokyo", "Panda Garden", "Cafeteria", "Wendy",
                "Buffalo Wild Wings", "LongHorn Steak House", "Arby", "Fifi's", "Royal Mandrin",
                "Rickey's", "Square Donuts", "Chavas", "Pizza King", "J Gumbo's"
        };
        return places[mRandom.nextInt(places.length)];
    }





    @Override
    public FriendsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.friend_name_view,parent,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(FriendsAdapter.ViewHolder holder, int position) {
        String name = mNames.get(position);
        String place = mPlaces.get(position);
        holder.nameTextView.setText(name);
        holder.placeTextView.setText(place);
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
        TextView placeTextView;
        public ViewHolder(View view){
            super(view);
            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    deleteName(getAdapterPosition());
                    return false;
                }
            });
            nameTextView = (TextView) view.findViewById(R.id.name_view);
            placeTextView = (TextView) view.findViewById(R.id.place_view);
        }
    }
}
