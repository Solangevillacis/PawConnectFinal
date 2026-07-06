package pawconnect.persistencia;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import pawconnect.modelo.Campana;
import pawconnect.modelo.Donacion;
import pawconnect.modelo.Fundacion;
import pawconnect.modelo.Mascota;
import pawconnect.modelo.SolicitudVoluntariado;
import pawconnect.modelo.TipoActividad;
import pawconnect.modelo.Usuario;
import pawconnect.modelo.Voluntario;
import pawconnect.modelo.Zona;

public class ManejadorArchivos {

    public static final String CARPETA_DATOS = "datos";
    public static final String ARCHIVO_USUARIOS = CARPETA_DATOS + File.separator + "usuarios.txt";
    public static final String ARCHIVO_VOLUNTARIOS = CARPETA_DATOS + File.separator + "voluntarios.txt";
    public static final String ARCHIVO_FUNDACIONES = CARPETA_DATOS + File.separator + "fundaciones.txt";
    public static final String ARCHIVO_MASCOTAS = CARPETA_DATOS + File.separator + "mascotas.txt";
    public static final String ARCHIVO_DONACIONES = CARPETA_DATOS + File.separator + "donaciones.txt";
    public static final String ARCHIVO_CAMPANAS = CARPETA_DATOS + File.separator + "campanas.txt";
    public static final String ARCHIVO_SOLICITUDES = CARPETA_DATOS + File.separator + "solicitudes.txt";

    private static void asegurarCarpeta() {
        File carpeta = new File( CARPETA_DATOS );
        if ( !carpeta.exists() ) {
            carpeta.mkdirs();
        }
    }

    private static String unirLista( ArrayList<String> lista, String separador ) {
        String texto = "";
        for ( int i = 0; i < lista.size(); i++ ) {
            texto += lista.get( i );
            if ( i < lista.size() - 1 ) {
                texto += separador;
            }
        }
        return texto;
    }

    // ----------------------------------------------------------------
    // Usuarios
    // ----------------------------------------------------------------

    public static void guardarUsuarios( ArrayList<Usuario> usuarios ) throws Exception {
        asegurarCarpeta();
        PrintWriter escritor = new PrintWriter( new FileWriter( ARCHIVO_USUARIOS ) );
        for ( Usuario u : usuarios ) {
            String notificacionesTexto = unirLista( u.darNotificaciones(), "~" );
            escritor.println( u.darCedula() + "|" + u.darNombre() + "|" + u.darCorreo() + "|" + u.darClave() + "|"
                    + u.darTelefono() + "|" + u.darDireccion() + "|" + u.darIngresosMensuales() + "|"
                    + u.darTienePatio() + "|" + u.darTieneExperiencia() + "|" + u.darTipoVivienda() + "|"
                    + u.darTamanoFamilia() + "|" + u.darHorasLibresDia() + "|" + u.darTotalDonado() + "|"
                    + notificacionesTexto );
        }
        escritor.close();
    }

    public static ArrayList<Usuario> cargarUsuarios() throws Exception {
        ArrayList<Usuario> usuarios = new ArrayList<Usuario>();
        File archivo = new File( ARCHIVO_USUARIOS );
        if ( !archivo.exists() ) {
            return usuarios;
        }
        BufferedReader lector = new BufferedReader( new FileReader( archivo ) );
        String linea = lector.readLine();
        while ( linea != null ) {
            String[] datos = linea.split( "\\|", -1 );
            Usuario u = new Usuario( datos[0], datos[1], datos[2], datos[3], datos[4], datos[5],
                    Double.parseDouble( datos[6] ), Boolean.parseBoolean( datos[7] ),
                    Boolean.parseBoolean( datos[8] ), datos[9], Integer.parseInt( datos[10] ),
                    Double.parseDouble( datos[11] ) );
            double totalDonado = Double.parseDouble( datos[12] );
            if ( totalDonado > 0 ) {
                u.registrarDonacion( totalDonado );
            }
            if ( datos.length > 13 && !datos[13].isEmpty() ) {
                for ( String mensaje : datos[13].split( "~" ) ) {
                    u.agregarNotificacion( mensaje );
                }
            }
            usuarios.add( u );
            linea = lector.readLine();
        }
        lector.close();
        return usuarios;
    }

