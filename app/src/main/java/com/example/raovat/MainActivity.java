package com.example.raovat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.raovat.Models.InfoUser;
import com.example.raovat.api.APIClient;
import com.example.raovat.api.APIService;
import com.example.raovat.history.FragmentHistory;
import com.example.raovat.listpost.PostActivity;
import com.example.raovat.login.LoginActivity;
import com.example.raovat.search.SearchActivity;
import com.example.raovat.tabprofile.FragmentProfile;
import com.google.gson.JsonObject;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    SharedPreferences sharedPreferences;
    boolean b3 = false;

    boolean ck1 = true;
    boolean ck2 = true;
    boolean remember;


    @BindView(R.id.rl_sv)
    RelativeLayout rlSv;
    RelativeLayout rlMain;
    @BindView(R.id.rl_user)
    RelativeLayout rlUser;
    @BindView(R.id.rl_history)
    RelativeLayout rlHistory;
    @BindView(R.id.rl_logout)
    RelativeLayout rlLogout;
    @BindView(R.id.rl_dodientu)
    RelativeLayout rlDodientu;
    @BindView(R.id.rl_thucung)
    RelativeLayout rlThucung;

    @BindView(R.id.ln_add)
    LinearLayout lnAdd;
    @BindView(R.id.iv_profile)
    ImageView ivProfile;
    @BindView(R.id.iv_history)
    ImageView ivHistory;
    @BindView(R.id.iv_logout)
    ImageView ivLogout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
//        init();
////        initAction();
        final APIService service = APIClient.getClient();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("MaKH", "12312");
        jsonObject.addProperty("TenKH", "12312");
        jsonObject.addProperty("diachi", "12312");
        jsonObject.addProperty("CMND", "12312");
        jsonObject.addProperty("SDT", "12312");

        service.loginn(jsonObject)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String s) {
                        Log.d("AAA", s + "");

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("AAA", e + "");

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }


    private void init() {
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        sharedPreferences = getSharedPreferences("Login", MODE_PRIVATE);
    }

    @Override
    public void onBackPressed() {
        ivHistory.setImageResource(R.drawable.ic_historyoff);
        ivProfile.setImageResource(R.drawable.ic_useroff);
        super.onBackPressed();
    }

    private void initAction() {
        rlSv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SearchActivity.class));
            }
        });
        rlUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!ck2) {
                    fragmentManager.popBackStack();
                    ck2 = true;
                }
                ivHistory.setImageResource(R.drawable.ic_historyoff);
                if (ck1) {
                    ivProfile.setImageResource(R.drawable.ic_useron);
                    fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.ln_add, new FragmentProfile()).addToBackStack("profile");
                    fragmentTransaction.commit();
                    ck1 = false;
                } else {
                    ivProfile.setImageResource(R.drawable.ic_useroff);
                    fragmentManager.popBackStack();
                    ck1 = true;
                }


            }
        });
        rlHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!ck1) {
                    fragmentManager.popBackStack();
                    ck1 = true;
                }
                ivProfile.setImageResource(R.drawable.ic_useroff);

                if (ck2) {
                    ivHistory.setImageResource(R.drawable.ic_historyon);
                    fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.ln_add, new FragmentHistory()).addToBackStack("history");
                    fragmentTransaction.commit();
                    ck2 = false;
                } else {
                    fragmentManager.popBackStack();
                    ivHistory.setImageResource(R.drawable.ic_historyoff);
                    ck2 = true;

                }


            }
        });
        rlLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove("IdUser");
                editor.remove("Sdt");
                editor.commit();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                finish();
            }
        });
        rlDodientu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, PostActivity.class));

            }
        });

    }

}
