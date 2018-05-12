package com.example.l8411.wut2eat29.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

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
import java.util.Random;

/**
 * Created by chenj11 on 3/23/2018.
 */

public class InvitationNameAdapter extends RecyclerView.Adapter<InvitationNameAdapter.ViewHolder> {
    private List<HashMap> invitations;
    private List<String> inviteKeys;
    private FirebaseAuth mAuth;
    private DatabaseReference mRef;

    public InvitationNameAdapter(List<HashMap> invitations, List<String> inviteKeys) {
        this.invitations = invitations;
        this.inviteKeys = inviteKeys;
        Log.d("WTE","The invitation name adapter is created");
    }


    @Override
    public InvitationNameAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.invitation_view,parent,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final InvitationNameAdapter.ViewHolder holder, final int position) {
        mAuth = FirebaseAuth.getInstance();
        mRef = FirebaseDatabase.getInstance().getReference();
        mRef.child("user").orderByChild("userID").equalTo((String) invitations.get(position).get("send_uid")).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String name = (String) dataSnapshot.getChildren().iterator().next().child("userNickName").getValue();
                holder.nameTextView.setText(String.format("%s send you an invitation",inviteKeys.get(position)));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    @Override
    public int getItemCount() {
        return invitations.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView nameTextView;
        ImageView acceptButton;
        ImageView declindeButton;

        public ViewHolder(View view){
            super(view);
            acceptButton = view.findViewById(R.id.imageButtonYes);
            declindeButton = view.findViewById(R.id.imageButtonNo);
            nameTextView = view.findViewById(R.id.Iname_view);
        }
    }
}
