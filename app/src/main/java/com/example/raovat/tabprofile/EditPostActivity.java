package com.example.raovat.tabprofile;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.raovat.Models.Categorychild1;
import com.example.raovat.Models.Categoryparen;
import com.example.raovat.Models.InfoCategoryparen;
import com.example.raovat.Models.Post;
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
import com.example.raovat.sell.OnDelImg;
import com.example.raovat.sell.OnSendData;
import com.example.raovat.sell.SendIdPost;
import com.example.raovat.sell.adapter.ImageSellAdapter;
import com.example.raovat.sell.fragment.FragmentB1;
import com.example.raovat.sell.fragment.FragmentB3;
import com.example.raovat.sell.fragment.FragmentB4;
import com.example.raovat.sell.fragment.FragmentB5;
import com.example.raovat.sell.fragment.FragmentB6;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;

import java.io.File;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
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


public class EditPostActivity extends AppCompatActivity implements OnPhotoListSelect, OnSendData, SendIdPost, SendIDCategoryparent, SendPositionRemove, OnDelImg {
    FragmentTransaction fragmentTransaction;
    FragmentManager fragmentManager;
    ImageFileLoader imageFileLoader;
    ArrayList<File> excludedImages;
    ImageSellAdapter imageSellAdapter;
    ArrayList<String> listImg;
    ArrayList<String> listPortUrl;
    ArrayList<String> listImgNew;
    List<MultipartBody.Part> partLists;
    FragmentB6 fragmentB6;
    FragmentB4 fragmentB4;
    Fragment fragmentB5;
    String address = "";
    Post post;
    String nameCategoryChild = "";
    String nameCategoryparent = "";
    String iDCategoryparent = "";
    Uri uri;

    SLoading sLoading;
    final APIService service = APIClient.getClient();
    private Boolean checkDelImg = false;

