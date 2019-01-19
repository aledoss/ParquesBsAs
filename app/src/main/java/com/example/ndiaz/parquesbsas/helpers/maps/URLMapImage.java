package com.example.ndiaz.parquesbsas.helpers.maps;

import com.example.ndiaz.parquesbsas.ParquesApplication;
import com.example.ndiaz.parquesbsas.R;

public class URLMapImage {

    private static final String BEGINING_URL = "https://maps.googleapis.com/maps/api/staticmap?center=";
    private String latitudCenter;
    private String longitudCenter;
    private String latitudMarker;
    private String longitudMarker;
    private int zoom;
    private int height;
    private int width;

    public URLMapImage(Builder builder) {
        this.latitudCenter = builder.latitudCenter;
        this.longitudCenter = builder.longitudCenter;
        this.latitudMarker = builder.latitudMarker;
        this.longitudMarker = builder.longitudMarker;
        this.zoom = 18;
        this.width = 640;   //default value for free
        this.height = 640;
    }

    public String getUrl() {
        return BEGINING_URL + latitudMarker + "," + longitudMarker + "&zoom=" + zoom + "&size=" +
                width + "x" + height + "&markers=color:red%7Clabel:A%7C" + latitudMarker + "," +
                longitudMarker + "&key=" + ParquesApplication.getInstance().getApplicationContext().getString(R.string.google_maps_key);
    }

    public static String getBeginingUrl() {
        return BEGINING_URL;
    }

    public String getLatitudCenter() {
        return latitudCenter;
    }

    public String getLongitudCenter() {
        return longitudCenter;
    }

    public String getLatitudMarker() {
        return latitudMarker;
    }

    public String getLongitudMarker() {
        return longitudMarker;
    }

    public int getZoom() {
        return zoom;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public static class Builder {

        private String latitudCenter;
        private String longitudCenter;
        private String latitudMarker;
        private String longitudMarker;

        public Builder setLatitudCenter(String latitudCenter) {
            this.latitudCenter = latitudCenter;
            return this;
        }

        public Builder setLongitudCenter(String longitudCenter) {
            this.longitudCenter = longitudCenter;
            return this;
        }

        public Builder setLatitudMarker(String latitudMarker) {
            this.latitudMarker = latitudMarker;
            return this;
        }

        public Builder setLongitudMarker(String longitudMarker) {
            this.longitudMarker = longitudMarker;
            return this;
        }

        public URLMapImage build() {
            return new URLMapImage(this);
        }
    }
}
