package com.ews.fitnessmobile;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import com.ews.fitnessmobile.dao.LoginDAO;
import com.ews.fitnessmobile.model.Login;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.ButterKnife;


public class LoginActivity extends AppCompatActivity {

    private static final String TAG_LOG = "LoginActivity";
    public static final String PUT_LOGIN = "com.ews.fitnessmobile.PUT_LOGIN";

    public static final String KEY_APP_PREFERENCES = "com.ews.fitnessmobile.login" ;
    public static final String KEY_LOGIN = "login" ;

    private Login login;
    private LoginDAO loginDAO;
    private SharedPreferences sharedPreferences;
    private Gson gson = new Gson();

    @BindView(R.id.etUsername)
    EditText etUsername;

    @BindView(R.id.etPassword)
    EditText etPassword;

    @BindView(R.id.tilPassword)
    TextInputLayout tilPassword;

    @BindView(R.id.cbKeepConnected)
    CheckBox cbKeepConnected;

    @BindView(R.id.loginFacebook)
    LoginButton loginButton;

    CallbackManager callbackManager;

    private FirebaseAnalytics firebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);


        //Utilizado pq eu quero enviar dados pro analitic manualmente
        this.firebaseAnalytics = FirebaseAnalytics.getInstance(this);

        //se cadastrando em um topico de forma fixa
        FirebaseMessaging.getInstance().subscribeToTopic("fitness");
        //-------------------

        this.loginDAO = new LoginDAO(this);
        this.sharedPreferences = getSharedPreferences(KEY_APP_PREFERENCES, MODE_PRIVATE);

        if (isConnected()) {
            initApp();
        }

        loginButton.setReadPermissions("email");
        callbackManager = CallbackManager.Factory.create();
        // Callback registration
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                login = new Login("FACEBOOK", loginResult.getAccessToken().getToken(), true);
                keepConnected();
                initApp();
                // App code
                Log.d(TAG_LOG, "onSuccess AccessToken --> " + loginResult.getAccessToken().getToken());
                Bundle bundle = new Bundle();
                bundle.putString("TYPE_LOGIN", "FACEBOOK");
                firebaseAnalytics.logEvent("onSuccess", bundle);
            }

            @Override
            public void onCancel() {
                // App code
                Log.d(TAG_LOG, "onCancel");
                Bundle bundle = new Bundle();
                bundle.putString("TYPE_LOGIN", "FACEBOOK");
                firebaseAnalytics.logEvent("onCancel", bundle);
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
                Log.e(TAG_LOG, exception.getMessage());
                Bundle bundle = new Bundle();
                bundle.putString("TYPE_LOGIN", "FACEBOOK");
                bundle.putString("ERROR", exception.getMessage());
                firebaseAnalytics.logEvent("onError", bundle);
            }
        });


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private boolean isLoginValid() {
        this.login = loginDAO.getLogin(login);
        return login != null;
    }

    /**
     * Add Object Login to SharedPReferences
     */
    private void keepConnected() {
        boolean commit = this.sharedPreferences.edit().putString(KEY_LOGIN, gson.toJson(this.login)).commit();
        Log.d(TAG_LOG, "COMMIT SHARED PREFERENCES --> " + commit);
    }

    private boolean isConnected() {
        String json = this.sharedPreferences.getString(KEY_LOGIN, "");
        if (!json.equals("")) this.login = gson.fromJson(json, Login.class);
        return !json.equals("") || AccessToken.getCurrentAccessToken() != null;
    }

    private void initApp() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(PUT_LOGIN, this.login);
        startActivity(intent);
        finish();
    }

    public void login(View view) {
        Log.d(TAG_LOG, "login");
        Bundle bundle = new Bundle();
        this.login = new Login(this.etUsername.getText().toString(), this.etPassword.getText().toString());
        if (isLoginValid()) {
            if (cbKeepConnected.isChecked()) keepConnected();
            initApp();
        } else {
            this.tilPassword.setErrorEnabled(true);
            this.tilPassword.setError(getResources().getString(R.string.tx_credentials_invalid));
            bundle.putString("ERROR", getResources().getString(R.string.tx_credentials_invalid));
        }
        bundle.putString("TYPE_LOGIN", "APPLICATION");
        firebaseAnalytics.logEvent("login", bundle);
    }

}
