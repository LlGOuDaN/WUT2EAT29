package com.example.l8411.wut2eat29.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.style.BulletSpan;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.l8411.wut2eat29.Activity.MainActivity;
import com.example.l8411.wut2eat29.Adapter.HistoryAdapter;
import com.example.l8411.wut2eat29.Adapter.ViewVoteAdapter;
import com.example.l8411.wut2eat29.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class ViewVoteFragment extends Fragment implements View.OnKeyListener {
    private FirebaseAuth mAuth;
    private DatabaseReference mRef;
    private String mOwner;

    public ViewVoteFragment() {
        // Required empty public constructor
    }

    public static ViewVoteFragment newInstance(String Owner){
        ViewVoteFragment fragment = new ViewVoteFragment();
        Bundle bundle = new Bundle();
        bundle.putString("OwnerID", Owner);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        mRef = FirebaseDatabase.getInstance().getReference();
        Bundle bundle = this.getArguments();
        if(bundle != null){
        mOwner = bundle.getString("OwnerID");}
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_view_vote, container, false);
        final TextView mTextView = view.findViewById(R.id.view_vote_top_text);
        mRef.child("user").child(mOwner).child("voteList").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                HashMap<String,Boolean> voteMap =(HashMap<String,Boolean>) dataSnapshot.getValue();
                for ( Map.Entry<String,Boolean> entry : voteMap.entrySet() ) {
                    if(entry.getValue()){
                        mRef.child("votes").child(entry.getKey()).child("resturant").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                mTextView.setText(String.format(getString(R.string.restaurant_s),dataSnapshot.getValue().toString()));
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


        RecyclerView recyclerView = view.findViewById(R.id.view_vote_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        ViewVoteAdapter adapter = new ViewVoteAdapter(mRef,mAuth,mOwner);
        recyclerView.setAdapter(adapter);
        recyclerView.requestFocus();
        recyclerView.setOnKeyListener(this);
        return view;
    }

    @Override
    public boolean onKey(View view, int i, KeyEvent keyEvent) {
        if (i == KeyEvent.KEYCODE_BACK) {
            Log.d("back", "back click");
            MainActivity main = (MainActivity) getContext();
            getFragmentManager().popBackStack("Votes", FragmentManager.POP_BACK_STACK_INCLUSIVE);
            main.navigationView.setVisibility(View.VISIBLE);
            main.viewPager.setVisibility(View.VISIBLE);
            if (main.viewPager.getCurrentItem() == 0) {
                main.findViewById(R.id.search_view).setVisibility(View.VISIBLE);
                main.findViewById(R.id.fab_here).setVisibility(View.VISIBLE);
            }
            return true;
        }
        return false;
    }

}