    String CategoryChildId = "";
    String AreaId = "";
    SendIdPost sendIdPost;
    SendIDCategoryparent sendIDCategoryparent;
    int sizeUrl;

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
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.iv_camera)
    ImageView ivCamera;
    @BindView(R.id.tv_title1)
    TextView tvTitle1;
    @BindView(R.id.tv_title2)
    TextView tvTitle2;
    @BindView(R.id.tv_title3)
    TextView tvTitle3;
    @BindView(R.id.tv_title4)
    TextView tvTitle4;
    @BindView(R.id.tv_text)
    TextView tvText;
    @BindView(R.id.tv_title5)
    TextView tvTitle5;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.rl_load)
    RelativeLayout rlLoad;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_post);
        ButterKnife.bind(this);
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        init();
        initAction();


    }


    private void initAction() {
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ivCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.TITLE, "New Picture");
                values.put(MediaStore.Images.Media.DESCRIPTION, "From your Camera");
                uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                Log.d("AAA", uri.getPath());
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                intent.putExtra("return-data", true);
                startActivityForResult(intent, 123);
            }
        });
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
                if (tvTitle.getText().toString().equals("") || tvPrice.getText().toString().equals("")
                        || tvAddress.getText().toString().equals("") || tvSdt.getText().toString().equals("")
                        || tvCategoryChilds.getText().toString().equals("")) {
                    Toast.makeText(EditPostActivity.this, "Thông tin không được trống!", Toast.LENGTH_SHORT).show();
                } else {
                    updatePost();
                }


                Log.d("AAA", tvTitle.getText().toString() + " " + tvPrice.getText().toString() + " " + tvAddress.getText().toString() + " " + tvSdt.getText().toString() + " " + tvDescription.getText().toString() + " " + post.getUserId() + " " +
                        AreaId + " " + CategoryChildId + " " + post.getId());


            }
        });
        rlTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager.popBackStack();
                bundle.putString("Title", tvTitle.getText().toString());
                fragmentB4.setArguments(bundle);
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.rl_sellMain, fragmentB4).addToBackStack("b4");
                fragmentTransaction.commit();


            }
        });
        rlPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fragmentManager.popBackStack();
                bundle.putString("Sdt", tvSdt.getText().toString());
                bundle.putString("Price", tvPrice.getText().toString());
                fragmentB6.setArguments(bundle);
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.rl_sellMain, fragmentB6).addToBackStack("b6");
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
                bundle.putString("Sdt", tvSdt.getText().toString());
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
                bundle.putString("Description", tvDescription.getText().toString());

                fragmentB5.setArguments(bundle);
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.rl_sellMain, fragmentB5).addToBackStack("b5");
                fragmentTransaction.commit();

            }
        });
    }

    private void updatePost() {
        JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty("PostName", tvTitle.getText().toString());
        jsonObject.addProperty("Price", tvPrice.getText().toString().replace(".", "").trim());
        jsonObject.addProperty("Address", tvAddress.getText().toString());
        jsonObject.addProperty("PhoneNumber", tvSdt.getText().toString());
        jsonObject.addProperty("Description", tvDescription.getText().toString());
        jsonObject.addProperty("UserId", post.getUserId());
        jsonObject.addProperty("AreaId", AreaId);
//         jsonObject.add("PostUrl", listImg);
//        jsonObject.add("FileId", listFileId);
        jsonObject.addProperty("CategoryChildId", CategoryChildId);
        jsonObject.addProperty("Status", post.getStatus());
        sLoading.show();
        service.updatePost(post.getId(), jsonObject)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Post>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Post post) {

                        Log.d("AAA", post.getPostName() + "");

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("AAA", e + "");
                        sLoading.dismiss();

                    }

                    @Override
                    public void onComplete() {
                        Log.d("AAA", "ok");
                        sendIdPost.sendIDPost(post.getId());
                        sLoading.dismiss();
                        Toast.makeText(EditPostActivity.this, "Thành công!", Toast.LENGTH_SHORT).show();


                    }
                });
    }


    private void init() {

        sLoading = new SLoading(this);
        listImg = new ArrayList<>();
        listPortUrl = new ArrayList<>();
        listImgNew = new ArrayList<>();
        partLists = new ArrayList<>();
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rvImgSell.setLayoutManager(layoutManager);
        fragmentGalleryPhotoList = new FragmentGalleryPhotoList();
        Intent intent = getIntent();
        if (intent != null) {
            Gson gson = new Gson();
            post = gson.fromJson(intent.getStringExtra("PostEdit"), Post.class);
            getNameCategoryparent(post.getCategoryChildId());
            tvTitle.setText(post.getPostName());
            tvAddress.setText(post.getAddress());
            tvSdt.setText(post.getPhoneNumber());
            NumberFormat formatter = new DecimalFormat("#,###");
            double myNumber = Double.parseDouble(post.getPrice());
            String formattedNumber = formatter.format(myNumber);
            tvPrice.setText(formattedNumber);
            tvDescription.setText(post.getDescription());
            sizeUrl = post.getPostUrl().size();
            for (int i = 0; i < post.getPostUrl().size(); i++) {
                if (!post.getPostUrl().get(i).toString().trim().equals("")) {
                    listPortUrl.add(post.getPostUrl().get(i));
                    listImg.add(post.getPostUrl().get(i));
                    Log.d("AAA", post.getPostUrl().get(i) + " urlImg");
                }
            }

            imageSellAdapter = new ImageSellAdapter(listImg, this);
            rvImgSell.setAdapter(imageSellAdapter);
            AreaId = post.getAreaId();
            CategoryChildId = post.getCategoryChildId();


        }

        sharedPreferences = getSharedPreferences("Login", Context.MODE_PRIVATE);
        bundle = new Bundle();
        sendIdPost = (SendIdPost) this;
        sendIDCategoryparent = (SendIDCategoryparent) this;

        categoryParentName = new ArrayList<>();
        excludedImages = new ArrayList<>();
        fragmentB6 = new FragmentB6();
        fragmentB4 = new FragmentB4();
        fragmentB5 = new FragmentB5();
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
        listImgNew.addAll(list);
        imageSellAdapter.notifyDataSetChanged();


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
        Log.d("AAA", AreaId + "");

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
        Log.d("AAA", categoryChildId + "");

    }

    @Override
    public void sendCategoryParents(String name) {
        tvCategoryparent.setText(name);

    }

    @Override
    public void sendIDPost(String id) {

        sLoading.show();
        for (int i = 0; i < listImgNew.size(); i++) {
            File file = new File(listImgNew.get(i));
            Log.d("AAA", listImgNew.get(i));
            String file_path = file.getAbsolutePath();
            String[] arrFile = file_path.split("\\.");
            file_path = arrFile[0] + System.currentTimeMillis() + "." + arrFile[1];
            RequestBody requestFile =
                    RequestBody.create(MediaType.parse("multipart/form-data"), file);
            MultipartBody.Part body =
                    MultipartBody.Part.createFormData("image", file_path, requestFile);
            partLists.add(body);

        }
        service.uploadImage(id, partLists)
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
                        Log.d("AAA", e + "erro");

                    }

                    @Override
                    public void onComplete() {
                        Toast.makeText(EditPostActivity.this, "Thành công!", Toast.LENGTH_SHORT).show();
                        finish();

                    }
                });


    }

    private void getNameCategoryparent(String id) {
        progressBar.setVisibility(View.VISIBLE);
        final APIService service = APIClient.getClient();
        service.getIdCategoryParents(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Categorychild1>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Categorychild1 categorychild) {
                        iDCategoryparent = categorychild.getCategoryParent();
                        nameCategoryChild = categorychild.getCategoryChildName();

                    }

                    @Override
                    public void onError(Throwable e) {
                        progressBar.setVisibility(View.GONE);

                    }

                    @Override
                    public void onComplete() {
                        sendIDCategoryparent.sendIDCategoryparent(iDCategoryparent);
                        tvCategoryChilds.setText(nameCategoryChild);


                    }
                });
    }

    @Override
    public void sendIDCategoryparent(String iD) {

        final APIService service = APIClient.getClient();
        service.ListPost(iD)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Categoryparen>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Categoryparen categoryparen) {
                        nameCategoryparent = categoryparen.getCategoryParentName();


                    }

                    @Override
                    public void onError(Throwable e) {

                        progressBar.setVisibility(View.GONE);

                    }

                    @Override
                    public void onComplete() {
                        tvCategoryparent.setText(nameCategoryparent);
                        progressBar.setVisibility(View.GONE);
                        rlLoad.setVisibility(View.VISIBLE);


                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 123 && resultCode == this.RESULT_OK) {
            listImg.add(getRealPathFromURI(uri));

        }
    }

    public String getRealPathFromURI(Uri contentUri) {
        String path = null;
        String[] proj = {MediaStore.MediaColumns.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
            path = cursor.getString(column_index);
        }
        cursor.close();
        return path;
    }

    @Override
    public void sendPosition(int position) {
        Log.d("AAA", position + "");
    }

    @Override
    public void checkDel(boolean checkDel) {
        Log.d("DellImg", checkDel + "");
        checkDelImg = true;


    }
}

