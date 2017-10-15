package com.example.ndiaz.parquesbsas.edittextvalidator;

import com.example.ndiaz.parquesbsas.helpers.strings.TextValidator;

public abstract class EditTextValidator {

    TextValidator textValidator = new TextValidator();

    public abstract boolean validate();

    public abstract String getComponentName();

    protected boolean hasIncorrectLength(String text, int length1, int length2) {
        return (text.length() < length1 || text.length() > length2);
    }
}
