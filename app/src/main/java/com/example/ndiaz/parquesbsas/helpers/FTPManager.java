package com.example.ndiaz.parquesbsas.helpers;

import android.util.Log;

import java.io.File;
import java.util.concurrent.Callable;

import io.reactivex.Completable;
import it.sauronsoftware.ftp4j.FTPClient;
import it.sauronsoftware.ftp4j.FTPDataTransferListener;

import static com.example.ndiaz.parquesbsas.constants.Constants.FTP_HOST;
import static com.example.ndiaz.parquesbsas.constants.Constants.FTP_PASS;
import static com.example.ndiaz.parquesbsas.constants.Constants.FTP_USER;

/**
 * Created by Lenwe on 10/11/2016.
 */

public class FTPManager {

    public FTPManager() {
    }

    public Completable uploadFile(String imagen, String directoryToUpload) {
        return Completable.fromCallable((Callable) () -> {
            File file = new File(imagen);
            FTPClient client = new FTPClient();
            client.connect(FTP_HOST, 21);   //host, puerto
            client.login(FTP_USER, FTP_PASS);
            client.setType(FTPClient.TYPE_BINARY);
            client.changeDirectory(directoryToUpload);
            client.upload(file, new MyFTPTransferDataListener());

            return null;
        });
    }

    private class MyFTPTransferDataListener implements FTPDataTransferListener {

        private final String TAG = MyFTPTransferDataListener.class.getSimpleName();

        @Override
        public void started() {
            Log.d(TAG, "FTP Data Transfer: started");
        }

        @Override
        public void transferred(int i) {
            Log.d(TAG, "FTP Data Transfer: transferido: " + i);
        }

        @Override
        public void completed() {
            Log.d(TAG, "FTP Data Transfer: transferencia completa");
        }

        @Override
        public void aborted() {
            Log.d(TAG, "FTP Data Transfer: transferencia abortada");
        }

        @Override
        public void failed() {
            Log.d(TAG, "FTP Data Transfer: transferencia fallida");
        }

    }
}
