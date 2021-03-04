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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Iterator;

import entradas.Entradas;

public class ModTel extends AppCompatActivity {
    ArrayList<Usuario> miLista;
    RecyclerView miRecycler;
    EditText edit;
    AdaptadorUsuario2 elAdaptador;
    int modificar;
    BBDD cbdd = new BBDD(this);
    boolean vo = false;


    String ges;
    String root;
    String user;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_mod_telefono);

        Typeface font = ResourcesCompat.getFont(this, R.font.police_person);
        TextView policePerson = (TextView) findViewById(R.id.TextView1);
        policePerson.setTypeface(font);

        // shared preference recibe el mensaje cuando registra si va bien.
        SharedPreferences preferencias = getSharedPreferences("variables", Context.MODE_PRIVATE);
        ges = preferencias.getString("Extra_ges", "");
        root = preferencias.getString("Extra_root", "");
        user = preferencias.getString("Extra_usu", "");
        SharedPreferences.Editor editor = preferencias.edit();
        editor.remove("Extra_ges");
        editor.remove("Extra_root");
        editor.remove("Extra_usu");
        editor.commit();

        edit = findViewById(R.id.EditText1);

        cbdd.openForWrite();
        //PASAMOS LOS DATOS DE LOS USUARIOS AL RECICLER VIEW PARA MOSTRARLOS
        miLista = (ArrayList<Usuario>) cbdd.getUsuarios();
        miRecycler = findViewById(R.id.miRecycler);
        miRecycler.setLayoutManager(new LinearLayoutManager(this));
        elAdaptador = new AdaptadorUsuario2(miLista);

        miRecycler.setAdapter(elAdaptador);
        listar();
    }

    private void listar() {

        elAdaptador = new AdaptadorUsuario2(miLista);
        //INVOCAR UNA DEVOLUCIÓN DE LLAMADA CUANDO SE HACE CLIC EN UNA VISTA
        elAdaptador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //SI NO PASAS NADA NOS SALE UN PANEL INFORMANDO QUE EL CAMPO  ESTA VACIO
                if (!edit.getText().toString().equals("")) {
                    //SI NO PASAS UN NUMERO NOS SALE UN PANEL INFORMANDO QUE HAS DE PASAR NUMEROS
                    if (Entradas.comprNumerico(edit.getText().toString()) == true) {
                        mostrarToast(getResources().getString(R.string.pulsado) + miLista.get(miRecycler.getChildAdapterPosition(v)).getNombre());

                        //GUARDA LA POSICION DEL ELEMENTO PULSADO
                        modificar = miRecycler.getChildAdapterPosition(v);

                        //BUSCA EL USUARIO QUE ESTA EN LA POSICION SELECCIONADA
                        ArrayList<Usuario> array = elAdaptador.getMiLista();
                        Iterator it = array.iterator();
                        Usuario usu = null;
                        while (it.hasNext()) {
                            Usuario u = (Usuario) it.next();
                            if (u.getId() == elAdaptador.getMiLista().get(modificar).getId()) {
                                usu = (Usuario) u;
                            }

                        }

                        usu = new Usuario(usu.getId(), usu.getNombre(),
                                usu.getContra(), Integer.parseInt(edit.getText().toString()), usu.isGes(), usu.isRoot());

                        //ACTUALIZA EL USUARIO
                        int i = cbdd.updateUsuario(usu.getId(), usu);
                        cbdd.ver();
                        vo = true;
                        if (vo) {
                            vo = false;
                            cargarDatos();
                        }
                    } else {
                        mostrarToast(getResources().getString(R.string.introduceNume));
                        cargarDatos();
                    }
                } else {
                    mostrarToast(getResources().getString(R.string.noTelefono));
                    cargarDatos();
                }
            }
        });

        miRecycler.setAdapter(elAdaptador);

    }

    //VUELA CARGAR ESTA CLASE
    public void cargarDatos() {
        // Va a ver roles y asa las propiedades
        SharedPreferences preferencias = getSharedPreferences("variables", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferencias.edit();
        editor.putString("Extra_ges", ges);
        editor.putString("Extra_root", root);
        editor.putString("Extra_usu", user);
        editor.commit();

        Intent intent = new Intent(ModTel.this, ModTel.class);
        ModTel.this.startActivity(intent);
    }

    //VUELVE AL MENU
    public void miVolver(View v) {
        // Va a ver roles y asa las propiedades
        SharedPreferences preferencias = getSharedPreferences("variables", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferencias.edit();
        editor.putString("Extra_ges", ges);
        editor.putString("Extra_root", root);
        editor.putString("Extra_usu", user);
        editor.commit();

        Intent intent = new Intent(v.getContext(), MenuUsuario.class);
        startActivity(intent);
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

