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
    private String mVoteOwner;
    private DatabaseReference mRef;
    private FirebaseAuth mAuth;
    private HashMap<String,Integer> mVoteDetails;
    private Object[] mVoteDetailsList;
    private ArrayList<String> mVoteUIDList;
    private ArrayList<String> mVoteUIDListTwo;
    private ArrayList<String> mVoterNameList;
    private ArrayList<Integer> mVoterStatus;

    public ViewVoteAdapter(DatabaseReference Ref, FirebaseAuth mAuth, String Owner) {
        this.mVoteOwner = Owner;
        this.mRef = Ref;
        this.mAuth = mAuth;
        this.mVoterNameList = new ArrayList<>();
        this.mVoteUIDListTwo = new ArrayList<>();
        this.mVoterStatus = new ArrayList<>();
        this.mVoteUIDList = new ArrayList<>();
        final DatabaseReference mVoteRef = mRef.child("user").child(mVoteOwner).child("voteList");
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
                                mVoteDetailsList = mVoteDetails.entrySet().toArray();

                                for ( Map.Entry<String,Integer> entry : mVoteDetails.entrySet() ) {
                                    mVoteUIDList.add(entry.getKey());
                                    mVoterStatus.add(entry.getValue());
                                }
//                                for (int i=0;i<mVoteUIDList.size();i++) {
//                                    final int finalI = i;
//                                    mRef.child("user").child(mVoteUIDList.get(i)).child("userNickName").addListenerForSingleValueEvent(new ValueEventListener() {
//                                        @Override
//                                        public void onDataChange(DataSnapshot dataSnapshot) {
////                                            if(mVoterNameList.size()!=mVoteUIDList.size()){
//                                                mVoterNameList.add(dataSnapshot.getValue().toString());
//                                                mVoteUIDListTwo.add(mVoteUIDList.get(finalI));
//                                                mVoterStatus.set(finalI,mVoteDetails.get(mVoteUIDListTwo.get(finalI)));
////                                            }
////                                            else{
////                                                mVoterNameList.set(finalI,dataSnapshot.getValue().toString());
////                                            }
//                                            notifyDataSetChanged();
//                                        }
//
//                                        @Override
//                                        public void onCancelled(DatabaseError databaseError) {
//
//                                        }
//                                    });
//                                }
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
    public void onBindViewHolder(final ViewHolder holder, int position) {
        mRef.child("user").child(mVoteUIDList.get(position)).child("userNickName").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String name = dataSnapshot.getValue().toString();
                holder.VoterName.setText(name);
//                                            if(mVoterNameList.size()!=mVoteUIDList.size()){
//                mVoterNameList.add(dataSnapshot.getValue().toString());
//                mVoteUIDListTwo.add(mVoteUIDList.get(finalI));
//                mVoterStatus.set(finalI,mVoteDetails.get(mVoteUIDListTwo.get(finalI)));
//                                            }
//                                            else{
//                                                mVoterNameList.set(finalI,dataSnapshot.getValue().toString());
//                                            }
                notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
//        String name = mVoterNameList.get(position);
        Integer status = mVoterStatus.get(position);

        if(status == 1){
            holder.VoteStatus.setImageResource(R.drawable.icons8_thumbs_up_24);
        }else if(status ==2){
            holder.VoteStatus.setImageResource(R.drawable.icons8_thumbs_down_24);
        }
    }

    @Override
    public int getItemCount() {
        return mVoteUIDList.size();
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
