package com.example.l8411.wut2eat29.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.l8411.wut2eat29.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by chenj11 on 3/23/2018.
 */

public class StartAVoteAdapter extends RecyclerView.Adapter<StartAVoteAdapter.ViewHolder> {
    private List<DataSnapshot> mStartVotes;
    private DatabaseReference mRef;
    public StartAVoteAdapter(List<DataSnapshot> mStartVotes) {
        this.mStartVotes = mStartVotes;
        mRef = FirebaseDatabase.getInstance().getReference();
    }



    @Override
    public StartAVoteAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.start_avote_view,parent,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final StartAVoteAdapter.ViewHolder holder, int position) {
        mRef.child("user").child(mStartVotes.get(position).getKey()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final String name = (String) dataSnapshot.child("userNickName").getValue();
                holder.nameTextView.setText(name);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return mStartVotes.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView nameTextView;

        public ViewHolder(View view){
            super(view);

            nameTextView = (TextView) view.findViewById(R.id.Sname_view);

        }
    }
}
