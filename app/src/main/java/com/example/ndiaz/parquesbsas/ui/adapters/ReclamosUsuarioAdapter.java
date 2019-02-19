package com.example.ndiaz.parquesbsas.ui.adapters;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ndiaz.parquesbsas.R;
import com.example.ndiaz.parquesbsas.helpers.ViewHelper;
import com.example.ndiaz.parquesbsas.listeners.OnReclamoListenerClick;
import com.example.ndiaz.parquesbsas.model.ReclamoFecha;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReclamosUsuarioAdapter extends RecyclerView.Adapter<ReclamosUsuarioAdapter.MyViewHolder> {

    private static final int FECHA = 1;
    private static final int RECLAMO = 2;
    private Context context;
    private List<ReclamoFecha> reclamosFechas;
    private ViewHelper viewHelper;
    private OnReclamoListenerClick listener;

    public ReclamosUsuarioAdapter(Context context, List<ReclamoFecha> reclamosFechas,
                                  OnReclamoListenerClick listener) {
        this.context = context;
        this.reclamosFechas = reclamosFechas;
        this.listener = listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;

        if (viewType == FECHA) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_fecha,
                    parent, false);
        } else {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_reclamo_usuario,
                    parent, false);
        }

        return new ReclamosUsuarioAdapter.MyViewHolder(v);
    }

    @Override
    public int getItemViewType(int position) {
        int type;

        if (reclamosFechas.get(position).isFecha()) {
            type = FECHA;
        } else {
            type = RECLAMO;
        }

        return type;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        ReclamoFecha reclamoFecha = reclamosFechas.get(position);

        if (reclamoFecha.isFecha()) {
            holder.txtFecha.setText(reclamoFecha.getFormattedDate());
        } else {
            holder.txtDescReclamo.setText(reclamoFecha.getReclamo().getNombre());
            holder.txtNombreParque.setText(reclamoFecha.getReclamo().getNombreParque());
            holder.deleteReclamo.setOnClickListener(V -> showDialogDeleteReclamo(reclamoFecha.getReclamo().getIdReclamoUsuarioParque()));
            holder.itemView.setOnClickListener(v -> listener.onClick(reclamoFecha));
            viewHelper.changeXMLViewColor(holder.viewEstadoReclamo, reclamoFecha.getReclamo().getColorEstado());
        }
    }

    private void showDialogDeleteReclamo(int idURP) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(R.string.delete_reclamo)
                .setPositiveButton(R.string.dialog_delete, (dialog, which) -> listener.onDelete(idURP))
                .setNegativeButton(R.string.dialog_cancel, null)
                .setCancelable(false)
                .show();
    }

    @Override
    public int getItemCount() {
        return reclamosFechas.size();
    }

    public ReclamoFecha getItem(int position) {
        if (reclamosFechas != null && !reclamosFechas.isEmpty()) {
            return reclamosFechas.get(position);
        }
        return new ReclamoFecha();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @Nullable
        @BindView(R.id.txtDescReclamo)
        TextView txtDescReclamo;
        @Nullable
        @BindView(R.id.txtNombreParque)
        TextView txtNombreParque;
        @Nullable
        @BindView(R.id.circleState)
        View viewEstadoReclamo;
        @Nullable
        @BindView(R.id.txtFecha)
        TextView txtFecha;
        @Nullable
        @BindView(R.id.deleteReclamo)
        ImageView deleteReclamo;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            viewHelper = new ViewHelper();
        }
    }

    public void setItemList(List<ReclamoFecha> reclamosFechas) {
        this.reclamosFechas = reclamosFechas;
    }
}
