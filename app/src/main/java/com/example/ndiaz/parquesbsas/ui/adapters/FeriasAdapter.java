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
import com.example.ndiaz.parquesbsas.model.Feria;
import com.example.ndiaz.parquesbsas.model.Parque;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FeriasAdapter extends RecyclerView.Adapter<FeriasAdapter.MyViewHolder> {

    private static final String TAG = FeriasAdapter.class.getSimpleName();
    private List<Feria> ferias;
    private Parque parque;
    private URLMapImage.Builder builder;

    public FeriasAdapter(Parque parque) {
        this.parque = parque;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_feria,
                parent, false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Feria feria = ferias.get(position);
        builder = new URLMapImage.Builder();

        holder.txtTitulo.setText(feria.getTipo());
        holder.txtDescripcion.setText(feria.getFecha());
        loadMapImage(holder, feria);
    }

    private void loadMapImage(final MyViewHolder holder, Feria feria) {
        Picasso
                .with(ParquesApplication.getInstance().getApplicationContext())
                .load(getMapUrl(feria).getUrl())
                .into(holder.imgMapaFeria, new Callback() {
                    @Override
                    public void onSuccess() {
                        holder.imgMapaFeria.setVisibility(View.VISIBLE);
                        holder.progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError() {
                        holder.progressBar.setVisibility(View.GONE);
                        Log.e(TAG, "loadMapImage, onError" );
                    }
                });
    }

    private URLMapImage getMapUrl(Feria feria) {
        return builder
                .setLatitudCenter(parque.getLatitud())
                .setLongitudCenter(parque.getLongitud())
                .setLatitudMarker(feria.getLatitud())
                .setLongitudMarker(feria.getLongitud())
                .build();
    }

    @Override
    public int getItemCount() {
        return this.ferias != null ? this.ferias.size() : 0;
    }

    public void setItemList(List<Feria> ferias) {
        this.ferias = ferias;
    }

    public List<Feria> getItemList() {
        return this.ferias != null ? this.ferias : new ArrayList<Feria>();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txtTitulo)
        TextView txtTitulo;
        @BindView(R.id.txtDescripcion)
        TextView txtDescripcion;
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
