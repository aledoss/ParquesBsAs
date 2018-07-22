package com.example.ndiaz.parquesbsas.ui.custom.filter_checkbox;

import android.support.annotation.ColorRes;

import com.example.ndiaz.parquesbsas.R;

public class FilterCheckBoxChecked implements FilterCheckBoxState {

    @ColorRes
    private int background = R.color.filter_background_checked;
    @ColorRes
    private int textColor = R.color.filter_text_color_checked;

    @Override
    public int getBackground() {
        return background;
    }

    @Override
    public int getTextColor() {
        return textColor;
    }
}
