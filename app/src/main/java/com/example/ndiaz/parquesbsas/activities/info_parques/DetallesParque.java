package com.example.ndiaz.parquesbsas.activities.info_parques;

import android.os.AsyncTask;
import android.os.Bundle;
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

public class DetallesParque extends AppCompatActivity {
    private Button btnEcoBici;
    protected XmlPullParserFactory xmlPullParserFactory;
    protected XmlPullParser parser;
    //private final String xmlPath = "http://www.w3schools.com/xml/cd_catalog.xml";
    private final String xmlPath = "https://recursos-data.buenosaires.gob.ar/ckan2/ecobici/estado-ecobici.xml";

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
                backgroundAsyncTask.execute(xmlPath);
            }
        });
    }

    private class BackgroundAsyncTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            URL url = null;
            String returnedResult = "";
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
                returnedResult = getLoadedXmlValues(parser);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            }
            return returnedResult;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (!s.equals("")) {
                Log.d("NICOTEST", s.toString());
            }
        }

        private String getLoadedXmlValues(XmlPullParser parser) throws XmlPullParserException, IOException {
            int eventType = parser.getEventType();
            String name = null;
            Entity mEntity = new Entity();
            /*while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_TAG) {
                    name = parser.getName();
                    if (name.equals("EstacionNombre")) {
                        mEntity.EstacionNombre = parser.nextText();


                    }/* else if (name.equals("ARTIST")) {
                        mEntity.artist = parser.nextText();
                    } else if (name.equals("COUNTRY")) {
                        mEntity.country = parser.nextText();
                    } else if (name.equals("COMPANY")) {
                        mEntity.company = parser.nextText();
                    } else if (name.equals("PRICE")) {
                        mEntity.price = parser.nextText();
                    }/* else if (name.equals("YEAR")) {
                        mEntity.year = parser.nextText();
                    }
                }
                eventType = parser.next();
            }*/

            //tendria que filtrar por el parque que eligió, es decir que la "EstacionNombre" tendria que ser tal
            //esto funca: el start tag seria el <id>, el end tag seria el </id>, el text seria lo que viene despues del starTag.
            //los documents son los tags que abren super cosas
            //la idea seria mostrar los datos en un custom dialog de manera linda
            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_DOCUMENT) {
                    Log.d("NICOTEST", "Start document");
                } else if (eventType == XmlPullParser.END_DOCUMENT) {
                    Log.d("NICOTEST", "End document");
                } else if (eventType == XmlPullParser.START_TAG) {
                    Log.d("NICOTEST", "Start tag " + parser.getName());
                } else if (eventType == XmlPullParser.END_TAG) {
                    Log.d("NICOTEST", "End tag " + parser.getName());
                } else if (eventType == XmlPullParser.TEXT) {
                    Log.d("NICOTEST", "Text " + parser.getText());
                }
                eventType = parser.next();
            }

            return mEntity.EstacionNombre/* + ", " + mEntity.artist + ", " + mEntity.country + ", " + mEntity.company + ", " + mEntity.price/* + ", " + mEntity.year*/;
        }

        public class Entity {
            public String EstacionNombre;
            /*public String artist;
            public String country;
            public String company;
            public String price;*/
            //public String year;
        }
    }
}