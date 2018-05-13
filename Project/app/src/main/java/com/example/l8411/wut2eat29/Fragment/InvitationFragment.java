package com.example.l8411.wut2eat29.Fragment;

import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.l8411.wut2eat29.Activity.MainActivity;
import com.example.l8411.wut2eat29.Adapter.InvitationNameAdapter;
import com.example.l8411.wut2eat29.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 interface
 * to handle interaction events.
 * Use the {@link InvitationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InvitationFragment extends Fragment implements View.OnKeyListener {

    private InvitationNameAdapter mInvitationAdapter;
    private DatabaseReference mRef;
    private FirebaseAuth mAuth;


    public InvitationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment InvitationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static InvitationFragment newInstance() {
        InvitationFragment fragment = new InvitationFragment();
        Bundle args = new Bundle();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRef = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View invitationNameView =inflater.inflate(R.layout.fragment_invitation, container, false);
        final RecyclerView recyclerView = (RecyclerView) invitationNameView.findViewById(R.id.recycler_view2) ;
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        //firebase stuff
        DatabaseReference mUserRef = mRef.child("user");
        mUserRef.child(mAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final List<HashMap> invitations = new ArrayList<>();
                final List<String> inviteKeys = new ArrayList<>();
                String userId = (String) dataSnapshot.child("userID").getValue();
                DatabaseReference mInvitationRef = mRef.child("invitation");
                mInvitationRef.orderByChild("recv_uid").equalTo(userId).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Iterator<DataSnapshot> iterator = dataSnapshot.getChildren().iterator();
                        while(iterator.hasNext()){
                            DataSnapshot current = iterator.next();
                            if( ((Long) ((HashMap)current.getValue()).get("status")) == 0L ){
                                invitations.add((HashMap) current.getValue());
                                inviteKeys.add(current.getKey());
                            }
                        }
                        mInvitationAdapter = new InvitationNameAdapter(invitations, inviteKeys);
                        recyclerView.setAdapter(mInvitationAdapter);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                recyclerView.setFocusableInTouchMode(true);
                recyclerView.requestFocus();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        recyclerView.setOnKeyListener(this);
        Log.d("WTE","ON create invitation fragment");
        return invitationNameView;
    }
    @Override
    public boolean onKey(View view, int i, KeyEvent keyEvent) {
        if (i == KeyEvent.KEYCODE_BACK) {
            Log.d("back", "back click");
            MainActivity main = (MainActivity) getContext();
            getFragmentManager().popBackStack("Invitation", FragmentManager.POP_BACK_STACK_INCLUSIVE);
            main.navigationView.setVisibility(View.VISIBLE);
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
