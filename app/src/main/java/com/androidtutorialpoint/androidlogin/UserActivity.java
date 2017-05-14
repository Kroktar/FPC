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
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.androidtutorialpoint.androidlogin.mDataObject.AsistenteDB;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

//nuevo spinner




public class UserActivity extends AppCompatActivity {

    private static final String TAG = "UserActivity";

    private TextView greetingTextView;
    private Button btnCliente;
    private Button btnLogOut;

    //nuevo checkuser

    private static final String URL_FOR_LOGIN = "http://www.fastpetcare.cl/android/checkClient.php";
    ProgressDialog progressDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        Bundle bundle = getIntent().getExtras();
        //String user = bundle.getString("username");
        greetingTextView = (TextView) findViewById(R.id.greeting_text_view);
        btnCliente = (Button) findViewById(R.id.buttonCliente);
        greetingTextView.setText("Asistente "+ AsistenteDB.getNombreAsistente());

        //nuevo checkuser
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);

        // Progress dialog
/*
        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(i);
            }
        }
        );

        */
        btnLogOut = (Button) findViewById(R.id.buttonCerrar);
        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(i);
            }
        });

        btnCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Nuevo CheckUser

                // Tag used to cancel the request
                String cancel_req_tag = "login";
                progressDialog.setMessage("Logging you in...");
                showDialog();
                StringRequest strReq = new StringRequest(Request.Method.POST,
                        URL_FOR_LOGIN, new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, "Register Response: " + response.toString());
                        hideDialog();
                        try {
                            JSONObject jObj = new JSONObject(response);
                            boolean error = jObj.getBoolean("error");

                            if (!error) {
                                //Nuevo
                                EditText rut = (EditText)findViewById(R.id.editTextRutUser);
                                String valorRut = rut.getText().toString();

                                Intent i = new Intent(
                                        UserActivity.this,
                                        ClienteActivity.class);
                                //Nuevo
                                i.putExtra("rutCliente", valorRut);
                                startActivity(i);
                            } else {
                                EditText rut = (EditText)findViewById(R.id.editTextRutUser);
                                String valorRut = rut.getText().toString();

                                Intent i = new Intent(
                                        UserActivity.this,
                                        ClienteExistenteActivity.class);
                                //Nuevo
                                i.putExtra("rutCliente", valorRut);
                                startActivity(i);


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
                        Log.e(TAG, "Login Error: " + error.getMessage());
                        Toast.makeText(getApplicationContext(),
                                error.getMessage(), Toast.LENGTH_LONG).show();
                        hideDialog();
                    }
                }) {

                    @Override
                    protected Map<String, String> getParams() {
                        // Posting params to login url
                        //Nuevo
                        EditText rut = (EditText)findViewById(R.id.editTextRutUser);
                        String valorRut = rut.getText().toString();
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("rut", valorRut);
                        return params;
                    }

                };
                // Adding request to request queue
                AppSingleton.getInstance(getApplicationContext()).addToRequestQueue(strReq,cancel_req_tag);



            }
        }
        );

    }



/*
    public void buttonClick(View view)
    {
        Intent intent = new Intent(getApplicationContext(), MascotaActivity.class);
        startActivity(intent);
    }
*/
//nuevo checkuser
private void showDialog() {
    if (!progressDialog.isShowing())
        progressDialog.show();
}
    private void hideDialog() {
        if (progressDialog.isShowing())
            progressDialog.dismiss();
    }




}


