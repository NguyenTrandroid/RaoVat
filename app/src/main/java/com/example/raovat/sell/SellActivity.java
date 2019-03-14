package com.example.raovat.sell;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.raovat.Models.InfoCategoryparen;
import com.example.raovat.Models.PostNew;

import com.example.raovat.R;
import com.example.raovat.Utils.SLoading;
import com.example.raovat.addphoto.FragmentGalleryPhotoList;
import com.example.raovat.addphoto.OnPhotoListSelect;
import com.example.raovat.api.APIClient;
import com.example.raovat.api.APIService;
import com.example.raovat.image_picker_module.furntion.ImageFileLoader;
import com.example.raovat.image_picker_module.furntion.ImageLoaderListener;
import com.example.raovat.image_picker_module.model.Folder;
import com.example.raovat.image_picker_module.model.Image;
import com.example.raovat.sell.adapter.ImageSellAdapter;
import com.example.raovat.sell.fragment.FragmentB1;
import com.example.raovat.sell.fragment.FragmentB2;
import com.example.raovat.sell.fragment.FragmentB3;
import com.example.raovat.sell.fragment.FragmentB4;
import com.example.raovat.sell.fragment.FragmentB5;
import com.example.raovat.sell.fragment.FragmentB6;
import com.example.raovat.sell.fragment.FragmentB7;
import com.google.gson.JsonObject;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;


public class SellActivity extends AppCompatActivity implements OnPhotoListSelect, OnSendData, SendIdPost {
    FragmentTransaction fragmentTransaction;
    FragmentManager fragmentManager;
    ImageFileLoader imageFileLoader;
    ArrayList<File> excludedImages;
    ArrayList<String> listImg;
    FragmentB6 fragmentB6;
    String address = "";
    SLoading sLoading;
    final APIService service = APIClient.getClient();


    String CategoryChildId = "";
    String AreaId = "";
    SendIdPost sendIdPost;

