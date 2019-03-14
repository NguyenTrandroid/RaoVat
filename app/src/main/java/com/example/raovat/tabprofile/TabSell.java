package com.example.raovat.tabprofile;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.Toast;

import com.example.raovat.Models.Categoryparen;
import com.example.raovat.Models.Post;
import com.example.raovat.R;
import com.example.raovat.Utils.SLoading;
import com.example.raovat.api.APIClient;
import com.example.raovat.api.APIService;
import com.example.raovat.listpost.PostActivity;
import com.example.raovat.listpost.adapter.DetailAdapter;
import com.example.raovat.listpost.adapter.PostAdapter;
import com.example.raovat.sell.SellActivity;
import com.google.gson.JsonObject;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

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

public class TabSell extends Fragment {
    CardView cvSell;
    RecyclerView recyclerView;
    ArrayList<Post> listPost;
    ArrayList<Post> listPostDeny;
    SLoading sLoading;
    SharedPreferences sharedPreferences;
    SellAdapter sellAdapter;
    final APIService service = APIClient.getClient();
    SendListPost sendListPost;



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
        init();
        initAction();

        return view;
    }

    private void init() {

        sLoading = new SLoading(getContext());
        listPost = new ArrayList<>();
        listPostDeny = new ArrayList<>();
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));
        sharedPreferences = getContext().getSharedPreferences("Login", Context.MODE_PRIVATE);
        getListPostUser(sharedPreferences.getString("IdUser", ""));
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

    private void getListPostUser(String id) {
        sLoading.show();

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
                            if (!posts.get(i).getStatus()) {
                                listPost.add(posts.get(i));
                            }
                        }


                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("AAA", e + "");
                        sLoading.dismiss();

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
                        sellAdapter = new SellAdapter(getContext(), listPost);
                        recyclerView.setAdapter(sellAdapter);
                        sLoading.dismiss();


                    }
                });


    }

    @Override
    public boolean onContextItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case 121:
                sLoading.show();
                JsonObject jsonObject = new JsonObject();
                Post post = listPost.get(item.getGroupId());
                EventBus.getDefault().post(post);

                jsonObject.addProperty("PostName", post.getPostName());
                jsonObject.addProperty("Price", post.getPrice());
                jsonObject.addProperty("Address", post.getAddress());
                jsonObject.addProperty("PhoneNumber", post.getPhoneNumber());
                jsonObject.addProperty("Description", post.getDescription());
                jsonObject.addProperty("UserId", post.getUserId());
                jsonObject.addProperty("AreaId", post.getAreaId());
                jsonObject.addProperty("CategoryChildId", post.getCategoryChildId());
                jsonObject.addProperty("Status", true);
                service.updateSttPost(listPost.get((item.getGroupId())).getId(), jsonObject)
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
                                Log.d("AAA",e+"");
                                sLoading.dismiss();

                            }

                            @Override
                            public void onComplete() {
                                listPost.remove(item.getGroupId());
                                sellAdapter.notifyDataSetChanged();
                                sLoading.dismiss();
                                Toast.makeText(getContext(), "Thành công!", Toast.LENGTH_SHORT).show();

                            }
                        });


                break;
            case 122:

                sLoading.show();
                service.delPostUser(listPost.get((item.getGroupId())).getId())
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

                            }

                            @Override
                            public void onComplete() {
                                listPost.remove(item.getGroupId());
                                sellAdapter.notifyDataSetChanged();
                                sLoading.dismiss();
                                Toast.makeText(getContext(), "Thành công!", Toast.LENGTH_SHORT).show();

                            }
                        });


                break;

        }
        return super.onContextItemSelected(item);
    }


}
