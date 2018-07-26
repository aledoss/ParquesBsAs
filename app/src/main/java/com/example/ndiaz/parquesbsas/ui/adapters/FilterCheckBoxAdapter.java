package com.example.ndiaz.parquesbsas.ui.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.example.ndiaz.parquesbsas.model.filter_checkbox.BaseFilterCheckbox;
import com.example.ndiaz.parquesbsas.ui.custom.filter_checkbox.FilterCheckBox;

public class FilterCheckBoxAdapter extends RecyclerView.Adapter<FilterCheckBoxAdapter.MyViewHolder> {

    private BaseFilterCheckbox baseFilterCheckbox;

    public FilterCheckBoxAdapter(BaseFilterCheckbox baseFilterCheckbox) {
        this.baseFilterCheckbox = baseFilterCheckbox;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        FilterCheckBox filterCheckBox = new FilterCheckBox(parent.getContext());
        return new FilterCheckBoxAdapter.MyViewHolder(filterCheckBox);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.getFilterCheckBox().setText(baseFilterCheckbox.getTitle(position));
//        baseFilterCheckbox.getId(position);
    }

    @Override
    public int getItemCount() {
        return baseFilterCheckbox.getCount();
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
