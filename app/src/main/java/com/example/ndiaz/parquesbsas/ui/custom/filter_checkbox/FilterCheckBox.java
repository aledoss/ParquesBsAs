package com.example.ndiaz.parquesbsas.ui.custom.filter_checkbox;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.view.View;

import com.example.ndiaz.parquesbsas.R;

import static android.util.TypedValue.COMPLEX_UNIT_PX;
import static android.view.Gravity.CENTER;

public class FilterCheckBox extends AppCompatTextView implements View.OnClickListener {

    private FilterCheckBoxState state;
    private boolean isChecked;

    public FilterCheckBox(Context context) {
        super(context);
        initializeVariables();
        initializeViewProperties();
        initializeListeners();
    }

    public FilterCheckBox(Context context, AttributeSet attrs) {
        super(context, attrs);
        initializeVariables();
        initializeViewProperties();
        initializeListeners();
    }

    public FilterCheckBox(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initializeVariables();
        initializeViewProperties();
        initializeListeners();
    }

    private void initializeViewProperties() {
        int defaultPadding = (int) getContext().getResources().getDimension(R.dimen.filter_checkbox_defaultPadding);
        setPadding(defaultPadding, defaultPadding, defaultPadding, defaultPadding);
        setMinLines(getContext().getResources().getInteger(R.integer.filter_checkbox_min_lines));
        setTextSize(COMPLEX_UNIT_PX, getContext().getResources().getDimension(R.dimen.fifteen_sp));
        setGravity(CENTER);
    }

    private void initializeVariables() {
        isChecked = false;
        handleCheckBoxState();
    }

    private void handleCheckBoxState() {
        if (isChecked()) {
            state = new FilterCheckBoxChecked();
        } else {
            state = new FilterCheckBoxUnchecked();
        }
        changeFormat();
    }

    private void changeFormat() {
        setBackgroundResource(state.getBackground());
        setTextColor(ContextCompat.getColor(getContext(), state.getTextColor()));
    }

    private void initializeListeners() {
        setOnClickListener(this);
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
        handleCheckBoxState();
    }

    @Override
    public void onClick(View v) {
        handleCheckedChange();
        handleCheckBoxState();
    }

    private void handleCheckedChange() {
        isChecked = !isChecked();
    }
}
