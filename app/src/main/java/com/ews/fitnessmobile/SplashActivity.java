package com.ews.fitnessmobile;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.ews.fitnessmobile.api.APIUtils;
import com.ews.fitnessmobile.api.LoginAPI;
import com.ews.fitnessmobile.dao.LoginDAO;
import com.ews.fitnessmobile.model.Login;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class SplashActivity extends AppCompatActivity {

    public static final int SPLASH_DELAY = 3500;

    @BindView(R.id.fitnessSplash)
    ImageView imageView;

    private LoginAPI loginAPI;
    private LoginDAO loginDAO = new LoginDAO(this);
    public static final String TAG_LOG = "SplashActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        this.loginAPI = APIUtils.getLoginAPI();
        loadSplash();
    }


    public void loadSplash() {

        Animation animation = AnimationUtils.loadAnimation(this, R.anim.animation_splash);
        animation.reset();
        this.imageView.clearAnimation();
        this.imageView.startAnimation(animation);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                loginAPI.getLogin()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<Login>() {
                        @Override
                        public void onCompleted() {
                            Log.d(TAG_LOG, "getLogin#onCompleted");
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.d(TAG_LOG, e.getMessage());
                        }

                        @Override
                        public void onNext(Login login) {
                            Login l = loginDAO.getLogin(login);
                            if (l == null) {
                                String response = loginDAO.add(login);
                                Log.d(TAG_LOG, response);
                            }
                        }
                    });

                Intent loginIntent = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(loginIntent);

                SplashActivity.this.finish();

            }

        }, SPLASH_DELAY);

    }

}
