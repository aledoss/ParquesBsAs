package com.example.ndiaz.parquesbsas.ui.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.ndiaz.parquesbsas.R;
import com.example.ndiaz.parquesbsas.model.Reclamo;

import java.util.ArrayList;

/**
 * Created by Lenwe on 01/12/2016.
 */

public class AdapterListaReclamos extends ArrayAdapter<Reclamo> {
    private Context context;
    private ArrayList<Reclamo> listaReclamos = new ArrayList<>();

    public AdapterListaReclamos(Context context, ArrayList<Reclamo> listaReclamos) {
        super(context, R.layout.row_reclamo, listaReclamos);
        this.context = context;
        this.listaReclamos = listaReclamos;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Reclamo reclamo = getItem(position);
        AdapterListaReclamos.ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new AdapterListaReclamos.ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.row_reclamo, parent, false);
            viewHolder.txtNombreParque = (TextView) convertView.findViewById(R.id.row_reclamo_txt_nombre_parque);
            viewHolder.txtNombreReclamo = (TextView) convertView.findViewById(R.id.row_reclamo_txt_nombre_reclamo);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (AdapterListaReclamos.ViewHolder) convertView.getTag();
        }

        viewHolder.txtNombreParque.setText(reclamo.getParque());
        viewHolder.txtNombreReclamo.setText(reclamo.getNombre());

        return convertView;
    }

    private static class ViewHolder {
        TextView txtNombreParque, txtNombreReclamo;
    }
}
