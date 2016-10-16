package com.example.ndiaz.parquesbsas.util;

/**
 * Created by Lenwe on 13/10/2016.
 */

public interface Constants {
    //Login
    String INICIARSESIONUSUARIO = "IniciarSesionUsuario";

    //Crear cuenta
    String CREARCUENTAUSUARIO = "CrearCuentaUsuario";

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
    String NOMBREPARQUECOLUMNA = "Nombre";
    String DESCRIPCIONPARQUECOLUMNA = "Descripcion";
    String IMAGENPARQUECOLUMNA = "Imagen";
    String LATITUDPARQUECOLUMNA = "Latitud";
    String LONGITUDPARQUECOLUMNA = "Longitud";
    String ALL_COLUMNS_PARQUES[] = {
            "id", NOMBREPARQUECOLUMNA, DESCRIPCIONPARQUECOLUMNA, IMAGENPARQUECOLUMNA, LATITUDPARQUECOLUMNA, LONGITUDPARQUECOLUMNA
    };

}
