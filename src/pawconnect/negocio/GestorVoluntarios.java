package pawconnect.negocio;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import pawconnect.modelo.SolicitudVoluntariado;
import pawconnect.modelo.TipoActividad;
import pawconnect.modelo.Voluntario;
import pawconnect.modelo.Zona;

public class GestorVoluntarios {

    private ArrayList<Voluntario> voluntarios;
    private ArrayList<SolicitudVoluntariado> solicitudes;
    private int contadorSolicitudes;

    public GestorVoluntarios() {
        voluntarios = new ArrayList<Voluntario>();
        solicitudes = new ArrayList<SolicitudVoluntariado>();
        contadorSolicitudes = 0;
    }

    public ArrayList<Voluntario> darVoluntarios() {
        return voluntarios;
    }

    public ArrayList<SolicitudVoluntariado> darSolicitudes() {
        return solicitudes;
    }

    public void establecerVoluntarios( ArrayList<Voluntario> lista ) {
        this.voluntarios = lista;
    }

    public void establecerSolicitudes( ArrayList<SolicitudVoluntariado> lista ) {
        this.solicitudes = lista;
        contadorSolicitudes = 0;
        for ( SolicitudVoluntariado s : solicitudes ) {
            String soloDigitos = s.darId().replaceAll( "[^0-9]", "" );
            try {
                int numero = Integer.parseInt( soloDigitos );
                if ( numero > contadorSolicitudes ) {
                    contadorSolicitudes = numero;
                }
            } catch ( NumberFormatException e ) {
                // Si el identificador no sigue el formato esperado, se ignora para el conteo.
            }
        }
    }

    public Voluntario buscarVoluntario( String cedula ) {
        Voluntario encontrado = null;
        for ( Voluntario v : voluntarios ) {
            if ( v.darCedula().equals( cedula ) ) {
                encontrado = v;
            }
        }
        return encontrado;
    }

    public SolicitudVoluntariado buscarSolicitud( String id ) {
        SolicitudVoluntariado encontrada = null;
        for ( SolicitudVoluntariado s : solicitudes ) {
            if ( s.darId().equals( id ) ) {
                encontrada = s;
            }
        }
        return encontrada;
    }

    public void registrarVoluntario( String cedula, String nombre, String correo, String clave,
                                      Zona zona, int disponibilidadHorasSemana, boolean tieneExperiencia ) throws Exception {
        if ( cedula == null || cedula.trim().isEmpty() ) {
            throw new Exception( "La cedula no puede estar vacia." );
        }
        if ( !ValidadorDatos.esCedulaValida( cedula ) ) {
            throw new Exception( "La cedula debe contener exactamente 10 digitos numericos." );
        }
        if ( nombre == null || !ValidadorDatos.esNombreValido( nombre ) ) {
            throw new Exception( "El nombre solo puede contener letras y espacios." );
        }
        if ( !ValidadorDatos.esCorreoValido( correo ) ) {
            throw new Exception( "Ingresa un correo electronico valido (ejemplo: usuario@gmail.com)." );
        }
        if ( buscarVoluntario( cedula ) != null ) {
            throw new Exception( "Ya existe un voluntario registrado con esa cedula." );
        }
        if ( disponibilidadHorasSemana <= 0 ) {
            throw new Exception( "La disponibilidad de horas debe ser mayor a cero." );
        }

        Voluntario nuevo = new Voluntario( cedula, nombre, correo, clave, zona,
                disponibilidadHorasSemana, tieneExperiencia );
        voluntarios.add( nuevo );
    }

    public Voluntario iniciarSesion( String cedula, String clave ) throws Exception {
        Voluntario v = buscarVoluntario( cedula );
        if ( v == null ) {
            throw new Exception( "No existe un voluntario registrado con esa cedula." );
        }
        if ( !v.darClave().equals( clave ) ) {
            throw new Exception( "La clave ingresada es incorrecta." );
        }
        return v;
    }

    public SolicitudVoluntariado publicarSolicitud( String cedulaFundacion, String descripcion,
                                                      Zona zonaRequerida, TipoActividad tipoActividad,
                                                      int horasMinimasSemana, boolean experienciaRequerida,
                                                      String fechaInicioTexto, String fechaFinTexto ) throws Exception {
        if ( descripcion == null || descripcion.trim().isEmpty() ) {
            throw new Exception( "La descripcion de la solicitud no puede estar vacia." );
        }
        if ( horasMinimasSemana <= 0 ) {
            throw new Exception( "Las horas minimas por semana deben ser mayores a cero." );
        }

        LocalDate fechaInicio;
        LocalDate fechaFin;
        try {
            fechaInicio = LocalDate.parse( fechaInicioTexto.trim() );
        } catch ( DateTimeParseException e ) {
            throw new Exception( "La fecha de inicio debe tener el formato aaaa-mm-dd." );
        }
        try {
            fechaFin = LocalDate.parse( fechaFinTexto.trim() );
        } catch ( DateTimeParseException e ) {
            throw new Exception( "La fecha de finalizacion debe tener el formato aaaa-mm-dd." );
        }
        if ( fechaFin.isBefore( fechaInicio ) ) {
            throw new Exception( "La fecha de finalizacion no puede ser anterior a la fecha de inicio." );
        }
        if ( fechaFin.isBefore( LocalDate.now() ) ) {
            throw new Exception( "La fecha de finalizacion no puede ser anterior a hoy." );
        }

        contadorSolicitudes++;
        String id = String.valueOf( contadorSolicitudes );
        SolicitudVoluntariado nueva = new SolicitudVoluntariado( id, cedulaFundacion, descripcion,
                zonaRequerida, tipoActividad, horasMinimasSemana, experienciaRequerida, fechaInicio, fechaFin );
        solicitudes.add( nueva );
        return nueva;
    }

