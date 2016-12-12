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
                DESCRIPCIONCORTAPARQUECOLUMNA + " text, " + DESCRIPCIONPARQUECOLUMNA + " text, " + DIRECCIONPARQUECOLUMNA +  " text, " +
                IMAGENPARQUECOLUMNA + " text, " + LATITUDPARQUECOLUMNA + " text, " + LONGITUDPARQUECOLUMNA + " text, " +
                BARRIOPARQUECOLUMNA + " text, " + COMUNAPARQUECOLUMNA + " text, " + LIKESPARQUECOLUMNA + " integer, " + HATESPARQUECOLUMNA +
                " integer, " + PATIOJUEGOSPARQUECOLUMNA + " text )");

        db.execSQL("create table if not exists " + TABLERECLAMOS + " (id integer primary key, " + NOMBRERECLAMOCOLUMNA + " text, " +
                NOMBRERECLAMOPQECOLUMNA + " text, " + COMENTARIORECLAMOCOLUMNA + " text, " + FECHACREACIONRECLAMOCOLUMNA + " text, " +
                LATITUDRECLAMOCOLUMNA + " text, " + LONGITUDRECLAMOCOLUMNA + " text, " + IMAGENRECLAMOCOLUMNA + " text )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean insertarReclamo(Reclamo reclamo) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        try {
            contentValues.put(NOMBRERECLAMOCOLUMNA, reclamo.getNombre());
            contentValues.put(NOMBRERECLAMOPQECOLUMNA, reclamo.getParque());
            contentValues.put(COMENTARIORECLAMOCOLUMNA, reclamo.getComentarios());
            contentValues.put(FECHACREACIONRECLAMOCOLUMNA, reclamo.getFechaCreacion());
            contentValues.put(LATITUDRECLAMOCOLUMNA, reclamo.getLatitud());
            contentValues.put(LONGITUDRECLAMOCOLUMNA, reclamo.getLongitud());
            contentValues.put(IMAGENRECLAMOCOLUMNA, reclamo.getImagen());
            db.insert(TABLERECLAMOS, null, contentValues);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public ArrayList<Reclamo> getAllReclamos() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Reclamo> listaReclamos = new ArrayList<>();
        Cursor cur = db.rawQuery("select * from " + TABLERECLAMOS, null);
        cur.moveToFirst();
        try {
            while (!cur.isAfterLast()) {
                Reclamo reclamo = new Reclamo();
                reclamo.setId(cur.getInt(cur.getColumnIndex("id")));
                reclamo.setNombre(cur.getString(cur.getColumnIndex(NOMBRERECLAMOCOLUMNA)));
                reclamo.setParque(cur.getString(cur.getColumnIndex(NOMBRERECLAMOPQECOLUMNA)));
                reclamo.setComentarios(cur.getString(cur.getColumnIndex(COMENTARIORECLAMOCOLUMNA)));
                reclamo.setFechaCreacion(cur.getString(cur.getColumnIndex(FECHACREACIONRECLAMOCOLUMNA)));
                reclamo.setLatitud(cur.getString(cur.getColumnIndex(LATITUDRECLAMOCOLUMNA)));
                reclamo.setLongitud(cur.getString(cur.getColumnIndex(LONGITUDRECLAMOCOLUMNA)));
                reclamo.setImagen(cur.getString(cur.getColumnIndex(IMAGENRECLAMOCOLUMNA)));
                listaReclamos.add(reclamo);
                cur.moveToNext();
            }
            return listaReclamos;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
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

    public Usuario getUsuario(String email, String password) {
        String whereClause = EMAILUSUARIOCOLUMNA + "= ? AND " + PASSWORDUSUARIOCOLUMNA + " = ?";
        String[] whereArgs = {email, password};
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cur = db.query(TABLEUSUARIOS, ALL_COLUMNS_USUARIOS, whereClause, whereArgs, null, null, null);
        try {
            cur.moveToFirst();
            Usuario usuario = new Usuario();
            usuario.setId(cur.getInt(cur.getColumnIndex("id")));
            usuario.setNombre(cur.getString(cur.getColumnIndex(NOMBREUSUARIOCOLUMNA)));
            usuario.setApellido(cur.getString(cur.getColumnIndex(APELLIDOUSUARIOCOLUMNA)));
            usuario.setDni(cur.getString(cur.getColumnIndex(DNIUSUARIOCOLUMNA)));
            usuario.setEmail(cur.getString(cur.getColumnIndex(EMAILUSUARIOCOLUMNA)));
            usuario.setPassword(cur.getString(cur.getColumnIndex(PASSWORDUSUARIOCOLUMNA)));
            return usuario;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean insertarParque(Parque parque) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        try {
            contentValues.put(NOMBREPARQUECOLUMNA, parque.getNombre());
            contentValues.put(DESCRIPCIONCORTAPARQUECOLUMNA, parque.getDescripcionCorta());
            contentValues.put(DESCRIPCIONPARQUECOLUMNA, parque.getDescripcion());
            contentValues.put(DIRECCIONPARQUECOLUMNA, parque.getDireccion());
            contentValues.put(IMAGENPARQUECOLUMNA, parque.getImagen());
            contentValues.put(LATITUDPARQUECOLUMNA, parque.getLatitud());
            contentValues.put(LONGITUDPARQUECOLUMNA, parque.getLongitud());
            contentValues.put(BARRIOPARQUECOLUMNA, parque.getBarrio());
            contentValues.put(COMUNAPARQUECOLUMNA, parque.getComuna());
            contentValues.put(LIKESPARQUECOLUMNA, parque.getLikes());
            contentValues.put(HATESPARQUECOLUMNA, parque.getHates());
            contentValues.put(PATIOJUEGOSPARQUECOLUMNA, parque.getPatioJuegos());
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
        try {
            while (!cur.isAfterLast()) {
                Parque parque = new Parque();
                parque.setId(cur.getInt(cur.getColumnIndex("id")));
                parque.setNombre(cur.getString(cur.getColumnIndex(NOMBREPARQUECOLUMNA)));
                parque.setDescripcionCorta(cur.getString(cur.getColumnIndex(DESCRIPCIONCORTAPARQUECOLUMNA)));
                parque.setDescripcion(cur.getString(cur.getColumnIndex(DESCRIPCIONPARQUECOLUMNA)));
                parque.setDireccion(cur.getString(cur.getColumnIndex(DIRECCIONPARQUECOLUMNA)));
                parque.setImagen(cur.getString(cur.getColumnIndex(IMAGENPARQUECOLUMNA)));
                parque.setLatitud(cur.getString(cur.getColumnIndex(LATITUDPARQUECOLUMNA)));
                parque.setLongitud(cur.getString(cur.getColumnIndex(LONGITUDPARQUECOLUMNA)));
                parque.setBarrio(cur.getString(cur.getColumnIndex(BARRIOPARQUECOLUMNA)));
                parque.setComuna(cur.getString(cur.getColumnIndex(COMUNAPARQUECOLUMNA)));
                parque.setLikes(cur.getInt(cur.getColumnIndex(LIKESPARQUECOLUMNA)));
                parque.setHates(cur.getInt(cur.getColumnIndex(HATESPARQUECOLUMNA)));
                parque.setPatioJuegos(cur.getString(cur.getColumnIndex(PATIOJUEGOSPARQUECOLUMNA)));
                listaParques.add(parque);
                cur.moveToNext();
            }
            return listaParques;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean updateParque(Parque parque) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        try {
            contentValues.put(DESCRIPCIONPARQUECOLUMNA, parque.getDescripcion());
            db.update(TABLEPARQUES, contentValues, "id = ?", new String[]{String.valueOf(parque.getId())});
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteParque(Parque parque) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            db.delete(TABLEPARQUES, "id = ?", new String[]{String.valueOf(parque.getId())});
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Parque getParque(int id){
        String whereClause = "id = ?";
        String[] whereArgs = {String.valueOf(id)};
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cur = db.query(TABLEPARQUES, ALL_COLUMNS_PARQUES, whereClause, whereArgs, null, null, null);
        try {
            cur.moveToFirst();
            Parque parque = new Parque();
            parque.setId(cur.getInt(cur.getColumnIndex("id")));
            parque.setNombre(cur.getString(cur.getColumnIndex(NOMBREPARQUECOLUMNA)));
            parque.setDescripcionCorta(cur.getString(cur.getColumnIndex(DESCRIPCIONCORTAPARQUECOLUMNA)));
            parque.setDescripcion(cur.getString(cur.getColumnIndex(DESCRIPCIONPARQUECOLUMNA)));
            parque.setDireccion(cur.getString(cur.getColumnIndex(DIRECCIONPARQUECOLUMNA)));
            parque.setImagen(cur.getString(cur.getColumnIndex(IMAGENPARQUECOLUMNA)));
            parque.setLatitud(cur.getString(cur.getColumnIndex(LATITUDPARQUECOLUMNA)));
            parque.setLongitud(cur.getString(cur.getColumnIndex(LONGITUDPARQUECOLUMNA)));
            parque.setBarrio(cur.getString(cur.getColumnIndex(BARRIOPARQUECOLUMNA)));
            parque.setComuna(cur.getString(cur.getColumnIndex(COMUNAPARQUECOLUMNA)));
            parque.setLikes(cur.getInt(cur.getColumnIndex(LIKESPARQUECOLUMNA)));
            parque.setHates(cur.getInt(cur.getColumnIndex(HATESPARQUECOLUMNA)));
            parque.setPatioJuegos(cur.getString(cur.getColumnIndex(PATIOJUEGOSPARQUECOLUMNA)));
            return parque;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


}
