package com.example.raovat.image_picker_module.furntion;


import com.example.raovat.image_picker_module.model.Folder;
import com.example.raovat.image_picker_module.model.Image;

import java.util.List;



public interface ImageLoaderListener {
    void onImageLoaded(List<Image> images, List<Folder> folders);
    void onFailed(Throwable throwable);

}
