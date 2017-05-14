package com.androidtutorialpoint.androidlogin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.androidtutorialpoint.androidlogin.mDataObject.AsistenteDB;
import com.androidtutorialpoint.androidlogin.mMySQL.Downloader;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.androidtutorialpoint.androidlogin.*;

public class ClienteExistenteActivity extends AppCompatActivity {

    private String urlAddress="http://www.fastpetcare.cl/android/mascota_select.php";
    private TextView duenoRut;
    private Button btnMascotaExistente;
    public Spinner sp;
    public String rut;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente_existente);

        Bundle bundle = getIntent().getExtras();
        rut = bundle.getString("rutCliente");


        TextView duenoRut = (TextView) findViewById(R.id.textViewDuenoRut);
        duenoRut.setText(rut);



        urlAddress ="http://www.fastpetcare.cl/android/mascota_select2.php?rut="+rut;
        //urlAddress ="http://www.fastpetcare.cl/android/mascota_select.php";


        //nuevo Spinner
        //final Spinner sp= (Spinner) findViewById(R.id.spinnerMascotaExistente);
        sp= (Spinner) findViewById(R.id.spinnerMascotaExistente);
        new Downloader(ClienteExistenteActivity.this,urlAddress,sp).execute();
        //String textMascota = sp.getSelectedItem().toString();


        btnMascotaExistente = (Button) findViewById(R.id.buttonIngresarExistente);



        btnMascotaExistente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitForm();
            }
        });

    }



    private void submitForm() {
        String seleccionado = sp.getSelectedItem().toString();

        int contador = AsistenteDB.getContador();
        int ayuda = 0;

        //String[] temp = new String[20];

        if(seleccionado.equals("Nueva Mascota"))
        {
            Intent intent = new Intent(
                    ClienteExistenteActivity.this,
                    MascotaActivity.class);
            //pasar datos
            intent.putExtra("rutCliente", rut);

            startActivity(intent);
            finish();
        }

        while(ayuda<contador)
        {
            //String str = AsistenteDB.getListadoMascotas()[ayuda];
            ArrayList str = AsistenteDB.getNombreArrayList();
            String texto =  (String)str.get(ayuda);



            //temp = texto.split(".");
            String[] temp2 = texto.split("\\.");
            String idMascota = temp2[0];
            String nombreMascota = temp2[1];



            if(seleccionado.equals(nombreMascota))
            {
                Intent intent = new Intent(
                        ClienteExistenteActivity.this,
                        AtencionActivity.class);
                //pasar datos
                intent.putExtra("nombreMascota", idMascota);
                startActivity(intent);
                finish();
            }

            ayuda++;
        }


    }

}