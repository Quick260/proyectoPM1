package Pojo;

public class archivo {
    int id_archivo;

    public int getId_archivo() {
        return id_archivo;
    }

    public void setId_archivo(int id_archivo) {
        this.id_archivo = id_archivo;
    }

    String nomObra;
    String tipoArt;

    String fecha;
    public int dia;
    public int mes;
    public int anio;

    public int getDia() {
        return dia;
    }

    public void setDia(int dia) {
        this.dia = dia;
    }

    public int getMes() {
        return mes;
    }

    public void setMes(int mes) {
        this.mes = mes;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    String usuario;
    String descripcion;
    String path;



    public String getNomObra() {
        return nomObra;
    }

    public void setNomObra(String nomObra) {
        this.nomObra = nomObra;
    }

    public String getTipoArt() {
        return tipoArt;
    }

    public void setTipoArt(String tipoArt) {
        this.tipoArt = tipoArt;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
