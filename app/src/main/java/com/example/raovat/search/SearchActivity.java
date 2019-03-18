package com.example.raovat.search;

import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.example.raovat.Models.Post;
import com.example.raovat.Models.SearchKey;
import com.example.raovat.R;
import com.example.raovat.api.APIClient;
import com.example.raovat.api.APIService;
import com.example.raovat.listpost.adapter.PostAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

public class SearchActivity extends AppCompatActivity {
    ArrayList<String> list;
    ArrayList<String> listTemp;
    ArrayList<Post> listSearch;
    boolean checkSearch;
    boolean checkKey = true;

    ArrayAdapter<String> itemsAdapter;
    Realm realm;
    RealmList<String> realmList;
    final APIService service = APIClient.getClient();


    @BindView(R.id.sv)
    SearchView sv;
    @BindView(R.id.lv_search)
    ListView lvSearch;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.rv_search)
    RecyclerView rvSearch;
    @BindView(R.id.cv_search)
    CardView cvSearch;
    @BindView(R.id.rl_notFound)
    RelativeLayout rlNotFound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        init();
        initAction();

    }

    private void initAction() {
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(final String query) {
                cvSearch.setVisibility(View.GONE);
                if (list.size() == 0) {
                    Log.d("AAA", "null");
                    saveKey(query);
                    list.add(query);
                } else {
                    for (int i = 0; i < list.size(); i++) {
                        if (query.equals(list.get(i))) {
                            checkKey = false;
                            break;
                        }
                    }
                    if (checkKey) {
                        saveKey(query);
                        list.add(query);
                    }
                }

                listSearch.clear();
                getListSearch(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                listTemp.clear();
                if (newText.length() == 0) {
                    cvSearch.setVisibility(View.GONE);
                    listTemp.addAll(list);
                    Log.d("AAA", listTemp.size() + "");
                } else {
                    cvSearch.setVisibility(View.VISIBLE);
                    for (int i = 0; i < list.size(); i++) {
                        try {
                            if (list.get(i).toLowerCase().contains(newText.toLowerCase())) {
                                listTemp.add(list.get(i));
                            }
                        } catch (Exception e) {

                        }
                    }
                    Log.d("AAA", listTemp.size() + "");
                }
                ArrayAdapter<String> itemsAdapterTemp =
                        new ArrayAdapter<String>(SearchActivity.this, android.R.layout.simple_list_item_1, listTemp);
                lvSearch.setAdapter(itemsAdapterTemp);


                return false;
            }
        });

        lvSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("AAA", listTemp.get(position));
                sv.setQuery(listTemp.get(position), true);

                cvSearch.setVisibility(View.GONE);
            }
        });


    }

    private void saveKey(final String str) {

        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm bgRealm) {
                SearchHistory history = bgRealm.createObject(SearchHistory.class);
                history.setStr(str);


            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                // Transaction was a success.
                Log.d("AAA", "thanh cong");
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                // Transaction failed and was automatically canceled.
                Log.d("AAA", "" + error);
            }
        });
    }

    private void readRealm(ArrayList<String> list) {
        RealmResults<SearchHistory> histories = realm.where(SearchHistory.class).findAll();
        for (SearchHistory history : histories) {
            list.add(history.getStr());
        }

    }

    private void init() {
        realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.commitTransaction();
        sv.onActionViewExpanded();
        sv.setQueryHint(Html.fromHtml("<font color = #000000>" + "Tìm kiếm..." + "</font>"));
        realmList = new RealmList<>();
        list = new ArrayList<>();
        listTemp = new ArrayList<>();
        readRealm(list);
        rvSearch.setLayoutManager(new GridLayoutManager(this, 1));
        listSearch = new ArrayList<>();

    }

    private void getListSearch(String key) {
        rlNotFound.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        service.listSearch(key)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SearchKey>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(SearchKey searchKey) {
                        checkSearch = searchKey.getStatus();
                        if (searchKey.getStatus()) {
                            listSearch.addAll(searchKey.getData());
                        }


                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("AAA", e + "");
                        progressBar.setVisibility(View.INVISIBLE);
                        rlNotFound.setVisibility(View.VISIBLE);


                    }

                    @Override
                    public void onComplete() {
                        if (checkSearch) {
                            final Calendar cal1 = Calendar.getInstance();
                            final Calendar cal2 = Calendar.getInstance();
                            Collections.sort(listSearch, new Comparator<Post>() {

                                @Override
                                public int compare(Post o1, Post o2) {
                                    cal1.setTime(o1.getPostDate());
                                    cal2.setTime(o2.getPostDate());
                                    return (int) (cal2.getTimeInMillis() - cal1.getTimeInMillis());
                                }
                            });
                            PostAdapter postAdapter = new PostAdapter(SearchActivity.this, listSearch);
                            rvSearch.setAdapter(postAdapter);
                            rlNotFound.setVisibility(View.INVISIBLE);

                        } else {
                            rlNotFound.setVisibility(View.VISIBLE);
                        }
                        progressBar.setVisibility(View.INVISIBLE);


                    }
                });
    }
}

