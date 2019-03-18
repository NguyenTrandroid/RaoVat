package com.example.raovat.sell.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.raovat.R;
import com.example.raovat.sell.OnSendData;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class FragmentB5 extends Fragment {
    Button btnNext, btnEnd;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    OnSendData onSendData;
    EditText edtContent;
    ImageView ivBack;
    String description;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onSendData = (OnSendData) getContext();
        Bundle bundle = getArguments();
        if (bundle != null) {
            description = bundle.getString("Description");        }

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, null);
        btnNext = view.findViewById(R.id.btn_next);
        btnEnd = view.findViewById(R.id.btn_end);
        edtContent = view.findViewById(R.id.edt_content);
        ivBack = view.findViewById(R.id.iv_back);
        fragmentManager = getFragmentManager();
        edtContent.setText(description);

        initAction();
        return view;

    }

    private void initAction() {
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fragmentManager.popBackStack();
            }
        });
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack(getFragmentManager().getBackStackEntryAt(0).getId(), FragmentManager.POP_BACK_STACK_INCLUSIVE);

            }
        });
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onSendData.sendDescription(edtContent.getText().toString());
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.rl_sellMain, new FragmentB6()).addToBackStack("B6");
                fragmentTransaction.commit();


            }
        });
    }

    @Override
    public void onDestroy() {
        onSendData.sendDescription(edtContent.getText().toString());

        super.onDestroy();
    }
}
