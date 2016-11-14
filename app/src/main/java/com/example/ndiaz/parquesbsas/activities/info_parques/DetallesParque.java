package com.example.ndiaz.parquesbsas.activities.info_parques;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.ndiaz.parquesbsas.R;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static com.example.ndiaz.parquesbsas.util.Constants.xmlURL;

public class DetallesParque extends AppCompatActivity {
    private Button btnEcoBici;
    protected XmlPullParserFactory xmlPullParserFactory;
    protected XmlPullParser parser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles_parque);
        try {
            xmlPullParserFactory = XmlPullParserFactory.newInstance();
            xmlPullParserFactory.setNamespaceAware(false);
            parser = xmlPullParserFactory.newPullParser();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
        btnEcoBici = (Button) findViewById(R.id.btn_eco_bici_parque_detalle);
        btnEcoBici.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BackgroundAsyncTask backgroundAsyncTask = new BackgroundAsyncTask();
                backgroundAsyncTask.execute(xmlURL);
            }
        });
    }

    private class BackgroundAsyncTask extends AsyncTask<String, Void, BackgroundAsyncTask.Entity> {

        @Override
        protected BackgroundAsyncTask.Entity doInBackground(String... params) {
            URL url = null;
            Entity entity = null;
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
        protected void onPostExecute(BackgroundAsyncTask.Entity entity) {
            super.onPostExecute(entity);
            if (entity != null) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(DetallesParque.this);
                alertDialog.setTitle("Estacion " + entity.getEstacionNombre())
                            .setMessage("Bicicletas disponibles: " + entity.getBicicletaDisponibles()
                                + "\nAnclajes disponibles: " + entity.getAnclajesDisponibles()
                                + "\nEstación disponible: " + entity.getEstacionDisponible())
                            .show();
            }
        }

        /*
        * Ejemplo XML
        * <Estacion>
        *    <EstacionId>6</EstacionId>
        *    <EstacionNombre>Parque Lezama</EstacionNombre>
        *    <BicicletaDisponibles>3</BicicletaDisponibles>
        *    <EstacionDisponible>SI</EstacionDisponible>
        *    <Latitud>-34.628233</Latitud>
        *    <Longitud>-58.369606</Longitud>
        *    <Numero>0</Numero>
        *    <Lugar>Martín García e Irala</Lugar>
        *    <Piso/>
        *    <AnclajesTotales>19</AnclajesTotales>
        *    <AnclajesDisponibles>16</AnclajesDisponibles>
        * </Estacion>
        */
        private Entity getLoadedXmlValues(XmlPullParser parser) throws XmlPullParserException, IOException {
            int eventType = parser.getEventType();
            Entity mEntity = new Entity();
            //tendria que filtrar por el parque que eligió, es decir que la "EstacionNombre" tendria que ser tal
            //esto funca: el start tag seria el <id>, el end tag seria el </id>, el text seria lo que viene despues del starTag.
            //los documents son los tags que abren super cosas
            //la idea seria mostrar los datos en un custom dialog de manera linda
            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_TAG) {
                    if (parser.getName().equalsIgnoreCase("EstacionNombre") /*&& parser.nextText().equalsIgnoreCase("Parque Lezama")*/) { //variable con el nombre del parque
                        String estacionNombre = parser.nextText();//hacer el if de si es el parque
                        if (estacionNombre.equalsIgnoreCase("Parque Lezama")) {
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

        public class Entity {
            public String EstacionNombre;
            public String BicicletaDisponibles;
            public String EstacionDisponible;
            public String AnclajesDisponibles;

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