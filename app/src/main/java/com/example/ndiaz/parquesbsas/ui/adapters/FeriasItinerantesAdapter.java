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
import com.example.ndiaz.parquesbsas.model.FeriaItinerante;
import com.example.ndiaz.parquesbsas.model.Parque;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FeriasItinerantesAdapter extends RecyclerView.Adapter<FeriasItinerantesAdapter.MyViewHolder> {

    private List<FeriaItinerante> feriasItinerantes;
    private Parque parque;
    private URLMapImage.Builder builder;
    private Context context;
    private ViewHelper viewHelper;
    private AlertDialogBuilder dialogBuilder;
    private IntentMap intentMap;

    public FeriasItinerantesAdapter(Context context, Parque parque) {
        this.context = context;
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
        FeriaItinerante feria = feriasItinerantes.get(position);
        builder = new URLMapImage.Builder();

        holder.txtTitulo.setText(feria.getFecha());
        holder.txtDescripcion.setText(feria.getDireccion());
        holder.txtComoLlego.setOnClickListener(v -> dialogBuilder.buildConfirmationDialogToNavigateMapsWI(context, intentMap,
                feria.getLatitud(), feria.getLongitud()).show());
        viewHelper.loadMapImage(context, holder.imgMapaFeria, holder.progressBar, getMapUrl(feria),
                FeriasItinerantesAdapter.class.getSimpleName());
    }

    private URLMapImage getMapUrl(FeriaItinerante feria) {
        return builder
                .setLatitudCenter(parque.getLatitud())
                .setLongitudCenter(parque.getLongitud())
                .setLatitudMarker(feria.getLatitud())
                .setLongitudMarker(feria.getLongitud())
                .build();
    }

    @Override
    public int getItemCount() {
        return this.feriasItinerantes != null ? this.feriasItinerantes.size() : 0;
    }

    public void setItemList(List<FeriaItinerante> ferias) {
        this.feriasItinerantes = ferias;
    }

    public List<FeriaItinerante> getItemList() {
        return this.feriasItinerantes != null ? this.feriasItinerantes : new ArrayList<FeriaItinerante>();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txtTitulo)
        TextView txtTitulo;
        @BindView(R.id.txtDescripcion)
        TextView txtDescripcion;
        @BindView(R.id.txtComoLlego)
        TextView txtComoLlego;
        @BindView(R.id.imgMapaFeria)
        ImageView imgMapaFeria;
        @BindView(R.id.progressBarFeria)
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
