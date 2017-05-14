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

//nuevo

import com.androidtutorialpoint.androidlogin.*;
import com.androidtutorialpoint.androidlogin.mMySQL.Downloader;

import android.widget.Spinner;

public class ClienteActivity extends AppCompatActivity {


    private static final String TAG = "ClienteActivity";
    private static final String URL_FOR_REGISTRATION = "http://www.fastpetcare.cl/android/registerclient.php";
    ProgressDialog progressDialog;
    private EditText signupInputRut, signupInputNombre, signupInputApellido, signupInputDireccion, signupInputComuna, signupInputTelefono, signupInputCelular, signupInputEmail;
    private Button btnIngresar;

    private EditText editTextRutCreate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente);

        Bundle bundle = getIntent().getExtras();
        String rut = bundle.getString("rutCliente");
        editTextRutCreate = (EditText) findViewById(R.id.editTextRut);
        editTextRutCreate.setText(rut);


        // Progress dialog
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);


        signupInputRut = (EditText) findViewById(R.id.editTextRut);
        signupInputNombre = (EditText) findViewById(R.id.editTextNombre);
        signupInputApellido = (EditText) findViewById(R.id.editTextApellido);
        signupInputDireccion = (EditText) findViewById(R.id.editTextDireccion);
        signupInputComuna = (EditText) findViewById(R.id.editTextComuna);
        signupInputTelefono = (EditText) findViewById(R.id.editTextTelefono);
        signupInputCelular = (EditText) findViewById(R.id.editTextCelular);
        signupInputEmail = (EditText) findViewById(R.id.editTextEmail);


        btnIngresar = (Button) findViewById(R.id.buttonIngresarCliente);



        btnIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitForm();
            }
        });

    }



    private void submitForm() {

        registerUser(signupInputRut.getText().toString(),
                signupInputNombre.getText().toString(),
                signupInputApellido.getText().toString(),
                signupInputDireccion.getText().toString(),
                signupInputComuna.getText().toString(),
                signupInputTelefono.getText().toString(),
                signupInputCelular.getText().toString(),
                signupInputEmail.getText().toString());

    }



    private void registerUser(final String rut,  final String nombre, final String apellido, final String direccion,
                              final String comuna, final String telefono, final String celular, final String email) {
        // Tag used to cancel the request
        String cancel_req_tag = "registerclient";

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
                                ClienteActivity.this,
                                MascotaActivity.class);
                        //pasar datos
                        intent.putExtra("rutCliente", rut);

                        startActivity(intent);
                        finish();

/*
                        EditText rut = (EditText)findViewById(R.id.editTextRutUser);
                        String valorRut = rut.getText().toString();

                        Intent i = new Intent(
                                UserActivity.this,
                                ClienteActivity.class);
                        //Nuevo
                        i.putExtra("rutCliente", valorRut);

*/

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
                params.put("rut", rut);
                params.put("nombre", nombre);
                params.put("apellido", apellido);
                params.put("direccion", direccion);
                params.put("comuna", comuna);
                params.put("telefono", telefono);
                params.put("celular", celular);
                params.put("email", email);
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