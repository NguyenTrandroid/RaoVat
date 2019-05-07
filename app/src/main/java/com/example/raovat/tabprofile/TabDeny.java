package com.example.raovat.tabprofile;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;


import com.example.raovat.Models.Post;
import com.example.raovat.R;

import com.example.raovat.Utils.SLoading;
import com.example.raovat.api.APIClient;
import com.example.raovat.api.APIService;
import com.example.raovat.sell.SellActivity;
import com.example.raovat.tabprofile.Adapter.DenyAdapter;
import com.google.gson.JsonObject;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class TabDeny extends Fragment {
    CardView cvSell;
    SLoading sLoading;
    ProgressBar progressBar;
    final APIService service = APIClient.getClient();
    public ArrayList<Post> listPostDeny;
    public RecyclerView recyclerView;
    SharedPreferences sharedPreferences;
    public DenyAdapter denyAdapter;
    public static boolean checkupdate = false;
    Dialog dialogDel;

    @Override
    public void onResume() {
        super.onResume();
        getListPostUserDeny(sharedPreferences.getString("IdUser", ""));

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tabprofile, null);
        cvSell = view.findViewById(R.id.cv_sell);
        recyclerView = view.findViewById(R.id.rv_PostUser);
        progressBar = view.findViewById(R.id.progressBar);


        init();
        initAction();


        return view;
    }

    private void init() {

        sLoading = new SLoading(getContext());
        listPostDeny = new ArrayList<>();
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));
        sharedPreferences = getContext().getSharedPreferences("Login", Context.MODE_PRIVATE);
        Log.d("AAA", sharedPreferences.getString("IdUser", ""));

    }

    private void initAction() {
        cvSell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), SellActivity.class));
            }
        });
    }

    public void getListPostUserDeny(String id) {
        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.INVISIBLE);

        service.ListPostUser(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Post>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<Post> posts) {
                        for (int i = 0; i < posts.size(); i++) {
                            if (posts.get(i).getStatus()) {
                                listPostDeny.add(posts.get(i));
                            }
                        }


                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("AAA", e + "");
                        progressBar.setVisibility(View.INVISIBLE);

                    }

                    @Override
                    public void onComplete() {
                        final Calendar cal1 = Calendar.getInstance();
                        final Calendar cal2 = Calendar.getInstance();
//                        Collections.sort(listPostDeny, new Comparator<Post>() {
//
//                            @Override
//                            public int compare(Post o1, Post o2) {
//                                cal1.setTime(o1.getPostDate());
//                                cal2.setTime(o2.getPostDate());
//                                return (int) (cal2.getTimeInMillis() - cal1.getTimeInMillis());
//                            }
//                        });
                        Collections.sort(listPostDeny, new Comparator<Post>() {
                            public int compare(Post o1, Post o2) {
                                return o2.getPostDate().compareTo(o1.getPostDate());
                            }
                        });
                        denyAdapter = new DenyAdapter(getContext(), listPostDeny);
                        recyclerView.setAdapter(denyAdapter);
                        progressBar.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);


                    }
                });
    }

    @Override
    public boolean onContextItemSelected(final MenuItem item) {
        if (item.getItemId() == 123) {
            dialogDel = new Dialog(Objects.requireNonNull(getContext()));
            dialogDel.setContentView(R.layout.dialog_delete);
            Objects.requireNonNull(dialogDel.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            Button btAdd = dialogDel.findViewById(R.id.bt_logout);
            Button btCancel = dialogDel.findViewById(R.id.bt_cancel);
            dialogDel.show();
            btAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sLoading.show();
                    service.delPostUser(listPostDeny.get((item.getGroupId())).getId())
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Observer<JsonObject>() {
                                @Override
                                public void onSubscribe(Disposable d) {

                                }

                                @Override
                                public void onNext(JsonObject object) {
                                    Log.d("AAA", object + "");

                                }

                                @Override
                                public void onError(Throwable e) {
                                    Log.d("AAA", e + "");
                                    sLoading.dismiss();
                                    dialogDel.dismiss();

                                }

                                @Override
                                public void onComplete() {
                                    listPostDeny.remove(item.getGroupId());
                                    denyAdapter.notifyDataSetChanged();
                                    sLoading.dismiss();
                                    Toast.makeText(getContext(), "Thành công!", Toast.LENGTH_SHORT).show();
                                    dialogDel.dismiss();
                                }
                            });

                }
            });
            btCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialogDel.cancel();
                }
            });


        }
        if (item.getItemId() == 124) {
            sLoading.show();
            Date date = Calendar.getInstance().getTime();
            JsonObject jsonObject = new JsonObject();
            Post post = listPostDeny.get((item.getGroupId()));
            jsonObject.addProperty("PostName", post.getPostName());
            jsonObject.addProperty("Price", post.getPrice());
            jsonObject.addProperty("Address", post.getAddress());
            jsonObject.addProperty("PhoneNumber", post.getPhoneNumber());
            jsonObject.addProperty("Description", post.getDescription());
            jsonObject.addProperty("UserId", post.getUserId());
            jsonObject.addProperty("PostDate", String.valueOf(date));
            jsonObject.addProperty("AreaId", post.getAreaId());
            jsonObject.addProperty("CategoryChildId", post.getCategoryChildId());
            jsonObject.addProperty("Status", false);
            service.updatePost(listPostDeny.get((item.getGroupId())).getId(), jsonObject)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<Post>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(Post post) {
                            sLoading.dismiss();
                            Log.d("AAA", post.getStatus() + "");

                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.d("AAA", e + "");
                            sLoading.dismiss();

                        }

                        @Override
                        public void onComplete() {
                            listPostDeny.remove(item.getGroupId());
                            denyAdapter.notifyDataSetChanged();
                            sLoading.dismiss();
                            Toast.makeText(getContext(), "Thành công!", Toast.LENGTH_SHORT).show();
                            checkupdate = true;

                        }
                    });
        }
        return super.onContextItemSelected(item);
    }

}
