package com.example.ndiaz.parquesbsas.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ndiaz.parquesbsas.R;
import com.example.ndiaz.parquesbsas.model.ParqueComponente;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ParqueComponentesAdapter extends RecyclerView.Adapter<ParqueComponentesAdapter.MyViewHolder> {

    private List<ParqueComponente> parqueComponentes;

    public ParqueComponentesAdapter(List<ParqueComponente> parqueComponentes) {
        this.parqueComponentes = parqueComponentes;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_componente_parque,
                parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        ParqueComponente parqueComponente = parqueComponentes.get(position);
        TextView txtComponente = holder.txtComponente;

        txtComponente.setText(parqueComponente.getNombreComponente());
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txtComponente)
        TextView txtComponente;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @Override
    public int getItemCount() {
        return parqueComponentes.size();
    }

    public ParqueComponente getitem(int position){
        if(parqueComponentes != null){
            return parqueComponentes.get(position);
        }
        return null;
    }
}
