package Model;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 *
 * @author landr
 */
public class Mensaje implements Serializable {

    private int idMensaje;
    private int idProyecto;
    private int idUsuario;
    private String mensaje;
    private Timestamp fechaEnvio;
    private String nombreUsuario;

    public Mensaje() {
    }

    public Mensaje(int idProyecto, int idUsuario, String mensaje) {
        this.idProyecto = idProyecto;
        this.idUsuario = idUsuario;
        this.mensaje = mensaje;
    }

    public int getIdMensaje() {
        return idMensaje;
    }

    public void setIdMensaje(int idMensaje) {
        this.idMensaje = idMensaje;
    }

    public int getIdProyecto() {
        return idProyecto;
    }

    public void setIdProyecto(int idProyecto) {
        this.idProyecto = idProyecto;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public Timestamp getFechaEnvio() {
        return fechaEnvio;
    }

    public void setFechaEnvio(Timestamp fechaEnvio) {
        this.fechaEnvio = fechaEnvio;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }
}