    ArrayList<InfoCategoryparen> categoryParentName;
    FragmentGalleryPhotoList fragmentGalleryPhotoList;
    SharedPreferences sharedPreferences;
    Bundle bundle;
    @BindView(R.id.tl_sell)
    RelativeLayout tlSell;
    @BindView(R.id.rv_imgSell)
    RecyclerView rvImgSell;
    @BindView(R.id.iv_addimg)
    ImageView ivAddimg;
    @BindView(R.id.txt_addimg)
    TextView txtAddimg;
    @BindView(R.id.txt_chupanh)
    TextView txtChupanh;
    @BindView(R.id.rl_rv)
    RelativeLayout rlRv;
    @BindView(R.id.rl_sellMain)
    RelativeLayout rlSellMain;
    @BindView(R.id.tv_categoryparent)
    TextView tvCategoryparent;
    @BindView(R.id.rl_categoryparent)
    RelativeLayout rlCategoryparent;
    @BindView(R.id.tv_CategoryChilds)
    TextView tvCategoryChilds;
    @BindView(R.id.rl_CategoryChilds)
    RelativeLayout rlCategoryChilds;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rl_title)
    RelativeLayout rlTitle;
    @BindView(R.id.tv_Price)
    TextView tvPrice;
    @BindView(R.id.rl_Price)
    RelativeLayout rlPrice;
    @BindView(R.id.tv_Description)
    TextView tvDescription;
    @BindView(R.id.rl_Description)
    RelativeLayout rlDescription;
    @BindView(R.id.tv_Address)
    TextView tvAddress;
    @BindView(R.id.rl_Address)
    RelativeLayout rlAddress;
    @BindView(R.id.tv_Sdt)
    TextView tvSdt;
    @BindView(R.id.rl_Sdt)
    RelativeLayout rlSdt;
    @BindView(R.id.rl_ok)
    RelativeLayout rlOk;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell);
        ButterKnife.bind(this);
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.rl_sellMain, new FragmentB1()).addToBackStack("B1");
        fragmentTransaction.commit();
        init();
        initAction();


    }


    private void initAction() {
        ivAddimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentGalleryPhotoList.setArguments(bundle);
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.rl_sellMain, fragmentGalleryPhotoList).addToBackStack("galery");
                fragmentTransaction.commit();


            }
        });
        rlOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                createPost(tvTitle.getText().toString(), tvPrice.getText().toString(), tvAddress.getText().toString(), tvSdt.getText().toString(),
                        tvDescription.getText().toString(), sharedPreferences.getString("IdUser", ""),
                        AreaId, CategoryChildId);


            }
        });
        rlTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager.popBackStack();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.rl_sellMain, new FragmentB4()).addToBackStack("b4");
                fragmentTransaction.commit();


            }
        });
        rlPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager.popBackStack();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.rl_sellMain, new FragmentB6()).addToBackStack("b6");
                fragmentTransaction.commit();

            }
        });
        rlAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager.popBackStack();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.rl_sellMain, new FragmentB3()).addToBackStack("b3");
                fragmentTransaction.commit();

            }
        });
        rlSdt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fragmentManager.popBackStack();
                bundle.putString("Price", tvPrice.getText().toString());
                fragmentB6.setArguments(bundle);
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.rl_sellMain, fragmentB6).addToBackStack("b6");
                fragmentTransaction.commit();


            }
        });

        rlCategoryparent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager.popBackStack();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.rl_sellMain, new FragmentB1()).addToBackStack("b1");
                fragmentTransaction.commit();


            }
        });
        rlDescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager.popBackStack();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.rl_sellMain, new FragmentB5()).addToBackStack("b5");
                fragmentTransaction.commit();

            }
        });
    }

    private void createPost(final String postName, final String Price, final String Address,
                            final String PhoneNumber, final String Description,
                            final String UserId, final String AreaId, final String CategoryChildId) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("PostName", postName);
        jsonObject.addProperty("Price", Price);
        jsonObject.addProperty("Address", Address);
        jsonObject.addProperty("PhoneNumber", PhoneNumber);
        jsonObject.addProperty("Description", Description);
        jsonObject.addProperty("UserId", UserId);
        jsonObject.addProperty("AreaId", AreaId);
        jsonObject.addProperty("CategoryChildId", CategoryChildId);
        sLoading.show();

        service.createPost(jsonObject).
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new Observer<PostNew>() {
                    String id = "";

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(PostNew postNew) {
                        id = postNew.getData().getId();


                    }

                    @Override
                    public void onError(Throwable e) {
                        sLoading.dismiss();
                        Log.d("AAA", e + "");

                    }

                    @Override
                    public void onComplete() {
                        sendIdPost.sendIDPost(id);


                    }
                });
    }


    private void init() {
        sLoading = new SLoading(this);
        sharedPreferences = getSharedPreferences("Login", Context.MODE_PRIVATE);
        bundle = new Bundle();
        sendIdPost = (SendIdPost) this;
        listImg = new ArrayList<>();
        categoryParentName = new ArrayList<>();
        excludedImages = new ArrayList<>();
        fragmentB6 = new FragmentB6();

        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rvImgSell.setLayoutManager(layoutManager);
        fragmentGalleryPhotoList = new FragmentGalleryPhotoList();

        imageFileLoader = new ImageFileLoader(this);
        imageFileLoader.loadDeviceImages(true, false, excludedImages, new ImageLoaderListener() {
            @Override
            public void onImageLoaded(List<Image> images, List<Folder> folders) {
                bundle.putSerializable("listimage", (Serializable) images);
                bundle.putSerializable("listfolder", (Serializable) folders);

            }

            @Override
            public void onFailed(Throwable throwable) {

            }
        }, true, false, false, false);


    }


    @Override
    public void sendPhotolist(ArrayList<String> list, boolean closeFragment) {
        fragmentManager.popBackStack();
        listImg.addAll(list);
        ImageSellAdapter imageSellAdapter = new ImageSellAdapter(listImg, this);
        rvImgSell.setAdapter(imageSellAdapter);

        File file = new File(list.get(0));
        String file_path = file.getAbsolutePath();
        String[] arrFile = file_path.split("\\.");
        file_path = arrFile[0] + System.currentTimeMillis() + "." + arrFile[1];
        RequestBody requestFile =
                RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("image", file_path, requestFile);
//        final APIService service = APIClient.getClient();
//        service.uploadImage("5c73c9305fcdd417d40d8d6f", body)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Observer<JsonObject>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//
//                    }
//
//                    @Override
//                    public void onNext(JsonObject object) {
//                        Log.d("AAA", object.toString() + "");
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        Log.d("AAA", e + "");
//
//                    }
//
//                    @Override
//                    public void onComplete() {
//
//                    }
//                });

    }


    @Override
    public void sendPostName(String postName) {
        tvTitle.setText(postName);

    }

    @Override
    public void sendPriceSdt(String price, String sdt) {
        tvPrice.setText(price);
        tvSdt.setText(sdt);

    }


    @Override
    public void sendDescription(String description) {
        tvDescription.setText(description);

    }

    @Override
    public void sendIDAddress(String id, String address) {
        this.address = "";
        this.address += address;
        Log.d("AAA", this.address);
        AreaId = "";
        AreaId += id;

    }

    @Override
    public void sendAddress(String address) {
        address = address + ", " + this.address;
        tvAddress.setText(address);
        Log.d("AAA", tvAddress.getText().toString());

    }

    @Override
    public void sendCategoryChildId(String categoryChildId, String name) {
        tvCategoryChilds.setText(name);
        this.CategoryChildId = "";
        this.CategoryChildId += categoryChildId;

    }

    @Override
    public void sendCategoryParents(String name) {
        tvCategoryparent.setText(name);

    }

    Fragment getCurrentFragment() {
        Fragment currentFragment = getSupportFragmentManager()
                .findFragmentById(R.id.rl_sellMain);
        return currentFragment;
    }


    @Override
    public void sendIDPost(String id) {

        sLoading.show();
        for (int i = 0; i < listImg.size(); i++) {
            File file = new File(listImg.get(i));
            String file_path = file.getAbsolutePath();
            String[] arrFile = file_path.split("\\.");
            file_path = arrFile[0] + System.currentTimeMillis() + "." + arrFile[1];
            RequestBody requestFile =
                    RequestBody.create(MediaType.parse("multipart/form-data"), file);
            MultipartBody.Part body =
                    MultipartBody.Part.createFormData("image", file_path, requestFile);

            service.uploadImage(id, body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<JsonObject>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(JsonObject object) {
                        Log.d("AAA", object.toString() + "");

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("AAA", e + "");

                    }

                    @Override
                    public void onComplete() {

                    }
                });
        }

        Toast.makeText(this, "Thành công!", Toast.LENGTH_SHORT).show();
        finish();

    }
}

