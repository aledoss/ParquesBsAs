package com.example.ndiaz.parquesbsas.edittextvalidator.usuario;

import android.content.Context;
import android.widget.EditText;

import com.example.ndiaz.parquesbsas.R;
import com.example.ndiaz.parquesbsas.edittextvalidator.EditTextValidator;

import static com.example.ndiaz.parquesbsas.constants.LoginConstants.LAST_NAME;

public class EditTextLastNameValidator extends EditTextValidator {

    private EditText etLastName;
    private Context context;

    public EditTextLastNameValidator(EditText etLastName) {
        this.etLastName = etLastName;
        this.context = etLastName.getContext();
    }

    @Override
    public boolean validate() {
        String lastName = String.valueOf(etLastName.getText()).trim();
        if (lastName.isEmpty()) {
            etLastName.setError(context.getString(R.string.invalid_last_name));
            return false;
        } else if (hasIncorrectLength(lastName, 2, 32)) {
            etLastName.setError(context.getString(R.string.invalid_length));
            return false;
        } else if (!textValidator.isAlpha(lastName)) {
            etLastName.setError(context.getString(R.string.only_alpha));
            return false;
        }

        return true;
    }

    @Override
    public String getComponentName() {
        return LAST_NAME;
    }
}
