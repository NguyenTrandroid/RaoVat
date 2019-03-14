package com.example.raovat.sell.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
    RelativeLayout rlNext;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    OnSendData onSendData;
    EditText edtContent;
    ImageView ivBack;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onSendData = (OnSendData) getContext();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, null);
        rlNext = view.findViewById(R.id.rl_next);
        edtContent = view.findViewById(R.id.edt_content);
        ivBack = view.findViewById(R.id.iv_back);
        fragmentManager = getFragmentManager();
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
        rlNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtContent.getText().toString().trim().equals("")) {
                    Toast.makeText(getContext(), "Mô tả không được trống", Toast.LENGTH_SHORT).show();
                } else {
                    onSendData.sendDescription(edtContent.getText().toString());
                    fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.rl_sellMain, new FragmentB6()).addToBackStack("B6");
                    fragmentTransaction.commit();
                }
//                String text = String.valueOf(edtContent.getText());
//                String[] multiLines = text.split("\n");
//                String s="";
//                for (int i =0;i<multiLines.length;i++){
//
//                    s=s+multiLines[i]+"\n";
//                }
//                Log.d("AAA",s);


            }
        });
    }

    @Override
    public void onDestroy() {
        onSendData.sendDescription(edtContent.getText().toString());

        super.onDestroy();
    }
}
