package com.example.raovat.sell.fragment;

import android.os.Bundle;
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

public class FragmentB7 extends Fragment {
    Button btnNext, btnEnd;
    FragmentTransaction fragmentTransaction;
    FragmentManager fragmentManager;
    OnSendData onSendData;
    EditText edtTitle;
    ImageView ivBack;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onSendData = (OnSendData) getContext();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_address, null);
        btnNext = view.findViewById(R.id.btn_next);
        btnEnd = view.findViewById(R.id.btn_end);
        edtTitle = view.findViewById(R.id.ed_title);
        ivBack = view.findViewById(R.id.iv_back);
        fragmentManager = getFragmentManager();
        initAction();
        return view;

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        onSendData.sendAddress(edtTitle.getText().toString());

    }

    private void initAction() {
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fragmentManager.popBackStack();
            }
        });
        btnEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getFragmentManager().popBackStack(getFragmentManager().getBackStackEntryAt(0).getId(), FragmentManager.POP_BACK_STACK_INCLUSIVE);


            }
        });
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSendData.sendAddress(edtTitle.getText().toString());
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.rl_sellMain, new FragmentB4()).addToBackStack("B5");
                fragmentTransaction.commit();
            }


    });

}

    @Override
    public void onDestroyView() {
        onSendData.sendAddress(edtTitle.getText().toString());
        super.onDestroyView();
    }
}
