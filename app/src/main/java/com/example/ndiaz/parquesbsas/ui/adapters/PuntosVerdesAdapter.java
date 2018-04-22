package com.example.ndiaz.parquesbsas.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.ndiaz.parquesbsas.R;
import com.example.ndiaz.parquesbsas.helpers.ViewHelper;
import com.example.ndiaz.parquesbsas.helpers.maps.URLMapImage;
import com.example.ndiaz.parquesbsas.model.Parque;
import com.example.ndiaz.parquesbsas.model.PuntoVerde;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PuntosVerdesAdapter extends RecyclerView.Adapter<PuntosVerdesAdapter.MyViewHolder> {

    private List<PuntoVerde> puntosVerdes;
    private Parque parque;
    private URLMapImage.Builder builder;
    private ViewHelper viewHelper;
    private Context context;

    public PuntosVerdesAdapter(Context context, Parque parque) {
        this.context = context;
        this.parque = parque;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_punto_verde,
                parent, false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        PuntoVerde puntoVerde = puntosVerdes.get(position);
        builder = new URLMapImage.Builder();

        holder.txtTitulo.setText(puntoVerde.getTipo());
        holder.txtDescripcion1.setText(puntoVerde.getDiasHorarios());
        holder.txtDescripcion2.setText(puntoVerde.getMateriales());
        viewHelper.loadMapImage(context, holder.imgMapaPuntoVerde, holder.progressBar, getMapUrl(puntoVerde),
                PuntosVerdesAdapter.class.getSimpleName());
    }

    private URLMapImage getMapUrl(PuntoVerde puntoVerde) {
        return builder
                .setLatitudCenter(parque.getLatitud())
                .setLongitudCenter(parque.getLongitud())
                .setLatitudMarker(puntoVerde.getLatitud())
                .setLongitudMarker(puntoVerde.getLongitud())
                .build();
    }

    @Override
    public int getItemCount() {
        return this.puntosVerdes != null ? this.puntosVerdes.size() : 0;
    }

    public void setItemList(List<PuntoVerde> puntosVerdes) {
        this.puntosVerdes = puntosVerdes;
    }

    public List<PuntoVerde> getItemList() {
        return this.puntosVerdes != null ? this.puntosVerdes : new ArrayList<PuntoVerde>();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txtTitulo)
        TextView txtTitulo;
        @BindView(R.id.txtDescripcion1)
        TextView txtDescripcion1;
        @BindView(R.id.txtDescripcion2)
        TextView txtDescripcion2;
        @BindView(R.id.imgMapaPuntoVerde)
        ImageView imgMapaPuntoVerde;
        @BindView(R.id.progressBarPuntoVerde)
        ProgressBar progressBar;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            viewHelper = new ViewHelper();
        }
    }

}
