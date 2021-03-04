package com.example.api;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;

public class MenuUsuario extends AppCompatActivity {
    // Variables
    String usur;
    String ges;
    String root;

    BBDD cbdd = new BBDD(this);

    ArrayList<Manga> miLista;
    RecyclerView miRecycler;
    AdaptadorManga elAdaptador;
    int modificar;

    // metodo que carga la pantalla
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_menu_usuario);
        // enlaze de las botones y el toolbar
        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        if (null != toolbar) {
            setSupportActionBar(toolbar);
        }
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);


        cbdd.openForWrite();
        //PASAMOS LOS DATOS DE LOS USUARIS AL RECICLER VIEW PARA MOSTRARLOS
        miLista = (ArrayList<Manga>) cbdd.getMangas();
        miRecycler = findViewById(R.id.miRecycler);
        miRecycler.setLayoutManager(new LinearLayoutManager(this));
        elAdaptador = new AdaptadorManga(miLista);

        miRecycler.setAdapter(elAdaptador);
        listar();


        // shared preference recibe el mensaje cuando registra si va bien.
        SharedPreferences preferencias = getSharedPreferences("variables", Context.MODE_PRIVATE);
        ges = preferencias.getString("Extra_ges", "");
        root = preferencias.getString("Extra_root", "");
        SharedPreferences.Editor editor = preferencias.edit();
        editor.remove("Extra_ges");
        editor.remove("Extra_root");
        editor.remove("Extra_usu");
        editor.commit();


    }


    private void listar() {

        AdaptadorManga elAdaptador = new AdaptadorManga(miLista);
        //INVOCAR UNA DEVOLUCIÓN DE LLAMADA CUANDO SE HACE CLIC EN UNA VISTA
        elAdaptador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mostrarToast(getResources().getString(R.string.pulsado) + " " + miLista.get(miRecycler.getChildAdapterPosition(v)).getNombre());

                modificar = miRecycler.getChildAdapterPosition(v);


            }
        });

        miRecycler.setAdapter(elAdaptador);

    }


    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu1, menu);
        return true;
    }

    //DIFERENTES METODOS DE LOS BOTONES del toolbar
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        Intent llamada;
        switch (id) {

            case R.id.item1:
                // Va a modificar el telefono y asa las propiedades
                SharedPreferences preferencias = getSharedPreferences("variables", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferencias.edit();
                editor.putString("Extra_ges", ges);
                editor.putString("Extra_root", root);
                editor.putString("Extra_usu", usur);
                editor.putString("Extra_page", "tel");
                editor.commit();

                Intent intent = new Intent(MenuUsuario.this, Animacion2.class);
                if (ges.equals("1") || Integer.parseInt(root) == 1) {
                    startActivity(intent);
                } else {
                    mostrarToast(getResources().getString(R.string.permisosAcceso));
                }
                return true;

            case R.id.item2:
                // Va a borrar usuario y asa las propiedades
                SharedPreferences preferencias2 = getSharedPreferences("variables", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor2 = preferencias2.edit();
                editor2.putString("Extra_ges", ges);
                editor2.putString("Extra_root", root);
                editor2.putString("Extra_usu", usur);
                editor2.putString("Extra_page", "borrar");

                editor2.commit();

                Intent intent2 = new Intent(MenuUsuario.this, Animacion2.class);
                if (Integer.parseInt(root) == 1) {
                    startActivity(intent2);
                } else {
                    mostrarToast(getResources().getString(R.string.permisosAcceso));
                }
                return true;

            case R.id.item3:
                // Va a ver los datos y asa las propiedades
                SharedPreferences preferencias3 = getSharedPreferences("variables", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor3 = preferencias3.edit();
                editor3.putString("Extra_ges", ges);
                editor3.putString("Extra_root", root);
                editor3.putString("Extra_usu", usur);
                editor3.putString("Extra_page", "datos");
                editor3.commit();

                Intent intent3 = new Intent(MenuUsuario.this, Animacion2.class);
                if (ges.equals("1") || Integer.parseInt(root) == 1) {
                    startActivity(intent3);
                } else {
                    mostrarToast(getResources().getString(R.string.permisosAcceso));
                }
                return true;


            case R.id.item4:

                // Va a ver roles y asa las propiedades
                SharedPreferences preferencias4 = getSharedPreferences("variables", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor4 = preferencias4.edit();
                editor4.putString("Extra_ges", ges);
                editor4.putString("Extra_root", root);
                editor4.putString("Extra_usu", usur);
                editor4.putString("Extra_page", "rol");
                editor4.commit();

                Intent intent4 = new Intent(MenuUsuario.this, Animacion2.class);
                if (Integer.parseInt(root) == 1) {
                    startActivity(intent4);
                } else {
                    mostrarToast(getResources().getString(R.string.permisosAcceso));
                }
                return true;
            case R.id.item5:

                // Va a ver roles y asa las propiedades
                SharedPreferences preferencias5 = getSharedPreferences("variables", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor5 = preferencias5.edit();
                editor5.putString("Extra_ges", ges);
                editor5.putString("Extra_root", root);
                editor5.putString("Extra_usu", usur);
                editor5.putString("Extra_page", "video");
                editor5.commit();

                Intent intent5 = new Intent(MenuUsuario.this, Animacion2.class);
                startActivity(intent5);

                return true;

            case R.id.item6:

                // Va a ver roles y asa las propiedades
                SharedPreferences preferencias6 = getSharedPreferences("variables", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor6 = preferencias6.edit();
                editor6.putString("Extra_ges", ges);
                editor6.putString("Extra_root", root);
                editor6.putString("Extra_usu", usur);
                editor6.putString("Extra_page", "poke");
                editor6.commit();

                Intent intent6 = new Intent(MenuUsuario.this, Animacion2.class);
                startActivity(intent6);

                return true;

        }
        return super.onOptionsItemSelected(item);
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
