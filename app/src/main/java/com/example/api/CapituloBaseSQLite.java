package com.example.api;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

public class CapituloBaseSQLite extends SQLiteOpenHelper {
    // Constantes para la tablaa de usuarios.
    private static final String TABLA_USUARIOS = "usuarios";
    private static final String idUsuario = "id";
    private static final String nombreUsuario = "nombre";
    private static final String contraUsuario = "contra";
    private static final String telefonoUsuario = "num";
    private static final String rolGesUsuario = "ges";
    private static final String rolRootUsuario = "root";
    private static final String pathUsuario = "path";


    //Constantes para la tabla de manga
    private static final String TABLA_MANGA = "mangas";
    private static final String idManga = "id";
    private static final String nombreManga = "nombre";
    private static final String desManga = "descripcion";
    private static final String pathManga = "pathMaga";

    //SQL que crea la tabla de usuario.
    private static final String CREATE_BD = "CREATE TABLE " +
            TABLA_USUARIOS + " (" + idUsuario + " Integer PRIMARY KEY AUTOINCREMENT, " + nombreUsuario + " TEXT NOT NULL, "
            + contraUsuario + " String NOT NULL, " + telefonoUsuario + " int NOT NULL, "
            + rolGesUsuario + " boolean NOT NULL, "
            + rolRootUsuario + " boolean NOT NULL," + pathUsuario + " String);";


    //SQL que crea la tabla de manga
    private static final String CREATE_BD2 = "CREATE TABLE " +
            TABLA_MANGA + " (" + idManga + " Integer PRIMARY KEY AUTOINCREMENT, " + nombreManga + " TEXT NOT NULL, "
            + desManga + " String NOT NULL, " + pathManga + " Integer);";

    //Constructor por defectode la base de datos.
    public CapituloBaseSQLite(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    //Ejecutable de la creacion de las tablas.
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_BD);
        db.execSQL(CREATE_BD2);

        try {
            db.execSQL("INSERT INTO " +
                    TABLA_USUARIOS + " VALUES ( " + null + " , 'Admin' , '" + AeSimpleSHA1.SHA1("Admin") + "', 625483254, 1, 1, null);");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (
                UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        AppCompatActivity app = new AppCompatActivity();
        //CEACION DE mangas POR DEFECO
        db.execSQL("INSERT INTO " +
                TABLA_MANGA + " VALUES ( " + null + " , 'Strongest Abandon Son' , 'Cuando Ye Mo se despertó repentinamente, se dio cuenta de " +
                "que todo a su alrededor parecía haber cambiado. Ha sido " +
                "transmigrado a la Tierra moderna donde la energía espiritual " +
                "es escasa. su maestro Luo Ying ( Luo Susu ) no estaba a la " +
                "vista. Lo más importante es que se encontró en el cuerpo de un " +
                "joven que también se llamaba Ye Mo.' , " + R.drawable.strongest + ");");

        db.execSQL("INSERT INTO " +
                TABLA_MANGA + " VALUES ( " + null + " , 'Martial Peak' ,  'El viaje hacia la cima marcial es solitario y largo. Ante " +
                "la adversidad, debes sobrevivir y permanecer inflexible. Solo " +
                "entonces podrás avanzar y continuar tu viaje para convertirte en " +
                "el más fuerte. El Pabellón Cielo Alto pone a prueba a sus " +
                "discípulos de las formas más duras para prepararlos para este " +
                "viaje. Un día, el humilde barrendero Yang Kai logró obtener un " +
                "libro negro, lo que lo puso en el camino hacia la cima del mundo " +
                "marcial.' , " + R.drawable.martial + ");");

        db.execSQL("INSERT INTO " +
                TABLA_MANGA + " VALUES ( " + null + " , 'Dr.Stone' ,  'Senku es un joven extremadamente inteligente con un gran don para " +
                "la ciencia y una ácida personalidad, y su mejor amigo es Taiju, que " +
                "es muy buena persona pero más apto para usar los músculos que para " +
                "pensar. Cuando tras cierto incidente toda la humanidad acaba convertida " +
                "en piedra, ellos logran despertarse en un mundo miles de años después, " +
                "con la civilización humana completamente desaparecida y con toda la " +
                "humanidad congelada en piedra como ellos estuvieron. Ahora es su " +
                " obligación rescatar a la gente y crear un nuevo mundo.' , " + R.drawable.dr + ");");


    }

    // Ejecutable de borrado y recreado de las tablas
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE if exists " + TABLA_USUARIOS);
        db.execSQL("DROP TABLE if exists " + TABLA_MANGA);

        onCreate(db);
    }

}
