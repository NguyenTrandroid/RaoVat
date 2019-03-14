package com.example.raovat.sell.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.raovat.Models.Categoryparen2;
import com.example.raovat.R;
import com.example.raovat.Utils.SLoading;
import com.example.raovat.api.APIClient;
import com.example.raovat.api.APIService;
import com.example.raovat.sell.CategoryParents;
import com.example.raovat.sell.OnSelectCategrory;
import com.example.raovat.sell.OnSendData;
import com.example.raovat.sell.adapter.B1Adapter;

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

public class FragmentB1 extends Fragment {
    Bundle bundle;
    ArrayList<CategoryParents> listNameCategoryParents;
    RecyclerView recyclerView;
    ImageView ivBack;
    TextView txtTitle;
    SLoading sLoading;
    OnSelectCategrory onSelectCategrory;
    RelativeLayout rlNext;
    FragmentTransaction fragmentTransaction;
    FragmentManager fragmentManager;
    FragmentB2 fragmentB2;
    int index;
    OnSendData onSendData;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onSendData = (OnSendData) getContext();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sell, null);
        recyclerView = view.findViewById(R.id.rc_categoryParents);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));
        ivBack = view.findViewById(R.id.iv_back);
        txtTitle = view.findViewById(R.id.tv_title);
        rlNext = view.findViewById(R.id.rl_next);
        fragmentManager = getFragmentManager();
        bundle = new Bundle();
        fragmentB2 = new FragmentB2();
        listNameCategoryParents = new ArrayList<>();
        sLoading = new SLoading(getContext());
        loadNameCategoryParents();
        onSelectCategrory = new OnSelectCategrory() {
            @Override
            public void sendPosition(int position) {
                bundle.putString("IdCategoryParents", listNameCategoryParents.get(position).getId());
                index = position;

            }
        };


        initAction();
        return view;
    }

    private void initAction() {
        rlNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onSendData.sendCategoryParents(listNameCategoryParents.get(index).getName());
                fragmentB2.setArguments(bundle);
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.rl_sellMain, fragmentB2).addToBackStack("B2");
                fragmentTransaction.commit();
            }
        });
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getActivity().finish();
            }
        });

    }

    private void loadNameCategoryParents() {
        sLoading.show();
        final APIService service = APIClient.getClient();
        service.getListNameCategoryParents()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Categoryparen2>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Categoryparen2 categoryparen2) {
                        for (int i = 0; i < categoryparen2.getData().size(); i++) {
                            listNameCategoryParents.add(new CategoryParents(categoryparen2.getData().get(i).getCategoryParentName(),
                                    categoryparen2.getData().get(i).getId()));
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        sLoading.dismiss();

                    }

                    @Override
                    public void onComplete() {
                        B1Adapter b1Adapter = new B1Adapter(onSelectCategrory, getActivity(), listNameCategoryParents);
                        recyclerView.setAdapter(b1Adapter);
                        sLoading.dismiss();


                    }
                });

    }

}
