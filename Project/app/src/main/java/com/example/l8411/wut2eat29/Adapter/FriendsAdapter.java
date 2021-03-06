package com.example.l8411.wut2eat29.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.l8411.wut2eat29.Model.History;
import com.example.l8411.wut2eat29.Model.Restaurant;
import com.example.l8411.wut2eat29.Model.UserProfile;
import com.example.l8411.wut2eat29.R;
import com.example.l8411.wut2eat29.Utils.utils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * Created by chenj11 on 3/23/2018.
 */

public class FriendsAdapter extends RecyclerView.Adapter<FriendsAdapter.ViewHolder> {
    private List<DataSnapshot> mFriends;
    private DatabaseReference mRef;
    private Bitmap bitmap;
    private FriendsAdapter.ViewHolder holder;
    private onImageViewClickListener listener;

    public FriendsAdapter(List<DataSnapshot> mFriends, onImageViewClickListener listener) {
        this.mFriends = mFriends;
        mRef = FirebaseDatabase.getInstance().getReference();
        this.listener = listener;
    }


    @Override
    public FriendsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.friend_name_view, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final FriendsAdapter.ViewHolder holder, final int position) {
        this.holder = holder;
        mRef.child("user").child(mFriends.get(position).getKey()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot dataSnapshot) {
                String resturantName, dateFormated;
                final String name = (String) dataSnapshot.child("userNickName").getValue();
                holder.nameTextView.setText(name);
                final String uid = dataSnapshot.getKey();
                if (dataSnapshot.child("todayChoice").getValue() == null) {
                    mRef.child("user").child(uid).child("todayChoice").setValue(utils.getEmptyHistory());
                    holder.placeTextView.setText(R.string.not_decided_yet);
                } else {
                    History dayOfChoice = dataSnapshot.child("todayChoice").getValue(History.class);
                    resturantName = dayOfChoice.getResturant().getName();
                    dateFormated = dayOfChoice.getDateFormated();
                    if (resturantName.equals("NULL") || !dateFormated.equals(utils.parseDate(Calendar.getInstance().getTime()))) {
                        holder.placeTextView.setText(R.string.not_decided_yet);
                        if (!resturantName.equals("NULL")) {

                            UserProfile mProfile = dataSnapshot.getValue(UserProfile.class);
                            if (mProfile.getHistory() == null) {
                                mProfile.setHistory(new ArrayList<History>());
                            }
                            mProfile.getHistory().add(0, dayOfChoice);
                            mRef.child("user").child(uid).child("history").setValue(mProfile.getHistory());
                            mRef.child("user").child(uid).child("todayChoice").setValue(utils.getEmptyHistory());
                        }
                    } else {
                        holder.placeTextView.setText(resturantName);
                    }
                }
                holder.imageButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        HashMap<String, String> notificationData = new HashMap<>();
                        notificationData.put("sent_authId", FirebaseAuth.getInstance().getCurrentUser().getUid());
                        mRef.child("notification").child(uid).push().setValue(notificationData);
                        listener.onImageViewClick();
                    }
                });
                new GetImageTask().execute(dataSnapshot.child("avatarUrl").getValue(String.class), holder.head);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }


    @Override
    public int getItemCount() {
        return mFriends.size();
    }


    public void deleteFriend(int position) {

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        TextView placeTextView;
        ImageButton imageButton;
        ImageView head;

        public ViewHolder(View view) {
            super(view);
            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    deleteFriend(getAdapterPosition());
                    return false;
                }
            });
            nameTextView = view.findViewById(R.id.name_view);
            placeTextView = view.findViewById(R.id.place_view);
            imageButton = view.findViewById(R.id.imageButton);
            head = view.findViewById(R.id.avatar_image);
        }
    }

    public class GetImageTask extends AsyncTask<Object,Object,Object> {

        @Override
        protected Object doInBackground(Object... objects) {
            String urlString = (String) objects[0];
            try {
                InputStream in = new URL(urlString).openStream();
                bitmap = BitmapFactory.decodeStream(in);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return objects[1];
        }

        @Override
        protected void onPostExecute(Object object) {
            super.onPostExecute(object);
            ImageView head = (ImageView) object;
            head.setImageBitmap(bitmap);
        }
    }

    public interface onImageViewClickListener{
        void onImageViewClick();
    }
}
