package com.example.l8411.wut2eat29.Fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.provider.ContactsContract;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.l8411.wut2eat29.Activity.MainActivity;
import com.example.l8411.wut2eat29.Adapter.StartAVoteAdapter;
import com.example.l8411.wut2eat29.Model.History;
import com.example.l8411.wut2eat29.Model.Restaurant;
import com.example.l8411.wut2eat29.Model.Vote;
import com.example.l8411.wut2eat29.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;



public class StartAVoteFragment extends android.support.v4.app.Fragment implements View.OnKeyListener {
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
        final DatabaseReference mUserRef = mRef.child("user").child(mAuth.getCurrentUser().getUid());
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
        Button confirmButton = StartAVoteView.findViewById(R.id.confirmStartVote);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference todayChoice = mUserRef.child("todayChoice");
                if(todayChoice.child("dateFormated").equals("NULL")
                        || todayChoice.child("resturant").child("name").equals("NULL")){
                    Toast.makeText(getActivity(), "Please choose a resturant before you start a vote.",
                            Toast.LENGTH_SHORT).show();
                }else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setTitle(getString(R.string.which_resturant));
                    final EditText editText = new EditText(getContext());
                    builder.setView(editText);
                    builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            String mResturant = editText.getText().toString();
                            Vote mVote = new Vote(mResturant);
                            mVote.setVoteOwnerID(mAuth.getCurrentUser().getUid());
                            List<String> nameList = mStartAVoteAdapter.getmVoteList();
                            HashMap<String,Integer> mvoteDetails = new HashMap<>();
                            for( String name : nameList){
                                mvoteDetails.put(name, 0);
                            }
                            mVote.setVoteDetails(mvoteDetails);
                            DatabaseReference voteRef = mRef.child("votes");
                            String voteID = voteRef.push().getKey();
                            voteRef.child(voteID).setValue(mVote);
                            mUserRef.child("voteList").child(voteID).setValue(true);
                            for(String userid : nameList){
                                mRef.child("user").child(userid).child("voteList").child(voteID).setValue(false);
                            }


                        }
                    });

                    builder.setNegativeButton(android.R.string.cancel, null);
                    builder.create().show();
                }
            }
        });
        recyclerView.requestFocus();
        recyclerView.setOnKeyListener(this);
        return StartAVoteView;
    }

    @Override
    public boolean onKey(View view, int i, KeyEvent keyEvent) {
        if (i == KeyEvent.KEYCODE_BACK) {
            Log.d("back", "back click");
            MainActivity main = (MainActivity) getContext();
            getFragmentManager().popBackStack("StartAVote", FragmentManager.POP_BACK_STACK_INCLUSIVE);
            main.navigationView.setVisibility(View.VISIBLE);
            main.viewPager.setVisibility(View.VISIBLE);
            if (main.viewPager.getCurrentItem() == 0) {
                main.findViewById(R.id.search_view).setVisibility(View.VISIBLE);
                main.findViewById(R.id.fab_here).setVisibility(View.VISIBLE);
            }
            return true;
        }
        Log.d("back", "back click");
        return false;
    }
}
