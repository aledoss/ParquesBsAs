package com.example.ndiaz.parquesbsas.activities.reclamos;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Rect;
import android.hardware.Camera;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.example.ndiaz.parquesbsas.R;
import com.example.ndiaz.parquesbsas.util.camara.CamaraPreview;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.ndiaz.parquesbsas.util.Constants.IMAGENBYTES;
import static com.example.ndiaz.parquesbsas.util.Constants.LASTLOCATIONLATITUD;
import static com.example.ndiaz.parquesbsas.util.Constants.LASTLOCATIONLONGITUD;

public class CamaraReclamo extends AppCompatActivity implements View.OnClickListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {
    private Camera mCamera;
    private CamaraPreview camaraPreview;
    private byte[] mImagen;
    @BindView(R.id.camera_preview)
    FrameLayout preview;
    @BindView(R.id.btnSacarFoto)
    Button btnSacarFoto;
    @BindView(R.id.btnConfirmar)
    Button btnConfirmar;
    @BindView(R.id.btnCancelar)
    Button btnCancelar;
    private static final int FOCUS_AREA_SIZE = 300;
    private GoogleApiClient googleApiClient;
    Location lastLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camara_reclamo);
        ButterKnife.bind(this);
        setupUI();
        if (checkCameraHardware(this)) {//si tiene camara..
            mCamera = getCameraInstance();
            camaraPreview = new CamaraPreview(this, mCamera);   //creo la preview de la camara, le paso el contexto y la instancia de la camara
            camaraPreview.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        try {
                            focusOnTouch(event);    //al tocar la preview hace foco
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    return true;
                }
            });
            preview.addView(camaraPreview); //agrego la vista de la preview al framelayout
        }
        setupGoogleApiClient();
    }

    /**
     * Creo la instancia de googleapiclient con la api de location services para luego poder
     * utilizar el metodo getLastLocation()
     */
    private void setupGoogleApiClient() {
        try {
            if (googleApiClient == null) {
                googleApiClient = new GoogleApiClient.Builder(this)
                        .addApi(LocationServices.API)
                        .addConnectionCallbacks(this)
                        .addOnConnectionFailedListener(this)
                        .build();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setupUI() {
        btnSacarFoto.setOnClickListener(this);
        btnConfirmar.setOnClickListener(this);
        btnCancelar.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSacarFoto:
                //take picture: setea los bytes en el mImagen
                mCamera.takePicture(null, null, mPicture);  //(momento en el que saca la foto, sonido, imagen jpg)
                btnSacarFoto.setEnabled(false);
                btnConfirmar.setEnabled(true);
                btnCancelar.setEnabled(true);
                /*Intent intent = new Intent();
                intent.putExtra(LASTLOCATIONLATITUD, getLastLocation().getLatitude());
                intent.putExtra(LASTLOCATIONLONGITUD, getLastLocation().getLongitude());
                setResult(Activity.RESULT_OK, intent);  //le devuelvo que salio ok, los bytes de la imagen, la latitud y longitud
                finish();*/
                break;
            case R.id.btnConfirmar:
                //Hago el intent para devolver los bytes de la imagen
                Intent intent = new Intent();
                intent.putExtra(IMAGENBYTES, getmImagen());
                if (lastLocation != null) { //si el gps está apagado, no le devuelvo nada
                    intent.putExtra(LASTLOCATIONLATITUD, getLastLocation().getLatitude());
                    intent.putExtra(LASTLOCATIONLONGITUD, getLastLocation().getLongitude());
                }
                setResult(Activity.RESULT_OK, intent);  //le devuelvo que salio ok, los bytes de la imagen, la latitud y longitud
                finish();
                break;
            case R.id.btnCancelar:
                btnSacarFoto.setEnabled(true);
                btnConfirmar.setEnabled(false);
                btnCancelar.setEnabled(false);
                mCamera.startPreview(); //vuelvo a mostrar la preview
                break;
        }
    }

    private static Camera getCameraInstance() {
        Camera c = null;
        try {
            c = Camera.open(0); //abro la camara de atras
            Camera.Parameters parameters = c.getParameters();   //obtengo los parametros actuales
            parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
            c.setParameters(parameters);    //le seteo los parametros
        } catch (Exception e) {
            e.printStackTrace();
        }
        return c;
    }

    private boolean checkCameraHardware(Context context) {
        if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected void onPause() {
        releaseCamera();
        super.onPause();
    }

    private void releaseCamera() {
        if (mCamera != null) {
            mCamera.release();   //finaliza la previsualizacion
            mCamera = null;
        }
    }

    private void focusOnTouch(MotionEvent event) {
        if (mCamera != null) {
            Camera.Parameters parameters = mCamera.getParameters();
            if (parameters.getMaxNumMeteringAreas() > 0) {
                Log.i("NICOTEST", "fancy !");
                Rect rect = calculateFocusArea(event.getX(), event.getY());

                parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
                List<Camera.Area> meteringAreas = new ArrayList<Camera.Area>();
                meteringAreas.add(new Camera.Area(rect, 800));
                parameters.setFocusAreas(meteringAreas);

                mCamera.setParameters(parameters);
                mCamera.autoFocus(mAutoFocusTakePictureCallback);
            } else {
                mCamera.autoFocus(mAutoFocusTakePictureCallback);
            }
        }
    }

    private Rect calculateFocusArea(float x, float y) {
        int left = clamp(Float.valueOf((x / camaraPreview.getWidth()) * 2000 - 1000).intValue(), FOCUS_AREA_SIZE);
        int top = clamp(Float.valueOf((y / camaraPreview.getHeight()) * 2000 - 1000).intValue(), FOCUS_AREA_SIZE);

        return new Rect(left, top, left + FOCUS_AREA_SIZE, top + FOCUS_AREA_SIZE);
    }

    private int clamp(int touchCoordinateInCameraReper, int focusAreaSize) {
        int result;
        if (Math.abs(touchCoordinateInCameraReper) + focusAreaSize / 2 > 1000) {
            if (touchCoordinateInCameraReper > 0) {
                result = 1000 - focusAreaSize / 2;
            } else {
                result = -1000 + focusAreaSize / 2;
            }
        } else {
            result = touchCoordinateInCameraReper - focusAreaSize / 2;
        }
        return result;
    }

    private Camera.AutoFocusCallback mAutoFocusTakePictureCallback = new Camera.AutoFocusCallback() {
        @Override
        public void onAutoFocus(boolean success, Camera camera) {
            if (success) {
                Log.i("NICOTEST", "Foco");
            } else {
                Log.i("NICOTEST", "No Foco!");
            }
        }
    };

    Camera.PictureCallback mPicture = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] bytes, Camera camera) {
            setmImagen(bytes);
        }
    };

    public byte[] getmImagen() {
        return mImagen;
    }

    public void setmImagen(byte[] mImagen) {
        this.mImagen = mImagen;
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        //cuando se conecta el GoogleApiClient, obtiene la ultima ubicacion detectada
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        lastLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
        setLastLocation(lastLocation);
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.d("GoogleApiClient", "Connection suspended");
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d("GoogleApiClient", "Connection failed");
    }

    @Override
    protected void onStart() {
        googleApiClient.connect();
        super.onStart();
    }

    @Override
    protected void onStop() {
        googleApiClient.disconnect();
        super.onStop();
    }

    public Location getLastLocation() {
        return lastLocation;
    }

    public void setLastLocation(Location lastLocation) {
        this.lastLocation = lastLocation;
    }
}
