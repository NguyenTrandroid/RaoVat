package com.example.raovat.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.raovat.MainActivity;
import com.example.raovat.Models.InfoUser;
import com.example.raovat.R;

import com.example.raovat.Utils.SLoading;
import com.example.raovat.api.APIClient;
import com.example.raovat.api.APIService;
import com.example.raovat.customview.BTLogin;
import com.google.gson.JsonObject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatCheckBox;
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
    BTLogin cvLogin;
    @BindView(R.id.cv_signup)
    BTLogin cvSignup;
    @BindView(R.id.rl_mainlg)
    RelativeLayout rlMainlg;


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
                test();

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
        sLoading = new SLoading(this);
        sharedPreferences = getSharedPreferences("Login", MODE_PRIVATE);

        if (!sharedPreferences.getString("IdUser", "").equals("")) {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);


            finish();
        } else {
            rlMainlg.setVisibility(View.VISIBLE);
        }
        Intent intent = getIntent();
        edtUser.setText(intent.getStringExtra("Phone"));
    }

    private void test() {
        Log.d("AAA", id + "asdasdada");
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
                        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                        finish();
                    }
                });
    }
}
