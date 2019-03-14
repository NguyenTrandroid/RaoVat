package com.example.raovat.addphoto;

import android.content.ContentResolver;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.raovat.R;
import com.example.raovat.addphoto.adapter.GalleryPhotoListAdapter;
import com.example.raovat.image_picker_module.furntion.ImageFileLoader;
import com.example.raovat.image_picker_module.model.Folder;
import com.example.raovat.image_picker_module.model.Image;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;


public class FragmentGalleryPhotoList extends Fragment implements OnPhotoSelectt {
    ArrayList<Image> images;
    ArrayList<Folder> folders;
    ArrayList<String> listResource;
    ArrayList<String> listPhotoSelected;
    FragmentGallerySelect fragmentGallerySelect;
    FragmentTransaction fragmentTransaction;
    FragmentManager fragmentManager;
    GalleryPhotoListAdapter adapter;
    RecyclerView recyclerView;
    Bundle bundle;

    ArrayList<File> excludedImages;
    OnPhotoListSelect onPhotoListSelect;
    private ImageFileLoader imageFileLoader;
    TextView txtOk;
    RelativeLayout rlGallery;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_galleryphoto, null);
        txtOk = view.findViewById(R.id.tv_done);
        rlGallery = view.findViewById(R.id.rl_gallery);
        recyclerView = view.findViewById(R.id.rv_galleryphoto);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        recyclerView.setHasFixedSize(true);
        adapter = new GalleryPhotoListAdapter(listResource, getContext(), R.layout.item_galleryphoto, this, 9);
        recyclerView.setAdapter(adapter);
        initAction();
        return view;
    }

    private void initAction() {
        txtOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPhotoListSelect.sendPhotolist(listPhotoSelected, false);

            }
        });
        rlGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listPhotoSelected.clear();
                fragmentGallerySelect.setArguments(bundle);
                fragmentGallerySelect.setTargetFragment(FragmentGalleryPhotoList.this, 1);
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.rl_sellMain, fragmentGallerySelect, "galleryphoto").addToBackStack("galleryselect");
                fragmentTransaction.commit();


            }
        });

    }


    private void init() {
        onPhotoListSelect = (OnPhotoListSelect) getContext();
        bundle = getArguments();
        fragmentManager = getFragmentManager();
        images = (ArrayList<Image>) bundle.getSerializable("listimage");
        folders = (ArrayList<Folder>) bundle.getSerializable("listfolder");
        fragmentGallerySelect = new FragmentGallerySelect();
        listResource = new ArrayList<>();
        listPhotoSelected = new ArrayList<>();
        for (int i = 0; i < images.size(); i++) {
            File file = new File(images.get(i).getPath());
            long length = file.length();
            if (length != 0) {
                listResource.add(images.get(i).getPath());
            } else {
                ContentResolver contentResolver = getActivity().getContentResolver();
                contentResolver.delete(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        MediaStore.Images.ImageColumns.DATA + "=?", new String[]{images.get(i).getPath()});
            }
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        for (int i = 0; i < folders.get(data.getIntExtra("position", 0)).getImages().size(); i++) {
            if (listResource.contains(folders.get(data.getIntExtra("position", 0)).getImages().get(i).getPath())) {
                listResource.remove(folders.get(data.getIntExtra("position", 0)).getImages().get(i).getPath());
                listResource.add(0, folders.get(data.getIntExtra("position", 0)).getImages().get(i).getPath());
            } else {
                listResource.add(0, folders.get(data.getIntExtra("position", 0)).getImages().get(i).getPath());
            }
        }
//

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

    @Override
    public void sendPhotoo(String path, boolean closeFragment) {
        txtOk.setVisibility(View.VISIBLE);
        boolean isSelected = false;
        int position = -1;
        if (listPhotoSelected.isEmpty()) {
            listPhotoSelected.add(path);
        } else {
            for (int i = 0; i < listPhotoSelected.size(); i++) {

                if (listPhotoSelected.get(i).equals(path)) {
                    isSelected = true;
                    position = i;
                }
            }
            if (isSelected == true) {
                listPhotoSelected.remove(position);
                if (listPhotoSelected.isEmpty()) {
                    txtOk.setVisibility(View.GONE);
                }
            } else listPhotoSelected.add(path);
        }


    }

}