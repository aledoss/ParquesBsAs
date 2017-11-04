package com.example.ndiaz.parquesbsas.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.ndiaz.parquesbsas.constants.Constants;
import com.example.ndiaz.parquesbsas.model.Parque;
import com.example.ndiaz.parquesbsas.model.Reclamo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lenwe on 13/10/2016.
 */

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

        db.execSQL("create table if not exists " + TABLE_RECLAMOS + " (id integer primary key, " + NOMBRE_RECLAMO + " text, " +
                NOMBRE_RECLAMO_PQE + " text, " + COMENTARIO_RECLAMO + " text, " + FECHA_CREACION_RECLAMO + " text, " +
                LATITUD_RECLAMO + " text, " + LONGITUD_RECLAMO + " text, " + IMAGEN_RECLAMO + " text )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_PARQUES);
        db.execSQL("drop table if exists " + TABLE_RECLAMOS);
        onCreate(db);
    }

    public boolean insertarReclamo(Reclamo reclamo) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        try {
            contentValues.put(NOMBRE_RECLAMO, reclamo.getNombre());
            contentValues.put(NOMBRE_RECLAMO_PQE, reclamo.getParque());
            contentValues.put(COMENTARIO_RECLAMO, reclamo.getComentarios());
            contentValues.put(FECHA_CREACION_RECLAMO, reclamo.getFechaCreacion());
            contentValues.put(LATITUD_RECLAMO, reclamo.getLatitud());
            contentValues.put(LONGITUD_RECLAMO, reclamo.getLongitud());
            contentValues.put(IMAGEN_RECLAMO, reclamo.getImagen());
            db.insert(TABLE_RECLAMOS, null, contentValues);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public ArrayList<Reclamo> getAllReclamos() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Reclamo> listaReclamos = new ArrayList<>();
        Cursor cur = db.rawQuery("select * from " + TABLE_RECLAMOS, null);
        cur.moveToFirst();
        try {
            while (!cur.isAfterLast()) {
                Reclamo reclamo = new Reclamo();
                reclamo.setId(cur.getInt(cur.getColumnIndex("id")));
                reclamo.setNombre(cur.getString(cur.getColumnIndex(NOMBRE_RECLAMO)));
                reclamo.setParque(cur.getString(cur.getColumnIndex(NOMBRE_RECLAMO_PQE)));
                reclamo.setComentarios(cur.getString(cur.getColumnIndex(COMENTARIO_RECLAMO)));
                reclamo.setFechaCreacion(cur.getString(cur.getColumnIndex(FECHA_CREACION_RECLAMO)));
                reclamo.setLatitud(cur.getString(cur.getColumnIndex(LATITUD_RECLAMO)));
                reclamo.setLongitud(cur.getString(cur.getColumnIndex(LONGITUD_RECLAMO)));
                reclamo.setImagen(cur.getString(cur.getColumnIndex(IMAGEN_RECLAMO)));
                listaReclamos.add(reclamo);
                cur.moveToNext();
            }
            return listaReclamos;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            cur.close();
        }
    }

    public ArrayList<Parque> getAllParques() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Parque> listaParques = new ArrayList<>();
        Cursor cur = db.rawQuery("select * from " + TABLE_PARQUES, null);
        cur.moveToFirst();
        try {
            while (!cur.isAfterLast()) {
                Parque parque = new Parque();
                parque.setId_parque(cur.getInt(cur.getColumnIndex("id")));
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
                parque.setId_parque(cur.getInt(cur.getColumnIndex("id")));
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
                contentValues.put("id", parque.getId_parque());
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
