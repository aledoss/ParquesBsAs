package com.example.ndiaz.parquesbsas.ui.activities.info_parques;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.ndiaz.parquesbsas.ParquesApplication;
import com.example.ndiaz.parquesbsas.R;
import com.example.ndiaz.parquesbsas.contract.DescGralContract;
import com.example.ndiaz.parquesbsas.helpers.AlertDialogBuilder;
import com.example.ndiaz.parquesbsas.helpers.ViewHelper;
import com.example.ndiaz.parquesbsas.helpers.maps.IntentMap;
import com.example.ndiaz.parquesbsas.helpers.maps.URLMapImage;
import com.example.ndiaz.parquesbsas.interactor.DescGralInteractor;
import com.example.ndiaz.parquesbsas.model.Parque;
import com.example.ndiaz.parquesbsas.model.parquelike.ParqueLike;
import com.example.ndiaz.parquesbsas.model.parquelike.ParqueLikeDecrease;
import com.example.ndiaz.parquesbsas.model.parquelike.ParqueLikeDefault;
import com.example.ndiaz.parquesbsas.model.parquelike.ParqueLikeIncrease;
import com.example.ndiaz.parquesbsas.presenter.DescGralPresenter;
import com.example.ndiaz.parquesbsas.ui.activities.BaseActivity;

import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.OnClick;

import static com.example.ndiaz.parquesbsas.constants.SharedPrefConstants.PARQUE_USER_LIKE;

