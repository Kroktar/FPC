package com.androidtutorialpoint.androidlogin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;

import com.androidtutorialpoint.androidlogin.mDataObject.AsistenteDB;
import com.androidtutorialpoint.androidlogin.mMySQL.Downloader;

import java.util.ArrayList;

public class VeterinarioActivity extends AppCompatActivity {

    public Spinner spVet;
    private Button btnMascotaExistente;
    private String urlAddress="http://www.fastpetcare.cl/android/veterinario_select.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_veterinario);

        btnMascotaExistente = (Button) findViewById(R.id.buttonAsistir);

        spVet= (Spinner) findViewById(R.id.spinnerVeterinarios);
        new Downloader(VeterinarioActivity.this,urlAddress,spVet).execute();


        btnMascotaExistente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String veterinario = spVet.getSelectedItem().toString();

                int contador = AsistenteDB.getContador();
                int ayuda = 0;


                /*

                Intent intent = new Intent(
                        VeterinarioActivity.this,
                        UserActivity.class);
                AsistenteDB.setNombreVeterinario(veterinario);
                startActivity(intent);
                finish();

                */


                while(ayuda<contador)
                {
                    //String str = AsistenteDB.getListadoMascotas()[ayuda];
                    ArrayList str = AsistenteDB.getNombreArrayList();
                    String texto =  (String)str.get(ayuda);



                    //temp = texto.split(".");
                    String[] temp2 = texto.split("\\.");
                    String idMascota = temp2[0];
                    String nombreMascota = temp2[1];



                    if(veterinario.equals(nombreMascota))
                    {
                        Intent intent = new Intent(
                                VeterinarioActivity.this,
                                UserActivity.class);
                        //pasar datos
                        AsistenteDB.setNombreVeterinario(veterinario);
                        AsistenteDB.setRutVeterinario(idMascota);
                        //intent.putExtra("nombreMascota", idMascota);
                        startActivity(intent);
                        finish();
                    }

                    ayuda++;
                }
            }
        });
    }
}
