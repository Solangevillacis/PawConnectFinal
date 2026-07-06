package pawconnect.modelo;

import java.util.ArrayList;

public class Usuario extends Cuenta implements IDonante {

    public static final double INGRESO_ALTO = 800.0;
    public static final double INGRESO_MEDIO = 400.0;
    public static final double MONTO_FRECUENTE = 50.0;
    public static final double MONTO_VIP = 200.0;

    private String telefono;
    private String direccion;
    private double ingresosMensuales;
    private boolean tienePatio;
    private boolean tieneExperiencia;
    private String tipoVivienda;
    private int tamanoFamilia;
    private double horasLibresDia;
    private double totalDonado;
    private ArrayList<String> mascotasInteres;
    private ArrayList<String> notificaciones;

    public Usuario( String cedula, String nombre, String correo, String clave, String telefono, String direccion,
                    double ingresosMensuales, boolean tienePatio, boolean tieneExperiencia,
                    String tipoVivienda, int tamanoFamilia, double horasLibresDia ) {
        super( cedula, nombre, correo, clave );
        this.telefono = telefono;
        this.direccion = direccion;
        this.ingresosMensuales = ingresosMensuales;
        this.tienePatio = tienePatio;
        this.tieneExperiencia = tieneExperiencia;
        this.tipoVivienda = tipoVivienda;
        this.tamanoFamilia = tamanoFamilia;
        this.horasLibresDia = horasLibresDia;
        this.totalDonado = 0;
        this.mascotasInteres = new ArrayList<String>();
        this.notificaciones = new ArrayList<String>();
    }

    public String darTelefono() {
        return telefono;
    }

    public String darDireccion() {
        return direccion;
    }

    public double darIngresosMensuales() {
        return ingresosMensuales;
    }

    public boolean darTienePatio() {
        return tienePatio;
    }

    public boolean darTieneExperiencia() {
        return tieneExperiencia;
    }

    public String darTipoVivienda() {
        return tipoVivienda;
    }

    public int darTamanoFamilia() {
        return tamanoFamilia;
    }

    public double darHorasLibresDia() {
        return horasLibresDia;
    }

    public double darTotalDonado() {
        return totalDonado;
    }

    public ArrayList<String> darMascotasInteres() {
        return mascotasInteres;
    }

    public ArrayList<String> darNotificaciones() {
        return notificaciones;
    }

    public void agregarNotificacion( String mensaje ) {
        notificaciones.add( mensaje );
    }

    public void limpiarNotificaciones() {
        notificaciones.clear();
    }

    public int calcularPuntajeAdoptante() {
        int puntaje = 0;

        if ( ingresosMensuales >= INGRESO_ALTO ) {
            puntaje += 25;
        } else if ( ingresosMensuales >= INGRESO_MEDIO ) {
            puntaje += 15;
        }

        if ( tienePatio ) {
            puntaje += 25;
        }

        if ( tieneExperiencia ) {
            puntaje += 20;
        }

        if ( horasLibresDia >= 4 ) {
            puntaje += 20;
        } else if ( horasLibresDia >= 2 ) {
            puntaje += 10;
        }

        if ( tamanoFamilia <= 4 ) {
            puntaje += 10;
        }

        return puntaje;
    }

    @Override
    public String darCategoriaDonante() {
        if ( totalDonado >= MONTO_VIP ) {
            return "VIP";
        } else if ( totalDonado >= MONTO_FRECUENTE ) {
            return "Frecuente";
        } else {
            return "Nuevo";
        }
    }

    public void registrarDonacion( double monto ) throws Exception {
        if ( monto <= 0 ) {
            throw new Exception( "El monto de la donación debe ser mayor a cero." );
        }
        totalDonado += monto;
    }

    public void agregarInteres( String idMascota ) throws Exception {
        if ( mascotasInteres.contains( idMascota ) ) {
            throw new Exception( "Ya mostraste interés en esta mascota anteriormente." );
        }
        mascotasInteres.add( idMascota );
    }

    @Override
    public String mostrarPerfil() {
        return "=== Perfil de Usuario ===" +
               "\nNombre: " + darNombre() +
               "\nCédula: " + darCedula() +
               "\nCorreo: " + darCorreo() +
               "\nTeléfono: " + telefono +
               "\nDirección: " + direccion +
               "\nVivienda: " + tipoVivienda +
               "\nTiene patio: " + ( tienePatio ? "Sí" : "No" ) +
               "\nExperiencia con mascotas: " + ( tieneExperiencia ? "Sí" : "No" ) +
               "\nHoras libres al día: " + horasLibresDia +
               "\nTamaño de familia: " + tamanoFamilia +
               "\nPuntaje como adoptante: " + calcularPuntajeAdoptante() + "/100" +
               "\nCategoría donante: " + darCategoriaDonante() +
               "\nTotal donado: $" + totalDonado;
    }
}
