package com.example.ndiaz.parquesbsas.helpers;

import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;

import com.example.ndiaz.parquesbsas.ui.activities.BaseActivity;

import java.io.File;

/**
 * // https://developer.android.com/training/camera/photobasics
 */
public class IntentCamera {

    public static final int REQUEST_CODE_CAMERA = 123;
    private BaseActivity baseActivity;

    public IntentCamera(BaseActivity baseActivity) {
        this.baseActivity = baseActivity;
    }

    public void navigateToCamera(File imageFile) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(baseActivity.getPackageManager()) != null) {
            Uri photoURI = FileProvider.getUriForFile(baseActivity,
                    "com.example.android.fileprovider",
                    imageFile);
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
            baseActivity.startActivityForResult(takePictureIntent, REQUEST_CODE_CAMERA);
        }
    }

}
