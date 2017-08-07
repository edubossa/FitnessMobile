package com.ews.fitnessmobile.firebase;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by wallace on 05/08/17.
 */
public class ClientFirebaseInstanceIdService extends FirebaseInstanceIdService {

    public static final String TAG = "ClientFirebase";

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        String refreshToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refresh token: " + refreshToken);
        sendRegistrationToServer(refreshToken);
    }


    private void sendRegistrationToServer(String token) {
        Log.d("TOKEN", token);
    }

}
