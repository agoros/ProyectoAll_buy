package es.myapplication2;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private Spinner comboProvincias;
    private TextView lblMensaje;
    private GridView grdDatosArt;
    private Button buscar;




    // /////////////////////////////////////////////////////////////////////////
    static ArrayList<Campanya> listaCampanyas;
    Activity a;
    Context context;
    JSONArray camps;
    private final String URLllistarCamp = "http://cascas.esy.es/llistarCamp.php";
    private final String urlFiltra= "http://cascas.esy.es/filtraCamp.php";

    EditText textoBuscar;

///////////////////////////////////////////////////////////////////////////////////////////////



    @SuppressLint("InlinedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        listaCampanyas = new ArrayList<Campanya>();
        a = this;
        context = getApplicationContext();
        lblMensaje = (TextView) findViewById(R.id.textView6);
        grdDatosArt = (GridView) findViewById(R.id.GridArticulos);
        comboProvincias = (Spinner) findViewById(R.id.CmbOpciones);
        buscar = (Button) findViewById(R.id.button);
        buscar.requestFocus();



        ///carga combo provincias
        ArrayAdapter<CharSequence> adapterPr =  ArrayAdapter.createFromResource(this, R.array.array_prov,
                        android.R.layout.simple_spinner_item);
        comboProvincias.setAdapter(adapterPr);



        //evento que captura selección del Grid
        grdDatosArt.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> parent, android.view.View v, int position, long id) {

                        lblMensaje.setText("Seleccionado: " + listaCampanyas.get(position).getId());
                        //Creamos el Intent
                        Intent intent = new Intent(MainActivity.this, DetalleArtActivity.class);

                        //Creamos la información a pasar entre actividades
                        Bundle b = new Bundle();
                        b.putString("urlImg", listaCampanyas.get(position).getUrlImg());
                        b.putInt("id", listaCampanyas.get(position).getId());
                        b.putString("nombre", listaCampanyas.get(position).getNombre());
                        b.putString("NombreProv", listaCampanyas.get(position).getNombreProv());
                        b.putDouble("precio", listaCampanyas.get(position).getPrecio());
                        b.putString("fechaFin", listaCampanyas.get(position).getFechaFin());
                        b.putString("detalleCamp", listaCampanyas.get(position).getDetalleCamp());



                        //Añadimos la información al intent
                        finish();
                        intent.putExtras(b);

                        //Iniciamos la nueva actividad
                        startActivity(intent);

                    }
                });
        //evento pulsa button BUSCAR
        buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                //new GetContacts(listaa).execute();
                textoBuscar = (EditText)findViewById(R.id.TxtImagenHintBuscar);
                //String txtBuscar = textoBuscar.getText().toString();

                //String prov = comboProvincias.getSelectedItem().toString();

                // Building Parameters
                //List params = new ArrayList();
                //params.add(new BasicNameValuePair("urlFiltra", urlFiltra));
                //params.add(new BasicNameValuePair("txtBuscar", txtBuscar));
                //params.add(new BasicNameValuePair("prov", prov));
                grdDatosArt.setAdapter(null);
                new GetAllCampanyasFiltr(grdDatosArt).execute();

                //Toast.makeText(MainActivity.this, txtBuscar+" "+comboProvincias.getSelectedItem().toString(), Toast.LENGTH_LONG).show();
            }
        });


      new GetAllCampanyas(grdDatosArt).execute();
    }

        private class GetAllCampanyas extends AsyncTask<Void, Void, Void> {
            GridView listaArt;
            private ProgressDialog pDialog;



            public GetAllCampanyas(GridView grdDatosArt) {
                this.listaArt = grdDatosArt;
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                pDialog = new ProgressDialog(MainActivity.this);
                pDialog.setMessage("Cargando datos ...");
                pDialog.setIndeterminate(false);
                pDialog.setCancelable(true);
                pDialog.show();
            }

            @Override
            protected Void doInBackground(Void... urls) {
             //   String urldisplay = datos[0];
            //    String txtBuscar= datos[1];
            //    String prv = datos[2];
            //    List<NameValuePair> params = null;
               // params.add(new BasicNameValuePair("txtBuscar", txtBuscar));
                //params.add(new BasicNameValuePair("prv", prv));


                // CREAMOS LA INSTANCIA DE LA CLASE
                JSONParser sh = new JSONParser();

                //original
                String jsonStr = sh.makeServiceCall(URLllistarCamp, JSONParser.GET);
               // String jsonStr = sh.makeServiceCall2(URLllistarCamp); FUNCIONAAAAAA

                if (jsonStr != null) {
                    try {
                        JSONObject jsonObj = new JSONObject(jsonStr);

                        // Getting JSON Array node
                        camps = jsonObj.getJSONArray("campanyas");

                        // looping through All Equipos
                        for (int i = 0; i < camps.length(); i++) {
                            JSONObject c = camps.getJSONObject(i);

                            int id = c.getInt("id");
                            String nombre = c.getString("nombre");

                            double precio = c.getDouble("precio");
                            String provincia = c.getString("NombreProv");
                            String urlImg = c.getString("urlImg");

                            String detalleCamp= c.getString("detalleCamp");
                            String fechafin= c.getString("fechaFin");

                            Campanya e = new Campanya();
                            e.setId(id);
                            e.setNombre(nombre);
                            e.setPrecio(precio);
                            e.setNombreProv(provincia);
                            e.setUrlImg(urlImg);
                            e.setDetalleCamp(detalleCamp);
                            e.setFechaFin(fechafin);

                            listaCampanyas.add(e);


                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    Log.e("ServiceHandler", "Esta habiendo problemas para cargar el JSON");
                    Toast.makeText(MainActivity.this, "No existen campañas con los criterios indicados", Toast.LENGTH_LONG).show();
                }

                return null;
            }

            protected void onPostExecute(Void result) {
                super.onPostExecute(result);
                // Dismiss the progress dialog
                if (pDialog.isShowing()) {
                    pDialog.dismiss();
                }
                new CargarListTask().execute();
            }
        }


    private class GetAllCampanyasFiltr extends AsyncTask<String, Void, Void> {
        GridView listaArt;
        private ProgressDialog pDialog;



        public GetAllCampanyasFiltr(GridView grdDatosArt) {
            this.listaArt = grdDatosArt;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Cargando datos ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(String... datos) {
            listaCampanyas.clear();
            String txtBus = textoBuscar.getText().toString();
            String provi = comboProvincias.getSelectedItem().toString();

            List <NameValuePair>params = new ArrayList();
                params.add(new BasicNameValuePair("txtbuscar", txtBus.toUpperCase()));
                params.add(new BasicNameValuePair("provi", provi));


            // CREAMOS LA INSTANCIA DE LA CLASE
            JSONParser sh = new JSONParser();

            //original
            String jsonStr = sh.makeServiceCall(urlFiltra, JSONParser.GET,params);
            // String jsonStr = sh.makeServiceCall2(URLllistarCamp); FUNCIONAAAAAA

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    camps = jsonObj.getJSONArray("campanyas");

                    // looping through All Equipos
                    for (int i = 0; i < camps.length(); i++) {
                        JSONObject c = camps.getJSONObject(i);

                        int id = c.getInt("id");
                        String nombre = c.getString("nombre");

                        double precio = c.getDouble("precio");
                        String provincia = c.getString("NombreProv");
                        String urlImg = c.getString("urlImg");

                        String detalleCamp= c.getString("detalleCamp");
                        String fechafin= c.getString("fechaFin");

                        Campanya e = new Campanya();
                        e.setId(id);
                        e.setNombre(nombre);
                        e.setPrecio(precio);
                        e.setNombreProv(provincia);
                        e.setUrlImg(urlImg);
                        e.setDetalleCamp(detalleCamp);
                        e.setFechaFin(fechafin);

                        listaCampanyas.add(e);


                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Log.e("ServiceHandler", "Esta habiendo problemas para cargar el JSON");

            }

            return null;
        }

        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing()) {
                pDialog.dismiss();
            }
            new CargarListTask().execute();
        }
    }



            class CargarListTask extends AsyncTask<Void, String, AdaptadorLista> {
                @Override
                protected void onPreExecute() {
                    // TODO Auto-generated method stub
                    super.onPreExecute();
                }

                protected AdaptadorLista doInBackground(Void... arg0) {
                    // TODO Auto-generated method stub

                    try {

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }

                    AdaptadorLista adaptador = null;
//                    adaptador.clear();
                    adaptador = new AdaptadorLista(a, listaCampanyas);


                    return adaptador;
                }

                @Override
                protected void onPostExecute(AdaptadorLista result) {
                    // TODO Auto-generated method stub
                    super.onPostExecute(result);

                    grdDatosArt.setAdapter(result);

                }


            }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
      /*  int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
*/
        switch (item.getItemId()) {
            case R.id.action_nuevo:
                Log.i("ActionBar", "Nuevo!");
                finish();
                Intent n = new Intent(this, AltaArt.class);
                startActivity(n);
                return true;
            case R.id.action_buscar:
                Log.i("ActionBar", "Buscar!");;
                finish();
                Intent b = new Intent(this, MainActivity.class);
                startActivity(b);
                return true;
            case R.id.action_settings:
                Log.i("ActionBar", "Settings!");;
                return true;

            case R.id.salir:
                Log.i("ActionBar", "Settings!");;
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

        //return super.onOptionsItemSelected(item);
    }




}

