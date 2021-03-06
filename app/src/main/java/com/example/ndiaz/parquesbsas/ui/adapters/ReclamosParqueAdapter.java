package com.example.ndiaz.parquesbsas.ui.adapters;

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

public class ReclamosParqueAdapter extends RecyclerView.Adapter<ReclamosParqueAdapter.MyViewHolder> {

    private List<Reclamo> reclamos;

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_reclamo_parque,
                parent, false);
        return new ReclamosParqueAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Reclamo reclamo = reclamos.get(position);

        holder.txtTitulo.setText(reclamo.getNombre());
        holder.txtCantReclamos.setText("(" + reclamo.getCantidad() + ")");
    }

    @Override
    public int getItemCount() {
        return reclamos != null ? reclamos.size() : 0;
    }

    public Reclamo getItem(int position) {
        if (reclamos != null && !reclamos.isEmpty()) {
            return reclamos.get(position);
        }
        return new Reclamo();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txtTitulo)
        TextView txtTitulo;
        @BindView(R.id.txtCantidadReclamos)
        TextView txtCantReclamos;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void setItemList(List<Reclamo> reclamos){
        this.reclamos = reclamos;
    }
}
