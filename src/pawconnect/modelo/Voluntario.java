package pawconnect.modelo;

import java.util.HashMap;

public class Voluntario extends Cuenta {

    public enum Nivel {
        NUEVO, ACTIVO, EXPERTO
    }

    public static final double HORAS_NIVEL_ACTIVO = 30.0;
    public static final double HORAS_NIVEL_EXPERTO = 100.0;

    private Zona zona;
    private int disponibilidadHorasSemana;
    private boolean tieneExperiencia;
    private HashMap<TipoActividad, Double> horasPorTipo;
    private double horasAcumuladas;
    private Nivel nivel;

    public Voluntario( String cedula, String nombre, String correo, String clave,
                        Zona zona, int disponibilidadHorasSemana, boolean tieneExperiencia ) {
        super( cedula, nombre, correo, clave );
        this.zona = zona;
        this.disponibilidadHorasSemana = disponibilidadHorasSemana;
        this.tieneExperiencia = tieneExperiencia;
        this.horasPorTipo = new HashMap<TipoActividad, Double>();
        for ( TipoActividad tipo : TipoActividad.values() ) {
            horasPorTipo.put( tipo, 0.0 );
        }
        this.horasAcumuladas = 0;
        this.nivel = Nivel.NUEVO;
    }

    public Zona darZona() {
        return zona;
    }

    public int darDisponibilidadHorasSemana() {
        return disponibilidadHorasSemana;
    }

    public boolean darTieneExperiencia() {
        return tieneExperiencia;
    }

    public HashMap<TipoActividad, Double> darHorasPorTipo() {
        return horasPorTipo;
    }

    public double darHorasDeTipo( TipoActividad tipo ) {
        Double horas = horasPorTipo.get( tipo );
        return horas == null ? 0.0 : horas;
    }

    public double darHorasAcumuladas() {
        return horasAcumuladas;
    }

    public Nivel darNivel() {
        return nivel;
    }

    public void acumularHoras( TipoActividad tipo, double horas ) throws Exception {
        if ( horas <= 0 ) {
            throw new Exception( "Las horas a acumular deben ser mayores a cero." );
        }
        double actual = darHorasDeTipo( tipo );
        horasPorTipo.put( tipo, actual + horas );
        horasAcumuladas += horas;
        actualizarNivel();
    }

    private void actualizarNivel() {
        if ( horasAcumuladas >= HORAS_NIVEL_EXPERTO ) {
            nivel = Nivel.EXPERTO;
        } else if ( horasAcumuladas >= HORAS_NIVEL_ACTIVO ) {
            nivel = Nivel.ACTIVO;
        } else {
            nivel = Nivel.NUEVO;
        }
    }

    public boolean cumpleRequisitos( Zona zonaRequerida, int horasMinimas, boolean experienciaRequerida ) {
        boolean cumpleZona = this.zona == zonaRequerida;
        boolean cumpleHoras = this.disponibilidadHorasSemana >= horasMinimas;
        boolean cumpleExperiencia = !experienciaRequerida || this.tieneExperiencia;
        return cumpleZona && cumpleHoras && cumpleExperiencia;
    }

    public String darResumenHorasPorTipo() {
        StringBuilder resumen = new StringBuilder();
        for ( TipoActividad tipo : TipoActividad.values() ) {
            double horas = darHorasDeTipo( tipo );
            if ( horas > 0 ) {
                resumen.append( tipo ).append( ": " ).append( horas ).append( " horas\n" );
            }
        }
        return resumen.length() == 0 ? "Sin horas registradas todavia" : resumen.toString().trim();
    }

    @Override
    public String mostrarPerfil() {
        return "=== Perfil de Voluntario ===" +
               "\nNombre: " + darNombre() +
               "\nCédula: " + darCedula() +
               "\nCorreo: " + darCorreo() +
               "\nZona: " + zona +
               "\nDisponibilidad: " + disponibilidadHorasSemana + " horas/semana" +
               "\nExperiencia: " + ( tieneExperiencia ? "Sí" : "No" ) +
               "\nHoras acumuladas: " + horasAcumuladas +
               "\nNivel: " + nivel +
               "\n--- Horas por tipo de actividad ---\n" + darResumenHorasPorTipo();
    }
}
