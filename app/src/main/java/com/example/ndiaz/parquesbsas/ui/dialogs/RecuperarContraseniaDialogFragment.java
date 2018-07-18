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
import com.example.ndiaz.parquesbsas.listeners.OnRecuperarContraseniaListener;

import butterknife.BindView;

import static com.example.ndiaz.parquesbsas.edittextvalidator.usuario.UserFactoryEditText.RECOVER_USER_EMAIL_ORIGIN;

public class RecuperarContraseniaDialogFragment extends BaseDialogFragment {

    @BindView(R.id.etEmail)
    EditText etEmail;

    private OnRecuperarContraseniaListener listener;
    private ViewHelper viewHelper;

    public static RecuperarContraseniaDialogFragment newInstance(OnRecuperarContraseniaListener listener) {
        RecuperarContraseniaDialogFragment fragment = new RecuperarContraseniaDialogFragment();
        fragment.listener = listener;
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewHelper = new ViewHelper();
    }

    @Override
    int getLayout() {
        return R.layout.dialog_recuperar_contrasenia;
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
                listener.onRecuperarContrasenia(etEmail.getText().toString());
                dismiss();
            }
        });
    }

    private boolean datosCorrectos() {
        UserFactoryEditText factoryEditText = new UserFactoryEditText();

        factoryEditText.setEtEmail(etEmail);
        factoryEditText.setOrigin(RECOVER_USER_EMAIL_ORIGIN);

        return viewHelper.isValidData(factoryEditText);
    }
}
