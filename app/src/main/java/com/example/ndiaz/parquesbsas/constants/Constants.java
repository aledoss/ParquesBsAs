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
    String TABLEPARQUES = "Parques";
    String IDPARQUECOLUMNA = "Id_Parque";
    String NOMBREPARQUECOLUMNA = "Nombre";
    String DESCRIPCIONCORTAPARQUECOLUMNA = "Descripcion_Corta";
    String DESCRIPCIONPARQUECOLUMNA = "Descripcion";
    String DIRECCIONPARQUECOLUMNA = "Direccion";
    String IMAGENPARQUECOLUMNA = "Imagen";
    String LATITUDPARQUECOLUMNA = "Latitud";
    String LONGITUDPARQUECOLUMNA = "Longitud";
    String BARRIOPARQUECOLUMNA = "Barrio";
    String COMUNAPARQUECOLUMNA = "Comuna";
    String LIKESPARQUECOLUMNA = "Likes";
    String HATESPARQUECOLUMNA = "Hates";
    String PATIOJUEGOSPARQUECOLUMNA = "Patio_Juegos";
    String ALL_COLUMNS_PARQUES[] = {
            "id", NOMBREPARQUECOLUMNA, DESCRIPCIONCORTAPARQUECOLUMNA, DESCRIPCIONPARQUECOLUMNA, DIRECCIONPARQUECOLUMNA, IMAGENPARQUECOLUMNA,
            LATITUDPARQUECOLUMNA, LONGITUDPARQUECOLUMNA, BARRIOPARQUECOLUMNA, COMUNAPARQUECOLUMNA, LIKESPARQUECOLUMNA ,
            HATESPARQUECOLUMNA, PATIOJUEGOSPARQUECOLUMNA
    };

    ////Reclamos
    String TABLERECLAMOS = "Reclamos";
    String IDRECLAMOCOLUMNA = "Id_Reclamo";
    String NOMBRERECLAMOCOLUMNA = "Nombre";
    String NOMBRERECLAMOPQECOLUMNA = "Nombre_Parque";
    String COMENTARIORECLAMOCOLUMNA = "Comentarios";
    String FECHACREACIONRECLAMOCOLUMNA = "Fecha_Creacion";
    String LATITUDRECLAMOCOLUMNA = "Latitud";
    String LONGITUDRECLAMOCOLUMNA = "Longitud";
    String IMAGENRECLAMOCOLUMNA = "Imagen";

    String IMAGENBYTES = "ImagenBytes";
    String LASTLOCATIONLATITUD = "LastLocationLatitud";
    String LASTLOCATIONLONGITUD = "LastLocationLongitud";
    String RECLAMODETALLES = "ReclamoDetalles";

    //JSONREQUEST
    ////Parques
    String ID_PARQUE = "id_parque";
    String NOMBRE_PARQUE = "nombre";
    String DESC_CORTA_PARQUE = "desc_corta";
    String DESC_LARGA_PARQUE = "desc_larga";
    String DIRECCION_PARQUE = "direccion";
    String IMG_PARQUE = "img";
    String COMUNA_PARQUE = "comuna";
    String BARRIO_PARQUE = "barrio";
    String LATITUD_PARQUE = "latitud";
    String LONGITUD_PARQUE = "longitud";
    String LIKES_PARQUE = "likes";
    String HATES_PARQUE = "hates";
    String PATIO_JUEGOS_PARQUE = "patio_juegos";

    //Settings
    String SETTINGS_CHECBOX_INICIO_SESION_AUTO = "settings_checkbox_inicio_sesion_auto";

    //XML
    String xmlURL = "https://recursos-data.buenosaires.gob.ar/ckan2/ecobici/estado-ecobici.xml";

    //Otros
    String INGRESOPRIMERAVEZ = "IngresoPrimeraVez";

    //FTP
    String FTP_HOST = "185.28.20.89";
    String FTP_USER = "android";
    String FTP_PASS = "Android123";

    //Json Request
    int TIMEOUT_MS = 10000;
    int MAX_RETRIES = 3;

    //REQUEST URLS
    String URL = "http://appweb158.hol.es";
    //String ALL_PARQUES_URL = "http://192.168.0.104/parques/index.php/ws_parques/todos";
    String ALL_PARQUES_URL = "http://appweb158.hol.es/ws_parques/todos";
    //String ALL_PARQUES_URL = "http://webapp321.eshost.com.ar/ws_parques/todos";
    String IMAGENES_PARQUES_URL = "http://appweb158.hol.es/public/img/parques/";

}
