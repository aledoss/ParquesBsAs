package com.example.ndiaz.parquesbsas.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.ndiaz.parquesbsas.ParquesApplication;
import com.example.ndiaz.parquesbsas.R;
import com.example.ndiaz.parquesbsas.helpers.maps.URLMapImage;
import com.example.ndiaz.parquesbsas.model.Parque;
import com.example.ndiaz.parquesbsas.model.PuntoVerde;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PuntosVerdesAdapter extends RecyclerView.Adapter<PuntosVerdesAdapter.MyViewHolder> {

    private static final String TAG = PuntosVerdesAdapter.class.getSimpleName();
    private List<PuntoVerde> puntosVerdes;
    private Parque parque;
    private URLMapImage.Builder builder;

    public PuntosVerdesAdapter(Parque parque) {
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
        loadMapImage(holder, puntoVerde);
    }

    private void loadMapImage(final MyViewHolder holder, PuntoVerde puntoVerde) {
        Picasso
                .with(ParquesApplication.getInstance().getApplicationContext())
                .load(getMapUrl(puntoVerde).getUrl())
                .into(holder.imgMapaFeria, new Callback() {
                    @Override
                    public void onSuccess() {
                        holder.imgMapaFeria.setVisibility(View.VISIBLE);
                        holder.progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError() {
                        holder.progressBar.setVisibility(View.GONE);
                        Log.e(TAG, "loadMapImage, onError");
                    }
                });
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
        @BindView(R.id.imgMapaFeria)
        ImageView imgMapaFeria;
        @BindView(R.id.progressBarFeria)
        ProgressBar progressBar;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
