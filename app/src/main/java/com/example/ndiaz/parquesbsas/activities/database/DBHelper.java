package com.example.ndiaz.parquesbsas.activities.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.ndiaz.parquesbsas.activities.util.Constants;

import java.util.ArrayList;

/**
 * Created by Lenwe on 13/10/2016.
 */

public class DBHelper extends SQLiteOpenHelper implements Constants {


    public DBHelper(Context context) {
        super(context, DATABASE, null, VERSIONDB);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table if not exists " + TABLEUSUARIOS + " (id integer primary key, " + NOMBREUSUARIOCOLUMNA + " text, " +
                APELLIDOUSUARIOCOLUMNA + " text, " + DNIUSUARIOCOLUMNA + " text, " + EMAILUSUARIOCOLUMNA + " text, " +
                PASSWORDUSUARIOCOLUMNA + " text )");

        db.execSQL("create table if not exists " + TABLEPARQUES + " (id integer primary key, " + NOMBREPARQUECOLUMNA + " text, " +
                DESCRIPCIONPARQUECOLUMNA + " text, " + IMAGENPARQUECOLUMNA + " text, " + LATITUDPARQUECOLUMNA + " text, " +
                LONGITUDPARQUECOLUMNA + " text )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean insertarUsuario(Usuario usuario) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        try {
            contentValues.put(NOMBREUSUARIOCOLUMNA, usuario.getNombre());
            contentValues.put(APELLIDOUSUARIOCOLUMNA, usuario.getApellido());
            contentValues.put(DNIUSUARIOCOLUMNA, usuario.getDni());
            contentValues.put(EMAILUSUARIOCOLUMNA, usuario.getEmail());
            contentValues.put(PASSWORDUSUARIOCOLUMNA, usuario.getPassword());
            db.insert(TABLEUSUARIOS, null, contentValues);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public ArrayList<Usuario> getAllUsers(){

        return null;
    }


}