    // ----------------------------------------------------------------
    // Voluntarios
    // ----------------------------------------------------------------

    public static void guardarVoluntarios( ArrayList<Voluntario> voluntarios ) throws Exception {
        asegurarCarpeta();
        PrintWriter escritor = new PrintWriter( new FileWriter( ARCHIVO_VOLUNTARIOS ) );
        for ( Voluntario v : voluntarios ) {
            String horasPorTipoTexto = "";
            for ( TipoActividad tipo : TipoActividad.values() ) {
                horasPorTipoTexto += tipo + ":" + v.darHorasDeTipo( tipo );
                horasPorTipoTexto += ",";
            }
            escritor.println( v.darCedula() + "|" + v.darNombre() + "|" + v.darCorreo() + "|" + v.darClave() + "|"
                    + v.darZona() + "|" + v.darDisponibilidadHorasSemana() + "|" + v.darTieneExperiencia() + "|"
                    + horasPorTipoTexto );
        }
        escritor.close();
    }

    public static ArrayList<Voluntario> cargarVoluntarios() throws Exception {
        ArrayList<Voluntario> voluntarios = new ArrayList<Voluntario>();
        File archivo = new File( ARCHIVO_VOLUNTARIOS );
        if ( !archivo.exists() ) {
            return voluntarios;
        }
        BufferedReader lector = new BufferedReader( new FileReader( archivo ) );
        String linea = lector.readLine();
        while ( linea != null ) {
            String[] datos = linea.split( "\\|", -1 );
            Voluntario v = new Voluntario( datos[0], datos[1], datos[2], datos[3],
                    Zona.valueOf( datos[4] ), Integer.parseInt( datos[5] ), Boolean.parseBoolean( datos[6] ) );
            if ( datos.length > 7 && !datos[7].isEmpty() ) {
                String[] pares = datos[7].split( "," );
                for ( String par : pares ) {
                    if ( par.trim().isEmpty() ) {
                        continue;
                    }
                    String[] partes = par.split( ":" );
                    TipoActividad tipo = TipoActividad.valueOf( partes[0] );
                    double horas = Double.parseDouble( partes[1] );
                    if ( horas > 0 ) {
                        v.acumularHoras( tipo, horas );
                    }
                }
            }
            voluntarios.add( v );
            linea = lector.readLine();
        }
        lector.close();
        return voluntarios;
    }

    // ----------------------------------------------------------------
    // Fundaciones
    // ----------------------------------------------------------------

    public static void guardarFundaciones( ArrayList<Fundacion> fundaciones ) throws Exception {
        asegurarCarpeta();
        PrintWriter escritor = new PrintWriter( new FileWriter( ARCHIVO_FUNDACIONES ) );
        for ( Fundacion f : fundaciones ) {
            escritor.println( f.darCedula() + "|" + f.darNombre() + "|" + f.darCorreo() + "|" + f.darClave() + "|"
                    + f.darDireccion() + "|" + f.darCausa() + "|" + f.darAdopcionesExitosas() );
        }
        escritor.close();
    }

    public static ArrayList<Fundacion> cargarFundaciones() throws Exception {
        ArrayList<Fundacion> fundaciones = new ArrayList<Fundacion>();
        File archivo = new File( ARCHIVO_FUNDACIONES );
        if ( !archivo.exists() ) {
            return fundaciones;
        }
        BufferedReader lector = new BufferedReader( new FileReader( archivo ) );
        String linea = lector.readLine();
        while ( linea != null ) {
            String[] datos = linea.split( "\\|" );
            Fundacion f = new Fundacion( datos[0], datos[1], datos[2], datos[3], datos[4], datos[5] );
            int adopciones = Integer.parseInt( datos[6] );
            for ( int i = 0; i < adopciones; i++ ) {
                f.registrarAdopcionExitosa();
            }
            fundaciones.add( f );
            linea = lector.readLine();
        }
        lector.close();
        return fundaciones;
    }

    // ----------------------------------------------------------------
    // Mascotas
    // ----------------------------------------------------------------

