package com.example.ndiaz.parquesbsas.constants;

/**
 * Created by Lenwe on 13/10/2016.
 */

public interface Constants {

    //Edit text validator
    String LOGIN_ORIGIN = "Login Origin";
    String CREATE_USER_ORIGIN = "Create User Origin";

    //Crear cuenta
    String CREARCUENTAUSUARIO = "CrearCuentaUsuario";

    //Crear parque
    String CREARPARQUE = "CrearParque";

    //Detalles Parque
    String PARQUEDETALLES = "ParqueDetalles";
    String ACCIONMODIFICARPARQUE = "AccionModificarParque";

    //DB
    String DATABASE = "ParquesBsAs";
    int VERSIONDB = 1;
    ////Usuarios
    String TABLEUSUARIOS = "Usuarios";
    String NOMBREUSUARIOCOLUMNA = "Nombre";
    String APELLIDOUSUARIOCOLUMNA = "Apellido";
    String DNIUSUARIOCOLUMNA = "DNI";
    String EMAILUSUARIOCOLUMNA = "Email";
    String PASSWORDUSUARIOCOLUMNA = "Password";
    String ALL_COLUMNS_USUARIOS[] = {
            "id", NOMBREUSUARIOCOLUMNA, APELLIDOUSUARIOCOLUMNA, DNIUSUARIOCOLUMNA, EMAILUSUARIOCOLUMNA, PASSWORDUSUARIOCOLUMNA
    };

    ////Parques
    String TABLE_PARQUES = "Parques";
    String ID_PARQUE = "Id_Parque";
    String NOMBRE_PARQUE = "Nombre";
    String DESCRIPCIONCORTAPARQUECOLUMNA = "Descripcion_Corta";
    String DESCRIPCION_PARQUE = "Descripcion";
    String DIRECCION_PARQUE = "Direccion";
    String IMAGEN_PARQUE = "Imagen";
    String IMAGEN_ANDROID_PARQUE = "Imagen_Android";
    String LATITUD_PARQUE = "Latitud";
    String LONGITUD_PARQUE = "Longitud";
    String BARRIO_PARQUE = "Barrio";
    String COMUNA_PARQUE = "Comuna";
    String PATIO_JUEGOS_PARQUE = "Patio_Juegos";
    String WIFI_PARQUE = "WiFi";
    String ALL_COLUMNS_PARQUES[] = {
            "id", NOMBRE_PARQUE, DESCRIPCIONCORTAPARQUECOLUMNA, DESCRIPCION_PARQUE, DIRECCION_PARQUE, IMAGEN_PARQUE,
            IMAGEN_ANDROID_PARQUE, LATITUD_PARQUE, LONGITUD_PARQUE, BARRIO_PARQUE, COMUNA_PARQUE, PATIO_JUEGOS_PARQUE
    };

    String IMAGENBYTES = "ImagenBytes";
    String LASTLOCATIONLATITUD = "LastLocationLatitud";
    String LASTLOCATIONLONGITUD = "LastLocationLongitud";
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

    //Json Request
    int TIMEOUT_MS = 10000;
    int MAX_RETRIES = 3;

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
