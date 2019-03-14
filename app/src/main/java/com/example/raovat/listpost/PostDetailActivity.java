package com.example.raovat.listpost;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.raovat.Models.Post;
import com.example.raovat.R;
import com.example.raovat.listpost.adapter.PagerAdapter;
import com.google.gson.Gson;
import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;

public class PostDetailActivity extends AppCompatActivity {
    Post post;

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.rl_call)
    RelativeLayout rlCall;
    @BindView(R.id.rl_sms)
    RelativeLayout rlSms;
    @BindView(R.id.dots_indicator)
    WormDotsIndicator dotsIndicator;
    @BindView(R.id.tv_postName)
    TextView tvPostName;
    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.tv_Description)
    TextView tvDescription;
    @BindView(R.id.tv_Address)
    TextView tvAddress;
    @BindView(R.id.tv_date)
    TextView tvDate;
    @BindView(R.id.iv_sms)
    ImageView ivSms;
    @BindView(R.id.cv_call)
    CardView cvCall;
    @BindView(R.id.vp_imgPost)
    ViewPager vpImgPost;
    @BindView(R.id.iv_notImg)
    ImageView ivNotImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail2);
        ButterKnife.bind(this);
        init();
        initAction();
    }

    private void initAction() {
        rlCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.fromParts("tel", post.getPhoneNumber() + "", null)));
            }
        });
        rlSms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.fromParts("sms", post.getPhoneNumber() + "", null)));

            }
        });
    }

    private void init() {
        Intent intent = getIntent();
        if (intent != null) {
            Gson gson = new Gson();
            post = gson.fromJson(intent.getStringExtra("PostNew"), Post.class);
            Log.d("AAA", post.getPostName());
            tvPostName.setText(post.getPostName());
            tvPrice.setText(post.getPrice() + " " + "đ");
            tvDescription.setText(post.getDescription());
            tvAddress.setText(post.getAddress());
            DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            tvDate.setText(df.format(post.getPostDate()));
            if (post.getPostUrl().size() != 0) {
                PagerAdapter adapter = new PagerAdapter(this, post.getPostUrl());
                vpImgPost.setAdapter(adapter);
                dotsIndicator.setViewPager(vpImgPost);
            } else {
                ivNotImg.setVisibility(View.VISIBLE);
                dotsIndicator.setVisibility(View.INVISIBLE);


            }


        }
    }
}
