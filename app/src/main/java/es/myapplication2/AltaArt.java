package es.myapplication2;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by casa on 17/06/2015.
 */
public class AltaArt extends AppCompatActivity implements OnItemSelectedListener {


    ProgressDialog pDialog;
    JSONParser jsonParser = new JSONParser();
    private final String urlAltaCamp= "http://cascas.esy.es/altaCamp.php";
    private static final String TAG_SUCCESS = "success";
    private Spinner spProvincias, spLocalidades;
    private String valorCateg, valorProd;
    private EditText eTxtdetalleCamp, eTxtPrecio, eTxtqDisp, eTxtUrl;
    private Spinner comboProvincias;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alta_art);

        this.spProvincias = (Spinner) findViewById(R.id.sp_provincia);
        this.spLocalidades = (Spinner) findViewById(R.id.sp_localidad);
        this.comboProvincias = (Spinner) findViewById(R.id.prv);


        loadSpinnerProvincias();

    }

    /**
     * Populate the Spinner.
     */
    private void loadSpinnerProvincias() {

        ///carga combo provincias
        ArrayAdapter<CharSequence> adapterPr =  ArrayAdapter.createFromResource(this, R.array.array_prov2,
                android.R.layout.simple_spinner_item);
        comboProvincias.setAdapter(adapterPr);


        // Create an ArrayAdapter using the string array and a default spinner
        // layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.categorias, android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        this.spProvincias.setAdapter(adapter);

        // This activity implements the AdapterView.OnItemSelectedListener
        this.spProvincias.setOnItemSelectedListener(this);
        this.spLocalidades.setOnItemSelectedListener(this);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos,
                               long id) {

        switch (parent.getId()) {
            case R.id.sp_provincia:

                // Retrieves an array
                TypedArray arrayLocalidades = getResources().obtainTypedArray(
                        R.array.array_categ_a_prod);
                CharSequence[] localidades = arrayLocalidades.getTextArray(pos);
                arrayLocalidades.recycle();

                // Create an ArrayAdapter using the string array and a default
                // spinner layout
                ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(
                        this, android.R.layout.simple_spinner_item,
                        android.R.id.text1, localidades);

                // Specify the layout to use when the list of choices appears
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                // Apply the adapter to the spinner
                this.spLocalidades.setAdapter(adapter);

                valorCateg = spProvincias.getSelectedItem().toString();
                valorProd = spLocalidades.getSelectedItem().toString();


                break;

            case R.id.sp_localidad:

                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // Callback method to be invoked when the selection disappears from this
        // view. The selection can disappear for instance when touch is
        // activated or when the adapter becomes empty.
    }

    /**
     * Shows the selected strings from spinners.
     *
     * @param v The view that was clicked.
     */
    public void showLocalidadSelected(View v) {
       /* Toast.makeText(
                getApplicationContext(),
                getString(R.string.message, spLocalidades.getSelectedItem()
                        .toString(), spProvincias.getSelectedItem().toString()),
                Toast.LENGTH_LONG).show();

    */
        eTxtdetalleCamp = (EditText) findViewById(R.id.eTxtdetalleCamp);
        eTxtPrecio = (EditText) findViewById(R.id.eTxtPrecio);
        eTxtqDisp = (EditText) findViewById(R.id.eTxtqDisp);
        eTxtUrl = (EditText) findViewById(R.id.eTxtUrl);

        new AltaArticulo().execute();
    }

    private class AltaArticulo extends AsyncTask<String, Void, Void> {

        private ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(AltaArt.this);
            pDialog.setMessage("Creando ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        protected Void doInBackground(String... args) {
            //valorCateg, valorProd
            String nomCamp = valorProd.substring(2);
            String detCamp = String.valueOf(eTxtdetalleCamp.getText());
            String idProd = valorProd.substring(0, 1);
            String prv = comboProvincias.getSelectedItem().toString().substring(0, 2);
            String precio = String.valueOf(eTxtPrecio.getText());

            String qdisp = String.valueOf(eTxtqDisp.getText());
            String urlArt = String.valueOf(eTxtUrl.getText());

            //String idCa = valorCateg.substring(0, 1);
            //String nomCat = valorCateg.substring(2);



            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("nomCamp", nomCamp));
            params.add(new BasicNameValuePair("detCamp", detCamp));
            params.add(new BasicNameValuePair("idProd", idProd));
            params.add(new BasicNameValuePair("prv", prv));
            params.add(new BasicNameValuePair("precio", precio));
            params.add(new BasicNameValuePair("qdisp", qdisp));
            params.add(new BasicNameValuePair("urlArt", urlArt));

            // getting JSON Object
            // Note that create product url accepts POST method
            JSONObject json = jsonParser.makeHttpRequest(urlAltaCamp, "POST", params);

            // check log cat fro response
            Log.d("Create Response", json.toString());

            // check for success tag
            try {
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    // successfully created product

                    //Toast Toas1 = Toast.makeText(getApplicationContext(), "Creada correctamente", Toast.LENGTH_SHORT);
                    //Toas1.show();
                    //Toast.makeText(MainActivity.this, "No existen campañas con los criterios indicados", Toast.LENGTH_LONG).show();
                    //Toast.makeText(Register.this, file_url, Toast.LENGTH_LONG).show();
                   // finish();
                    //return json.;

                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    finish();

                    startActivity(i);


                } else {
                    // failed to create product
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }


        protected void onPostExecute(String file_url) {
            // dismiss the dialog once done
            pDialog.dismiss();
           //Toast.makeText(DetalleArtActivity.this, "Participaci�n registrada", Toast.LENGTH_SHORT).show();
           // new ActualizarCamps().execute();



        }

    }




        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            // Inflate the menu; this adds items to the action bar if it is present.
            getMenuInflater().inflate(R.menu.menu_alta_art, menu);
            return true;
        }
    }

