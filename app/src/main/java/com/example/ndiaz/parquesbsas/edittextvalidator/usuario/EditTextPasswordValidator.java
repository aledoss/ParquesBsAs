package com.example.ndiaz.parquesbsas.edittextvalidator.usuario;

import android.content.Context;
import android.widget.EditText;

import com.example.ndiaz.parquesbsas.R;
import com.example.ndiaz.parquesbsas.edittextvalidator.EditTextValidator;

import static com.example.ndiaz.parquesbsas.constants.LoginConstants.PASSWORD;

public class EditTextPasswordValidator extends EditTextValidator {

    private EditText etPass;
    private Context context;

    public EditTextPasswordValidator(EditText etPass) {
        this.etPass = etPass;
        this.context = etPass.getContext();
    }

    @Override
    public boolean validate() {
        String password = String.valueOf(etPass.getText()).trim();
        if (password.isEmpty()) {
            etPass.setError(context.getString(R.string.invalid_password));
            return false;
        } else if (hasIncorrectLength(password, 8, 64)) {
            etPass.setError(context.getString(R.string.invalid_length));
            return false;
        } else if (!textValidator.isValidPassword(password)) {
            etPass.setError(context.getString(R.string.invalid_password_text));
            return false;
        }

        return true;
    }

    @Override
    public String getComponentName() {
        return PASSWORD;
    }
}
