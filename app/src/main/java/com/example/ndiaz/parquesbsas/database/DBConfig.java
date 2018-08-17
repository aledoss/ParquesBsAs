package com.example.ndiaz.parquesbsas.database;

public interface DBConfig {

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
    String LIKES_PARQUE = "Likes";
    String HATES_PARQUE = "Hates";
    String ALL_COLUMNS_PARQUES[] = {
            "id", NOMBRE_PARQUE, DESCRIPCIONCORTAPARQUECOLUMNA, DESCRIPCION_PARQUE, DIRECCION_PARQUE, IMAGEN_PARQUE,
            IMAGEN_ANDROID_PARQUE, LATITUD_PARQUE, LONGITUD_PARQUE, BARRIO_PARQUE, COMUNA_PARQUE, PATIO_JUEGOS_PARQUE,
            WIFI_PARQUE, LIKES_PARQUE, HATES_PARQUE
    };

}
