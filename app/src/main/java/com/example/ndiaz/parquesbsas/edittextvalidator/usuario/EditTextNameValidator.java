package com.example.ndiaz.parquesbsas.edittextvalidator.usuario;

import android.content.Context;
import android.widget.EditText;

import com.example.ndiaz.parquesbsas.R;
import com.example.ndiaz.parquesbsas.edittextvalidator.EditTextValidator;

import static com.example.ndiaz.parquesbsas.constants.LoginConstants.NAME;

public class EditTextNameValidator extends EditTextValidator {

    private EditText etName;
    private Context context;

    public EditTextNameValidator(EditText etName) {
        this.etName = etName;
        this.context = etName.getContext();
    }

    @Override
    public boolean validate() {
        String name = String.valueOf(etName.getText()).trim();
        if (name.isEmpty()) {
            etName.setError(context.getString(R.string.invalid_name));
            return false;
        } else if (hasIncorrectLength(name, 3, context.getResources().getInteger(R.integer.max_length_nombre))) {
            etName.setError(context.getString(R.string.invalid_length));
            return false;
        } else if (!textValidator.isAlpha(name)) {
            etName.setError(context.getString(R.string.only_alpha));
            return false;
        }

        return true;
    }

    @Override
    public String getComponentName() {
        return NAME;
    }

}
