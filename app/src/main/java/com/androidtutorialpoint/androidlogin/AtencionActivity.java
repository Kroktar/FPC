package com.androidtutorialpoint.androidlogin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
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

//nuevo fotos
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;



public class AtencionActivity extends AppCompatActivity/* implements View.OnClickListener*/{


    private static final String TAG = "AtencionActivity";
    private static final String URL_FOR_REGISTRATION = "http://www.fastpetcare.cl/android/registeratencion.php";
    ProgressDialog progressDialog;

    //private EditText desparacitacion, antirabica, sextuple, octuple, triplefelina, leucemiafelina;
    private String desparacitacion, antirabica, sextuple, octuple, triplefelina, leucemiafelina, rutdoctor, rutasistente;
    private Button btnIngresar;

    private String nombreMascota;

    //nuevo subir foto

    public static final String UPLOAD_KEY = "image";
    private int PICK_IMAGE_REQUEST = 10001;
    private Button buttonChoose;
    private Uri filePath;
    private Bitmap bitmap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atencion);

//variable nombreMascota
        Bundle bundle = getIntent().getExtras();
        nombreMascota = bundle.getString("nombreMascota");

        // Progress dialog
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);

        btnIngresar = (Button) findViewById(R.id.buttonAtencion);

        btnIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitForm();
            }
        });

        //nuevo foto
        buttonChoose = (Button) findViewById(R.id.buttonChoose);


    }

    // nuevo foto
/*
    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }
    */
/*
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            filePath = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
*/
/*
    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }
*/

/*
    @Override
    public void onClick(View v) {
        if (v == buttonChoose) {
            showFileChooser();
        }
    }
*/

    private void submitForm() {

        CheckBox ChkDesparacitacion = (CheckBox) findViewById(R.id.checkBoxDesparacitacion);
        CheckBox ChkAntirabica = (CheckBox) findViewById(R.id.checkBoxAntirabica);
        CheckBox ChkSextuple = (CheckBox) findViewById(R.id.checkBoxSextuple);
        CheckBox ChkOctuple = (CheckBox) findViewById(R.id.checkBoxOctuple);
        CheckBox ChkTriplefelina = (CheckBox) findViewById(R.id.checkBoxTriplefelina);
        CheckBox ChkLeucemiafelina = (CheckBox) findViewById(R.id.checkBoxLeucemiafelina);
        rutdoctor = AsistenteDB.getRutVeterinario();
        rutasistente = AsistenteDB.getRutAsistente();

        if(ChkDesparacitacion.isChecked())
        {
            desparacitacion = "SI";
        }
        else
        {
            desparacitacion = "NO";
        }

        if(ChkAntirabica.isChecked())
        {
            antirabica = "SI";
        }
        else
        {
            antirabica = "NO";
        }

        if(ChkSextuple.isChecked())
        {
            sextuple = "SI";
        }
        else
        {
            sextuple = "NO";
        }

        if(ChkOctuple.isChecked())
        {
            octuple = "SI";
        }
        else
        {
            octuple = "NO";
        }

        if(ChkTriplefelina.isChecked())
        {
            triplefelina = "SI";
        }
        else
        {
            triplefelina = "NO";
        }

        if(ChkLeucemiafelina.isChecked())
        {
            leucemiafelina = "SI";
        }
        else
        {
            leucemiafelina = "NO";
        }

        //String uploadImage = getStringImage(bitmap);

        registerAtencion(desparacitacion,
                antirabica,
                sextuple,
                octuple,
                triplefelina,
                leucemiafelina,
                rutdoctor,
                rutasistente
                //uploadImage
                );

    }



    private void registerAtencion(final String desparacitacion,  final String antirabica, final String sextuple,
                              final String octuple, final String triplefelina, final String leucemiafelina, final String rutdoctor, final String rutasistente/*, final String uploadImage*/ ) {
        // Tag used to cancel the request
        String cancel_req_tag = "registeratencion";

        progressDialog.setMessage("Atencion ...");
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
                                AtencionActivity.this,
                                UserActivity.class);
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
                params.put("desparacitacion", desparacitacion);
                params.put("antirabica", antirabica);
                params.put("sextuple", sextuple);
                params.put("octuple", octuple);
                params.put("triplefelina", triplefelina);
                params.put("leucemiafelina", leucemiafelina);
                params.put("id_mascota", nombreMascota);
                params.put("rut_doctor", rutdoctor);
                params.put("rut_asistente", rutasistente);
                //params.put("imagen", uploadImage);
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
