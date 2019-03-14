package com.example.raovat.addphoto;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import com.example.raovat.R;

import com.example.raovat.addphoto.adapter.GalleryFolderSelectAdapter;
import com.example.raovat.image_picker_module.model.Folder;

import java.util.ArrayList;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class FragmentGallerySelect extends Fragment implements GalleryFolderSelectAdapter.OnDataPass {
    ArrayList<Folder> folders;
    ArrayList<String> foldernames;
    ArrayList<String> firstimages;
    FragmentGalleryPhotoList fragmentGalleryPhotoList;
    FragmentTransaction fragmentTransaction;
    FragmentManager fragmentManager;
    ImageView imgClose;
    Bundle bundle;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        init();


    }

    private void init() {
        fragmentGalleryPhotoList = new FragmentGalleryPhotoList();
        fragmentManager = getFragmentManager();
        foldernames = new ArrayList<>();
        firstimages = new ArrayList<>();
        bundle = getArguments();
        folders = (ArrayList<Folder>) bundle.getSerializable("listfolder");
        for (int i = 0; i < folders.size(); i++) {
            foldernames.add(folders.get(i).getFolderName());
            firstimages.add(folders.get(i).getImages().get(0).getPath());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_galleryselect, null);
        imgClose = view.findViewById(R.id.iv_close);
        RecyclerView recyclerView = view.findViewById(R.id.rv_galleryselect);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));
        recyclerView.setHasFixedSize(true);
        GalleryFolderSelectAdapter adapter = new GalleryFolderSelectAdapter(foldernames, getContext(), firstimages, this);
        recyclerView.setAdapter(adapter);
        initAction();


        return view;
    }

    private void initAction() {
        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager.popBackStack();

            }
        });
    }


    @Override
    public void onDataPass(int data) {
        getFragmentManager().popBackStack();
        getTargetFragment().onActivityResult(
                getTargetRequestCode(),
                Activity.RESULT_OK,
                new Intent().putExtra("position", data)
        );

    }
}