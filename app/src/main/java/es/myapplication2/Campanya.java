package es.myapplication2;


/**
 * Created by casa on 10/06/2015.
 */
public class Campanya {

    private int id;
    private String nombre;
    private String detalleCamp;
    private int idProd;
    private int idProv;
    private double precio;
    private int qDisp;
    private int qReservada;
    private String fechaInicio;
    private String fechaFin;
    private String urlImg;

    public Campanya() {
    }

    public String getUrlImg() {
        return urlImg;
    }

    public void setUrlImg(String urlImg) {
        this.urlImg = urlImg;
    }

    private String nombreProv;

    public String getNombreProv() {
        return nombreProv;
    }

    public void setNombreProv(String nombreProv) {
        this.nombreProv = nombreProv;
    }

    private Producto producto;
    private Provincia provincia;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDetalleCamp() {
        return detalleCamp;
    }

    public void setDetalleCamp(String detalleCamp) {
        this.detalleCamp = detalleCamp;
    }

    public int getIdProd() {
        return idProd;
    }

    public void setIdProd(int idProd) {
        this.idProd = idProd;
    }

    public int getIdProv() {
        return idProv;
    }

    public void setIdProv(int idProv) {
        this.idProv = idProv;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getqDisp() {
        return qDisp;
    }

    public void setqDisp(int qDisp) {
        this.qDisp = qDisp;
    }

    public int getqReservada() {
        return qReservada;
    }

    public void setqReservada(int qReservada) {
        this.qReservada = qReservada;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Provincia getProvincia() {
        return provincia;
    }

    public void setProvincia(Provincia provincia) {
        this.provincia = provincia;
    }

    //private String
/*
    Date date = new Date();
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    private String fecha = dateFormat.format(date); //te devuelve la fecha en string con el formato dd/MM/yyyy

*/

}