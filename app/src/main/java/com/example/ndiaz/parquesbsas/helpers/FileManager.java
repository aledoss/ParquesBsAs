package com.example.ndiaz.parquesbsas.helpers;

import android.os.Environment;
import android.widget.Toast;

import com.example.ndiaz.parquesbsas.ui.activities.BaseActivity;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileManager {

    private BaseActivity baseActivity;
    private String imageFileName;

    public FileManager(BaseActivity baseActivity) {
        this.baseActivity = baseActivity;
    }

    public File createImageFile() {
        // Create an image file name
        File tempFile = null;
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = baseActivity.getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        try {
            tempFile = File.createTempFile(
                    imageFileName,  /* prefix */
                    ".jpg",         /* suffix */
                    storageDir      /* directory */
            );
            setImageFileName(tempFile.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(baseActivity, "No se pudo crear el archivo de la imagen", Toast.LENGTH_SHORT).show();
        }

        return tempFile;
    }

    public String getImageFileName() {
        return imageFileName;
    }

    public void setImageFileName(String imageFileName) {
        this.imageFileName = imageFileName;
    }
}
