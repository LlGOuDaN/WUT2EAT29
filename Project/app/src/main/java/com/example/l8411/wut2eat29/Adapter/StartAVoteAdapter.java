package com.example.l8411.wut2eat29.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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

    public List<String> getmVoteList() {
        return mVoteList;
    }

    private List<String> mVoteList;
    public StartAVoteAdapter(List<DataSnapshot> mStartVotes) {
        this.mStartVotes = mStartVotes;
        mRef = FirebaseDatabase.getInstance().getReference();
        mVoteList = new ArrayList<String>();
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
        CheckBox mCheckbox;
        public ViewHolder(View view){
            super(view);

            nameTextView = (TextView) view.findViewById(R.id.Sname_view);
            mCheckbox = (CheckBox) view.findViewById(R.id.checkBox);
            mCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                    if(isChecked){
                        if(!mVoteList.contains(nameTextView.getText().toString())){
                            mVoteList.add(nameTextView.getText().toString());
                        }
                    }else{
                        if(mVoteList.contains(nameTextView.getText().toString())){
                            mVoteList.remove(nameTextView.getText().toString());
                        }
                    }
                }
            });
        }
    }
}
