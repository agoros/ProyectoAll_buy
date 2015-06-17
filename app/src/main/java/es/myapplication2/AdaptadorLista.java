package es.myapplication2;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.apache.http.NameValuePair;

import java.io.InputStream;
import java.net.MalformedURLException;

import java.net.URL;
import java.util.ArrayList;

/**
 * Created by casa on 11/06/2015.
 */
public class AdaptadorLista extends ArrayAdapter {

    // Hacemos que nuestra clase herede las características de un ArrayAdapter

    Activity context;
    protected ArrayList<Campanya> items;

    //private NameValuePair[] datos;
    /* Creamos las variables necesarias para capturar el contexto
    *  y los datos que se publicarán en la lista
    */

    public AdaptadorLista(Activity context,ArrayList<Campanya> items) {

        super(context,R.layout.lista_tema,items);
        this.context=context;

        //this.items = null;

        this.items=items;
        // TODO Auto-generated constructor stub
    }
    //CUENTA LOS ELEMENTOS
    @Override
    public int getCount() {

        return items.size();
    }
    //DEVUELVE UN OBJETO DE UNA DETERMINADA POSICION
    @Override
    public Object getItem(int arg0) {
        return items.get(arg0);
    }



    /* Constructor de la clase, donde pasamos por parámetro los datos
     * a mostrar en la lista y el contexto
    */
    public View getView(int position, View convertView, ViewGroup parent)
    {
        // SE GENERA UN CONVERTVIEW POR MOTIVOS DE EFICIENCIA DE MEMORIA
        //ES UN NIVEL MAS BAJO DE VISTA, PARA QUE OCUPEN MENOS MEMORIA LAS
        View v = convertView;

        if(convertView == null){
            LayoutInflater inf = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inf.inflate(R.layout.lista_tema, null);
        }

        Campanya dir = items.get(position);
        //RELLENAMOS LA IMAGEN Y EL TEXTO
        //IMAGEN
        ImageView img = (ImageView) v.findViewById(R.id.ImagenArea1);
        if(img != null) {
            new LoadImage(img).execute(dir.getUrlImg());


        }
        //CAMPOS
        TextView id = (TextView) v.findViewById(R.id.id);
        id.setText("Id: "+Integer.toString(dir.getId()));

        TextView nombre = (TextView) v.findViewById(R.id.nombre);
        nombre.setText("Producto: "+dir.getNombre());

        TextView precio = (TextView) v.findViewById(R.id.precio);
        precio.setText("Precio: "+String.valueOf(dir.getPrecio())+" euros");

        TextView provincia = (TextView) v.findViewById(R.id.provincia);
        provincia.setText("Provincia: "+dir.getNombreProv());

        // DEVOLVEMOS VISTA
        return v;


    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }
    class LoadImage extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public LoadImage(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        @Override
        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];

            URL url_value = null;
            try {
                url_value = new URL(urldisplay);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            Bitmap mIcon11 = null;
            try {
               //Icon11 = BitmapFactory.decodeStream((InputStream) new URL(urldisplay).getContent());


                mIcon11 = BitmapFactory.decodeStream(url_value.openConnection().getInputStream());

            } catch (Exception e) {
                //Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }

}
