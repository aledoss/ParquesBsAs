package com.example.ndiaz.parquesbsas.util.camara;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import it.sauronsoftware.ftp4j.FTPClient;
import it.sauronsoftware.ftp4j.FTPDataTransferListener;

import static com.example.ndiaz.parquesbsas.util.Constants.FTP_HOST;
import static com.example.ndiaz.parquesbsas.util.Constants.FTP_PASS;
import static com.example.ndiaz.parquesbsas.util.Constants.FTP_USER;

/**
 * Created by Lenwe on 10/11/2016.
 */

public class PhotoHandler /*implements Camera.PictureCallback*/ {
    private Context context;
    private byte[] data;

    public PhotoHandler(Context context, byte[] imagen) {
        this.context = context;
        this.data = imagen;
    }

    /*@Override
    public void onPictureTaken(byte[] data, Camera camera) {*/
    public void procesarImagen() {
        Toast.makeText(context, "Foto sacada", Toast.LENGTH_SHORT).show();
        File pictureFileDir = getDir();
        if (!pictureFileDir.exists() && !pictureFileDir.mkdirs()) {
            Log.d("NICOTEST", "No se puede crear el directorio para la iamgen");
            Toast.makeText(context, "No se puede crear el directorio para la iamgen", Toast.LENGTH_SHORT).show();
            return;
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyymmddhhmmss");
        String date = dateFormat.format(new Date());
        String photoFile = "Cupones_" + date + ".jpg";
        String fileName = pictureFileDir.getPath() + File.separator + photoFile;//path + \ + \ nombrefoto
        File pictureFile = new File(fileName);
        try {
            FileOutputStream fos = new FileOutputStream(pictureFile);
            fos.write(data);
            fos.close();
            Toast.makeText(context, "Imagen guardada", Toast.LENGTH_SHORT).show();
            Log.d("NICOTEST", "Direccion: " + fileName.toString());
        } catch (FileNotFoundException e) {
            Log.d("NICOTEST", "File" + fileName + "not saved: " + e.getMessage());
            Toast.makeText(context, "No se pudo guardar la imagen", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        } catch (IOException e) {
            Log.d("NICOTEST", "File" + fileName + "not saved: " + e.getMessage());
            Toast.makeText(context, "No se pudo guardar la imagen", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        uploadFileFTP(pictureFile);
    }

    //}


    private File getDir() {
        File sdDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        return new File(sdDir, "CameraApiDemo");
    }

    private void uploadFileFTP(File file) {
        FTPClient client = new FTPClient();
        try {
            client.connect(FTP_HOST, 21);   //host, puerto
            client.login(FTP_USER, FTP_PASS);
            client.setType(FTPClient.TYPE_BINARY);
            client.changeDirectory("/images/");
            client.upload(file, new PhotoHandler.FTPTransferListener());
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("NICOTEST", e.getMessage());
        }
    }

    public class FTPTransferListener implements FTPDataTransferListener {

        @Override
        public void started() {
            //btnSacarFoto.setEnabled(false);
            Toast.makeText(context, "Cargando imagen", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void transferred(int i) {
            Toast.makeText(context, "Transfiriendo", Toast.LENGTH_SHORT).show();
            Log.d("NICOTEST", "transferido: " + i);
        }

        @Override
        public void completed() {
            //btnSacarFoto.setEnabled(true);
            Toast.makeText(context, "Transferencia completa", Toast.LENGTH_SHORT).show();
            Log.d("NICOTEST", "Transferencia completa");
        }

        @Override
        public void aborted() {
            //btnSacarFoto.setEnabled(true);
            Toast.makeText(context, "Transferencia abortada", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void failed() {
            //btnSacarFoto.setEnabled(true);
            Toast.makeText(context, "Fallo en la transferencia", Toast.LENGTH_SHORT).show();
        }
    }
}
