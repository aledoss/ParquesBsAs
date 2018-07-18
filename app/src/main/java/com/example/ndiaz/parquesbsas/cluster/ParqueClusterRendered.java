package com.example.ndiaz.parquesbsas.cluster;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.content.ContextCompat;

import com.example.ndiaz.parquesbsas.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;

public class ParqueClusterRendered extends DefaultClusterRenderer<ParqueCluster> {

    private Context context;

    public ParqueClusterRendered(Context context, GoogleMap map, ClusterManager<ParqueCluster> clusterManager) {
        super(context, map, clusterManager);
        this.context = context;
    }

    @Override
    protected void onBeforeClusterItemRendered(ParqueCluster item, MarkerOptions markerOptions) {
        BitmapDrawable bitmapdraw = (BitmapDrawable) context.getResources().getDrawable(R.mipmap.ic_launcher);
        Bitmap b = bitmapdraw.getBitmap();
        Bitmap smallMarker = Bitmap.createScaledBitmap(b, 65, 65, false);
        BitmapDescriptor markerDescriptor = BitmapDescriptorFactory.fromBitmap(smallMarker);

        markerOptions.icon(markerDescriptor);
    }

    @Override
    protected int getColor(int clusterSize) {
        return ContextCompat.getColor(context, R.color.colorPrimary);
    }
}
