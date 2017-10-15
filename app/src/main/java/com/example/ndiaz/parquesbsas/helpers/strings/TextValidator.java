package com.example.ndiaz.parquesbsas.helpers.strings;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextValidator {

    public boolean isAlpha(String name) {
        return name.matches("[a-zA-Z]+");
    }

    public boolean isValidPassword(String password) {
        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$";

        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();
    }

}
