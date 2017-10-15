package com.example.ndiaz.parquesbsas.edittextvalidator;

import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import static com.example.ndiaz.parquesbsas.constants.Constants.CREATE_USER_ORIGIN;
import static com.example.ndiaz.parquesbsas.constants.Constants.LOGIN_ORIGIN;
import static com.example.ndiaz.parquesbsas.constants.LoginConstants.DNI;
import static com.example.ndiaz.parquesbsas.constants.LoginConstants.EMAIL;
import static com.example.ndiaz.parquesbsas.constants.LoginConstants.LAST_NAME;
import static com.example.ndiaz.parquesbsas.constants.LoginConstants.LC;
import static com.example.ndiaz.parquesbsas.constants.LoginConstants.NAME;
import static com.example.ndiaz.parquesbsas.constants.LoginConstants.PASSPORT;
import static com.example.ndiaz.parquesbsas.constants.LoginConstants.PASSWORD;

public class FactoryEditText {

    private EditText etName;
    private EditText etLastName;
    private EditText etEmail;
    private EditText etPassword;
    private EditText etDocNum;
    private String docType;
    private String origin;

    public FactoryEditText(EditText etEmail, EditText etPassword) {
        this.etEmail = etEmail;
        this.etPassword = etPassword;
        this.origin = LOGIN_ORIGIN;
    }

    public FactoryEditText(EditText etName, EditText etLastName, EditText etEmail,
                           EditText etPassword, EditText etDocNum, String docType) {
        this.etName = etName;
        this.etLastName = etLastName;
        this.etEmail = etEmail;
        this.etPassword = etPassword;
        this.etDocNum = etDocNum;
        this.docType = docType;
        this.origin = CREATE_USER_ORIGIN;
    }

    public List<EditTextValidator> createEditTextValidators() {
        List<EditTextValidator> editTextValidators = new ArrayList<>();

        editTextValidators.add(new EditTextEmailValidator(etEmail));
        editTextValidators.add(new EditTextPasswordValidator(etPassword));

        if(origin.equalsIgnoreCase(CREATE_USER_ORIGIN)){
            editTextValidators.add(new EditTextNameValidator(etName));
            editTextValidators.add(new EditTextLastNameValidator(etLastName));
            editTextValidators.add(setDocTypeValidator());
        }

        return editTextValidators;
    }

    private EditTextValidator setDocTypeValidator() {
        EditTextValidator editTextValidator = null;
        switch (docType) {
            case DNI:
                editTextValidator = new EditTextDNIValidator(etDocNum);
                break;
            case PASSPORT:
                break;
            case LC:
                break;
            default:
                break;
        }
        return editTextValidator;
    }

}
