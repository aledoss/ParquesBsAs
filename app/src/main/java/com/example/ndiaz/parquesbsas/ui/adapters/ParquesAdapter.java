package com.example.ndiaz.parquesbsas.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ndiaz.parquesbsas.R;
import com.example.ndiaz.parquesbsas.model.Parque;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ParquesAdapter extends RecyclerView.Adapter<ParquesAdapter.MyViewHolder> {

    private List<Parque> parques;

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_parque,
                parent, false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Parque parque = parques.get(position);

        holder.txtNombreParque.setText(parque.getNombre());
        holder.txtDescCorta.setText(parque.getDescripcionCorta());
    }

    @Override
    public int getItemCount() {
        return this.parques != null ? this.parques.size() : 0;
    }

    public void setItemList(List<Parque> parques) {
        this.parques = parques;
    }

    public List<Parque> getParques() {
        return this.parques != null ? this.parques : new ArrayList<Parque>();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.row_parque_txt_nombre)
        TextView txtNombreParque;
        @BindView(R.id.row_parque_txt_desc_corta)
        TextView txtDescCorta;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
