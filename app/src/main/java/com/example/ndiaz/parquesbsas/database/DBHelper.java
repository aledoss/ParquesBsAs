package com.example.ndiaz.parquesbsas.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.ndiaz.parquesbsas.constants.Constants;
import com.example.ndiaz.parquesbsas.model.Parque;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper implements Constants {


    public DBHelper(Context context) {
        super(context, DATABASE, null, VERSIONDB);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table if not exists " + TABLE_PARQUES + " (id integer primary key, " + NOMBRE_PARQUE + " text, " +
                DESCRIPCIONCORTAPARQUECOLUMNA + " text, " + DESCRIPCION_PARQUE + " text, " + DIRECCION_PARQUE + " text, " +
                IMAGEN_PARQUE + " text, " + LATITUD_PARQUE + " text, " + LONGITUD_PARQUE + " text, " +
                BARRIO_PARQUE + " text, " + COMUNA_PARQUE + " text, " + PATIO_JUEGOS_PARQUE + " integer, " +
                WIFI_PARQUE + " integer )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_PARQUES);
        onCreate(db);
    }

    public ArrayList<Parque> getAllParques() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Parque> listaParques = new ArrayList<>();
        Cursor cur = db.rawQuery("select * from " + TABLE_PARQUES, null);
        cur.moveToFirst();
        try {
            while (!cur.isAfterLast()) {
                Parque parque = new Parque();
                parque.setIdParque(cur.getInt(cur.getColumnIndex("id")));
                parque.setNombre(cur.getString(cur.getColumnIndex(NOMBRE_PARQUE)));
                parque.setDescripcion(cur.getString(cur.getColumnIndex(DESCRIPCION_PARQUE)));
                parque.setDireccion(cur.getString(cur.getColumnIndex(DIRECCION_PARQUE)));
                parque.setImagen(cur.getString(cur.getColumnIndex(IMAGEN_PARQUE)));
                parque.setLatitud(cur.getString(cur.getColumnIndex(LATITUD_PARQUE)));
                parque.setLongitud(cur.getString(cur.getColumnIndex(LONGITUD_PARQUE)));
                parque.setBarrio(cur.getString(cur.getColumnIndex(BARRIO_PARQUE)));
                parque.setComuna(cur.getString(cur.getColumnIndex(COMUNA_PARQUE)));
                parque.setHasPatioJuegos(cur.getInt(cur.getColumnIndex(PATIO_JUEGOS_PARQUE)) == 1);
                parque.setHasWifi(cur.getInt(cur.getColumnIndex(WIFI_PARQUE)) == 1);

                listaParques.add(parque);
                cur.moveToNext();
            }
            return listaParques;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            cur.close();
        }
    }

    public Parque getParque(int id) {
        String whereClause = "id = ?";
        String[] whereArgs = {String.valueOf(id)};
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cur = db.query(TABLE_PARQUES, ALL_COLUMNS_PARQUES, whereClause, whereArgs, null, null, null);
        try {
            Parque parque = new Parque();
            if (cur.moveToFirst()) {
                parque.setIdParque(cur.getInt(cur.getColumnIndex("id")));
                parque.setNombre(cur.getString(cur.getColumnIndex(NOMBRE_PARQUE)));
                parque.setDescripcion(cur.getString(cur.getColumnIndex(DESCRIPCION_PARQUE)));
                parque.setDireccion(cur.getString(cur.getColumnIndex(DIRECCION_PARQUE)));
                parque.setImagen(cur.getString(cur.getColumnIndex(IMAGEN_PARQUE)));
                parque.setLatitud(cur.getString(cur.getColumnIndex(LATITUD_PARQUE)));
                parque.setLongitud(cur.getString(cur.getColumnIndex(LONGITUD_PARQUE)));
                parque.setBarrio(cur.getString(cur.getColumnIndex(BARRIO_PARQUE)));
                parque.setComuna(cur.getString(cur.getColumnIndex(COMUNA_PARQUE)));
                parque.setHasPatioJuegos(cur.getInt(cur.getColumnIndex(PATIO_JUEGOS_PARQUE)) == 1);
                parque.setHasWifi(cur.getInt(cur.getColumnIndex(WIFI_PARQUE)) == 1);
            }
            return parque;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            cur.close();
        }
    }

    public Boolean insertarParques(List<Parque> parques) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        try {
            for (Parque parque : parques) {
                ContentValues contentValues = new ContentValues();
                contentValues.put("id", parque.getIdParque());
                contentValues.put(NOMBRE_PARQUE, parque.getNombre());
                contentValues.put(DESCRIPCION_PARQUE, parque.getDescripcion());
                contentValues.put(DIRECCION_PARQUE, parque.getDireccion());
                contentValues.put(IMAGEN_PARQUE, parque.getImagen());
                contentValues.put(LATITUD_PARQUE, parque.getLatitud());
                contentValues.put(LONGITUD_PARQUE, parque.getLongitud());
                contentValues.put(BARRIO_PARQUE, parque.getBarrio());
                contentValues.put(COMUNA_PARQUE, parque.getComuna());
                contentValues.put(PATIO_JUEGOS_PARQUE, parque.getHasPatioJuegos() ? 1 : 0);
                contentValues.put(WIFI_PARQUE, parque.getHasWifi() ? 1 : 0);

                db.insert(TABLE_PARQUES, null, contentValues);
            }
            db.setTransactionSuccessful();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            db.endTransaction();
        }
    }
}