    public static void guardarMascotas( ArrayList<Mascota> mascotas ) throws Exception {
        asegurarCarpeta();
        PrintWriter escritor = new PrintWriter( new FileWriter( ARCHIVO_MASCOTAS ) );
        for ( Mascota m : mascotas ) {
            String interesadosTexto = unirLista( m.darInteresados(), "," );
            escritor.println( m.darId() + "|" + m.darNombre() + "|" + m.darEspecie() + "|" + m.darRaza() + "|"
                    + m.darSexo() + "|" + m.darValorEdad() + "|" + m.darUnidadEdad() + "|" + m.darTamano() + "|"
                    + m.darEstadoSalud() + "|" + m.darCedulaFundacion() + "|" + m.darDiasEnEspera() + "|"
                    + m.darEstado() + "|" + interesadosTexto );
        }
        escritor.close();
    }

    public static ArrayList<Mascota> cargarMascotas() throws Exception {
        ArrayList<Mascota> mascotas = new ArrayList<Mascota>();
        File archivo = new File( ARCHIVO_MASCOTAS );
        if ( !archivo.exists() ) {
            return mascotas;
        }
        BufferedReader lector = new BufferedReader( new FileReader( archivo ) );
        String linea = lector.readLine();
        while ( linea != null ) {
            String[] datos = linea.split( "\\|", -1 );
            Mascota m = new Mascota( datos[0], datos[1], Mascota.Especie.valueOf( datos[2] ), datos[3],
                    Mascota.Sexo.valueOf( datos[4] ), Integer.parseInt( datos[5] ),
                    Mascota.UnidadEdad.valueOf( datos[6] ), Mascota.Tamano.valueOf( datos[7] ), datos[8], datos[9] );
            m.aumentarDiasEnEspera( Integer.parseInt( datos[10] ) );
            if ( datos.length > 12 && !datos[12].isEmpty() ) {
                for ( String cedula : datos[12].split( "," ) ) {
                    m.agregarInteresado( cedula );
                }
            }
            if ( datos[11].equals( "ADOPTADA" ) ) {
                m.marcarAdoptada();
            }
            mascotas.add( m );
            linea = lector.readLine();
        }
        lector.close();
        return mascotas;
    }

    // ----------------------------------------------------------------
    // Donaciones
    // ----------------------------------------------------------------

    public static void guardarDonaciones( ArrayList<Donacion> donaciones ) throws Exception {
        asegurarCarpeta();
        PrintWriter escritor = new PrintWriter( new FileWriter( ARCHIVO_DONACIONES ) );
        for ( Donacion d : donaciones ) {
            String idCampana = d.darIdCampana() == null ? "" : d.darIdCampana();
            escritor.println( d.darId() + "|" + d.darCedulaUsuario() + "|" + d.darCedulaFundacion() + "|"
                    + d.darMonto() + "|" + d.darConcepto() + "|" + idCampana );
        }
        escritor.close();
    }

    public static ArrayList<Donacion> cargarDonaciones() throws Exception {
        ArrayList<Donacion> donaciones = new ArrayList<Donacion>();
        File archivo = new File( ARCHIVO_DONACIONES );
        if ( !archivo.exists() ) {
            return donaciones;
        }
        BufferedReader lector = new BufferedReader( new FileReader( archivo ) );
        String linea = lector.readLine();
        while ( linea != null ) {
            String[] datos = linea.split( "\\|", -1 );
            String idCampana = datos[5].isEmpty() ? null : datos[5];
            Donacion d = new Donacion( datos[0], datos[1], datos[2], Double.parseDouble( datos[3] ),
                    datos[4], idCampana );
            donaciones.add( d );
            linea = lector.readLine();
        }
        lector.close();
        return donaciones;
    }

    // ----------------------------------------------------------------
    // Campanas
    // ----------------------------------------------------------------

    public static void guardarCampanas( ArrayList<Campana> campanas ) throws Exception {
        asegurarCarpeta();
        PrintWriter escritor = new PrintWriter( new FileWriter( ARCHIVO_CAMPANAS ) );
        for ( Campana c : campanas ) {
            escritor.println( c.darId() + "|" + c.darCedulaFundacion() + "|" + c.darNombre() + "|"
                    + c.darDescripcion() + "|" + c.darMontoMeta() + "|" + c.darMontoRecaudado() + "|"
                    + c.darFechaInicio() + "|" + c.darFechaFin() );
        }
        escritor.close();
    }

