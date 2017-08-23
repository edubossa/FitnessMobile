package com.ews.fitnessmobile.firebase;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;


import com.ews.fitnessmobile.MainActivity;
import com.ews.fitnessmobile.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by wallace on 05/08/17.
 */

public class ClientFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "ClientFirebaseMessaging";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        Log.d(TAG, "From: " + remoteMessage.getFrom());
        Log.d(TAG, "Notification Message Body: " + remoteMessage.getNotification().getBody());

        //Obtemm parametros da msg, caso precise tratar na sua regra de negocio
        if (remoteMessage.getData().size() > 0) {
            String token = remoteMessage.getData().get("token");
            String idPromocao = remoteMessage.getData().get("idPromocao");
            String status = remoteMessage.getData().get("status");
            Log.d("Token", token);
            Log.d("idPromocao", idPromocao);
            Log.d("status", status);
        }

        if (remoteMessage.getNotification() != null) {
            showNotification(
                    remoteMessage.getNotification().getTitle(),
                    remoteMessage.getNotification().getBody());
        }

    }

    private void showNotification(String title, String menssage) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent =
                PendingIntent.getActivity(this, 0 /* Request Code*/, intent, PendingIntent.FLAG_ONE_SHOT);


        Uri defaultUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setContentTitle(title)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentText(menssage)
                .setAutoCancel(true)
                .setSound(defaultUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of Notfication*/, notificationBuilder.build());

    }

}
