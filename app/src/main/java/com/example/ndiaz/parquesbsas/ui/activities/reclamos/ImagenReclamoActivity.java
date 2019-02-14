package com.example.ndiaz.parquesbsas.ui.activities.reclamos;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.example.ndiaz.parquesbsas.BuildConfig;
import com.example.ndiaz.parquesbsas.R;
import com.squareup.picasso.Picasso;

public class ImagenReclamoActivity extends AppCompatActivity {

    private static final String IMAGE_RECLAMO_NAME = "IMAGE_RECLAMO_NAME";
    private ImageView imgReclamo;
    private String imageReclamoName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imagen_reclamo);

        initializeVariables();
        loadImage();
    }

    public static Intent newIntent(Context context, String urlImgReclamo) {
        Intent intent = new Intent(context, ImagenReclamoActivity.class);
        intent.putExtra(IMAGE_RECLAMO_NAME, urlImgReclamo);
        return intent;
    }

    private void initializeVariables() {
        imageReclamoName = getIntent().getExtras().getString(IMAGE_RECLAMO_NAME, "");
        imgReclamo = findViewById(R.id.imgReclamo);
    }

    private void loadImage() {
        Picasso.with(this)
                .load(BuildConfig.IMAGENES_RECLAMOS_URL + imageReclamoName)
                .into(imgReclamo);
    }


}