    public void aplicarASolicitud( String idSolicitud, String cedulaVoluntario ) throws Exception {
        SolicitudVoluntariado s = buscarSolicitud( idSolicitud );
        if ( s == null ) {
            throw new Exception( "No existe una solicitud con ese identificador." );
        }
        Voluntario v = buscarVoluntario( cedulaVoluntario );
        if ( v == null ) {
            throw new Exception( "No existe un voluntario con esa cedula." );
        }
        s.aplicar( v );
    }

    public void responderPostulante( String idSolicitud, String cedulaVoluntario, boolean aceptar ) throws Exception {
        SolicitudVoluntariado s = buscarSolicitud( idSolicitud );
        if ( s == null ) {
            throw new Exception( "No existe una solicitud con ese identificador." );
        }
        if ( aceptar ) {
            s.aceptarPostulante( cedulaVoluntario );
        } else {
            s.rechazarPostulante( cedulaVoluntario );
        }
    }

    public ArrayList<Voluntario> listarPostulantes( String idSolicitud ) throws Exception {
        SolicitudVoluntariado s = buscarSolicitud( idSolicitud );
        if ( s == null ) {
            throw new Exception( "No existe una solicitud con ese identificador." );
        }
        ArrayList<Voluntario> lista = new ArrayList<Voluntario>();
        for ( String cedula : s.darPostulantes() ) {
            Voluntario v = buscarVoluntario( cedula );
            if ( v != null ) {
                lista.add( v );
            }
        }
        return lista;
    }

    public void acumularHoras( String cedulaVoluntario, TipoActividad tipo, double horas ) throws Exception {
        Voluntario v = buscarVoluntario( cedulaVoluntario );
        if ( v == null ) {
            throw new Exception( "No existe un voluntario con esa cedula." );
        }
        v.acumularHoras( tipo, horas );
    }

    public ArrayList<SolicitudVoluntariado> listarSolicitudesVisiblesPara( String cedulaVoluntario ) {
        ArrayList<SolicitudVoluntariado> lista = new ArrayList<SolicitudVoluntariado>();
        for ( SolicitudVoluntariado s : solicitudes ) {
            boolean estaDisponible = s.darEstado() != SolicitudVoluntariado.EstadoSolicitud.CERRADO;
            boolean yaAplico = s.yaAplico( cedulaVoluntario );
            if ( estaDisponible || yaAplico ) {
                lista.add( s );
            }
        }
        return lista;
    }

    /**
     * Filtra las solicitudes visibles para un voluntario combinando zona y tipo de actividad.
     * Cualquiera de los dos puede ser null para no aplicar ese filtro.
     */
    public ArrayList<SolicitudVoluntariado> filtrarSolicitudes( String cedulaVoluntario, Zona zona, TipoActividad tipo ) {
        ArrayList<SolicitudVoluntariado> resultado = new ArrayList<SolicitudVoluntariado>();
        for ( SolicitudVoluntariado s : listarSolicitudesVisiblesPara( cedulaVoluntario ) ) {
            boolean cumple = true;
            if ( zona != null && s.darZonaRequerida() != zona ) {
                cumple = false;
            }
            if ( tipo != null && s.darTipoActividad() != tipo ) {
                cumple = false;
            }
            if ( cumple ) {
                resultado.add( s );
            }
        }
        return resultado;
    }

    public void eliminarSolicitud( String idSolicitud, String cedulaFundacion ) throws Exception {
        SolicitudVoluntariado s = buscarSolicitud( idSolicitud );
        if ( s == null ) {
            throw new Exception( "No existe una solicitud con ese identificador." );
        }
        if ( !s.darCedulaFundacion().equals( cedulaFundacion ) ) {
            throw new Exception( "Esta solicitud no pertenece a su fundacion." );
        }
        if ( s.darEstado() == SolicitudVoluntariado.EstadoSolicitud.CERRADO ) {
            throw new Exception( "No se puede eliminar una solicitud que ya esta cerrada." );
        }
        solicitudes.remove( s );
    }

    public ArrayList<SolicitudVoluntariado> listarSolicitudesPorFundacion( String cedulaFundacion ) {
        ArrayList<SolicitudVoluntariado> lista = new ArrayList<SolicitudVoluntariado>();
        for ( SolicitudVoluntariado s : solicitudes ) {
            if ( s.darCedulaFundacion().equals( cedulaFundacion ) ) {
                lista.add( s );
            }
        }
        return lista;
    }
}
