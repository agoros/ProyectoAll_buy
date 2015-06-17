package es.myapplication2;

/**
 * Created by casa on 10/06/2015.
 */
public class Producto {

    private int id;
    private int idCategoria;
    private String NombreProd;

    public Producto() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getNombreProd() {
        return NombreProd;
    }

    public void setNombreProd(String nombreProd) {
        NombreProd = nombreProd;
    }
}
