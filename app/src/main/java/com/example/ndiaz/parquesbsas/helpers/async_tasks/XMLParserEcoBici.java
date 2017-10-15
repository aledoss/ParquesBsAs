package com.example.ndiaz.parquesbsas.helpers.async_tasks;

import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static com.example.ndiaz.parquesbsas.constants.Constants.xmlURL;

/**
 * Created by Lenwe on 28/11/2016.
 */

public class XMLParserEcoBici {

    private XmlPullParser parser;
    private AppCompatActivity activity;
    private String nombreParque;

    public XMLParserEcoBici(XmlPullParser parser, AppCompatActivity activity, String nombreParque) {
        this.parser = parser;
        this.activity = activity;
        this.nombreParque = nombreParque;
        XMLParserBackground backgroundAsyncTask = new XMLParserBackground();
        try {
            backgroundAsyncTask.execute(xmlURL);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private class XMLParserBackground extends AsyncTask<String, Void, XMLParserEcoBici.XMLParserBackground.EcoBiciEntity> {

        @Override
        protected XMLParserEcoBici.XMLParserBackground.EcoBiciEntity doInBackground(String... params) {
            URL url = null;
            XMLParserEcoBici.XMLParserBackground.EcoBiciEntity entity = null;
            try {
                url = new URL(params[0]);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            HttpURLConnection conn = null;
            try {
                conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000);
                conn.setConnectTimeout(20000);
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                conn.connect();
                InputStream is = conn.getInputStream();
                parser.setInput(is, null);
                entity = getLoadedXmlValues(parser);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            }
            return entity;
        }

        @Override
        protected void onPostExecute(EcoBiciEntity ecoBiciEntity) {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(activity);
            alertDialog.setTitle("Estacion " + nombreParque);
            if (ecoBiciEntity.getEstacionNombre() != null) {
                alertDialog.setMessage("Bicicletas disponibles: " + ecoBiciEntity.getBicicletaDisponibles()
                        + "\nAnclajes disponibles: " + ecoBiciEntity.getAnclajesDisponibles()
                        + "\nEstación disponible: " + ecoBiciEntity.getEstacionDisponible());
            } else {
                alertDialog.setMessage("No posee estación de ecobici.");
            }
            alertDialog.show();
            super.onPostExecute(ecoBiciEntity);
        }

        private EcoBiciEntity getLoadedXmlValues(XmlPullParser parser) throws XmlPullParserException, IOException {
            int eventType = parser.getEventType();
            EcoBiciEntity mEntity = new EcoBiciEntity();
            //tendria que filtrar por el parque que eligió, es decir que la "EstacionNombre" tendria que ser tal
            //esto funca: el start tag seria el <id>, el end tag seria el </id>, el text seria lo que viene despues del starTag.
            //los documents son los tags que abren super cosas
            //la idea seria mostrar los datos en un custom dialog de manera linda
            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_TAG) {
                    if (parser.getName().equalsIgnoreCase("EstacionNombre") /*&& parser.nextText().equalsIgnoreCase("Parque Lezama")*/) { //variable con el nombre del parque
                        String estacionNombre = parser.nextText();//hacer el if de si es el parque
                        if (estacionNombre.equalsIgnoreCase(nombreParque)) {
                            mEntity.setEstacionNombre(estacionNombre);
                            Log.d("NICOTEST", "Estacion nombre: " + estacionNombre);
                            parser.nextTag();
                            if (parser.getName().equalsIgnoreCase("BicicletaDisponibles")) {
                                String bicisDisp = parser.nextText();
                                mEntity.setBicicletaDisponibles(bicisDisp);
                                Log.d("NICOTEST", "Bicis disp: " + bicisDisp);
                                parser.nextTag();
                                if (parser.getName().equalsIgnoreCase("EstacionDisponible")) {
                                    String estacionDisp = parser.nextText();
                                    mEntity.setEstacionDisponible(estacionDisp);
                                    Log.d("NICOTEST", "Estacion disp: " + estacionDisp);
                                    parser.nextTag();
                                    String tagActual = parser.getName();
                                    while (!tagActual.equalsIgnoreCase("AnclajesDisponibles")) {  //me muevo por los tags/text hasta anclajesDisponibles
                                        Log.d("NICOTEST", "tag " + tagActual);
                                        parser.nextText();
                                        parser.nextTag();
                                        tagActual = parser.getName();
                                    }
                                    String anclajesDisponibles = parser.nextText();
                                    mEntity.setAnclajesDisponibles(anclajesDisponibles);
                                    Log.d("NICOTEST", "Anclajes disp: " + anclajesDisponibles);
                                }
                            }
                        }
                    }
                }
                eventType = parser.next();
            }
            return mEntity;
        }

        class EcoBiciEntity {
            String EstacionNombre;
            String BicicletaDisponibles;
            String EstacionDisponible;
            String AnclajesDisponibles;

            public String getEstacionNombre() {
                return EstacionNombre;
            }

            public void setEstacionNombre(String estacionNombre) {
                EstacionNombre = estacionNombre;
            }

            public String getBicicletaDisponibles() {
                return BicicletaDisponibles;
            }

            public void setBicicletaDisponibles(String bicicletaDisponibles) {
                BicicletaDisponibles = bicicletaDisponibles;
            }

            public String getEstacionDisponible() {
                return EstacionDisponible;
            }

            public void setEstacionDisponible(String estacionDisponible) {
                EstacionDisponible = estacionDisponible;
            }

            public String getAnclajesDisponibles() {
                return AnclajesDisponibles;
            }

            public void setAnclajesDisponibles(String anclajesDisponibles) {
                AnclajesDisponibles = anclajesDisponibles;
            }
        }
    }
}

