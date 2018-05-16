package com.example.l8411.wut2eat29.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.l8411.wut2eat29.Adapter.DoVoteAdapter;
import com.example.l8411.wut2eat29.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DoVote extends Fragment  {
    private DoVoteAdapter mDoVoteAdapter;
    private DatabaseReference mRef;
    private FirebaseAuth mAuth;

    public DoVote() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRef = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View doVoteView = inflater.inflate(R.layout.fragment_do_vote, container, false);
        final RecyclerView recyclerView = (RecyclerView) doVoteView.findViewById(R.id.do_vote_recycler_view) ;
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        final DatabaseReference mUserRef = mRef.child("user").child(mAuth.getCurrentUser().getUid());
        mUserRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                HashMap<String,Boolean> votelist = (HashMap<String, Boolean>) dataSnapshot.child("voteList").getValue();
                List<String> voteInvolved = new ArrayList<>();
                for(Map.Entry<String,Boolean> entry : votelist.entrySet()){
                    if(!entry.getValue()){
                        voteInvolved.add(entry.getKey());
                    }
                }
                mDoVoteAdapter = new DoVoteAdapter(mRef,mAuth,voteInvolved,getActivity());
                recyclerView.setAdapter(mDoVoteAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return doVoteView;
    }

    public static DoVote newInstance(){
        DoVote fragment = new DoVote();
        return fragment;
    }
}
