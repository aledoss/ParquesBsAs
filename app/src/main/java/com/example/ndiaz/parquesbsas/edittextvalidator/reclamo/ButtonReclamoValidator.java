package com.example.ndiaz.parquesbsas.edittextvalidator.reclamo;

import android.content.Context;
import android.widget.Button;
import android.widget.Toast;

import com.example.ndiaz.parquesbsas.R;
import com.example.ndiaz.parquesbsas.edittextvalidator.EditTextValidator;

public class ButtonReclamoValidator extends EditTextValidator {

    private Button btnReclamo;
    private Context context;

    ButtonReclamoValidator(Button btnReclamo) {
        this.btnReclamo = btnReclamo;
        this.context = btnReclamo.getContext();
    }

    @Override
    public boolean validate() {
        String reclamo = btnReclamo.getText().toString().trim();
        String defaultMensaje = context.getString(R.string.elegir_reclamo);
        boolean isValid = true;

        if (reclamo.equalsIgnoreCase(defaultMensaje)) {
            isValid = false;
            btnReclamo.setError(defaultMensaje);
            Toast.makeText(context, defaultMensaje, Toast.LENGTH_LONG).show();
        }

        return isValid;
    }

    @Override
    public String getComponentName() {
        return null;
    }
}
