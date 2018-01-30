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
import com.example.ndiaz.parquesbsas.model.ReclamoFecha;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReclamosUsuarioAdapter extends RecyclerView.Adapter<ReclamosUsuarioAdapter.MyViewHolder> {

    private List<ReclamoFecha> reclamosFechas;

    public ReclamosUsuarioAdapter(List<ReclamoFecha> reclamosFechas) {
        this.reclamosFechas = reclamosFechas;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Depende el viewtype se va a inflar determinada vista (reclamo o fecha)

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_reclamo_usuario,
                parent, false);
        return new ReclamosUsuarioAdapter.MyViewHolder(v);
    }

    @Override
    public int getItemViewType(int position) {
        // TODO: 30/1/2018 Obtener el item de la lista mediante la posición y devolver un entero (definir constantes) referenciando a la vista que debería inflar
        return super.getItemViewType(position);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        ReclamoFecha reclamoFecha = reclamosFechas.get(position);

        /*holder.txtDescReclamo.setText(reclamoFecha.getNombre());
        holder.txtNombreParque.setText(reclamoFecha.getNombreParque());
        changeEstadoReclamoColor(holder.viewEstadoReclamo, reclamoFecha.getColorEstado());*/
        // TODO: 29/1/2018 fecha (como cabecera de la lista)
    }

    private void changeEstadoReclamoColor(View viewEstadoReclamo, String colorEstado) {
        Drawable background = viewEstadoReclamo.getBackground();
        ((ShapeDrawable)background).getPaint().setColor(Color.parseColor(colorEstado));
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

    public void setItemList(List<ReclamoFecha> reclamosFechas){
        this.reclamosFechas = reclamosFechas;
    }
}
