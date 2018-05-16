package com.example.l8411.wut2eat29.Adapter;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.l8411.wut2eat29.Fragment.ViewVoteFragment;
import com.example.l8411.wut2eat29.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DoVoteAdapter extends RecyclerView.Adapter<DoVoteAdapter.ViewHolder>{

    private DatabaseReference mRef;
    private FirebaseAuth mAuth;
    private List<String> mVoteList;
    private List<String> mVoteOwnerIDList;
    private List<String> mVoteOwnerNameList;
    private FragmentActivity mActivity;

    public DoVoteAdapter(final DatabaseReference mRef, FirebaseAuth mAuth, List<String> mVoteList, FragmentActivity activity) {
        this.mRef = mRef;
        this.mAuth = mAuth;
        this.mVoteList = mVoteList;
        mVoteOwnerIDList = new ArrayList<>();
        mVoteOwnerNameList= new ArrayList<>();
        this.mActivity = activity;
        for (String voteid:
             mVoteList) {
            mRef.child("votes").child(voteid).child("voteOwnerID").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    mVoteOwnerIDList.add(dataSnapshot.getValue().toString());
                    mRef.child("user").child(dataSnapshot.getValue().toString()).child("userNickName").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            mVoteOwnerNameList.add(dataSnapshot.getValue().toString());
                            notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });


                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.do_vote_detail_view,parent,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        View.OnClickListener clickview = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction ft = mActivity.getSupportFragmentManager().beginTransaction();
                Log.d("Votes", "click");
                ft.add(R.id.fragment_container, ViewVoteFragment.newInstance(mVoteOwnerIDList.get(position))).commit();
                ft.addToBackStack("Votes");
            }
        };
        holder.doVote.setOnClickListener(clickview);
        holder.nameTextView.setText(String.format("%s want to eat with you!",mVoteOwnerNameList.get(position)));
        holder.acceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mRef.child("votes").child(mVoteList.get(position)).child("voteDetails").child(mAuth.getCurrentUser().getUid()).setValue(1);
            }
        });
        holder.declinedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mRef.child("votes").child(mVoteList.get(position)).child("voteDetails").child(mAuth.getCurrentUser().getUid()).setValue(2);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mVoteOwnerNameList.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView nameTextView;
        ImageView acceptButton;
        ImageView declinedButton;
        View doVote;
        public ViewHolder(View view){
            super(view);
            doVote = view;
            acceptButton = view.findViewById(R.id.imageButtonYes);
            declinedButton = view.findViewById(R.id.imageButtonNo);
            nameTextView = view.findViewById(R.id.Iname_view);
        }
    }
}
