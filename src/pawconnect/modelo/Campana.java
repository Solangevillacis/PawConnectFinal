package pawconnect.modelo;

import java.time.LocalDate;

public class Campana {

    public enum EstadoCampana {
        ACTIVA, COMPLETADA, NO_LOGRADA
    }

    private String id;
    private String cedulaFundacion;
    private String nombre;
    private String descripcion;
    private double montoMeta;
    private double montoRecaudado;
    private LocalDate fechaInicio;
    private LocalDate fechaFinLimite;

    public Campana( String id, String cedulaFundacion, String nombre, String descripcion,
                     double montoMeta, LocalDate fechaInicio, LocalDate fechaFinLimite ) {
        this.id = id;
        this.cedulaFundacion = cedulaFundacion;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.montoMeta = montoMeta;
        this.montoRecaudado = 0;
        this.fechaInicio = fechaInicio;
        this.fechaFinLimite = fechaFinLimite;
    }

    public String darId() {
        return id;
    }

    public String darCedulaFundacion() {
        return cedulaFundacion;
    }

    public String darNombre() {
        return nombre;
    }

    public String darDescripcion() {
        return descripcion;
    }

    public double darMontoMeta() {
        return montoMeta;
    }

    public double darMontoRecaudado() {
        return montoRecaudado;
    }

    public LocalDate darFechaInicio() {
        return fechaInicio;
    }

    public LocalDate darFechaFin() {
        return fechaFinLimite;
    }

    public double darPorcentajeAlcanzado() {
        return ( montoRecaudado / montoMeta ) * 100;
    }

    public EstadoCampana darEstado() {
        if ( montoRecaudado >= montoMeta ) {
            return EstadoCampana.COMPLETADA;
        } else if ( LocalDate.now().isAfter( fechaFinLimite ) ) {
            return EstadoCampana.NO_LOGRADA;
        } else {
            return EstadoCampana.ACTIVA;
        }
    }

    public String darMensajeEstado() {
        EstadoCampana estado = darEstado();
        if ( estado == EstadoCampana.COMPLETADA ) {
            return "Campaña completada con éxito.";
        } else if ( estado == EstadoCampana.NO_LOGRADA ) {
            return "No se logró alcanzar el objetivo.";
        } else {
            return "Campaña en curso.";
        }
    }

    public void agregarDonacion( double monto ) throws Exception {
        if ( darEstado() == EstadoCampana.COMPLETADA ) {
            throw new Exception( "Esta campana ya fue completada, no se aceptan mas donaciones." );
        }
        if ( monto <= 0 ) {
            throw new Exception( "El monto debe ser mayor a cero." );
        }
        montoRecaudado += monto;
    }

    public void extenderFecha( LocalDate nuevaFecha ) throws Exception {
        if ( !nuevaFecha.isAfter( fechaFinLimite ) ) {
            throw new Exception( "La nueva fecha limite debe ser posterior a la fecha limite actual." );
        }
        fechaFinLimite = nuevaFecha;
    }

    @Override
    public String toString() {
        return nombre + " - $" + montoRecaudado + "/$" + montoMeta + " (" + String.format( "%.1f", darPorcentajeAlcanzado() )
                + "%) - Limite: " + fechaFinLimite + " - [" + darEstado() + "]";
    }
}
