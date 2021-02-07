package com.example.api;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

public class MenuUsuario extends AppCompatActivity {
    // Variables
    private final String Extra_ges = "ges";
    private final String Extra_root = "root";
    private final String Extra_usu = "usu";
    String usur;
    String ges;
    String root;

    CapituloBaseSQLite bd = new CapituloBaseSQLite(this, "Manga", null, 1);
    BBDD cbdd = new BBDD(this);

    // metodo que carga la pantalla
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_menu_usuario);
        // enlaze de las botones y el toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        if (null != toolbar) {
            setSupportActionBar(toolbar);
        }
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);


        Button boton1 = (Button) findViewById(R.id.Boton1);
        Button boton2 = (Button) findViewById(R.id.Boton2);
        Button boton3 = (Button) findViewById(R.id.Boton3);
        Button boton4 = (Button) findViewById(R.id.Boton4);


        //TODOS LOS BOTONES SE OCULTAN
        boton1.setVisibility(View.GONE);
        boton2.setVisibility(View.GONE);
        boton3.setVisibility(View.GONE);
        boton4.setVisibility(View.GONE);

        // Recojje las variables pasadas de la vista anterior.
        Intent intent = getIntent();

        ges = intent.getStringExtra(Extra_ges);
        root = intent.getStringExtra(Extra_root);
        usur = intent.getStringExtra(Extra_usu);


        //COMPRUEBA LOS ROLES DEL USUARIO ACTIVO Y ACTIVA SUS BOTONES
        if (ges.equals("1")) {
            boton1.setVisibility(View.VISIBLE);
            boton3.setVisibility(View.VISIBLE);
        }
        if (Integer.parseInt(root) == 1) {
            boton1.setVisibility(View.VISIBLE);
            boton2.setVisibility(View.VISIBLE);
            boton3.setVisibility(View.VISIBLE);
            boton4.setVisibility(View.VISIBLE);
        }
    }

    // Va a modificar el telefono y asa las propiedades
    public void miModTel(View v) {
        Intent intent = new Intent(v.getContext(), ModTel.class);
        intent.putExtra(Extra_ges, ges);
        intent.putExtra(Extra_root, root);
        intent.putExtra(Extra_usu, usur);

        startActivity(intent);
    }
    // Va a borrar usuario y asa las propiedades
    public void miBorrarUsu(View v) {
        Intent intent = new Intent(v.getContext(), BorrarUsuario.class);
        intent.putExtra(Extra_ges, ges);
        intent.putExtra(Extra_root, root);
        intent.putExtra(Extra_usu, usur);

        startActivity(intent);
    }
    // Va a ver los datos y asa las propiedades

    public void miVer(View v) {
        Intent intent = new Intent(v.getContext(), VerDatos.class);
        intent.putExtra(Extra_ges, ges);
        intent.putExtra(Extra_root, root);
        intent.putExtra(Extra_usu, usur);

        startActivity(intent);
    }
    // Va a ver roles y asa las propiedades

    public void miRol(View v) {
        Intent intent = new Intent(v.getContext(), ModRol.class);
        intent.putExtra(Extra_ges, ges);
        intent.putExtra(Extra_root, root);
        intent.putExtra(Extra_usu, usur);

        startActivity(intent);
    }





    // Va a ver manga y asa las propiedades

    public void miManga(View v) {
        Intent intent = new Intent(v.getContext(), VerMangas.class);
        intent.putExtra(Extra_ges, ges);
        intent.putExtra(Extra_root, root);
        intent.putExtra(Extra_usu, usur);

        startActivity(intent);
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu1, menu);
        return true;
    }
    //DIFERENTES METODOS DE LOS BOTONES del toolbar
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.item1:
                System.exit (2);
                return true;

            case R.id.item2:
                Toast.makeText(getApplicationContext(), "Se ha deslogeado", Toast.LENGTH_SHORT).show();
                Intent llamada =new Intent(MenuUsuario.this, MainActivity.class);
                startActivity(llamada);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
