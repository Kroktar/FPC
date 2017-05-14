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
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;

public class MascotaActivity extends AppCompatActivity {


    private static final String TAG = "MascotaActivity";
    private static final String URL_FOR_REGISTRATION = "http://www.fastpetcare.cl/android/registerpet.php";
    ProgressDialog progressDialog;

    private EditText signupInputNombre, signupInputRaza, signupInputEdad, signupInputPeso;
    private Button btnIngresar;
    private RadioGroup especieRadioGroup;
    private RadioGroup sexoRadioGroup;

    private String rut_dueno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mascota);

//variable rut dueno
        Bundle bundle = getIntent().getExtras();
        rut_dueno = bundle.getString("rutCliente");

        // Progress dialog
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);

        signupInputNombre = (EditText) findViewById(R.id.signup_input_nombre);
        signupInputRaza = (EditText) findViewById(R.id.signup_input_raza);
        signupInputEdad = (EditText) findViewById(R.id.signup_input_edad);
        signupInputPeso = (EditText) findViewById(R.id.signup_input_peso);

        btnIngresar = (Button) findViewById(R.id.btn_signup_mascota);


            sexoRadioGroup = (RadioGroup) findViewById(R.id.sexo_radio_group);
            especieRadioGroup = (RadioGroup) findViewById(R.id.especie_radio_group);

        btnIngresar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    submitForm();
                }
        });

    }

    private void submitForm() {

        int selectedId = sexoRadioGroup.getCheckedRadioButtonId();
        String sexo;
        if(selectedId == R.id.macho_radio_btn){
            sexo = "Macho";}
        else{
            sexo = "Hembra";}

        int selectedId2 = especieRadioGroup.getCheckedRadioButtonId();
        String especie;
        if(selectedId2 == R.id.canino_radio_btn){
            especie = "Canino";}
        else{
            especie = "Felino";}

        registerUser(signupInputNombre.getText().toString(),
                especie,
                signupInputRaza.getText().toString(),
                signupInputEdad.getText().toString(),
                signupInputPeso.getText().toString(),
                sexo,
                rut_dueno);

    }



    private void registerUser(final String nombre,  final String especie, final String raza,
                              final String edad, final String peso, final String sexo, final String rut) {
        // Tag used to cancel the request
        String cancel_req_tag = "registerpet";

        progressDialog.setMessage("Adding you ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                URL_FOR_REGISTRATION, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Register Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    if (!error) {
                        //String user = jObj.getJSONObject("user").getString("nombre");
                        //Toast.makeText(getApplicationContext(), "La mascota " + user +"se ha registrado!", Toast.LENGTH_SHORT).show();

                        // Launch login activity
                        Intent intent = new Intent(
                                MascotaActivity.this,
                                AtencionActivity.class);

                        //pasar datos
                        String idMascota = jObj.getJSONObject("user").getString("id");
                        intent.putExtra("nombreMascota", idMascota);
                        startActivity(intent);
                        finish();
                    } else {

                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getApplicationContext(),
                                errorMsg, Toast.LENGTH_LONG).show();


                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Registration Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                params.put("nombre", nombre);
                params.put("especie", especie);
                params.put("raza", raza);
                params.put("edad", edad);
                params.put("peso", peso);
                params.put("sexo", sexo);
                params.put("rutdueno", rut_dueno);
                return params;
            }
        };
        // Adding request to request queue
        AppSingleton.getInstance(getApplicationContext()).addToRequestQueue(strReq, cancel_req_tag);
    }

    private void showDialog() {
        if (!progressDialog.isShowing())
            progressDialog.show();
    }

    private void hideDialog() {
        if (progressDialog.isShowing())
            progressDialog.dismiss();
    }

}