package com.example.ndiaz.parquesbsas.activities.reclamos;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Rect;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.ndiaz.parquesbsas.R;
import com.example.ndiaz.parquesbsas.util.camara.CamaraPreview;
import com.example.ndiaz.parquesbsas.util.camara.PhotoHandler;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CamaraReclamo extends AppCompatActivity implements View.OnClickListener{
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camara_reclamo);
        ButterKnife.bind(this);
        setupUI();
        if (checkCameraHardware(this)) {//si tiene camara..
            mCamera = getCameraInstance();
            camaraPreview = new CamaraPreview(this, mCamera);
            camaraPreview.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        focusOnTouch(event);
                    }
                    return true;
                }
            });
            preview.addView(camaraPreview);
        }
    }

    private void setupUI() {
        btnSacarFoto.setOnClickListener(this);
        btnConfirmar.setOnClickListener(this);
        btnCancelar.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btnSacarFoto:
                Toast.makeText(this, "Pum", Toast.LENGTH_SHORT).show();
                mCamera.takePicture(null, null, mPicture);
                btnSacarFoto.setEnabled(false);
                btnConfirmar.setEnabled(true);
                btnCancelar.setEnabled(true);
                break;
            case R.id.btnConfirmar:
                btnSacarFoto.setEnabled(true);
                btnConfirmar.setEnabled(false);
                btnCancelar.setEnabled(false);
                Toast.makeText(this, "Imagen Guardada", Toast.LENGTH_SHORT).show();
                PhotoHandler handler = new PhotoHandler(getApplicationContext(), getmImagen());
                handler.procesarImagen();
                //tendria que hacer el finish, con el activityresult.
                break;
            case R.id.btnCancelar:
                btnSacarFoto.setEnabled(true);
                btnConfirmar.setEnabled(false);
                btnCancelar.setEnabled(false);
                mCamera.startPreview();
                break;
        }
    }

    private static Camera getCameraInstance() {
        Camera c = null;
        try {
            c = Camera.open(0);
            Camera.Parameters parameters = c.getParameters();
            parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
            c.setParameters(parameters);
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
            mCamera.release();        // release the camera for other applications
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
                Log.i("NICOTEST", "success!");
            } else {
                Log.i("NICOTEST", "fail!");
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
}
