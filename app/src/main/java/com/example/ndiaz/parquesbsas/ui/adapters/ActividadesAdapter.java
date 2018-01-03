package com.example.ndiaz.parquesbsas.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ndiaz.parquesbsas.R;
import com.example.ndiaz.parquesbsas.model.Actividad;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ActividadesAdapter extends RecyclerView.Adapter<ActividadesAdapter.MyViewHolder>{

    private List<Actividad> actividades;

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_simple_title,
                parent, false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Actividad actividad = actividades.get(position);

        holder.txtTitulo.setText(actividad.getNombre());
    }

    @Override
    public int getItemCount() {
        return this.actividades != null ? this.actividades.size() : 0;
    }

    public void setItemList(List<Actividad> parques) {
        this.actividades = parques;
    }

    public List<Actividad> getItemList() {
        return this.actividades != null ? this.actividades : new ArrayList<Actividad>();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txtTitulo)
        TextView txtTitulo;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
