package com.example.ndiaz.parquesbsas.util.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ndiaz.parquesbsas.R;
import com.example.ndiaz.parquesbsas.database.Parque;

import java.util.ArrayList;

/**
 * Created by Lenwe on 19/10/2016.
 */

public class AdapterListaParques extends ArrayAdapter<Parque> {

    Context context;
    ArrayList<Parque> listaParques = new ArrayList<>();

    public AdapterListaParques(Context context, ArrayList<Parque> listaParques) {
        super(context, R.layout.row_parque, listaParques);
        this.context = context;
        this.listaParques = listaParques;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Parque parque = getItem(position);
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.row_parque, parent, false);
            viewHolder.imgParque = (ImageView) convertView.findViewById(R.id.row_parque_img);
            viewHolder.txtNombreParque = (TextView) convertView.findViewById(R.id.row_parque_txt_nombre);
            viewHolder.txtDescripcionParque = (TextView) convertView.findViewById(R.id.row_parque_txt_desc_corta);

            convertView.setTag(viewHolder);
        } else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.txtNombreParque.setText(parque.getNombre());
        viewHolder.txtDescripcionParque.setText(parque.getDescripcionCorta());

        return convertView;
    }

    private static class ViewHolder {
        TextView txtNombreParque, txtDescripcionParque;
        ImageView imgParque;
    }
}
