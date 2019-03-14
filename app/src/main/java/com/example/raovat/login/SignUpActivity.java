package com.example.raovat.login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.raovat.Models.InfoUser;
import com.example.raovat.Models.User1;
import com.example.raovat.R;
import com.example.raovat.Utils.SLoading;
import com.example.raovat.api.APIClient;
import com.example.raovat.api.APIService;
import com.google.gson.JsonObject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class SignUpActivity extends AppCompatActivity {
    SLoading sLoading;
    Intent intent;

    @BindView(R.id.iv_logo)
    ImageView ivLogo;
    @BindView(R.id.edt_name)
    EditText edtName;
    @BindView(R.id.edt_user)
    EditText edtUser;
    @BindView(R.id.edt_pass)
    EditText edtPass;
    @BindView(R.id.edt_pass2)
    EditText edtPass2;
    @BindView(R.id.cv_login)
    CardView cvLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);
        init();
        initAction();

    }

    private void initAction() {
        cvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtName.getText().toString().equals("") || edtPass.getText().toString().equals("") || edtPass2.getText().toString().equals("") || edtUser.getText().toString().equals("")) {
                    Toast.makeText(SignUpActivity.this, "Thông tin không được trống!", Toast.LENGTH_SHORT).show();

                } else {
                    if (!edtPass.getText().toString().equals(edtPass2.getText().toString())) {
                        Toast.makeText(SignUpActivity.this, "Mật khẩu không trùng nhau!", Toast.LENGTH_SHORT).show();

                    } else {
                        intent.putExtra("Phone",edtUser.getText().toString());
                        signUp(edtUser.getText().toString(), edtPass.getText().toString(), edtName.getText().toString());

                    }
                }

            }
        });
    }

    private void init() {
        sLoading = new SLoading(this);
        intent = new Intent(this, LoginActivity.class);
    }

    private void signUp(String phone, String pass, String fullName) {
        sLoading.show();
        final APIService service = APIClient.getClient();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("PhoneNumber", phone);
        jsonObject.addProperty("Password", pass);
        jsonObject.addProperty("FullName", fullName);
        service.singup(jsonObject)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<User1>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(User1 user1) {



                    }

                    @Override
                    public void onError(Throwable e) {
                        sLoading.dismiss();
                        Toast.makeText(SignUpActivity.this, "Tài khoản đã tồn tại!", Toast.LENGTH_SHORT).show();


                    }

                    @Override
                    public void onComplete() {
                        Toast.makeText(SignUpActivity.this, "Thành công!", Toast.LENGTH_SHORT).show();
                        startActivity(intent);
                        finish();

                    }
                });
    }
}
