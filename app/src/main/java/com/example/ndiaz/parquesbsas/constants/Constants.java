package com.example.ndiaz.parquesbsas.constants;

/**
 * Created by Lenwe on 13/10/2016.
 */

public interface Constants {
    //Detalles Parque
    String PARQUEDETALLES = "ParqueDetalles";
    String ID_PARQUE = "Id_Parque";

    String RECLAMODETALLES = "ReclamoDetalles";

    //XML
    String xmlURL = "https://recursos-data.buenosaires.gob.ar/ckan2/ecobici/estado-ecobici.xml";

    //Otros
    String INGRESOPRIMERAVEZ = "IngresoPrimeraVez";

    //FTP
    String FTP_HOST = "185.28.20.89";
    String FTP_USER = ""; //configurar
    String FTP_PASS = ""; //configurar
    String FTP_ANDROID_IMAGE_DIRECTORY = "public/img/android/";

    //REQUEST URLS
    String BASE_URL = "http://192.168.0.100/nico/";
    String API_URL = BASE_URL + "index.php/";
    String IMAGENES_PARQUES_URL = BASE_URL + "public/img/parques/";
    //String ALL_PARQUES_URL = "http://192.168.0.104/parques/index.php/ws_parques/todos";
    String ALL_PARQUES_URL = "http://appweb158.hol.es/ws_parques/todos";
    //String ALL_PARQUES_URL = "http://webapp321.eshost.com.ar/ws_parques/todos";

    //Intent, extras
    String MESSAGE = "Message";

}
