package com.example.ndiaz.parquesbsas.edittextvalidator.usuario;

import android.widget.EditText;

import com.example.ndiaz.parquesbsas.edittextvalidator.EditTextValidator;
import com.example.ndiaz.parquesbsas.edittextvalidator.FactoryEditText;

import java.util.ArrayList;
import java.util.List;

import static com.example.ndiaz.parquesbsas.constants.LoginConstants.DNI;
import static com.example.ndiaz.parquesbsas.constants.LoginConstants.LC;
import static com.example.ndiaz.parquesbsas.constants.LoginConstants.PASSPORT;

public class UserFactoryEditText implements FactoryEditText {


    private static final String LOGIN_ORIGIN = "Login Origin";
    private static final String CREATE_USER_ORIGIN = "Create User Origin";
    public static final String UPDATE_USER_NAME_ORIGIN = "Update User Name Origin";
    public static final String UPDATE_USER_DOC_ORIGIN = "Update User Doc Origin";
    private EditText etName;
    private EditText etLastName;
    private EditText etEmail;
    private EditText etPassword;
    private EditText etDocNum;
    private String docType;
    private String origin;

    public UserFactoryEditText() {
    }

    public UserFactoryEditText(EditText etEmail, EditText etPassword) {
        this.etEmail = etEmail;
        this.etPassword = etPassword;
        this.origin = LOGIN_ORIGIN;
    }

    public UserFactoryEditText(EditText etName, EditText etLastName, EditText etEmail,
                               EditText etPassword, EditText etDocNum, String docType) {
        this.etName = etName;
        this.etLastName = etLastName;
        this.etEmail = etEmail;
        this.etPassword = etPassword;
        this.etDocNum = etDocNum;
        this.docType = docType;
        this.origin = CREATE_USER_ORIGIN;
    }

    @Override
    public List<EditTextValidator> createEditTextValidators() {
        List<EditTextValidator> editTextValidators = new ArrayList<>();

        switch (origin) {
            case CREATE_USER_ORIGIN:
                editTextValidators.add(new EditTextNameValidator(etName));
                editTextValidators.add(new EditTextLastNameValidator(etLastName));
                editTextValidators.add(setDocTypeValidator());
                editTextValidators.add(new EditTextEmailValidator(etEmail));
                editTextValidators.add(new EditTextPasswordValidator(etPassword));
                break;
            case LOGIN_ORIGIN:
                editTextValidators.add(new EditTextEmailValidator(etEmail));
                editTextValidators.add(new EditTextPasswordValidator(etPassword));
                break;
            case UPDATE_USER_NAME_ORIGIN:
                editTextValidators.add(new EditTextNameValidator(etName));
                editTextValidators.add(new EditTextLastNameValidator(etLastName));
                break;
            case UPDATE_USER_DOC_ORIGIN:
                editTextValidators.add(setDocTypeValidator());
                break;
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
                //break;
            case LC:
                //break;
            default:
                editTextValidator = new EditTextDNIValidator(etDocNum);
                break;
        }
        return editTextValidator;
    }

    public void setEtName(EditText etName) {
        this.etName = etName;
    }

    public void setEtLastName(EditText etLastName) {
        this.etLastName = etLastName;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public void setEtDocNum(EditText etDocNum) {
        this.etDocNum = etDocNum;
    }

    public void setDocType(String docType) {
        this.docType = docType;
    }
}
