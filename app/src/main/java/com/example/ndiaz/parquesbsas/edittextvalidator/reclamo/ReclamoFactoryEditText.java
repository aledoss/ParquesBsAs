package com.example.ndiaz.parquesbsas.edittextvalidator.reclamo;

import android.widget.Button;
import android.widget.EditText;

import com.example.ndiaz.parquesbsas.edittextvalidator.EditTextValidator;
import com.example.ndiaz.parquesbsas.edittextvalidator.FactoryEditText;

import java.util.ArrayList;
import java.util.List;

public class ReclamoFactoryEditText implements FactoryEditText{

    private EditText etComentario;
    private Button btnReclamo;

    public ReclamoFactoryEditText(EditText etComentario, Button btnReclamo) {
        this.etComentario = etComentario;
        this.btnReclamo = btnReclamo;
    }

    @Override
    public List<EditTextValidator> createEditTextValidators(){
        List<EditTextValidator> editTextValidators = new ArrayList<>();

        editTextValidators.add(new EditTextComentarioValidator(etComentario));
        editTextValidators.add(new ButtonReclamoValidator(btnReclamo));

        return editTextValidators;
    }
}
