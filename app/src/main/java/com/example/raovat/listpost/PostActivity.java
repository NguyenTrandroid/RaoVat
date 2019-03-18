package com.example.raovat.listpost;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.example.raovat.Models.Address;
import com.example.raovat.Models.Categoryparen;
import com.example.raovat.Models.Categoryparen2;
import com.example.raovat.Models.Data2;
import com.example.raovat.Models.Datum1;
import com.example.raovat.Models.MultiSearch;
import com.example.raovat.Models.Post;
import com.example.raovat.R;
import com.example.raovat.Utils.SLoading;
import com.example.raovat.api.APIClient;
import com.example.raovat.api.APIService;
import com.example.raovat.listpost.adapter.DetailAdapter;
import com.example.raovat.listpost.adapter.PostAdapter;
import com.example.raovat.search.SearchActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class PostActivity extends AppCompatActivity implements OnClickDetail {
    ArrayList<String> listAddress;
    ArrayList<String> listDetail;
    ArrayList<String> listIDDetail;
    ArrayList<Post> listPost;
    ArrayList<Data2> listIdAddress;
    ArrayList<String> listNameCategoryParents;
    ArrayList<Datum1> listIDCategoryParents;
    String iDCategoryParents = "";
    String idAddress;
    String idDetail = "";


    int index;

    SLoading sLoading;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    Bundle bundle;
    FragmentPostDetail fragmentPostDetail;
    final APIService service = APIClient.getClient();


    @BindView(R.id.rl_sv)
    RelativeLayout rlSv;
    @BindView(R.id.spn_address)
    Spinner spnAddress;
    @BindView(R.id.spn_detail)
    Spinner spnDetail;
    @BindView(R.id.rv_detail)
    RecyclerView rvDetail;
    @BindView(R.id.rv_post)
    RecyclerView rvPost;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        ButterKnife.bind(this);
        init();
        initAction();


    }

    private void initAction() {
        spnAddress.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d("AAA", position + "");
                idAddress = listIdAddress.get(position).getId();
                getListSearch(listIdAddress.get(position).getId(), iDCategoryParents, "");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spnDetail.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                listDetail.clear();
                listPost.clear();
                iDCategoryParents = "";
                getListCategoryChild(listIDCategoryParents.get(position).getId());
                getListSearch(idAddress, listIDCategoryParents.get(position).getId(), "");
                iDCategoryParents = listIDCategoryParents.get(position).getId();


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        rlSv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PostActivity.this, SearchActivity.class));
                finish();
            }
        });

    }

    private void init() {
        Intent intent = getIntent();
        if (intent != null) {
            iDCategoryParents = intent.getStringExtra("IdCategoryparent");
        }
        Log.d("AAA", iDCategoryParents + "");
        sLoading = new SLoading(this);
        listPost = new ArrayList<>();
        listAddress = new ArrayList<>();
        listDetail = new ArrayList<>();
        listIdAddress = new ArrayList<>();
        listIDDetail = new ArrayList<>();
        listNameCategoryParents = new ArrayList<>();
        listIDCategoryParents = new ArrayList<>();
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        bundle = new Bundle();
        fragmentPostDetail = new FragmentPostDetail();
        getListNameCategoryParents();
        getListAddress();

        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rvDetail.setLayoutManager(layoutManager);
        rvPost.setLayoutManager(new GridLayoutManager(this, 1));


    }

    @Override
    public void sendDetail(String detail, int position) {
        idDetail = "";
        idDetail = listIDDetail.get(position);
        getListSearch(idAddress, iDCategoryParents, idDetail);


    }

    private void getListSearch(String idAddress, String idCategoryParents, String idDetail) {
        progressBar.setVisibility(View.VISIBLE);
        service.searchMulti(idAddress, idCategoryParents, idDetail)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MultiSearch>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(MultiSearch multiSearch) {
                        listPost.clear();
                        for (int i = 0; i < multiSearch.getData().size(); i++) {
                            if (!multiSearch.getData().get(i).getStatus()) {
                                listPost.add(multiSearch.getData().get(i));
                            }
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("AAA", e + "");

                    }

                    @Override
                    public void onComplete() {
                        final Calendar cal1 = Calendar.getInstance();
                        final Calendar cal2 = Calendar.getInstance();
                        Collections.sort(listPost, new Comparator<Post>() {

                            @Override
                            public int compare(Post o1, Post o2) {
                                cal1.setTime(o1.getPostDate());
                                cal2.setTime(o2.getPostDate());
                                return (int) (cal2.getTimeInMillis() - cal1.getTimeInMillis());
                            }
                        });
                        PostAdapter postAdapter = new PostAdapter(PostActivity.this, listPost);
                        rvPost.setAdapter(postAdapter);
                        progressBar.setVisibility(View.GONE);
                        sLoading.dismiss();


                    }
                });

    }

    private void getListCategoryChild(String id) {


        service.ListPost(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Categoryparen>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Categoryparen categoryparen) {
                        listIDDetail.clear();
                        for (int i = 0; i < categoryparen.getCategoryChilds().size(); i++) {
                            listDetail.add(categoryparen.getCategoryChilds().get(i).getCategoryChildName());
                            listIDDetail.add(categoryparen.getCategoryChilds().get(i).getId());
//                            for (int j = 0; j < categoryparen.getCategoryChilds().get(i).getPosts().size(); j++) {
//                                if (!categoryparen.getCategoryChilds().get(i).getPosts().get(j).getStatus()) {
//                                    listPost.add(categoryparen.getCategoryChilds().get(i).getPosts().get(j));
//                                }
//                            }

                        }


                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("AAA", e + "");
                        progressBar.setVisibility(View.GONE);

                    }

                    @Override
                    public void onComplete() {

                        DetailAdapter detailAdapter = new DetailAdapter(PostActivity.this, listDetail);
                        rvDetail.setAdapter(detailAdapter);


                    }
                });


    }

    private void getListAddress() {
        sLoading.show();
        service.getListAddress()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Address>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Address address) {
                        listIdAddress.addAll(address.getData());
                        for (int i = 0; i < address.getData().size(); i++) {
                            listAddress.add(address.getData().get(i).getAreaName());
                        }


                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(PostActivity.this, android.R.layout.simple_spinner_item, listAddress);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spnAddress.setAdapter(adapter);
                        spnAddress.setSelection(2);
                        idAddress = listIdAddress.get(2).getId();
                    }
                });

    }

    private void getListNameCategoryParents() {

        service.getListNameCategoryParents()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Categoryparen2>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Categoryparen2 categoryparen2) {
                        listIDCategoryParents.addAll(categoryparen2.getData());
                        for (int i = 0; i < categoryparen2.getData().size(); i++) {
                            if (categoryparen2.getData().get(i).getId().equals(iDCategoryParents)) {
                                index = i;
                            }
                            listNameCategoryParents.add(categoryparen2.getData().get(i).getCategoryParentName());
                        }


                    }

                    @Override
                    public void onError(Throwable e) {
                        sLoading.dismiss();

                    }

                    @Override
                    public void onComplete() {
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(PostActivity.this, android.R.layout.simple_spinner_item, listNameCategoryParents);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spnDetail.setAdapter(adapter);
                        spnDetail.setSelection(index);
                        sLoading.dismiss();


                    }
                });


    }


}
