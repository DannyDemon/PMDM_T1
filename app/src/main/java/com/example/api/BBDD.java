package com.example.api;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Iterator;

public class BBDD {

    private static final int VERSION = 1;
    private static final String NOMBRE_BBDD = "Manga.db";

    private static final String TABLA_USUARIOS = "usuarios";
    private static final String idUsuario = "id";
    private static final int NUM_COL_ID = 0;
    private static final String nombreUsuario = "nombre";
    private static final int NUM_COL_NOMBRE = 1;
    private static final String contraUsuario = "contra";
    private static final int NUM_COL_CONTRA = 2;
    private static final String telefonoUsuario = "num";
    private static final int NUM_COL_TELEFONO = 3;
    private static final String rolGesUsuario = "ges";
    private static final int NUM_COL_ROLGES = 4;
    private static final String rolRootUsuario = "root";
    private static final int NUM_COL_ROLROOT = 5;
    private static final String pathUsuario = "path";
    private static final int path_Usuario = 6;

    private static final String TABLA_MANGA = "mangas";
    private static final String idManga = "id";
    private static final int NUM_MANGA_ID = 0;
    private static final String nombreManga = "nombre";
    private static final int NUM_MANGA_NOMBRE = 1;
    private static final String desManga = "descripcion";
    private static final int NUM_MANGA_DES = 2;
    private static final String pathManga = "pathMaga";
    private static final int path_MANGA = 3;



    private SQLiteDatabase bbdd;
    private CapituloBaseSQLite Manga;

    public BBDD(Context context) {
        Manga = new CapituloBaseSQLite(context, NOMBRE_BBDD, null, VERSION);
    }

    public void openForWrite() {
        bbdd = Manga.getWritableDatabase();
    }

    public void openForRead() {
        bbdd = Manga.getReadableDatabase();
    }

    public void close() {
        bbdd.close();
    }

    public SQLiteDatabase getBbdd() {
        return bbdd;
    }

    public long insertUsuario(Usuario usuario) {
        ContentValues content = new ContentValues();
        content.put(idUsuario, usuario.getId());
        content.put(nombreUsuario, usuario.getNombre());
        content.put(contraUsuario, usuario.getContra());
        content.put(telefonoUsuario, usuario.getNum());
        content.put(rolGesUsuario, usuario.isGes());
        content.put(rolRootUsuario, usuario.isRoot());
        content.put(pathUsuario, usuario.getPath());

        return bbdd.insert(TABLA_USUARIOS, null, content);
    }

    public int updateUsuario(int id, Usuario usuario) {
        ContentValues content = new ContentValues();
        content.put(nombreUsuario, usuario.getNombre());
        content.put(contraUsuario, usuario.getContra());
        content.put(telefonoUsuario, usuario.getNum());
        content.put(rolGesUsuario, usuario.isGes());
        content.put(rolRootUsuario, usuario.isRoot());
        return bbdd.update(TABLA_USUARIOS, content, idUsuario + " = " + id, null);
    }

    public int removeUsuario(int id) {
        return bbdd.delete(TABLA_USUARIOS, idUsuario + " = " + id, null);
    }

    public ArrayList<Usuario> getUsuarios() {
        ArrayList<Usuario> variable = new ArrayList<>();
        Cursor cursores = bbdd.query(TABLA_USUARIOS, new String[]{idUsuario, nombreUsuario, contraUsuario, telefonoUsuario,
                 rolGesUsuario, rolRootUsuario, pathUsuario}, null, null, null, null, idUsuario);
        if (cursores.getCount() == 0) {
            cursores.close();
            return null;
        }
        while (cursores.moveToNext()) {
            Usuario chapter = new Usuario();
            chapter.setId(cursores.getInt(NUM_COL_ID));
            chapter.setNombre(cursores.getString(NUM_COL_NOMBRE));
            chapter.setContra(cursores.getString(NUM_COL_CONTRA));
            chapter.setNum(cursores.getInt(NUM_COL_TELEFONO));
            chapter.setGes(cursores.getInt(NUM_COL_ROLGES));
            chapter.setRoot(cursores.getInt(NUM_COL_ROLROOT));
            chapter.setPath(cursores.getString(path_Usuario));
            variable.add(chapter);
        }
        cursores.close();
        return variable;
    }


    public void ver() {
        ArrayList<Usuario> variable = new ArrayList<>();
        Cursor cursores = bbdd.query(TABLA_USUARIOS, new String[]{idUsuario, nombreUsuario, contraUsuario, telefonoUsuario,
                 rolGesUsuario, rolRootUsuario}, null, null, null, null, idUsuario);
        if (cursores.getCount() == 0) {
            cursores.close();
        }

        while (cursores.moveToNext()) {
            Usuario chapter = new Usuario();
            chapter.setId(cursores.getInt(NUM_COL_ID));
            chapter.setNombre(cursores.getString(NUM_COL_NOMBRE));
            chapter.setContra(cursores.getString(NUM_COL_CONTRA));
            chapter.setNum(cursores.getInt(NUM_COL_TELEFONO));
            chapter.setGes(cursores.getInt(NUM_COL_ROLGES));
            chapter.setRoot(cursores.getInt(NUM_COL_ROLROOT));
            variable.add(chapter);
        }
        cursores.close();

        Iterator it = variable.iterator();
        while (it.hasNext()) {
            Usuario u = (Usuario) it.next();
            System.out.println(u.toString());

        }
    }

    public long insertManga(Manga manga) {
        ContentValues content = new ContentValues();
        content.put(idManga, manga.getId());
        content.put(nombreManga, manga.getNombre());
        content.put(desManga, manga.getDescripcion());
        content.put(pathManga, manga.getPath());

        return bbdd.insert(TABLA_MANGA, null, content);
    }

    public ArrayList<Manga> getMangas() {
        ArrayList<Manga> variable = new ArrayList<>();
        Cursor cursores = bbdd.query(TABLA_MANGA, new String[]{idManga, nombreManga, desManga, pathManga}, null, null, null, null, idUsuario);
        if (cursores.getCount() == 0) {
            cursores.close();
            return null;
        }

        while (cursores.moveToNext()) {
            Manga chapter = new Manga();
            chapter.setId(cursores.getInt(NUM_MANGA_ID));
            chapter.setNombre(cursores.getString(NUM_MANGA_NOMBRE));
            chapter.setDescripcion(cursores.getString(NUM_MANGA_DES));
            chapter.setPath(cursores.getInt(path_MANGA));
            variable.add(chapter);
        }
        cursores.close();
        return variable;
    }



    public void verManga() {
        ArrayList<Manga> variable = new ArrayList<>();
        Cursor cursores = bbdd.query(TABLA_MANGA, new String[]{idManga, nombreManga, desManga, pathManga}, null, null, null, null, idUsuario);
        if (cursores.getCount() == 0) {
            cursores.close();
        }
        while (cursores.moveToNext()) {
            Manga chapter = new Manga();
            chapter.setId(cursores.getInt(NUM_MANGA_ID));
            chapter.setNombre(cursores.getString(NUM_MANGA_NOMBRE));
            chapter.setDescripcion(cursores.getString(NUM_MANGA_DES));
            chapter.setPath(cursores.getInt(path_MANGA));

            variable.add(chapter);
        }
        cursores.close();

        Iterator it = variable.iterator();
        while (it.hasNext()) {
            Manga u = (Manga) it.next();
            System.out.println(u.toString());

        }
    }

    public void removeAll() {
        Manga.onUpgrade(bbdd, VERSION, VERSION + 1);
    }

}
