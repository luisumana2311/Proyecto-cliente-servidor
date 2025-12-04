package Model;

import java.io.Serializable;
import java.sql.Date;

public class Sprint implements Serializable {

    private Integer idSprint;
    private Integer idProyecto;
    private String nombre;
    private Integer numeroSprint;
    private Date fechaInicio;
    private Date fechaFin;

    public Sprint() {
    }

    public Sprint(String nombre, String fechaInicio, String fechaFin, int idUsuario, int idProyecto) {
        this.nombre = nombre;
        this.idProyecto = idProyecto;
        try {
            if (fechaInicio != null && !fechaInicio.isEmpty()) {
                this.fechaInicio = Date.valueOf(fechaInicio);
            }
            if (fechaFin != null && !fechaFin.isEmpty()) {
                this.fechaFin = Date.valueOf(fechaFin);
            }
        } catch (IllegalArgumentException e) {
            this.fechaInicio = null;
            this.fechaFin = null;
        }
    }

    public Integer getIdSprint() {
        return idSprint;
    }

    public void setIdSprint(Integer idSprint) {
        this.idSprint = idSprint;
    }

    public Integer getIdProyecto() {
        return idProyecto;
    }

    public void setIdProyecto(Integer idProyecto) {
        this.idProyecto = idProyecto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getNumeroSprint() {
        return numeroSprint;
    }

    public void setNumeroSprint(Integer numeroSprint) {
        this.numeroSprint = numeroSprint;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        try {
            if (fechaInicio != null && !fechaInicio.isEmpty()) {
                this.fechaInicio = Date.valueOf(fechaInicio);
            }
        } catch (IllegalArgumentException e) {
            this.fechaInicio = null;
        }
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        try {
            if (fechaFin != null && !fechaFin.isEmpty()) {
                this.fechaFin = Date.valueOf(fechaFin);
            }
        } catch (IllegalArgumentException e) {
            this.fechaFin = null;
        }
    }
}
