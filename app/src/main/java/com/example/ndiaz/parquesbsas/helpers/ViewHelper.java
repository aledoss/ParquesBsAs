package com.example.ndiaz.parquesbsas.helpers;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.text.InputType;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.ndiaz.parquesbsas.edittextvalidator.EditTextValidator;
import com.example.ndiaz.parquesbsas.edittextvalidator.FactoryEditText;
import com.example.ndiaz.parquesbsas.helpers.maps.URLMapImage;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ViewHelper {

    public boolean isValidData(FactoryEditText factoryEditText) {
        List<EditTextValidator> editTextValidators = factoryEditText.createEditTextValidators();
        boolean validData = true;

        for (int i = 0; validData && i < editTextValidators.size(); i++) {
            EditTextValidator editText = editTextValidators.get(i);
            validData = editText.validate();
        }

        return validData;
    }

    public boolean tooglePasswordTextType(EditText etPassword, MotionEvent event) {
        if (!etPassword.getText().toString().equalsIgnoreCase("")) {
            final int DRAWABLE_RIGHT = 2;
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                if (event.getRawX() >= (etPassword.getRight() - etPassword.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                    etPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
                    return true;
                }
            } else {
                if (event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL) {
                    etPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
            }
        }
        return false;
    }

    public void loadMapImage(final Context context, final ImageView imgMapa, final ProgressBar progressBar,
                              URLMapImage urlMapImage, final String className) {
        Picasso
                .with(context)
                .load(urlMapImage.getUrl())
                .into(imgMapa, new Callback() {
                    @Override
                    public void onSuccess() {
                        imgMapa.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError() {
                        progressBar.setVisibility(View.GONE);
                        Log.e(className, "loadMapImage, onError");
                    }
                });
    }

    public void changeXMLViewColor(View view, String color) {
        Drawable background = view.getBackground();
        ((GradientDrawable) background).setColor(Color.parseColor("#" + color));
    }

}
