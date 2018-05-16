package com.example.l8411.wut2eat29.Fragment.BottomNavi;

import android.app.AlertDialog;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.l8411.wut2eat29.Adapter.FriendsAdapter;
import com.example.l8411.wut2eat29.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class FriendListFragment extends android.support.v4.app.Fragment implements FriendsAdapter.onImageViewClickListener, SwipeRefreshLayout.OnRefreshListener, View.OnKeyListener {
    private FriendsAdapter mFriendsAdapter;
    private FirebaseAuth mAuth;
    private DatabaseReference mRef;
    private SwipeRefreshLayout swipeRefreshLayout;


    public FriendListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment FriendListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FriendListFragment newInstance() {
        FriendListFragment fragment = new FriendListFragment();
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
        View friendNameView = inflater.inflate(R.layout.fragment_friend_list, container, false);
        final RecyclerView recyclerView = (RecyclerView) friendNameView.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        DatabaseReference mUserRef = mRef.child("user").child(mAuth.getCurrentUser().getUid());
        mUserRef.child("friendlist").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                friends.add(dataSnapshot);
                mFriendsAdapter = new FriendsAdapter(friends, FriendListFragment.this);
                recyclerView.setAdapter(mFriendsAdapter);
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
        swipeRefreshLayout = friendNameView.findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setOnRefreshListener(this);

        return friendNameView;
    }

    @Override
    public void onImageViewClick() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(getString(R.string.success));
        builder.setMessage(getString(R.string.notification_sent));
        builder.create().show();
    }

    @Override
    public void onRefresh() {
        mFriendsAdapter.notifyDataSetChanged();
        swipeRefreshLayout.setColorSchemeColors(0,0,0,0);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(false);
            }
        }, 1000);
    }

    @Override
    public boolean onKey(View view, int i, KeyEvent keyEvent) {
        if(i == KeyEvent.KEYCODE_BACK){

        }
        return false;
    }
}
