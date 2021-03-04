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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class VerDatos extends AppCompatActivity {

    String ges;
    String root;
    String user;


    ArrayList<Usuario> miLista;
    RecyclerView miRecycler;
    AdaptadorUsuario elAdaptador;
    int modificar;
    CapituloBaseSQLite bd = new CapituloBaseSQLite(this, "Manga", null, 1);
    BBDD cbdd = new BBDD(this);

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_ver_datos);

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

        cbdd.openForWrite();
        //PASAMOS LOS DATOS DE LOS USUARIS AL RECICLER VIEW PARA MOSTRARLOS
        miLista = (ArrayList<Usuario>) cbdd.getUsuarios();
        System.out.println(miLista);
        miRecycler = findViewById(R.id.miRecycler);
        miRecycler.setLayoutManager(new LinearLayoutManager(this));
        elAdaptador = new AdaptadorUsuario(miLista);

        miRecycler.setAdapter(elAdaptador);
        listar();
    }

    private void listar() {

        AdaptadorUsuario elAdaptador = new AdaptadorUsuario(miLista);
        //INVOCAR UNA DEVOLUCIÓN DE LLAMADA CUANDO SE HACE CLIC EN UNA VISTA
        elAdaptador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mostrarToast(getResources().getString(R.string.pulsado) + miLista.get(miRecycler.getChildAdapterPosition(v)).getNombre());

                modificar = miRecycler.getChildAdapterPosition(v);


            }
        });

        miRecycler.setAdapter(elAdaptador);

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