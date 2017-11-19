package com.example.ndiaz.parquesbsas.edittextvalidator.usuario;

import android.content.Context;
import android.widget.EditText;

import com.example.ndiaz.parquesbsas.R;
import com.example.ndiaz.parquesbsas.edittextvalidator.EditTextValidator;

import static com.example.ndiaz.parquesbsas.constants.LoginConstants.DNI;

public class EditTextDNIValidator extends EditTextValidator {

    private EditText etDNI;
    private Context context;

    public EditTextDNIValidator(EditText etDNI) {
        this.etDNI = etDNI;
        this.context = etDNI.getContext();
    }

    @Override
    public boolean validate() {
        String dni = etDNI.getText().toString().trim();
        if (dni.isEmpty()) {
            etDNI.setError(context.getString(R.string.invalid_dni));
            return false;
        } else if (hasIncorrectLength(dni, 6, 8)) {
            etDNI.setError(context.getString(R.string.invalid_length));
            return false;
        }
        return true;
    }

    @Override
    public String getComponentName() {
        return DNI;
    }

}
