package com.example.ndiaz.parquesbsas.ui.adapters;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ndiaz.parquesbsas.R;
import com.example.ndiaz.parquesbsas.model.Reclamo;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReclamosUsuarioAdapter extends RecyclerView.Adapter<ReclamosUsuarioAdapter.MyViewHolder> {

    private List<Reclamo> reclamos;

    public ReclamosUsuarioAdapter(List<Reclamo> reclamos) {
        this.reclamos = reclamos;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_reclamo_usuario,
                parent, false);
        return new ReclamosUsuarioAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Reclamo reclamo = reclamos.get(position);

        holder.txtDescReclamo.setText(reclamo.getNombre());
        holder.txtNombreParque.setText(reclamo.getNombreParque());
        changeEstadoReclamoColor(holder.viewEstadoReclamo, reclamo.getColorEstado());
        // TODO: 29/1/2018 fecha (como cabecera de la lista)
    }

    private void changeEstadoReclamoColor(View viewEstadoReclamo, String colorEstado) {
        Drawable background = viewEstadoReclamo.getBackground();
        ((ShapeDrawable)background).getPaint().setColor(Color.parseColor(colorEstado));
    }

    @Override
    public int getItemCount() {
        return reclamos.size();
    }

    public Reclamo getItem(int position) {
        if (reclamos != null && !reclamos.isEmpty()) {
            return reclamos.get(position);
        }
        return new Reclamo();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txtDescReclamo)
        TextView txtDescReclamo;
        @BindView(R.id.txtNombreParque)
        TextView txtNombreParque;
        @BindView(R.id.circleState)
        View viewEstadoReclamo;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void setItemList(List<Reclamo> reclamos){
        this.reclamos = reclamos;
    }
}
