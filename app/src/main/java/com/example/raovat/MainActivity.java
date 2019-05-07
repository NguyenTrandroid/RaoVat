package com.example.raovat;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.raovat.Utils.CheckPermission;
import com.example.raovat.Utils.Permissionruntime;
import com.example.raovat.customview.RLCategory;
import com.example.raovat.login.LoginActivity;
import com.example.raovat.posts.PostActivity;
import com.example.raovat.search.SearchActivity;
import com.example.raovat.tabprofile.FragmentProfile;

import java.util.Objects;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    SharedPreferences sharedPreferences;
    FragmentProfile fragmentProfile;
    Dialog dialogLogout;
    Permissionruntime permissionruntime;
    CheckPermission checkPermission;
    boolean ck1 = true;
    @BindView(R.id.iv_search)
    ImageView ivSearch;
    @BindView(R.id.rl_sv)
    RelativeLayout rlSv;
    @BindView(R.id.iv_profile)
    ImageView ivProfile;
    @BindView(R.id.rl_user)
    RelativeLayout rlUser;
    @BindView(R.id.iv_logout)
    ImageView ivLogout;
    @BindView(R.id.rl_logout)
    RelativeLayout rlLogout;
    @BindView(R.id.rl_main)
    RelativeLayout rlMain;
    @BindView(R.id.rl_dodientu)
    RLCategory rlDodientu;
    @BindView(R.id.rl_thucung)
    RLCategory rlThucung;
    @BindView(R.id.ln_add)
    LinearLayout lnAdd;
    @BindView(R.id.rl_xe)
    RLCategory rlXe;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        init();
        initAction();


    }


    private void init() {
        permissionruntime = new Permissionruntime(this);
        checkPermission = new CheckPermission(this);
        sharedPreferences = getSharedPreferences("Login", MODE_PRIVATE);
        fragmentProfile = new FragmentProfile();


    }


    @Override
    public void onBackPressed() {
        ivProfile.setImageResource(R.drawable.ic_useroff);
        super.onBackPressed();
    }

    private void initAction() {
        rlSv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SearchActivity.class));
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            }
        });
        rlUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkPermission.checkReadExternalPermission() && checkPermission.checkWriteExternalPermission() && checkPermission.checkCameraPermission()) {
                    if (ck1) {
                        ivProfile.setImageResource(R.drawable.ic_useron);
                        fragmentManager = getSupportFragmentManager();
                        fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.ln_add, new FragmentProfile(), "profile").addToBackStack("profile");
                        fragmentTransaction.commit();
                        Log.d("AAA", "tao moi");
                        ck1 = false;
                    } else {
                        Log.d("AAA", "huy");
                        ivProfile.setImageResource(R.drawable.ic_useroff);
                        fragmentManager = getSupportFragmentManager();
                        fragmentManager.popBackStack();
                        ck1 = true;
                    }
                } else {
                    permissionruntime.requestPermissionCameraStrorge(null);
                }


            }
        });

        rlLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogLogout = new Dialog(Objects.requireNonNull(MainActivity.this));
                dialogLogout.setContentView(R.layout.dialog_logout);
                Objects.requireNonNull(dialogLogout.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                Button btAdd = dialogLogout.findViewById(R.id.bt_logout);
                Button btCancel = dialogLogout.findViewById(R.id.bt_cancel);
                dialogLogout.show();
                btAdd.setOnClickListener(new View.OnClickListener() {
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
                btCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogLogout.cancel();
                    }
                });


            }
        });
        rlDodientu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PostActivity.class);
                intent.putExtra("IdCategoryparent", "5c7348cf2583740004db7348");
                startActivity(intent);
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);

            }
        });
        rlXe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PostActivity.class);
                intent.putExtra("IdCategoryparent", "5c7348b52583740004db7347");
                startActivity(intent);
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);

            }
        });


    }

}



