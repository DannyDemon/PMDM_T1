package com.example.api;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class CapituloBaseSQLite extends SQLiteOpenHelper {

    private static final String TABLA_USUARIOS = "usuarios";
    private static final String idUsuario = "id";
    private static final String nombreUsuario = "nombre";
    private static final String contraUsuario = "contra";
    private static final String telefonoUsuario = "num";
    private static final String rolGesUsuario = "ges";
    private static final String rolRootUsuario = "root";
    private static final String pathUsuario = "path";

    private static final String TABLA_MANGA = "mangas";
    private static final String idManga = "id";
    private static final String nombreManga = "nombre";
    private static final String desManga = "descripcion";
    private static final String pathManga = "pathMaga";


    private static final String CREATE_BD = "CREATE TABLE " +
            TABLA_USUARIOS + " (" + idUsuario + " Integer PRIMARY KEY AUTOINCREMENT, " + nombreUsuario + " TEXT NOT NULL, "
            + contraUsuario + " String NOT NULL, " + telefonoUsuario + " int NOT NULL, "
            + rolGesUsuario + " boolean NOT NULL, "
            + rolRootUsuario + " boolean NOT NULL," + pathUsuario + " String);";



    private static final String CREATE_BD2 = "CREATE TABLE " +
            TABLA_MANGA + " (" + idManga + " Integer PRIMARY KEY AUTOINCREMENT, " + nombreManga + " TEXT NOT NULL, "
            + desManga + " String NOT NULL, " + pathManga + " Integer);";


    public CapituloBaseSQLite(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_BD);
        db.execSQL(CREATE_BD2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE if exists " + TABLA_USUARIOS);
        db.execSQL("DROP TABLE if exists " + TABLA_MANGA);

        onCreate(db);
    }
}