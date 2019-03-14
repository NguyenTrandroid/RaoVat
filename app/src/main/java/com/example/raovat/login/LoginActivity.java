package com.example.raovat.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.raovat.MainActivity;
import com.example.raovat.Models.InfoUser;
import com.example.raovat.R;
import com.example.raovat.Utils.SLoading;
import com.example.raovat.api.APIClient;
import com.example.raovat.api.APIService;
import com.google.gson.JsonObject;
import com.wang.avi.AVLoadingIndicatorView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.cardview.widget.CardView;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class LoginActivity extends AppCompatActivity {
    public static boolean startSplashScreen = true;
    SharedPreferences sharedPreferences;
    String id;
    String sdt;
    SLoading sLoading;
    @BindView(R.id.iv_logo)
    ImageView ivLogo;
    @BindView(R.id.edt_user)
    EditText edtUser;
    @BindView(R.id.edt_pass)
    EditText edtPass;
    @BindView(R.id.cb_tk)
    AppCompatCheckBox cbTk;
    @BindView(R.id.cv_login)
    CardView cvLogin;
    @BindView(R.id.cv_signup)
    CardView cvSignup;
    @BindView(R.id.rl_mainlg)
    RelativeLayout rlMainlg;
    @BindView(R.id.iv_splash_screen)
    ImageView ivSplashScreen;
    @BindView(R.id.av_Loading)
    AVLoadingIndicatorView avLoading;
    @BindView(R.id.rl_splash_screen)
    RelativeLayout rlSplashScreen;
    CardView cardView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        init();
        initAction();


    }

    private void initAction() {
        cvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Login(edtUser.getText().toString(), edtPass.getText().toString());
            }
        });
        cvSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
            }
        });
    }


    private void init() {
        sharedPreferences = getSharedPreferences("Login", MODE_PRIVATE);
        if (startSplashScreen) {
            avLoading.show();
            SetSplashScreen();
            startSplashScreen = false;
        } else {
            if (!sharedPreferences.getString("IdUser", "").equals("")) {
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                finish();
            } else {
                avLoading.setVisibility(View.GONE);
                rlSplashScreen.setVisibility(View.GONE);
                rlMainlg.setVisibility(View.VISIBLE);
            }

        }

        sLoading = new SLoading(this);
        Intent intent = getIntent();
        edtUser.setText(intent.getStringExtra("Phone"));

    }


    private void SetSplashScreen() {
        AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1);
        alphaAnimation.setDuration(3369);
        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                ivSplashScreen.setVisibility(View.VISIBLE);

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                TranslateAnimation translateAnimation = new TranslateAnimation(0, 0, 0, -getWindow().getDecorView().getHeight());
                translateAnimation.setDuration(100);
                translateAnimation.setFillAfter(true);
                rlSplashScreen.startAnimation(translateAnimation);
                if (sharedPreferences.getString("IdUser", "").equals("")) {
                    rlSplashScreen.setVisibility(View.GONE);
                    rlMainlg.setVisibility(View.VISIBLE);
                } else {
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();
                }


            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        ivSplashScreen.startAnimation(alphaAnimation);
    }


    private void Login(String phone, String pass) {
        sLoading.show();
        final APIService service = APIClient.getClient();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("PhoneNumber", phone);
        jsonObject.addProperty("Password", pass);
        service.login(jsonObject)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<InfoUser>() {
                    @Override
                    public void onSubscribe(Disposable d) {


                    }

                    @Override
                    public void onNext(InfoUser infoUser) {
                        id = infoUser.getData().getId();


                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("AAA", e + "");
                        sLoading.dismiss();

                    }

                    @Override
                    public void onComplete() {
                        Intent intent1 = new Intent(LoginActivity.this, MainActivity.class);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("IdUser", id);
                        editor.putString("Sdt", edtUser.getText().toString());
                        editor.commit();
                        startActivity(intent1);
                        finish();
                    }
                });
    }
}
