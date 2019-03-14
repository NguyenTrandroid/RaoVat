package com.example.raovat.sell.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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

public class FragmentB2 extends Fragment {
    OnSelectCategrory onSelectCategrory;
    RelativeLayout rlNext;
    RecyclerView recyclerView;
    ImageView ivBack;
    TextView txtTitle;
    SLoading sLoading;
    Bundle bundle;
    boolean checknull;
    ArrayList<CategoryParents> listDetail;
    String id;
    int index;
    FragmentTransaction fragmentTransaction;
    FragmentManager fragmentManager;
    OnSendData onSendData;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bundle = getArguments();
        id = bundle.getString("IdCategoryParents");
        onSendData = (OnSendData) getContext();


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sell, null);
        sLoading = new SLoading(getContext());
        listDetail = new ArrayList<>();
        recyclerView = view.findViewById(R.id.rc_categoryParents);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));
        ivBack = view.findViewById(R.id.iv_back);
        txtTitle = view.findViewById(R.id.tv_title);
        rlNext = view.findViewById(R.id.rl_next);
        fragmentManager = getFragmentManager();
        getListCategoryChild(id);
        onSelectCategrory = new OnSelectCategrory() {
            @Override
            public void sendPosition(int position) {
                index = position;
                Log.d("AAA", position + "");


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
                if (!checknull) {
                    Log.d("AAA",listDetail.get(index).getId());
                    onSendData.sendCategoryChildId(listDetail.get(index).getId(), listDetail.get(index).getName());
                    fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.rl_sellMain, new FragmentB3()).addToBackStack("B3");
                    fragmentTransaction.commit();
                } else {
                    Toast.makeText(getContext(), "Không hợp lệ!", Toast.LENGTH_SHORT).show();

                }


            }
        });

    }

    private void getListCategoryChild(String id) {
        sLoading.show();
        final APIService service = APIClient.getClient();
        service.ListPost(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Categoryparen>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Categoryparen categoryparen) {
                        for (int i = 0; i < categoryparen.getCategoryChilds().size(); i++) {
                            listDetail.add(new CategoryParents(categoryparen.getCategoryChilds().get(i).getCategoryChildName(),
                                    categoryparen.getCategoryChilds().get(i).getId()));

                        }


                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("AAA", e + " asdadad");
                        checknull = true;
                        sLoading.dismiss();

                    }

                    @Override
                    public void onComplete() {
                        Log.d("AAA", listDetail.size() + "");
                        B2Adapter b2Adapter = new B2Adapter(onSelectCategrory, getContext(), listDetail);
                        recyclerView.setAdapter(b2Adapter);
                        checknull = false;
                        sLoading.dismiss();


                    }
                });
    }

    @Override
    public void onDestroy() {
        if (!checknull) {
            onSendData.sendCategoryChildId(listDetail.get(index).getId(), listDetail.get(index).getName());
        }
        super.onDestroy();
    }
}
