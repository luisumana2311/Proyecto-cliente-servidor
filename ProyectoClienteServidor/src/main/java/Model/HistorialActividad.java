package Model;

import java.sql.Timestamp;
import java.io.Serializable;

/**
 *
 * @author landr
 */
public class HistorialActividad implements Serializable {

    private int idHistorial;
    private int idUsuario;
    private int idTarea;
    private Timestamp fechaHora;
    private String accion;
    private String nombreUsuario;
    private String nombreTarea;

    public HistorialActividad() {
    }

    public HistorialActividad(int idUsuario, int idTarea, String accion) {
        this.idUsuario = idUsuario;
        this.idTarea = idTarea;
        this.accion = accion;
    }

    public int getIdHistorial() {
        return idHistorial;
    }

    public void setIdHistorial(int idHistorial) {
        this.idHistorial = idHistorial;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getIdTarea() {
        return idTarea;
    }

    public void setIdTarea(int idTarea) {
        this.idTarea = idTarea;
    }

    public Timestamp getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(Timestamp fechaHora) {
        this.fechaHora = fechaHora;
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getNombreTarea() {
        return nombreTarea;
    }

    public void setNombreTarea(String nombreTarea) {
        this.nombreTarea = nombreTarea;
    }
}
