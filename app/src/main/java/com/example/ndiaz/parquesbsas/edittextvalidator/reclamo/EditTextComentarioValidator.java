package com.example.ndiaz.parquesbsas.edittextvalidator.reclamo;

import android.content.Context;
import android.widget.EditText;

import com.example.ndiaz.parquesbsas.R;
import com.example.ndiaz.parquesbsas.edittextvalidator.EditTextValidator;

public class EditTextComentarioValidator extends EditTextValidator {

    private EditText etComentario;
    private Context context;

    public EditTextComentarioValidator(EditText etComentario) {
        this.etComentario = etComentario;
        this.context = etComentario.getContext();
    }

    @Override
    public boolean validate() {
        String comentario = etComentario.getText().toString().trim();
        boolean isValid = true;

        if (comentario.isEmpty()) {
            etComentario.setError(context.getString(R.string.invalid_comentario));
            isValid = false;
        } else if (hasIncorrectLength(comentario, 3, 512)) {
            etComentario.setError(context.getString(R.string.invalid_length));
            isValid = false;
        }

        return isValid;
    }

    @Override
    public String getComponentName() {
        return null;
    }
}
