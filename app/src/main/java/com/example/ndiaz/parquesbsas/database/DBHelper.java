package com.example.ndiaz.parquesbsas.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.ndiaz.parquesbsas.util.Constants;

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

    public ArrayList<Usuario> getAllUsers() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Usuario> listaUsuarios = new ArrayList<>();
        Cursor cur = db.rawQuery("select * from " + TABLEUSUARIOS, null);
        cur.moveToFirst();
        try {
            while (!cur.isAfterLast()) {
                Usuario usuario = new Usuario();
                usuario.setId(cur.getInt(cur.getColumnIndex("id")));
                usuario.setNombre(cur.getString(cur.getColumnIndex(NOMBREUSUARIOCOLUMNA)));
                usuario.setApellido(cur.getString(cur.getColumnIndex(APELLIDOUSUARIOCOLUMNA)));
                usuario.setDni(cur.getString(cur.getColumnIndex(DNIUSUARIOCOLUMNA)));
                usuario.setEmail(cur.getString(cur.getColumnIndex(EMAILUSUARIOCOLUMNA)));
                usuario.setPassword(cur.getString(cur.getColumnIndex(PASSWORDUSUARIOCOLUMNA)));
                listaUsuarios.add(usuario);
                cur.moveToNext();
            }
            return listaUsuarios;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Usuario getUsuario(String email, String password){
        String whereClause = EMAILUSUARIOCOLUMNA + "= ? AND " + PASSWORDUSUARIOCOLUMNA + " = ?";
        String[] whereArgs = {email, password};
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cur = db.query(TABLEUSUARIOS, ALL_COLUMNS_USUARIOS, whereClause, whereArgs, null, null, null);
        try{
            cur.moveToFirst();
            Usuario usuario = new Usuario();
            usuario.setId(cur.getInt(cur.getColumnIndex("id")));
            usuario.setNombre(cur.getString(cur.getColumnIndex(NOMBREUSUARIOCOLUMNA)));
            usuario.setApellido(cur.getString(cur.getColumnIndex(APELLIDOUSUARIOCOLUMNA)));
            usuario.setDni(cur.getString(cur.getColumnIndex(DNIUSUARIOCOLUMNA)));
            usuario.setEmail(cur.getString(cur.getColumnIndex(EMAILUSUARIOCOLUMNA)));
            usuario.setPassword(cur.getString(cur.getColumnIndex(PASSWORDUSUARIOCOLUMNA)));
            return usuario;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public boolean insertarParque(Parque parque) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        try {
            contentValues.put(NOMBREPARQUECOLUMNA, parque.getNombre());
            contentValues.put(DESCRIPCIONPARQUECOLUMNA, parque.getDescripcion());
            contentValues.put(IMAGENPARQUECOLUMNA, parque.getImagen());
            contentValues.put(LATITUDPARQUECOLUMNA, parque.getLatitud());
            contentValues.put(LONGITUDPARQUECOLUMNA, parque.getLongitud());
            db.insert(TABLEPARQUES, null, contentValues);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public ArrayList<Parque> getAllParques() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Parque> listaParques = new ArrayList<>();
        Cursor cur = db.rawQuery("select * from " + TABLEPARQUES, null);
        cur.moveToFirst();
        try{
            while(!cur.isAfterLast()){
                Parque parque = new Parque();
                parque.setId(cur.getInt(cur.getColumnIndex("id")));
                parque.setNombre(cur.getString(cur.getColumnIndex(NOMBREPARQUECOLUMNA)));
                parque.setDescripcion(cur.getString(cur.getColumnIndex(DESCRIPCIONPARQUECOLUMNA)));
                parque.setImagen(cur.getString(cur.getColumnIndex(IMAGENPARQUECOLUMNA)));
                parque.setLatitud(cur.getString(cur.getColumnIndex(LATITUDPARQUECOLUMNA)));
                parque.setLongitud(cur.getString(cur.getColumnIndex(LONGITUDPARQUECOLUMNA)));
                listaParques.add(parque);
                cur.moveToNext();
            }
            return listaParques;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }


}
