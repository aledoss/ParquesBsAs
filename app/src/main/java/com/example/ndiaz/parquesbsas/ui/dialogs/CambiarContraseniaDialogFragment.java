package com.example.ndiaz.parquesbsas.ui.dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.widget.Button;
import android.widget.EditText;

import com.example.ndiaz.parquesbsas.R;
import com.example.ndiaz.parquesbsas.edittextvalidator.usuario.UserFactoryEditText;
import com.example.ndiaz.parquesbsas.helpers.ViewHelper;
import com.example.ndiaz.parquesbsas.listeners.OnCambiarContraseniaListener;

import butterknife.BindView;
import butterknife.OnTextChanged;

import static com.example.ndiaz.parquesbsas.edittextvalidator.usuario.UserFactoryEditText.UPDATE_USER_PASSWORD_ORIGIN;

public class CambiarContraseniaDialogFragment extends BaseDialogFragment {

    @BindView(R.id.etOldPassword)
    EditText etOldPassword;
    @BindView(R.id.etNewPassword)
    EditText etNewPassword;
    @BindView(R.id.etRepeatNewPassword)
    EditText etRepeatNewPassword;

    private OnCambiarContraseniaListener listener;
    private ViewHelper viewHelper;

    @OnTextChanged(R.id.etRepeatNewPassword)
    protected void onRepeatNewPasswordChanged() {
        etRepeatNewPassword.setError(null);
    }

    public static CambiarContraseniaDialogFragment newInstance(OnCambiarContraseniaListener listener) {

        CambiarContraseniaDialogFragment fragment = new CambiarContraseniaDialogFragment();
        fragment.listener = listener;

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeVariables();
    }

    private void initializeVariables() {
        viewHelper = new ViewHelper();
    }

    @Override
    int getLayout() {
        return R.layout.dialog_cambiar_contrasenia;
    }

    @Override
    void initializeButtons(AlertDialog.Builder builder) {
        builder.setPositiveButton(R.string.dialog_accept, (dialog, which) -> {
        });
        builder.setNegativeButton(R.string.dialog_cancel, (dialog, which) -> dialog.dismiss());
    }

    @Override
    public void onResume() {
        super.onResume();
        handlePositiveClickButton();
    }

    private void handlePositiveClickButton() {
        Button positiveButton = ((AlertDialog) getDialog()).getButton(Dialog.BUTTON_POSITIVE);
        positiveButton.setOnClickListener(v -> {
            if (datosCorrectos()) {
                listener.onCambiarContrasenia(getText(etOldPassword), getText(etNewPassword));
                dismiss();
            }
        });
    }

    private boolean datosCorrectos() {
        UserFactoryEditText userFactoryEditText = new UserFactoryEditText();
        userFactoryEditText.setEtPassword(etNewPassword);
        userFactoryEditText.setEtRepeatPassword(etRepeatNewPassword);
        userFactoryEditText.setEtOldPassword(etOldPassword);
        userFactoryEditText.setOrigin(UPDATE_USER_PASSWORD_ORIGIN);
        boolean isValidPassword = viewHelper.isValidData(userFactoryEditText);

        if (isValidPassword) {
            if (getText(etNewPassword).equals(getText(etRepeatNewPassword))) {
                isValidPassword = true;
            } else {
                isValidPassword = false;
                etRepeatNewPassword.requestFocus();
                etRepeatNewPassword.setError(getString(R.string.contrasenias_deben_coincidir));
            }
        }

        return isValidPassword;
    }

    private String getText(EditText et) {
        return et.getText().toString();
    }
}
