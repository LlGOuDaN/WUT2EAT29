package com.example.l8411.wut2eat29.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.l8411.wut2eat29.Model.Vote;
import com.example.l8411.wut2eat29.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by chenj11 on 5/7/2018.
 */

public class ViewVoteAdapter extends RecyclerView.Adapter<ViewVoteAdapter.ViewHolder>{
    Vote mVote;
    HashMap<String,Integer> mVoteDetails;
    Object[] mVoteDetailsList;
    String[] mVoterNameList;
    Integer[] mVoterStatus;

    public ViewVoteAdapter() {
        this.mVote = new Vote("TestVote",null,new HashMap<String, Integer>());
        this.mVoteDetails = mVote.getVoteDetails();
        mVoteDetails.put("Arthur",1);
        mVoteDetails.put("Goudan Li",2);
        mVoteDetails.put("Someone",0);
        mVoteDetailsList = mVoteDetails.entrySet().toArray();
        mVoterNameList = mVoteDetails.keySet().toArray(new String[0]);

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_vote_list_view,parent,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String name = mVoterNameList[position];
        holder.VoterName.setText(name);
    }

    @Override
    public int getItemCount() {
        return mVoterNameList.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView VoterName;
        private ImageView VoteStatus;

        public ViewHolder(View itemView) {
            super(itemView);
            VoterName = itemView.findViewById(R.id.voterName_view);
            VoteStatus = itemView.findViewById(R.id.vote_status_view);
        }
    }
}
