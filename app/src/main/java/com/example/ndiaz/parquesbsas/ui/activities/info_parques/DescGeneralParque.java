package com.example.ndiaz.parquesbsas.ui.activities.info_parques;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.ndiaz.parquesbsas.R;
import com.example.ndiaz.parquesbsas.helpers.RecyclerItemClickListener;
import com.example.ndiaz.parquesbsas.model.ParqueComponente;
import com.example.ndiaz.parquesbsas.ui.adapters.ParqueComponentesAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DescGeneralParque extends AppCompatActivity {

    @BindView(R.id.rvParqueComponentes)
    RecyclerView rvParqueComponentes;

    private ParqueComponentesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_desc_general_parque);
        ButterKnife.bind(this);

        setupToolbar();
        cargarAdapter();
    }

    private void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_parque_detalles);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Parque Roca");
    }

    private void cargarAdapter() {
        List<ParqueComponente> parqueComponentes = fillList();

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);

        rvParqueComponentes.setLayoutManager(mLayoutManager);
        adapter = new ParqueComponentesAdapter(parqueComponentes);
        rvParqueComponentes.setAdapter(adapter);
        rvParqueComponentes.addOnItemTouchListener(new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //ParqueComponente parqueComponente = adapter.getitem(position);
                //parqueComponente.navigateToActivity(ParqueActivity.this);
            }
        }));
        adapter.notifyDataSetChanged();
    }

    private List<ParqueComponente> fillList() {
        List<ParqueComponente> parqueComponentes = new ArrayList<>();
        ParqueComponente parqueComponente = new ParqueComponente("Descripcion general") {
            @Override
            public void navigateToActivity(Context context) {
            }
        };
        ParqueComponente parqueComponente1 = new ParqueComponente("Ferias") {
            @Override
            public void navigateToActivity(Context context) {
            }
        };
        ParqueComponente parqueComponente2 = new ParqueComponente("Feria itinerantes") {
            @Override
            public void navigateToActivity(Context context) {
            }
        };
        ParqueComponente parqueComponente3 = new ParqueComponente("Actividades") {
            @Override
            public void navigateToActivity(Context context) {
            }
        };
        ParqueComponente parqueComponente4 = new ParqueComponente("Reclamos") {
            @Override
            public void navigateToActivity(Context context) {
            }
        };
        ParqueComponente parqueComponente5 = new ParqueComponente("Encuestas") {
            @Override
            public void navigateToActivity(Context context) {
            }
        };

        parqueComponentes.add(parqueComponente);
        parqueComponentes.add(parqueComponente1);
        parqueComponentes.add(parqueComponente2);
        parqueComponentes.add(parqueComponente3);
        parqueComponentes.add(parqueComponente4);
        parqueComponentes.add(parqueComponente5);

        return parqueComponentes;
    }
}
