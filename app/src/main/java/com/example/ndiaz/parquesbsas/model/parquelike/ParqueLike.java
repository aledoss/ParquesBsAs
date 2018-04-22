package com.example.ndiaz.parquesbsas.model.parquelike;

import android.content.Context;
import android.graphics.drawable.Drawable;

public interface ParqueLike {
    void refreshViews();

    Drawable getThumbsUpImg(Context context);

    Drawable getThumbsDownImg(Context context);

    void onClickImgBtnThumbsUp();

    void onClickimgBtnThumbsDown();
}
