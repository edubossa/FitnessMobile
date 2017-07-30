package com.ews.fitnessmobile;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.ews.fitnessmobile.dao.LoginDAO;
import com.ews.fitnessmobile.model.Login;
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

    @BindView(R.id.cbKeepConnected)
    CheckBox cbKeepConnected;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        this.loginDAO = new LoginDAO(this);
        this.sharedPreferences = getSharedPreferences(KEY_APP_PREFERENCES, MODE_PRIVATE);

        if (isConnected()) {
            initApp();
        }
    }

    private boolean isLoginValid() {
        this.login = loginDAO.getByUsername(login.getUsername());
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
        return !json.equals("");
    }

    private void initApp() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(PUT_LOGIN, this.login);
        startActivity(intent);
        finish();
    }

    public void login(View view) {
        Log.d(TAG_LOG, "login");
        this.login = new Login(this.etUsername.getText().toString(), this.etPassword.getText().toString());
        if (isLoginValid()) {
            if (cbKeepConnected.isChecked()) keepConnected();
            initApp();
        } else {
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.tx_credentials_invalid), Toast.LENGTH_SHORT).show();
        }
    }

}
