package Client;

import java.io.Serializable;

/**
 *
 * @author landr
 */
public class Notificacion implements Serializable {

    private String tipo;
    private int idProyecto;
    private int idSprint;
    private int idTarea;
    private Object data;

    public Notificacion() {
    }

    public Notificacion(String tipo, int idProyecto, int idSprint) {
        this.tipo = tipo;
        this.idProyecto = idProyecto;
        this.idSprint = idSprint;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getIdProyecto() {
        return idProyecto;
    }

    public void setIdProyecto(int idProyecto) {
        this.idProyecto = idProyecto;
    }

    public int getIdSprint() {
        return idSprint;
    }

    public void setIdSprint(int idSprint) {
        this.idSprint = idSprint;
    }

    public int getIdTarea() {
        return idTarea;
    }

    public void setIdTarea(int idTarea) {
        this.idTarea = idTarea;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
