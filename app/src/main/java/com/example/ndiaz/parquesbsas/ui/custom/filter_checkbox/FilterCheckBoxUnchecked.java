package com.example.ndiaz.parquesbsas.ui.custom.filter_checkbox;

import android.support.annotation.ColorRes;

import com.example.ndiaz.parquesbsas.R;

public class FilterCheckBoxUnchecked implements FilterCheckBoxState {

    @ColorRes
    private int background = R.color.filter_background_unchecked;
    @ColorRes
    private int textColor = R.color.filter_text_color_unchecked;

    @Override
    public int getBackground() {
        return background;
    }

    @Override
    public int getTextColor() {
        return textColor;
    }
}
