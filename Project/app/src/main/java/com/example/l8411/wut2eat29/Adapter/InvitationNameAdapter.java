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
        long status = (long) invitations.get(position).get("status");
        if(status == 0L){
            mAuth = FirebaseAuth.getInstance();
            mRef = FirebaseDatabase.getInstance().getReference();
            mRef.child("user").orderByChild("userID").equalTo((String) invitations.get(position).get("send_uid")).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    final DataSnapshot user_send = dataSnapshot.getChildren().iterator().next();
                    String name = (String) user_send.child("userNickName").getValue();
                    final String user_send_authID = user_send.getKey();
                    final String user_recv_authID = mAuth.getCurrentUser().getUid();
                    holder.nameTextView.setText(String.format("%s send you an invitation", name));
                    holder.acceptButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            mRef.child("invitation").child(inviteKeys.get(position)).child("status").setValue(1);
                            invitations.remove(position);
                            inviteKeys.remove(position);
                            notifyDataSetChanged();
                            Log.d("ids", "send : " + user_send_authID + "\n recv: " + user_recv_authID);
                            mRef.child("user").child(user_recv_authID).child("friendlist").child(user_send_authID).setValue(true);
                            mRef.child("user").child(user_send_authID).child("friendlist").child(user_recv_authID).setValue(true);
                        }
                    });

                    holder.declindeButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            mRef.child("invitation").child(inviteKeys.get(position)).child("status").setValue(2);
                            invitations.remove(position);
                            notifyDataSetChanged();
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
