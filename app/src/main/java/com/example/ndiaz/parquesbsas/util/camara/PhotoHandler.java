package com.example.ndiaz.parquesbsas.util.camara;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.example.ndiaz.parquesbsas.R;
import com.example.ndiaz.parquesbsas.util.FTPTransferData;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import it.sauronsoftware.ftp4j.FTPClient;

import static android.content.Context.NOTIFICATION_SERVICE;
import static com.example.ndiaz.parquesbsas.util.Constants.FTP_HOST;
import static com.example.ndiaz.parquesbsas.util.Constants.FTP_PASS;
import static com.example.ndiaz.parquesbsas.util.Constants.FTP_USER;

/**
 * Created by Lenwe on 10/11/2016.
 */

public class PhotoHandler {
    private Context context;
    private byte[] data;

    public PhotoHandler(Context context, byte[] imagen) {
        this.context = context;
        this.data = imagen;
    }

    public void procesarImagen() {
        //Obtengo la ubicacion a donde guardar el archivo de la foto
        File pictureFileDir = getDir();
        if (!pictureFileDir.exists() && !pictureFileDir.mkdirs()) {
            Log.d("NICOTEST", "No se puede crear el directorio para la iamgen");
            Toast.makeText(context, "No se puede crear el directorio para la iamgen", Toast.LENGTH_SHORT).show();
            return;
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyymmddhhmmss");
        String date = dateFormat.format(new Date());
        String photoFile = "Reclamo_" + date + ".jpg";
        String fileName = pictureFileDir.getPath() + File.separator + photoFile;//path + \ + \ nombrefoto
        File pictureFile = new File(fileName);
        try {
            FileOutputStream fos = new FileOutputStream(pictureFile);
            fos.write(data);
            fos.close();
            Toast.makeText(context, "Imagen guardada de forma local", Toast.LENGTH_SHORT).show();
            Log.d("NICOTEST", "Direccion: " + fileName);
        } catch (FileNotFoundException e) {
            Log.d("NICOTEST", "File" + fileName + "not saved: " + e.getMessage());
            Toast.makeText(context, "No se pudo guardar la imagen", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        } catch (IOException e) {
            Log.d("NICOTEST", "File" + fileName + "not saved: " + e.getMessage());
            Toast.makeText(context, "No se pudo guardar la imagen", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        FTPUpload ftpUpload = new FTPUpload();
        ftpUpload.execute(pictureFile);

    }

    private File getDir() {
        File sdDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        return new File(sdDir, "ParquesBsAs");
    }

    private class FTPUpload extends AsyncTask<File, Void, Void> {

        @Override
        protected Void doInBackground(File... params) {
            File file = params[0];
            FTPClient client = new FTPClient();
            try {
                client.connect(FTP_HOST, 21);   //host, puerto
                client.login(FTP_USER, FTP_PASS);
                client.setType(FTPClient.TYPE_BINARY);
                client.changeDirectory("/public/img/android/");
                client.upload(file, new FTPTransferData(context));
            } catch (Exception e) {
                e.printStackTrace();
                showNotif(e.getMessage(), context);
                Log.d("NICOTEST", e.getMessage());
            }
            return null;
        }
    }

    public static void showNotif(String mensaje, Context context) {
        try {
            //creo la notificacion
            Notification notification = new NotificationCompat.Builder(context)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle(context.getResources().getString(R.string.app_name))
                    .setContentText(mensaje)
                    .setAutoCancel(true)
                    .setLights(Color.GREEN, 500, 500)
                    .setVibrate(new long[]{500, 500})
                    .build();
            //creo un numero "unico" identificador de notificacion
            Random random = new Random();
            int notificationId = random.nextInt();
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
            notificationManager.notify(notificationId, notification);   //muestro la notificacion (id de notificacion, notificacion)
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
