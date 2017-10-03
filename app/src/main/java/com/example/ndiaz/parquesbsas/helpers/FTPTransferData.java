package com.example.ndiaz.parquesbsas.helpers;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.ndiaz.parquesbsas.helpers.camara.PhotoHandler;

import it.sauronsoftware.ftp4j.FTPDataTransferListener;

/**
 * Created by Lenwe on 01/12/2016.
 */

public class FTPTransferData implements FTPDataTransferListener {


    Context context;

    public FTPTransferData(Context context){
        this.context = context;
    }

    @Override
    public void started() {
        Log.d("NICOTEST", "started");
    }

    @Override
    public void transferred(int i) {
        Log.d("NICOTEST", "transferido: " + i);
    }

    @Override
    public void completed() {
        Toast.makeText(context, "Transferencia completa", Toast.LENGTH_SHORT).show();
        Log.d("NICOTEST", "Transferencia completa");
        PhotoHandler.showNotif("Transferencia completa" ,context);
    }

    @Override
    public void aborted() {
        Log.d("NICOTEST", "aborted");
        PhotoHandler.showNotif("Abortado" ,context);
    }

    @Override
    public void failed() {
        Log.d("NICOTEST", "failed");
        Toast.makeText(context, "Fallo en la transferencia", Toast.LENGTH_SHORT).show();
        PhotoHandler.showNotif("Fallo en la transferencia" ,context);
    }
}
