package com.example.l8411.wut2eat29.Utils;

import android.util.Log;

import com.example.l8411.wut2eat29.Model.UserProfile;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;

public class utils {
    public static UserProfile CURRENT_USER;

    public static String parseDate(Date date){
        String format = "MM/dd/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        String dateFormated = sdf.format(date);
        return dateFormated;
    }
}
