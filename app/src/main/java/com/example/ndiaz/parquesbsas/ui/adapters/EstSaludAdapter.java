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
import com.example.ndiaz.parquesbsas.helpers.AlertDialogBuilder;
import com.example.ndiaz.parquesbsas.helpers.ViewHelper;
import com.example.ndiaz.parquesbsas.helpers.maps.IntentMap;
import com.example.ndiaz.parquesbsas.helpers.maps.URLMapImage;
import com.example.ndiaz.parquesbsas.model.EstacionSaludable;
import com.example.ndiaz.parquesbsas.model.Parque;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EstSaludAdapter extends RecyclerView.Adapter<EstSaludAdapter.MyViewHolder> {

    private List<EstacionSaludable> estSaludables;
    private Parque parque;
    private URLMapImage.Builder builder;
    private Context context;
    private ViewHelper viewHelper;
    private AlertDialogBuilder dialogBuilder;
    private IntentMap intentMap;

    public EstSaludAdapter(Context context, Parque parque) {
        this.context = context;
        this.parque = parque;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_est_saludable,
                parent, false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        EstacionSaludable estacionSaludable = estSaludables.get(position);
        builder = new URLMapImage.Builder();

        holder.txtTitulo.setText(estacionSaludable.getServicios());
        holder.txtSubtitulo.setText(estacionSaludable.getFecha());
        holder.txtComoLlego.setOnClickListener(v -> dialogBuilder.buildConfirmationDialogToNavigateMapsWI(context, intentMap,
                estacionSaludable.getLatitud(), estacionSaludable.getLongitud()).show());
        viewHelper.loadMapImage(context, holder.imgMapaEstSaludable, holder.progressBar, getMapUrl(estacionSaludable),
                EstSaludAdapter.class.getSimpleName());
    }

    private URLMapImage getMapUrl(EstacionSaludable estacionSaludable) {
        return builder
                .setLatitudCenter(parque.getLatitud())
                .setLongitudCenter(parque.getLongitud())
                .setLatitudMarker(estacionSaludable.getLatitud())
                .setLongitudMarker(estacionSaludable.getLongitud())
                .build();
    }

    @Override
    public int getItemCount() {
        return this.estSaludables != null ? this.estSaludables.size() : 0;
    }

    public void setItemList(List<EstacionSaludable> estSaludables) {
        this.estSaludables = estSaludables;
    }

    public List<EstacionSaludable> getItemList() {
        return this.estSaludables != null ? this.estSaludables : new ArrayList<EstacionSaludable>();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txtTitulo)
        TextView txtTitulo;
        @BindView(R.id.txtSubtitulo)
        TextView txtSubtitulo;
        @BindView(R.id.txtComoLlego)
        TextView txtComoLlego;
        @BindView(R.id.imgMapaEstSaludable)
        ImageView imgMapaEstSaludable;
        @BindView(R.id.progressBarEstSaludable)
        ProgressBar progressBar;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            viewHelper = new ViewHelper();
            dialogBuilder = new AlertDialogBuilder();
            intentMap = new IntentMap(context);
        }
    }

}
