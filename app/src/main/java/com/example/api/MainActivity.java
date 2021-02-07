package com.example.api;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.widget.Toast;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;


public class MainActivity extends AppCompatActivity {

    //Creacion de variables
    static ArrayList<Usuario> usuarios = new ArrayList();
    static boolean res = false;

    private final String Extra_ges = "ges";
    private final String Extra_root = "root";
    private final String Extra_usu = "usu";

    static Usuario activeuser;

    TextView campoTextView1;

    EditText campoNombre;
    EditText campoContra;

    Button campoButton1;
    Button campoButton2;

    // Metodo que se inicia al arracar la pagina
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        CapituloBaseSQLite bd = new CapituloBaseSQLite(this, "Manga", null, 1);
        BBDD cbdd = new BBDD(this);
        cbdd.openForWrite();
        //BORRAR REGISTROS DDE LA BASE DE DATOS
        //cbdd.removeAll();

        //CEACION DE USUARIOS POR DEFECO
        Usuario admin = new Usuario("Admin", "Admin", 927179367, 1, 1);

        //INSERTAR USARIOS EN LA BD
        //cbdd.insertUsuario(admin);
        usuarios = (ArrayList<Usuario>) cbdd.getUsuarios();


        //CEACION DE mangas POR DEFECO
        Manga manga1 = new Manga("Strongest Abandoned Son",
                "Cuando Ye Mo se despertó repentinamente, se dio cuenta de " +
                        "que todo a su alrededor parecía haber cambiado. Ha sido " +
                        "transmigrado a la Tierra moderna donde la energía espiritual " +
                        "es escasa. su maestro Luo Ying ( Luo Susu ) no estaba a la " +
                        "vista. Lo más importante es que se encontró en el cuerpo de un " +
                        "joven que también se llamaba Ye Mo.", R.drawable.strongest);
        Manga manga2= new Manga("Martial Peak",
                "El viaje hacia la cima marcial es solitario y largo. Ante " +
                        "la adversidad, debes sobrevivir y permanecer inflexible. Solo " +
                        "entonces podrás avanzar y continuar tu viaje para convertirte en " +
                        "el más fuerte. El \"Pabellón Cielo Alto\" pone a prueba a sus " +
                        "discípulos de las formas más duras para prepararlos para este " +
                        "viaje. Un día, el humilde barrendero Yang Kai logró obtener un " +
                        "libro negro, lo que lo puso en el camino hacia la cima del mundo " +
                        "marcial.", R.drawable.martial);
        Manga manga3 = new Manga("Dr.Stone",
                "Senku es un joven extremadamente inteligente con un gran don para " +
                        "la ciencia y una ácida personalidad, y su mejor amigo es Taiju, que" +
                        " es muy buena persona pero más apto para usar los músculos que para " +
                        "pensar. Cuando tras cierto incidente toda la humanidad acaba convertida" +
                        " en piedra, ellos logran despertarse en un mundo miles de años después, " +
                        "con la civilización humana completamente desaparecida y con toda la " +
                        "humanidad congelada en piedra como ellos estuvieron. Ahora es su " +
                        "obligación rescatar a la gente y crear un nuevo mundo.", R.drawable.dr);


        //INSERTAR manga EN LA BD
        //cbdd.insertManga(manga1);
        //cbdd.insertManga(manga2);
        //cbdd.insertManga(manga3);


        // Metodo de ejecucion del programa
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);
        //Enlaza los datos de los xml en las variables
        campoTextView1 = findViewById(R.id.TextView1);

        campoNombre = findViewById(R.id.editText1);
        campoContra = findViewById(R.id.editText2);

        campoButton1 = findViewById(R.id.button1);
        campoButton2 = findViewById(R.id.button2);
        //Traduce la pagina
        Switch onOffSwitch = (Switch)  findViewById(R.id.switch1);
        onOffSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
               if(isChecked){
                    campoTextView1.setText(R.string.menuPrincial); ;

                    campoNombre.setHint(R.string.name);;
                    campoContra.setHint(R.string.password);;

                    campoButton1.setText(R.string.start); ;
                    campoButton2.setText(R.string.register); ;

               }else{
                   campoTextView1.setText(R.string.menuPrincialES); ;

                   campoNombre.setHint(R.string.nombre);;
                   campoContra.setHint(R.string.contraseña);;

                   campoButton1.setText(R.string.iniciar); ;
                   campoButton2.setText(R.string.registrarse); ;
               };
            }


        });
        // shared preference recibe el mensaje cuando registra si va bien.
        SharedPreferences preferencias=getSharedPreferences("variables", Context.MODE_PRIVATE);
        String mensaje = preferencias.getString("Mensaje","");
        SharedPreferences.Editor editor=preferencias.edit();
        editor.remove("Mensaje");
        editor.commit();
        if(!mensaje.equals(""))Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_SHORT).show();
    }

    //COMPRUEBA LA UTENTIFICACION
    public boolean comprobarInicio() {


        boolean nom = false;
        boolean con = false;
        boolean aut = false;

        Iterator<Usuario> itr = usuarios.iterator();
        while (itr.hasNext()) {
            Usuario u = itr.next();
            if (u.getNombre().equals(campoNombre.getText().toString())) {
                nom = true;
            }
            if (u.getContra().equals(campoContra.getText().toString())) {
                con = true;
            }

            if (nom && con) {
                aut = true;
                activeuser = u;
                break;
            }
            nom = false;
            con = false;
        }

        return aut;
    }

    //VA AL METODO REGISTRAR
    public void miRegistro(View v) {
        res = true;
        Intent intent = new Intent(v.getContext(), Registrar.class);
        startActivityForResult(intent, 0);

    }

    //VA AL MENU INICIAR
    public void miIniciar(View v) {
        boolean aut = comprobarInicio();
        if (aut) {
            Intent intent = new Intent(v.getContext(), MenuUsuario.class);
            intent.putExtra(Extra_ges, activeuser.isGes() + "");
            intent.putExtra(Extra_root, activeuser.isRoot() + "");
            intent.putExtra(Extra_usu, activeuser.getId() + "");

            startActivity(intent);

        }
    }


}
