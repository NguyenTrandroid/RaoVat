package com.example.raovat.listpost;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.raovat.Models.Post;
import com.example.raovat.R;
import com.google.gson.Gson;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FragmentPostDetail extends Fragment {
    RelativeLayout rlCall;
    RelativeLayout rlSms;
    Post post;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        Gson gson = new Gson();
        if (bundle != null) {
             post = gson.fromJson(bundle.getString("PostNew"), Post.class);
        }


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_post_detail, null);
        rlCall = view.findViewById(R.id.rl_call);
        rlSms = view.findViewById(R.id.rl_sms);
        initAction();
        return view;
    }

    private void initAction() {

    }
}
