package com.example.ndiaz.parquesbsas.ui.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.example.ndiaz.parquesbsas.model.filter_checkbox.BaseFilterCheckbox;
import com.example.ndiaz.parquesbsas.ui.custom.filter_checkbox.FilterCheckBox;

import java.util.List;

public class FilterCheckBoxAdapter extends RecyclerView.Adapter<FilterCheckBoxAdapter.MyViewHolder> {

    private List<BaseFilterCheckbox> baseFilterCheckboxs;

    public FilterCheckBoxAdapter(List<BaseFilterCheckbox> baseFilterCheckboxs) {
        this.baseFilterCheckboxs = baseFilterCheckboxs;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        FilterCheckBox filterCheckBox = new FilterCheckBox(parent.getContext());
        filterCheckBox.setOnClickListener(v -> {
            filterCheckBox.onClick(v);
            int position = (int) filterCheckBox.getTag();
            baseFilterCheckboxs.get(position).setChecked(filterCheckBox.isChecked());
        });
        return new FilterCheckBoxAdapter.MyViewHolder(filterCheckBox);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.getFilterCheckBox().setText(baseFilterCheckboxs.get(position).getTitle());
        holder.getFilterCheckBox().setTag(position);
        baseFilterCheckboxs.get(position).setChecked(holder.getFilterCheckBox().isChecked());
    }

    @Override
    public int getItemCount() {
        return baseFilterCheckboxs.size();
    }

    public List<BaseFilterCheckbox> getItems() {
        return baseFilterCheckboxs;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private FilterCheckBox filterCheckBox;

        public MyViewHolder(View itemView) {
            super(itemView);
            filterCheckBox = (FilterCheckBox) itemView;
        }

        public FilterCheckBox getFilterCheckBox() {
            return filterCheckBox;
        }
    }
}
