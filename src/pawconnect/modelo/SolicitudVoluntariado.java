package pawconnect.modelo;

import java.time.LocalDate;
import java.util.ArrayList;

public class SolicitudVoluntariado {

    public enum EstadoSolicitud {
        ABIERTO, EN_PROCESO, CERRADO
    }

    private String id;
    private String cedulaFundacion;
    private String descripcion;
    private Zona zonaRequerida;
    private TipoActividad tipoActividad;
    private int horasMinimasSemana;
    private boolean experienciaRequerida;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private ArrayList<String> postulantes;
    private ArrayList<String> aceptados;
    private ArrayList<String> rechazados;

    public SolicitudVoluntariado( String id, String cedulaFundacion, String descripcion,
                                   Zona zonaRequerida, TipoActividad tipoActividad, int horasMinimasSemana,
                                   boolean experienciaRequerida, LocalDate fechaInicio, LocalDate fechaFin ) {
        this.id = id;
        this.cedulaFundacion = cedulaFundacion;
        this.descripcion = descripcion;
        this.zonaRequerida = zonaRequerida;
        this.tipoActividad = tipoActividad;
        this.horasMinimasSemana = horasMinimasSemana;
        this.experienciaRequerida = experienciaRequerida;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.postulantes = new ArrayList<String>();
        this.aceptados = new ArrayList<String>();
        this.rechazados = new ArrayList<String>();
    }

    public TipoActividad darTipoActividad() {
        return tipoActividad;
    }

    public String darId() {
        return id;
    }

    public String darCedulaFundacion() {
        return cedulaFundacion;
    }

    public String darDescripcion() {
        return descripcion;
    }

    public Zona darZonaRequerida() {
        return zonaRequerida;
    }

    public int darHorasMinimasSemana() {
        return horasMinimasSemana;
    }

    public boolean darExperienciaRequerida() {
        return experienciaRequerida;
    }

    public LocalDate darFechaInicio() {
        return fechaInicio;
    }

    public LocalDate darFechaFin() {
        return fechaFin;
    }

    public ArrayList<String> darPostulantes() {
        return postulantes;
    }

    public ArrayList<String> darAceptados() {
        return aceptados;
    }

    public ArrayList<String> darRechazados() {
        return rechazados;
    }

    public EstadoSolicitud darEstado() {
        boolean fechaVencida = LocalDate.now().isAfter( fechaFin );
        if ( !aceptados.isEmpty() || fechaVencida ) {
            return EstadoSolicitud.CERRADO;
        } else if ( !postulantes.isEmpty() ) {
            return EstadoSolicitud.EN_PROCESO;
        } else {
            return EstadoSolicitud.ABIERTO;
        }
    }

    public boolean yaAplico( String cedula ) {
        return postulantes.contains( cedula ) || aceptados.contains( cedula ) || rechazados.contains( cedula );
    }

    public void aplicar( Voluntario voluntario ) throws Exception {
        if ( darEstado() == EstadoSolicitud.CERRADO ) {
            throw new Exception( "Esta solicitud de voluntariado ya esta cerrada y no acepta mas postulantes." );
        }
        String cedula = voluntario.darCedula();
        if ( yaAplico( cedula ) ) {
            throw new Exception( "Ya aplicaste a esta solicitud de voluntariado." );
        }
        postulantes.add( cedula );
    }

    public void aceptarPostulante( String cedula ) throws Exception {
        if ( !postulantes.contains( cedula ) ) {
            throw new Exception( "Este voluntario no se encuentra en la lista de postulantes pendientes." );
        }
        postulantes.remove( cedula );
        aceptados.add( cedula );

        ArrayList<String> restantes = new ArrayList<String>( postulantes );
        for ( String cedulaRestante : restantes ) {
            postulantes.remove( cedulaRestante );
            rechazados.add( cedulaRestante );
        }
    }

    public void rechazarPostulante( String cedula ) throws Exception {
        if ( !postulantes.contains( cedula ) ) {
            throw new Exception( "Este voluntario no se encuentra en la lista de postulantes pendientes." );
        }
        postulantes.remove( cedula );
        rechazados.add( cedula );
    }

    public String darEstadoDe( String cedula ) {
        if ( aceptados.contains( cedula ) ) {
            return "Aceptado";
        } else if ( rechazados.contains( cedula ) ) {
            return "Rechazado";
        } else if ( postulantes.contains( cedula ) ) {
            return "Pendiente";
        } else {
            return null;
        }
    }

    @Override
    public String toString() {
        return id + " - " + descripcion + " (" + tipoActividad + " - " + zonaRequerida + ") - " + fechaInicio
                + " al " + fechaFin + " - [" + darEstado() + "]";
    }
}
