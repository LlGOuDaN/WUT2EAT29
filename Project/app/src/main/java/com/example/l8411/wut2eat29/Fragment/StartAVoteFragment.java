package com.example.l8411.wut2eat29.Fragment;

import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.l8411.wut2eat29.Activity.MainActivity;
import com.example.l8411.wut2eat29.Adapter.StartAVoteAdapter;
import com.example.l8411.wut2eat29.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;



public class StartAVoteFragment extends android.support.v4.app.Fragment  {
    private StartAVoteAdapter mStartAVoteAdapter;
    private FirebaseAuth mAuth;
    private DatabaseReference mRef;
    public StartAVoteFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *

     * @return A new instance of fragment StartAVoteFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static StartAVoteFragment newInstance() {
        StartAVoteFragment fragment = new StartAVoteFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        mRef = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final List<DataSnapshot> friends = new ArrayList<>();
        View StartAVoteView =inflater.inflate(R.layout.fragment_start_avote, container, false);
        final RecyclerView recyclerView = (RecyclerView) StartAVoteView.findViewById(R.id.recycler_view3) ;
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        DatabaseReference mUserRef = mRef.child("user").child(mAuth.getCurrentUser().getUid());
        mUserRef.child("friendlist").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                friends.add(dataSnapshot);
                mStartAVoteAdapter = new StartAVoteAdapter(friends);
                recyclerView.setAdapter(mStartAVoteAdapter);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return StartAVoteView;
    }

}
