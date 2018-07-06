package com.example.ndiaz.parquesbsas.ui.dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.ndiaz.parquesbsas.R;
import com.example.ndiaz.parquesbsas.edittextvalidator.usuario.UserFactoryEditText;
import com.example.ndiaz.parquesbsas.helpers.ViewHelper;
import com.example.ndiaz.parquesbsas.listeners.OnCambiarNombYApeListener;
import com.example.ndiaz.parquesbsas.model.Usuario;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class CambiarNombYApeDialogFragment extends DialogFragment {

    @BindView(R.id.etNombre)
    EditText etNombre;
    @BindView(R.id.etApellido)
    EditText etApellido;

    private Unbinder unbinder;
    private Usuario usuario;
    private OnCambiarNombYApeListener listener;
    private ViewHelper viewHelper;

    public static CambiarNombYApeDialogFragment newInstance(@NonNull Usuario usuario, @NonNull OnCambiarNombYApeListener listener) {
        CambiarNombYApeDialogFragment fragment = new CambiarNombYApeDialogFragment();

        fragment.usuario = usuario;
        fragment.listener = listener;

        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_cambiar_nomb_y_ape, null);
        builder.setView(view);
        setCancelable(false);
        viewHelper = new ViewHelper();
        initializeButtons(builder);

        return builder.create();
    }

    private void initializeButtons(AlertDialog.Builder builder) {
        builder.setPositiveButton(R.string.dialog_accept, (dialog, which) -> {
        });
        builder.setNegativeButton(R.string.dialog_cancel, (dialog, which) -> dialog.dismiss());
    }

    private void initializeViews() {
        etNombre.setText(usuario.getNombre());
        etApellido.setText(usuario.getApellido());
    }

    @Override
    public void onStart() {
        super.onStart();
        unbinder = ButterKnife.bind(this, getDialog());
        initializeViews();
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
                listener.onCambiarNombYApe(etNombre.getText().toString(), etApellido.getText().toString());
                dismiss();
            }
        });
    }

    private boolean datosCorrectos() {
        UserFactoryEditText factoryEditText = new UserFactoryEditText();

        factoryEditText.setEtName(etNombre);
        factoryEditText.setEtLastName(etApellido);
        factoryEditText.setOrigin(UserFactoryEditText.UPDATE_USER_NAME_ORIGIN);

        return viewHelper.isValidData(factoryEditText);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
