package com.example.raovat.sell.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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

import java.text.DecimalFormat;
import java.text.ParseException;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;


public class FragmentB6 extends Fragment {
    EditText edtMoney;
    EditText edtSdt;
    SharedPreferences sharedPreferences;
    RelativeLayout rlNext;
    FragmentManager fragmentManager;
    OnSendData onSendData;
    String price;
    String sdt = "";
    ImageView ivBack;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onSendData = (OnSendData) getContext();
        Bundle bundle = getArguments();
        if (bundle != null) {
            price = bundle.getString("Price");
            sdt = bundle.getString("Sdt")+"";
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_price, null);
        sharedPreferences = getContext().getSharedPreferences("Login", Context.MODE_PRIVATE);
        edtMoney = view.findViewById(R.id.edt_money);
        rlNext = view.findViewById(R.id.rl_next);
        edtMoney.addTextChangedListener(new NumberTextWatcher(edtMoney));
        fragmentManager = getFragmentManager();
        edtSdt = view.findViewById(R.id.edt_sdt);
        ivBack = view.findViewById(R.id.iv_back);
        if (sdt.equals("")) {
            edtSdt.setText(sharedPreferences.getString("Sdt", ""));
        } else {
            edtSdt.setText(sdt);
        }

        edtMoney.setText(price);
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
                if (edtMoney.getText().toString().trim().equals("") || edtSdt.getText().toString().trim().equals("")) {
                    Toast.makeText(getContext(), "Thông tin không được trống!", Toast.LENGTH_SHORT).show();
                } else {
                    onSendData.sendPriceSdt(edtMoney.getText().toString(), edtSdt.getText().toString());
                    getFragmentManager().popBackStack(getFragmentManager().getBackStackEntryAt(0).getId(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
                }


            }
        });
    }

    @Override
    public void onDestroy() {
        onSendData.sendPriceSdt(edtMoney.getText().toString() + "", edtSdt.getText().toString());

        super.onDestroy();
    }

    class NumberTextWatcher implements TextWatcher {

        private DecimalFormat df;
        private DecimalFormat dfnd;
        private boolean hasFractionalPart;

        private EditText et;

        public NumberTextWatcher(EditText et) {
            df = new DecimalFormat("#,###.##");
            df.setDecimalSeparatorAlwaysShown(true);
            dfnd = new DecimalFormat("#,###");
            this.et = et;
            hasFractionalPart = false;
        }

        @SuppressWarnings("unused")
        private static final String TAG = "NumberTextWatcher";

        public void afterTextChanged(Editable s) {
            et.removeTextChangedListener(this);

            try {
                int inilen, endlen;
                inilen = et.getText().length();

                String v = s.toString().replace(String.valueOf(df.getDecimalFormatSymbols().getGroupingSeparator()), "");
                Number n = df.parse(v);
                int cp = et.getSelectionStart();
                if (hasFractionalPart) {
                    et.setText(df.format(n));
                } else {
                    et.setText(dfnd.format(n));
                }
                endlen = et.getText().length();
                int sel = (cp + (endlen - inilen));
                if (sel > 0 && sel <= et.getText().length()) {
                    et.setSelection(sel);
                } else {
                    // place cursor at the end?
                    et.setSelection(et.getText().length() - 1);
                }
            } catch (NumberFormatException nfe) {
                // do nothing?
            } catch (ParseException e) {
                // do nothing?
            }

            et.addTextChangedListener(this);
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (s.toString().contains(String.valueOf(df.getDecimalFormatSymbols().getDecimalSeparator()))) {
                hasFractionalPart = true;
            } else {
                hasFractionalPart = false;
            }
        }
    }
}
