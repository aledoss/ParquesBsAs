package com.example.ndiaz.parquesbsas.ui.activities.reclamos;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.example.ndiaz.parquesbsas.R;
import com.squareup.picasso.Picasso;

public class ImagenReclamoActivity extends AppCompatActivity {

    private static final String URL_IMAGE_RECLAMO = "URL_IMAGE_RECLAMO";
    private ImageView imgReclamo;
    private String urlImgReclamo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imagen_reclamo);

        initializeVariables();
        loadImage();
    }

    public static Intent newIntent(Context context, String urlImgReclamo) {
        Intent intent = new Intent(context, ImagenReclamoActivity.class);
        intent.putExtra(URL_IMAGE_RECLAMO, urlImgReclamo);
        return intent;
    }

    private void initializeVariables() {
        urlImgReclamo = getIntent().getExtras().getString(URL_IMAGE_RECLAMO, "");
        imgReclamo = findViewById(R.id.imgReclamo);
    }

    private void loadImage() {
        Picasso.with(this)
                .load(urlImgReclamo)
                .into(imgReclamo);
    }


}
