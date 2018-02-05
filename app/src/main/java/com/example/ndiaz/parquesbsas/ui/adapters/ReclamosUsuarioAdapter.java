package com.example.ndiaz.parquesbsas.ui.adapters;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ndiaz.parquesbsas.R;
import com.example.ndiaz.parquesbsas.model.ReclamoFecha;

import java.util.List;

import javax.annotation.Nullable;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReclamosUsuarioAdapter extends RecyclerView.Adapter<ReclamosUsuarioAdapter.MyViewHolder> {

    private static final int FECHA = 1;
    private static final int RECLAMO = 2;
    private List<ReclamoFecha> reclamosFechas;

    public ReclamosUsuarioAdapter(List<ReclamoFecha> reclamosFechas) {
        this.reclamosFechas = reclamosFechas;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;

        if (viewType == FECHA) {
            // TODO: 05/02/2018 Mejorar vista
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
            changeEstadoReclamoColor(holder.viewEstadoReclamo, reclamoFecha.getReclamo().getColorEstado());
        }

    }

    private void changeEstadoReclamoColor(View viewEstadoReclamo, String colorEstado) {
        Drawable background = viewEstadoReclamo.getBackground();
        ((GradientDrawable) background).setColor(Color.parseColor("#" + colorEstado));
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

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void setItemList(List<ReclamoFecha> reclamosFechas) {
        this.reclamosFechas = reclamosFechas;
    }
}
