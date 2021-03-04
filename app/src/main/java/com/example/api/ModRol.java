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
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Iterator;

public class ModRol extends AppCompatActivity {
    ArrayList<Usuario> miLista;
    RecyclerView miRecycler;
    AdaptadorUsuario2 elAdaptador;
    int modificar;
    BBDD cbdd = new BBDD(this);
    boolean vo = false;
    CheckBox ges;
    CheckBox root;


    String ges2;
    String root2;
    String user;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_mod_rol);
        ges = findViewById(R.id.checkBox2);
        root = findViewById(R.id.checkBox3);

        Typeface font = ResourcesCompat.getFont(this, R.font.police_person);
        TextView policePerson = (TextView) findViewById(R.id.TextView1);
        policePerson.setTypeface(font);

        // shared preference recibe el mensaje cuando registra si va bien.
        SharedPreferences preferencias = getSharedPreferences("variables", Context.MODE_PRIVATE);
        ges2 = preferencias.getString("Extra_ges", "");
        root2 = preferencias.getString("Extra_root", "");
        user = preferencias.getString("Extra_usu", "");
        SharedPreferences.Editor editor = preferencias.edit();
        editor.remove("Extra_ges");
        editor.remove("Extra_root");
        editor.remove("Extra_usu");
        editor.commit();

        cbdd.openForWrite();
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
                int gSi = 0;
                int rSi = 0;
                //SI LAS OCCIONES ESTAN ACTIVADAS PONEN EL ROL A UNO Y SI NO A CERO
                if (ges.isChecked()) {
                    gSi = 1;
                }
                if (root.isChecked()) {
                    rSi = 1;
                }
                usu = new Usuario(usu.getId(), usu.getNombre(),
                        usu.getContra(), usu.getNum()
                        , gSi, rSi);

                //ACTUALIZA EL ROL DE USUARIO SIEMPRE QUENO ESTE ACTIVO
                if (Integer.parseInt(user) != usu.getId()) {

                    int i = cbdd.updateUsuario(usu.getId(), usu);
                    vo = true;
                    if (vo) {
                        vo = false;
                        cargarDatos();
                    }
                    cbdd.ver();
                } else {
                    mostrarToast(getResources().getString(R.string.noRolActivo));
                    cargarDatos();
                }
            }

        });

        miRecycler.setAdapter(elAdaptador);

    }

    //VUELVE A CARGAR ESTA CLASE
    public void cargarDatos() {
        // Va a ver roles y asa las propiedades
        SharedPreferences preferencias = getSharedPreferences("variables", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferencias.edit();
        editor.putString("Extra_ges", ges2);
        editor.putString("Extra_root", root2);
        editor.putString("Extra_usu", user);
        editor.commit();

        Intent reiniciar = new Intent(ModRol.this, ModRol.class);
        ModRol.this.startActivity(reiniciar);
    }

    //VUELVE AL MENU
    public void miVolver(View v) {

        // Va a ver roles y asa las propiedades
        SharedPreferences preferencias = getSharedPreferences("variables", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferencias.edit();
        editor.putString("Extra_ges", ges2);
        editor.putString("Extra_root", root2);
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
