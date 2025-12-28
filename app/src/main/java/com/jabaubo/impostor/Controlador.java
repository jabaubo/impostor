package com.jabaubo.impostor;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Currency;

public class Controlador extends SQLiteOpenHelper {
    private Context context;
    private ArrayList<String> palabras = new ArrayList<>();
    private static final String DATABASE_NAME = "Palabras.db";
    private static final int DATABASE_VERSION=1;

    private static final String TABLE_NAME = "palabras";
    private static final String COLUMN_ID = "id_palabra";
    private static final String COLUMN_WORD = "palabra";

    public Controlador(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
        System.out.println("papafrita");
        palabrasPrueba();

    }

    public boolean databaseCargada(){
        String query = "SELECT * FROM " +TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        if (db!= null){
            Cursor cursor = db.rawQuery(query,null);
            return cursor.getCount() > 0;
        }
        return false;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        System.out.println("Creando DB");
        String query = "CREATE TABLE " + TABLE_NAME +
                " ("+COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"+
                COLUMN_WORD + " TEXT);" ;
        db.execSQL(query);
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_WORD,"patata");
        db.insert(TABLE_NAME,null,cv);
        System.out.println("DB Creada");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
        System.out.println("Kaboom");
    }

    public String cargarPalabras(){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME,null,null,null,null,null,null,null);
        cursor.moveToFirst();
        String palabras="";
        for (int i = 0 ; i < cursor.getCount() ; i++){
            palabras+=cursor.getString(1)+"\n";
            if (cursor.getPosition()<cursor.getCount()-1){
                cursor.moveToNext();
            }
        }
        return palabras;
    }

    public void palabrasPrueba() {
            SQLiteDatabase db = getWritableDatabase();
            db.delete(TABLE_NAME,null,null);
            // Palabras a insertar
            String[] palabras = {"gato", "perro", "casa", "árbol", "sol"};
            for (String palabra : palabras) {
                ContentValues values = new ContentValues();
                values.put(COLUMN_WORD, palabra); // Cambia el nombre de la columna según tu tabla
                db.insert(TABLE_NAME, null, values);
                System.out.println("Insertando patata");
            }
    }

    public String elegirPalabra(){

        SQLiteDatabase db = getReadableDatabase();
        System.out.println("Eligiendo palabra");
        Cursor cursor = db.query(TABLE_NAME,null,null,null,null,null,null,null);
        System.out.println("Kaboom");
        cursor.moveToFirst();
        cursor.moveToPosition((int)(Math.random()*cursor.getCount()));
        String palabra=cursor.getString(1);
        //cursor.close();
        return palabra;
    }

    public int wordCount(){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME,null,null,null,null,null,null,null);
        int wordcount =cursor.getCount();
        //db.close();
        //cursor.close();
        return (wordcount);
    }
    public void insertarPalabras(String[] palabras){
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_NAME,null,null);
        for (int i = 0; i < palabras.length; i++) {
            System.out.println("Insertado la palabra: " + palabras[i]);
            ContentValues cv = new ContentValues();
            cv.put(COLUMN_WORD,palabras[i]);
            db.insert(TABLE_NAME, null, cv);
        }
        //db.close();
    }
}
