package com.example.ndiaz.parquesbsas.ui.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ndiaz.parquesbsas.R;
import com.example.ndiaz.parquesbsas.model.Encuesta;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EncuestasParqueAdapter extends RecyclerView.Adapter<EncuestasParqueAdapter.MyViewHolder> {

    private List<Encuesta> encuestas;

    public EncuestasParqueAdapter(List<Encuesta> encuestas) {
        this.encuestas = encuestas;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_title_count,
                parent, false);
        return new EncuestasParqueAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Encuesta encuesta = encuestas.get(position);

        holder.txtTitulo.setText(encuesta.getNombre());
        holder.txtCantEncuestas.setText("(" + encuesta.getCant() + ")");
    }

    @Override
    public int getItemCount() {
        return encuestas.size();
    }

    public Encuesta getItem(int position) {
        if (encuestas != null && !encuestas.isEmpty()) {
            return encuestas.get(position);
        }
        return new Encuesta();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txtTitulo)
        TextView txtTitulo;
        @BindView(R.id.txtCant)
        TextView txtCantEncuestas;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void setItemList(List<Encuesta> encuestas) {
        this.encuestas = encuestas;
    }
}
