package com.example.ndiaz.parquesbsas.helpers;

import android.text.InputType;
import android.view.MotionEvent;
import android.widget.EditText;

import com.example.ndiaz.parquesbsas.edittextvalidator.EditTextValidator;
import com.example.ndiaz.parquesbsas.edittextvalidator.FactoryEditText;

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

}
