package pawconnect.negocio;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import pawconnect.modelo.Campana;
import pawconnect.modelo.Donacion;
import pawconnect.modelo.Usuario;

public class GestorDonaciones {

    private ArrayList<Donacion> donaciones;
    private ArrayList<Campana> campanas;
    private int contadorDonaciones;
    private int contadorCampanas;

    public GestorDonaciones() {
        donaciones = new ArrayList<Donacion>();
        campanas = new ArrayList<Campana>();
        contadorDonaciones = 0;
        contadorCampanas = 0;
    }

    public ArrayList<Donacion> darDonaciones() {
        return donaciones;
    }

    public ArrayList<Campana> darCampanas() {
        return campanas;
    }

    public void establecerDonaciones( ArrayList<Donacion> lista ) {
        this.donaciones = lista;
        contadorDonaciones = 0;
        for ( Donacion d : donaciones ) {
            int numero = extraerNumero( d.darId(), "DON" );
            if ( numero > contadorDonaciones ) {
                contadorDonaciones = numero;
            }
        }
    }

    public void establecerCampanas( ArrayList<Campana> lista ) {
        this.campanas = lista;
        contadorCampanas = 0;
        for ( Campana c : campanas ) {
            int numero = extraerNumero( c.darId(), "CAMP" );
            if ( numero > contadorCampanas ) {
                contadorCampanas = numero;
            }
        }
    }

    private int extraerNumero( String id, String prefijo ) {
        try {
            return Integer.parseInt( id.replace( prefijo, "" ) );
        } catch ( NumberFormatException e ) {
            return 0;
        }
    }

    public Campana buscarCampana( String id ) {
        Campana encontrada = null;
        for ( Campana c : campanas ) {
            if ( c.darId().equals( id ) ) {
                encontrada = c;
            }
        }
        return encontrada;
    }

    public Campana crearCampana( String cedulaFundacion, String nombre, String descripcion, double montoMeta,
                                  String fechaInicioTexto, String fechaFinTexto ) throws Exception {
        if ( nombre == null || nombre.trim().isEmpty() ) {
            throw new Exception( "El nombre de la campana no puede estar vacio." );
        }
        if ( montoMeta <= 0 ) {
            throw new Exception( "El monto meta debe ser mayor a cero." );
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
            throw new Exception( "La fecha limite debe tener el formato aaaa-mm-dd." );
        }
        if ( fechaFin.isBefore( fechaInicio ) ) {
            throw new Exception( "La fecha limite no puede ser anterior a la fecha de inicio." );
        }
        if ( fechaFin.isBefore( LocalDate.now() ) ) {
            throw new Exception( "La fecha limite no puede ser anterior a hoy." );
        }

        contadorCampanas++;
        String id = "CAMP" + contadorCampanas;
        Campana nueva = new Campana( id, cedulaFundacion, nombre, descripcion, montoMeta, fechaInicio, fechaFin );
        campanas.add( nueva );
        return nueva;
    }

    public void extenderCampana( String idCampana, String cedulaFundacion, String nuevaFechaTexto ) throws Exception {
        Campana c = buscarCampana( idCampana );
        if ( c == null ) {
            throw new Exception( "No existe una campana con ese identificador." );
        }
        if ( !c.darCedulaFundacion().equals( cedulaFundacion ) ) {
            throw new Exception( "Esta campana no pertenece a su fundacion." );
        }
        LocalDate nuevaFecha;
        try {
            nuevaFecha = LocalDate.parse( nuevaFechaTexto.trim() );
        } catch ( DateTimeParseException e ) {
            throw new Exception( "La nueva fecha limite debe tener el formato aaaa-mm-dd." );
        }
        c.extenderFecha( nuevaFecha );
    }

    public void eliminarCampana( String idCampana, String cedulaFundacion ) throws Exception {
        Campana c = buscarCampana( idCampana );
        if ( c == null ) {
            throw new Exception( "No existe una campana con ese identificador." );
        }
        if ( !c.darCedulaFundacion().equals( cedulaFundacion ) ) {
            throw new Exception( "Esta campana no pertenece a su fundacion." );
        }
        campanas.remove( c );
    }

    public ArrayList<Campana> listarCampanasPorFundacion( String cedulaFundacion ) {
        ArrayList<Campana> lista = new ArrayList<Campana>();
        for ( Campana c : campanas ) {
            if ( c.darCedulaFundacion().equals( cedulaFundacion ) ) {
                lista.add( c );
            }
        }
        return lista;
    }

    /**
     * Campanas disponibles para recibir donaciones: activas o vencidas sin meta en las que
     * la fundacion decidio seguir recaudando. Solo se excluyen las ya completadas.
     */
    public ArrayList<Campana> listarCampanasDisponiblesParaDonar() {
        ArrayList<Campana> lista = new ArrayList<Campana>();
        for ( Campana c : campanas ) {
            if ( c.darEstado() != Campana.EstadoCampana.COMPLETADA ) {
                lista.add( c );
            }
        }
        return lista;
    }

    public void registrarDonacion( Usuario usuario, String cedulaFundacion, double monto,
                                    String concepto, String idCampana ) throws Exception {
        if ( monto <= 0 ) {
            throw new Exception( "El monto de la donacion debe ser mayor a cero." );
        }
        if ( idCampana != null && !idCampana.trim().isEmpty() ) {
            Campana c = buscarCampana( idCampana );
            if ( c == null ) {
                throw new Exception( "No existe una campana con ese identificador." );
            }
            c.agregarDonacion( monto );
        }

        usuario.registrarDonacion( monto );
        contadorDonaciones++;
        String id = "DON" + contadorDonaciones;
        Donacion nueva = new Donacion( id, usuario.darCedula(), cedulaFundacion, monto, concepto, idCampana );
        donaciones.add( nueva );
    }

    public double calcularTotalRecaudadoPorFundacion( String cedulaFundacion ) {
        double total = 0;
        for ( Donacion d : donaciones ) {
            if ( d.darCedulaFundacion().equals( cedulaFundacion ) ) {
                total += d.darMonto();
            }
        }
        return total;
    }
}