    public static ArrayList<Campana> cargarCampanas() throws Exception {
        ArrayList<Campana> campanas = new ArrayList<Campana>();
        File archivo = new File( ARCHIVO_CAMPANAS );
        if ( !archivo.exists() ) {
            return campanas;
        }
        BufferedReader lector = new BufferedReader( new FileReader( archivo ) );
        String linea = lector.readLine();
        while ( linea != null ) {
            String[] datos = linea.split( "\\|", -1 );
            LocalDate fechaInicio;
            LocalDate fechaFin;
            if ( datos.length > 7 && !datos[6].isEmpty() && !datos[7].isEmpty() ) {
                fechaInicio = LocalDate.parse( datos[6] );
                fechaFin = LocalDate.parse( datos[7] );
            } else {
                fechaInicio = LocalDate.now();
                fechaFin = LocalDate.now().plusMonths( 1 );
            }
            Campana c = new Campana( datos[0], datos[1], datos[2], datos[3], Double.parseDouble( datos[4] ),
                    fechaInicio, fechaFin );
            double recaudado = Double.parseDouble( datos[5] );
            if ( recaudado > 0 ) {
                c.agregarDonacion( recaudado );
            }
            campanas.add( c );
            linea = lector.readLine();
        }
        lector.close();
        return campanas;
    }

    // ----------------------------------------------------------------
    // Solicitudes de voluntariado
    // ----------------------------------------------------------------

    public static void guardarSolicitudes( ArrayList<SolicitudVoluntariado> solicitudes ) throws Exception {
        asegurarCarpeta();
        PrintWriter escritor = new PrintWriter( new FileWriter( ARCHIVO_SOLICITUDES ) );
        for ( SolicitudVoluntariado s : solicitudes ) {
            escritor.println( s.darId() + "|" + s.darCedulaFundacion() + "|" + s.darDescripcion() + "|"
                    + s.darZonaRequerida() + "|" + s.darTipoActividad() + "|" + s.darHorasMinimasSemana() + "|"
                    + s.darExperienciaRequerida() + "|" + s.darFechaInicio() + "|" + s.darFechaFin() + "|"
                    + unirLista( s.darPostulantes(), "," ) + "|" + unirLista( s.darAceptados(), "," ) + "|"
                    + unirLista( s.darRechazados(), "," ) );
        }
        escritor.close();
    }

    public static ArrayList<SolicitudVoluntariado> cargarSolicitudes() throws Exception {
        ArrayList<SolicitudVoluntariado> solicitudes = new ArrayList<SolicitudVoluntariado>();
        File archivo = new File( ARCHIVO_SOLICITUDES );
        if ( !archivo.exists() ) {
            return solicitudes;
        }
        BufferedReader lector = new BufferedReader( new FileReader( archivo ) );
        String linea = lector.readLine();
        while ( linea != null ) {
            String[] datos = linea.split( "\\|", -1 );
            TipoActividad tipo = TipoActividad.valueOf( datos[4] );
            LocalDate fechaInicio = LocalDate.parse( datos[7] );
            LocalDate fechaFin = LocalDate.parse( datos[8] );
            SolicitudVoluntariado s = new SolicitudVoluntariado( datos[0], datos[1], datos[2],
                    Zona.valueOf( datos[3] ), tipo, Integer.parseInt( datos[5] ), Boolean.parseBoolean( datos[6] ),
                    fechaInicio, fechaFin );
            if ( datos.length > 9 && !datos[9].isEmpty() ) {
                for ( String cedula : datos[9].split( "," ) ) {
                    s.darPostulantes().add( cedula );
                }
            }
            if ( datos.length > 10 && !datos[10].isEmpty() ) {
                for ( String cedula : datos[10].split( "," ) ) {
                    s.darAceptados().add( cedula );
                }
            }
            if ( datos.length > 11 && !datos[11].isEmpty() ) {
                for ( String cedula : datos[11].split( "," ) ) {
                    s.darRechazados().add( cedula );
                }
            }
            solicitudes.add( s );
            linea = lector.readLine();
        }
        lector.close();
        return solicitudes;
    }
}
