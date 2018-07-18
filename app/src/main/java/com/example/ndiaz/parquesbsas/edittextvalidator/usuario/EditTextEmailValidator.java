package com.example.ndiaz.parquesbsas.edittextvalidator.usuario;

import android.content.Context;
import android.widget.EditText;

import com.example.ndiaz.parquesbsas.R;
import com.example.ndiaz.parquesbsas.edittextvalidator.EditTextValidator;

import static com.example.ndiaz.parquesbsas.constants.LoginConstants.EMAIL;

public class EditTextEmailValidator extends EditTextValidator {

    private EditText etEmail;
    private Context context;

    public EditTextEmailValidator(EditText etEmail) {
        this.etEmail = etEmail;
        this.context = etEmail.getContext();
    }

    @Override
    public boolean validate() {
        String email = String.valueOf(etEmail.getText()).trim();
        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.setError(context.getString(R.string.invalid_email));
            return false;
        } else if (hasIncorrectLength(email, 7, context.getResources().getInteger(R.integer.max_length_email))) {
            etEmail.setError(context.getString(R.string.invalid_length));
            return false;
        }


        return true;
    }

    @Override
    public String getComponentName() {
        return EMAIL;
    }
}
