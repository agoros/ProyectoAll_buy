package es.myapplication2;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by casa on 14/06/2015.
 */
public class DetalleArtActivity extends AppCompatActivity {

    ProgressDialog pDialog;
    JSONParser jsonParser = new JSONParser();
    Bitmap bitmap;
    ImageView img;
    private int idCampanya;
    private TextView txtViewProd, txtViewProv, txtViewPrecio, txtViewFin, txtViewDetCamp;
    private ImageView imgView;
    private Button btnParticipa;
    private int idUsuario;
    private final String urlAltaCampUsu= "http://cascas.esy.es/AltaCampUsuario.php";
    private final String urlActualizaCamps= "http://cascas.esy.es/ActualizaCamps.php";


    private static final String TAG_SUCCESS = "success";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detalle_art);

        //Localizar los controles
        imgView = (ImageView) findViewById(R.id.iView2);
        btnParticipa = (Button) findViewById(R.id.button2Participa);


        txtViewProd = (TextView) findViewById(R.id.txtViewProd);
        txtViewProv = (TextView) findViewById(R.id.txtViewProv);
        txtViewFin = (TextView) findViewById(R.id.txtViewFin);
        txtViewPrecio = (TextView) findViewById(R.id.txtViewPrecio);

        txtViewDetCamp = (TextView) findViewById(R.id.txtViewDetallCamp);

        //Recuperamos la información pasada en el intent
        Bundle bundle = this.getIntent().getExtras();

        //IMAGEN
        new LoadImage(imgView).execute(bundle.getString("urlImg"));

        //new LoadImage(img).execute(dir.getUrlImg());

        //Construimos el mensaje a mostrar
        //imgView.setImageBitmap(bundle.getI);


        txtViewProd.setText("PRODUCTO: " + bundle.getString("nombre"));
        txtViewPrecio.setText("PRECIO: " + Double.toString(bundle.getDouble("precio")) + " euros");
        txtViewProv.setText("PROVINCIA: " + bundle.getString("NombreProv"));
        txtViewFin.setText("FINALIZA: " + bundle.getString("fechaFin"));
        txtViewDetCamp.setText(bundle.getString("detalleCamp"));

        //obtengo ID campanya para update BBDD
        idCampanya = bundle.getInt("id");


        //evento pulsa button PARTICIPA
        btnParticipa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                //new GetContacts(listaa).execute();
                //Recuperamos las preferencias almacenadas
                //SharedPreferences prefs = getSharedPreferences("sp", Context.MODE_PRIVATE);

                // obtengo preferencias compartidas app: ID usuario
                SharedPreferences prefs = getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
                idUsuario = prefs.getInt("id", 111);

                new AltaCampUsuario().execute();


                //Toast.makeText(DetalleArtActivity.this, usuario+" "+id, Toast.LENGTH_LONG).show();
                //String pass = prefs.getString("pass", "");

                //nombre usuario


            }
        });
    }
    class AltaCampUsuario extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(DetalleArtActivity.this);
            pDialog.setMessage("Guardando participación...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        /**
         * guardo participación en campanyaUsuario
         */
        protected String doInBackground(String... args) {
            String idCamp = Integer.toString(idCampanya);
            String idUsu = Integer.toString(idUsuario);


            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("idCamp", idCamp));
            params.add(new BasicNameValuePair("idUsu", idUsu));

            // getting JSON Object
            // Note that create product url accepts POST method
            JSONObject json = jsonParser.makeHttpRequest(urlAltaCampUsu, "POST", params);

            // check log cat fro response
            Log.d("Create Response", json.toString());

            // check for success tag
            try {
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    // successfully created product
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
            Toast.makeText(DetalleArtActivity.this, "Participación registrada", Toast.LENGTH_SHORT).show();
            new ActualizarCamps().execute();

        }


    }

    class ActualizarCamps extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
        }

        protected String doInBackground(String... arg0) {
            // TODO Auto-generated method stub

            String idCamp = Integer.toString(idCampanya);

            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("idCamp", idCamp));

            // getting JSON Object
            // Note that create product url accepts POST method
            JSONObject json = jsonParser.makeHttpRequest(urlActualizaCamps, "POST", params);

            // check log cat fro response
            Log.d("Create Response", json.toString());

            // check for success tag
            try {
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    // successfully created product
                    // Intent i = new Intent(getApplicationContext(), MainActivity.class);
                   // finish();

                    //startActivity(i);


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
            //pDialog.dismiss();
            //Toast.makeText(DetalleArtActivity.this, "Participación registrada", Toast.LENGTH_SHORT).show();
            //new ActualizarCamps().execute();

        }
    }




        private class LoadImage extends AsyncTask<String, String, Bitmap> {
        ImageView bmImage;

        public LoadImage(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(DetalleArtActivity.this);
            pDialog.setMessage("Loading Image ....");
            pDialog.show();

        }
        protected Bitmap doInBackground(String... args) {
            try {
                bitmap = BitmapFactory.decodeStream((InputStream) new URL(args[0]).getContent());

            } catch (Exception e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        protected void onPostExecute(Bitmap image) {

            if(image != null){
                imgView.setImageBitmap(image);
                pDialog.dismiss();

            }else{

                pDialog.dismiss();
                Toast.makeText(DetalleArtActivity.this, "Image Does Not exist or Network Error", Toast.LENGTH_SHORT).show();

            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_atras, menu);
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
            case R.id.action_atras:
                Log.i("ActionBar", "Nuevo!");
                finish();
                Intent nuevaActivity = new Intent(this, MainActivity.class);
                startActivity(nuevaActivity);
                return true;
            case R.id.action_buscar:
                Log.i("ActionBar", "Buscar!");;
                return true;

            case R.id.action_settings:
                Log.i("ActionBar", "Settings!");;
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

        //return super.onOptionsItemSelected(item);
    }





}
