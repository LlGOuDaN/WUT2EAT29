package com.example.l8411.wut2eat29.Utils;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.l8411.wut2eat29.Activity.LoginActivity;
import com.example.l8411.wut2eat29.Activity.MainActivity;
import com.example.l8411.wut2eat29.Fragment.LoginFragment;
import com.example.l8411.wut2eat29.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.HashMap;
import java.util.Map;

public class MyMessagingService extends FirebaseMessagingService {
    @Override
    public void onDeletedMessages() {
        super.onDeletedMessages();
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        RemoteMessage.Notification notification = remoteMessage.getNotification();

        String title = notification.getTitle();
        String message = notification.getBody();
        Log.d("notification", remoteMessage.getData().toString());
        String notificationId = remoteMessage.getData().get("message_id");
        String userSent = remoteMessage.getData().get("sent_nickname");
        int id = getNotificationID(notificationId);
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        NotificationChannel channel = new NotificationChannel("wut2eat29_channel_01", "myChannel", NotificationManager.IMPORTANCE_DEFAULT);
        manager.createNotificationChannel(channel);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "wut2eat29_channel_01")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(userSent+ " " + message)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setVibrate(new long[] { 10000, 10000});


        Intent resultIntent = new Intent(this, LoginActivity.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,resultIntent,PendingIntent.FLAG_UPDATE_CURRENT);

        builder.setContentIntent(pendingIntent);



        manager.notify(id, builder.build());

    }


    private int getNotificationID(String id){
        int notificationId = 0;
        for(int i = 0; i < id.length(); i++){
            notificationId = notificationId + id.charAt(0);
        }
        return notificationId;
    }

}
