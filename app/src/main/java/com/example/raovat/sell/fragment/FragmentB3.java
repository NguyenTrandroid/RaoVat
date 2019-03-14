package com.example.raovat.sell.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.raovat.Models.Address;
import com.example.raovat.Models.Categoryparen;
import com.example.raovat.R;
import com.example.raovat.Utils.SLoading;
import com.example.raovat.api.APIClient;
import com.example.raovat.api.APIService;
import com.example.raovat.listpost.PostActivity;
import com.example.raovat.listpost.adapter.DetailAdapter;
import com.example.raovat.listpost.adapter.PostAdapter;
import com.example.raovat.sell.CategoryParents;
import com.example.raovat.sell.OnSelectCategrory;
import com.example.raovat.sell.OnSendData;
import com.example.raovat.sell.adapter.B2Adapter;
import com.example.raovat.sell.adapter.B3Adapter;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class FragmentB3 extends Fragment {
    OnSelectCategrory onSelectCategrory;
    RelativeLayout rlNext;
    RecyclerView recyclerView;
    ImageView ivBack;
    TextView txtTitle;
    SLoading sLoading;
    Bundle bundle;
    int index;
    FragmentTransaction fragmentTransaction;
    FragmentManager fragmentManager;
    OnSendData onSendData;


    ArrayList<CategoryParents> listAddress;
    String id;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onSendData = (OnSendData) getContext();


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sell, null);
        sLoading = new SLoading(getContext());
        listAddress = new ArrayList<>();
        recyclerView = view.findViewById(R.id.rc_categoryParents);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));
        ivBack = view.findViewById(R.id.iv_back);
        txtTitle = view.findViewById(R.id.tv_title);
        rlNext = view.findViewById(R.id.rl_next);
        txtTitle.setText("Địa chỉ");
        fragmentManager = getFragmentManager();
        getAddress();
        onSelectCategrory = new OnSelectCategrory() {
            @Override
            public void sendPosition(int position) {
                index = position;

            }
        };
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
                onSendData.sendIDAddress(listAddress.get(index).getId(), listAddress.get(index).getName());
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.rl_sellMain, new FragmentB7()).addToBackStack("B4");
                fragmentTransaction.commit();


            }
        });
    }


    private void getAddress() {
        sLoading.show();
        final APIService service = APIClient.getClient();
        service.getListAddress()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Address>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Address address) {
                        for (int i = 0; i < address.getData().size(); i++) {
                            listAddress.add(new CategoryParents(address.getData().get(i).getAreaName(),
                                    address.getData().get(i).getId()));

                        }


                    }

                    @Override
                    public void onError(Throwable e) {
                        sLoading.dismiss();

                    }

                    @Override
                    public void onComplete() {
                        B3Adapter b3Adapter = new B3Adapter(onSelectCategrory, getContext(), listAddress);
                        recyclerView.setAdapter(b3Adapter);
                        sLoading.dismiss();


                    }
                });
    }

    @Override
    public void onDestroy() {
        onSendData.sendIDAddress(listAddress.get(index).getId(), listAddress.get(index).getName());

        super.onDestroy();
    }
}
