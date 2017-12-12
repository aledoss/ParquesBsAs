package com.example.ndiaz.parquesbsas.ui.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ndiaz.parquesbsas.R;
import com.example.ndiaz.parquesbsas.model.Parque;

import java.util.List;

public class AdapterListaParques extends ArrayAdapter<Parque> {

    private Context context;
    private List<Parque> listaParques;

    public AdapterListaParques(Context context) {
        super(context, R.layout.row_parque);
        this.context = context;
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

    public void setItemList(List<Parque> parques) {
        this.listaParques = parques;
    }

    public List<Parque> getListaParques() {
        return listaParques;
    }

    @Override
    public int getCount() {
        return listaParques != null ? this.listaParques.size() : 0;
    }

    @Nullable
    @Override
    public Parque getItem(int position) {
        return this.listaParques.get(position);
    }

    private static class ViewHolder {
        TextView txtNombreParque, txtDescripcionParque;
        ImageView imgParque;
    }
}
