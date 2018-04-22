package com.example.ndiaz.parquesbsas.model.parquelike;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.ndiaz.parquesbsas.R;

public class ParqueLikeDefault implements ParqueLike {

    private Context context;
    private ImageButton imgBtnThumbsUp;
    private ImageButton imgBtnThumbsDown;
    private TextView txtThumbsUp;
    private TextView txtThumbsDown;

    public ParqueLikeDefault(Context context, ImageButton imgBtnThumbsUp, ImageButton imgBtnThumbsDown, TextView txtThumbsUp, TextView txtThumbsDown) {
        this.context = context;
        this.imgBtnThumbsUp = imgBtnThumbsUp;
        this.imgBtnThumbsDown = imgBtnThumbsDown;
        this.txtThumbsUp = txtThumbsUp;
        this.txtThumbsDown = txtThumbsDown;
    }

    @Override
    public void refreshViews() {
        imgBtnThumbsUp.setImageDrawable(getThumbsUpImg(this.context));
        imgBtnThumbsDown.setImageDrawable(getThumbsDownImg(this.context));
    }

    @Override
    public Drawable getThumbsUpImg(Context context) {
        return ContextCompat.getDrawable(context, R.drawable.ic_thumb_up);
    }

    @Override
    public Drawable getThumbsDownImg(Context context) {
        return ContextCompat.getDrawable(context, R.drawable.ic_thumb_down);
    }

    @Override
    public void onClickImgBtnThumbsUp() {
        increaseTextView(txtThumbsUp);
    }

    @Override
    public void onClickimgBtnThumbsDown() {
        increaseTextView(txtThumbsDown);
    }

    private void increaseTextView(TextView txt) {
        int thumbsUpQuantity = Integer.valueOf(txt.getText().toString());
        String totalThumbsUpQuantity = String.valueOf(thumbsUpQuantity + 1);
        txt.setText(totalThumbsUpQuantity);
    }
}
