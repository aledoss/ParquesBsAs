package com.example.ndiaz.parquesbsas.ui.activities.info_parques;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.ndiaz.parquesbsas.ParquesApplication;
import com.example.ndiaz.parquesbsas.R;
import com.example.ndiaz.parquesbsas.contract.ParqueContract;
import com.example.ndiaz.parquesbsas.helpers.recyclerview.RecyclerItemClickListener;
import com.example.ndiaz.parquesbsas.interactor.ParqueInteractor;
import com.example.ndiaz.parquesbsas.model.Parque;
import com.example.ndiaz.parquesbsas.model.ParqueComponente;
import com.example.ndiaz.parquesbsas.presenter.ParquePresenter;
import com.example.ndiaz.parquesbsas.ui.activities.BaseActivity;
import com.example.ndiaz.parquesbsas.ui.adapters.ParqueComponentesAdapter;
import com.squareup.picasso.Picasso;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.util.List;

import butterknife.BindView;

import static com.example.ndiaz.parquesbsas.constants.Constants.IMAGENES_PARQUES_URL;
import static com.example.ndiaz.parquesbsas.constants.Constants.PARQUEDETALLES;

public class ParqueActivity extends BaseActivity<ParqueContract.Presenter> implements ParqueContract.View {

    protected XmlPullParserFactory xmlPullParserFactory;
    protected XmlPullParser parser;
    @BindView(R.id.llContainer)
    CoordinatorLayout llContainer;
    @BindView(R.id.toolbar_parque_detalles)
    Toolbar toolbar;
    @BindView(R.id.img_detalle_parque)
    ImageView imgParque;
    @BindView(R.id.rvParqueComponentes)
    RecyclerView rvParqueComponentes;
    private Parque parque;
    private ParqueComponentesAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parque);
        //setupXMLParser();
        obtenerDatosParque();
        ParquesApplication.getInstance().setParque(parque);
        setupUI();
        presenter.doGetParqueComponents(parque.getIdParque());
    }

    @Override
    protected ParqueContract.Presenter createPresenter() {
        ParqueContract.Interactor parqueInteractor = new ParqueInteractor(getNetworkServiceImp());

        return new ParquePresenter(this, parqueInteractor);
    }

    private void setupXMLParser() {
        try {
            xmlPullParserFactory = XmlPullParserFactory.newInstance();
            xmlPullParserFactory.setNamespaceAware(false);
            parser = xmlPullParserFactory.newPullParser();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
    }

    private void setupUI() {
        setupToolbar();
        cargarImagen();
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(parque.getNombre());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void cargarImagen() {
        try {
            Picasso.with(this)
                    .load(IMAGENES_PARQUES_URL + parque.getImagenAndroid())
                    .error(R.drawable.place_holder_parque)
                    .placeholder(R.drawable.place_holder_parque)
                    .into(imgParque);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void obtenerDatosParque() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            parque = (Parque) bundle.getSerializable(PARQUEDETALLES);
        } else {
            finish();
            Toast.makeText(this, "No se puede mostrar el parque", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void showParqueComponents(List<ParqueComponente> parqueComponentes) {
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);

        rvParqueComponentes.setLayoutManager(mLayoutManager);
        adapter = new ParqueComponentesAdapter(parqueComponentes);
        rvParqueComponentes.setAdapter(adapter);
        rvParqueComponentes.addOnItemTouchListener(new RecyclerItemClickListener(this, (view, position) -> {
            ParqueComponente parqueComponente = adapter.getitem(position);
            parqueComponente.navigateToActivity(ParqueActivity.this);
        }));
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showMessage(String message) {
        showMessage(llContainer, message);
    }

}