package com.example.ndiaz.parquesbsas.ui.dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.View;

import butterknife.ButterKnife;
import butterknife.Unbinder;

abstract class BaseDialogFragment extends DialogFragment {

    private Unbinder unbinder;

    @Override
    public void onStart() {
        super.onStart();
        unbinder = ButterKnife.bind(this, getDialog());
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View view = getActivity().getLayoutInflater().inflate(getLayout(), null);
        builder.setView(view);
        initializeButtons(builder);

        return builder.create();
    }

    @LayoutRes
    abstract int getLayout();

    abstract void initializeButtons(AlertDialog.Builder builder);

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