public class DescGeneralParqueActivity extends BaseActivity<DescGralContract.Presenter>
        implements DescGralContract.View {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.clContainer)
    ConstraintLayout clContainer;
    @BindView(R.id.imgMap)
    ImageView imgMap;
    @BindView(R.id.imgBtnThumbsDown)
    ImageButton imgBtnThumbsDown;
    @BindView(R.id.imgBtnThumbsUp)
    ImageButton imgBtnThumbsUp;
    @BindView(R.id.txtThumbsUp)
    TextView txtThumbsUp;
    @BindView(R.id.txtThumbsDown)
    TextView txtThumbsDown;
    @BindView(R.id.txtDesc)
    TextView txtDesc;
    @BindView(R.id.txtComoLlego)
    TextView txtComoLlego;
    @BindView(R.id.imgStateWiFi)
    ImageView imgStateWiFi;
    @BindView(R.id.imgPatioJuegosState)
    ImageView imgStatePatioJuegos;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindDrawable(R.drawable.ic_cancel_red)
    Drawable icCancelRed;
    @BindDrawable(R.drawable.ic_check_green)
    Drawable icCheckGreen;

    private Parque parque;
    private ViewHelper viewHelper;
    private ParqueLike parqueLike;
    private IntentMap intentMap;
    private AlertDialogBuilder dialogBuilder;

    @OnClick(R.id.imgBtnThumbsUp)
    public void onThumbsUpClick() {
        disableLikeButtons();
        parqueLike.onClickImgBtnThumbsUp();
        presenter.doUpdateParqueLike(parque.getIdParque(), getUsuario().getId(), true, parqueLike);
    }

    @OnClick(R.id.imgBtnThumbsDown)
    public void onThumbsDownClick() {
        disableLikeButtons();
        parqueLike.onClickimgBtnThumbsDown();
        presenter.doUpdateParqueLike(parque.getIdParque(), getUsuario().getId(), false, parqueLike);
    }

    @OnClick(R.id.txtComoLlego)
    public void onTxtComoLlegoClick() {
        dialogBuilder.buildConfirmationDialogToNavigateMapsWI(this, intentMap, parque.getLatitud(),
                parque.getLongitud())
                .show();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_desc_general_parque);
        initializeVariables();
        setupUi();
        if (getUsuario() != null){
            presenter.doGetParqueLikeStatus(parque.getIdParque(), getUsuario().getId());
        }
    }

    @Override
    protected DescGralContract.Presenter createPresenter() {
        DescGralInteractor interactor = new DescGralInteractor(getNetworkServiceImp(),
                getSharedPreferences(PARQUE_USER_LIKE, MODE_PRIVATE));

        return new DescGralPresenter(this, interactor);
    }

    private void initializeVariables() {
        this.parque = ParquesApplication.getInstance().getParque();
        this.viewHelper = new ViewHelper();
        this.intentMap = new IntentMap(this);
        this.dialogBuilder = new AlertDialogBuilder();
        createDefaultParqueLike();
        handleUnloggedUser();
    }

    private void handleUnloggedUser() {
        if (getUsuario() == null) {
            imgBtnThumbsUp.setEnabled(false);
            imgBtnThumbsDown.setEnabled(false);
        }
    }

    private void setupUi() {
        initializeTxts();
        initializeImages();
        initializeToolbar();
    }

    private void initializeToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(parque.getNombre());
    }

    private void initializeTxts() {
        txtThumbsUp.setText(String.valueOf(parque.getLikes()));
        txtThumbsDown.setText(String.valueOf(parque.getHates()));
        txtDesc.setText(parque.getDescripcion());
    }

    private void initializeImages() {
        initializeMap();
        initializeStateWiFi();
        initializeStatePatioJuegos();
    }

    private void initializeMap() {
        viewHelper.loadMapImage(this, imgMap, progressBar, getMapUrl(), DescGeneralParqueActivity.class.getSimpleName());
    }

    private void initializeStateWiFi() {
        if (parque.getHasWifi()) {
            setCheckGreenImg(imgStateWiFi);
        } else {
            setCheckCancelImg(imgStateWiFi);
        }
    }

    private void initializeStatePatioJuegos() {
        if (parque.getHasPatioJuegos()) {
            setCheckGreenImg(imgStatePatioJuegos);
        } else {
            setCheckCancelImg(imgStatePatioJuegos);
        }
    }

    private void setCheckCancelImg(ImageView imgView) {
        imgView.setImageDrawable(icCancelRed);
    }

    private void setCheckGreenImg(ImageView imgView) {
        imgView.setImageDrawable(icCheckGreen);
    }

    private URLMapImage getMapUrl() {
        URLMapImage.Builder builder = new URLMapImage.Builder();
        return builder
                .setLatitudCenter(parque.getLatitud())
                .setLongitudCenter(parque.getLongitud())
                .setLatitudMarker(parque.getLatitud())
                .setLongitudMarker(parque.getLongitud())
                .build();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showMessage(String message) {
        showMessage(clContainer, message);
    }

    @Override
    public void refreshLikeViews() {
        this.parqueLike.refreshViews();
    }

    @Override
    public void createDefaultParqueLike() {
        this.parqueLike = new ParqueLikeDefault(this, imgBtnThumbsUp, imgBtnThumbsDown,
                txtThumbsUp, txtThumbsDown);
    }

    @Override
    public void createIncreaseParqueLike() {
        this.parqueLike = new ParqueLikeIncrease(this, imgBtnThumbsUp, imgBtnThumbsDown,
                txtThumbsUp, txtThumbsDown);
    }

    @Override
    public void createDecreaseParqueLike() {
        this.parqueLike = new ParqueLikeDecrease(this, imgBtnThumbsUp, imgBtnThumbsDown,
                txtThumbsUp, txtThumbsDown);
    }

    @Override
    public void enableLikeButtons() {
        imgBtnThumbsUp.setEnabled(true);
        imgBtnThumbsDown.setEnabled(true);
    }

    private void disableLikeButtons() {
        imgBtnThumbsUp.setEnabled(false);
        imgBtnThumbsDown.setEnabled(false);
    }

    @Override
    protected void onStop() {
        updateParqueInstanceLikes();
        super.onStop();
    }

    private void updateParqueInstanceLikes() {
        parque.setLikes(Integer.valueOf(txtThumbsUp.getText().toString()));
        parque.setHates(Integer.valueOf(txtThumbsDown.getText().toString()));

        ParquesApplication.getInstance().setParque(parque);
    }

}
