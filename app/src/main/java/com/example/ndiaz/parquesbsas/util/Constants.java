package com.example.ndiaz.parquesbsas.util;

/**
 * Created by Lenwe on 13/10/2016.
 */

public interface Constants {
    //Login
    String INICIARSESIONUSUARIO = "IniciarSesionUsuario";
    String EMAILLOGINSAVED = "EmailLoginSaved";
    String PASSWORDLOGINSAVED = "PasswordLoginSaved";
    String LOGINPREFERENCES = "LoginPreferences";

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
    String IMAGENPARQUECOLUMNA = "Imagen";
    String LATITUDPARQUECOLUMNA = "Latitud";
    String LONGITUDPARQUECOLUMNA = "Longitud";
    String ALL_COLUMNS_PARQUES[] = {
            "id", NOMBREPARQUECOLUMNA, DESCRIPCIONCORTAPARQUECOLUMNA, DESCRIPCIONPARQUECOLUMNA, IMAGENPARQUECOLUMNA, LATITUDPARQUECOLUMNA, LONGITUDPARQUECOLUMNA
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
}
