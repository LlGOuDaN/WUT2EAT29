package com.example.l8411.wut2eat29.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.l8411.wut2eat29.Model.Vote;
import com.example.l8411.wut2eat29.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by chenj11 on 5/7/2018.
 */

public class ViewVoteAdapter extends RecyclerView.Adapter<ViewVoteAdapter.ViewHolder>{
    private Vote mVote;
    private DatabaseReference mRef;
    private FirebaseAuth mAuth;
    private HashMap<String,Integer> mVoteDetails;
    private Object[] mVoteDetailsList;
    private String[] mVoterNameList;
    private Integer[] mVoterStatus;

    public ViewVoteAdapter(DatabaseReference Ref, FirebaseAuth mAuth) {
        this.mRef = Ref;
        this.mAuth = mAuth;
        this.mVoterNameList = new String[0];
        final DatabaseReference mVoteRef = mRef.child("user").child(mAuth.getCurrentUser().getUid()).child("voteList");
        mVoteRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map<String,Boolean> votelist = (Map<String, Boolean>) dataSnapshot.getValue();
                for(String voteid : votelist.keySet()){
                    if(votelist.get(voteid)){
                        mRef.child("votes").child(voteid).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                mVote = dataSnapshot.getValue(Vote.class);
                                mVoteDetails = mVote.getVoteDetails();
//                                mVoteDetails.put("Arthur",1);
//                                mVoteDetails.put("Goudan Li",2);
//                                mVoteDetails.put("Someone",0);
                                mVoteDetailsList = mVoteDetails.entrySet().toArray();
                                mVoterNameList = mVoteDetails.keySet().toArray(new String[0]);
                                notifyDataSetChanged();
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });

                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_vote_list_view,parent,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String name = mVoterNameList[position];
        holder.VoterName.setText(name);
    }

    @Override
    public int getItemCount() {
        return mVoterNameList.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView VoterName;
        private ImageView VoteStatus;

        public ViewHolder(View itemView) {
            super(itemView);
            VoterName = itemView.findViewById(R.id.voterName_view);
            VoteStatus = itemView.findViewById(R.id.vote_status_view);
        }
    }
}
