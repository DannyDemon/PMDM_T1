package com.example.api;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import java.util.ArrayList;
import java.util.Iterator;


public class MainActivity extends AppCompatActivity {

    //Creacion de variables
    static ArrayList<Usuario> usuarios = new ArrayList();
    static boolean res = false;

    static Usuario activeuser;


    EditText campoNombre;
    EditText campoContra;


    // Metodo que se inicia al arracar la pagina
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        BBDD cbdd = new BBDD(this);
        cbdd.openForWrite();

        usuarios = (ArrayList<Usuario>) cbdd.getUsuarios();


        // Metodo de ejecucion del programa
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);

        Typeface font = ResourcesCompat.getFont(this, R.font.police_person);
        TextView policePerson = (TextView) findViewById(R.id.TextView1);
        policePerson.setTypeface(font);

        // shared preference recibe el mensaje cuando registra si va bien.
        SharedPreferences preferencias = getSharedPreferences("variables", Context.MODE_PRIVATE);
        String mensaje = preferencias.getString("Mensaje", "");
        SharedPreferences.Editor editor = preferencias.edit();
        editor.remove("Mensaje");
        editor.commit();
        if (!mensaje.equals("")) mostrarToast(mensaje);
    }


    //COMPRUEBA LA UTENTIFICACION
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public boolean comprobarInicio() throws Exception {

        campoNombre = findViewById(R.id.editText1);
        campoContra = findViewById(R.id.editText2);

        boolean nom = false;
        boolean con = false;
        boolean aut = false;

        if (null != usuarios) {
            Iterator<Usuario> itr = usuarios.iterator();

            while (itr.hasNext()) {
                Usuario u = itr.next();


                if (u.getNombre().equals(campoNombre.getText().toString())) {
                    nom = true;
                }

                if (u.getContra().equals(AeSimpleSHA1.SHA1(campoContra.getText().toString()))) {
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

        }
        if (aut == false) {
            mostrarToast(getResources().getString(R.string.credencial));
        }

        return aut;
    }

    //VA AL METODO REGISTRAR
    public void miRegistro(View v) {
        res = true;
        Intent intent = new Intent(v.getContext(), Animacion2.class);
        startActivityForResult(intent, 0);

    }

    //VA AL MENU INICIAR
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void miIniciar(View v) throws Exception {
        boolean aut = comprobarInicio();
        if (aut) {
            SharedPreferences preferencias4 = getSharedPreferences("variables", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor4 = preferencias4.edit();
            editor4.putString("Extra_ges", activeuser.isGes() + "");
            editor4.putString("Extra_root", activeuser.isRoot() + "");
            editor4.commit();

            Intent intent = new Intent(this, MenuUsuario.class);
            startActivity(intent);

        }
    }

    private void mostrarToast(String texto) {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.custom_toast, (ViewGroup) findViewById(R.id.layout_base));

        TextView textView = layout.findViewById(R.id.txt);
        textView.setText(texto);

        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }
}
