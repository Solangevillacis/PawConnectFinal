package pawconnect.modelo;

public class Fundacion extends Cuenta {

    public static final int ADOPCIONES_BUENA_REPUTACION = 5;

    private String direccion;
    private String causa;
    private int adopcionesExitosas;

    public Fundacion( String cedula, String nombre, String correo, String clave,
                       String direccion, String causa ) {
        super( cedula, nombre, correo, clave );
        this.direccion = direccion;
        this.causa = causa;
        this.adopcionesExitosas = 0;
    }

    public String darDireccion() {
        return direccion;
    }

    public String darCausa() {
        return causa;
    }

    public int darAdopcionesExitosas() {
        return adopcionesExitosas;
    }

    public void registrarAdopcionExitosa() {
        adopcionesExitosas++;
    }

    public String darReputacion() {
        if ( adopcionesExitosas >= ADOPCIONES_BUENA_REPUTACION ) {
            return "Alta";
        } else if ( adopcionesExitosas > 0 ) {
            return "Media";
        } else {
            return "Nueva";
        }
    }

    @Override
    public String mostrarPerfil() {
        return "=== Perfil de Fundacion ===" +
               "\nNombre: " + darNombre() +
               "\nCedula/RUC: " + darCedula() +
               "\nCorreo: " + darCorreo() +
               "\nDireccion: " + direccion +
               "\nCausa: " + causa +
               "\nAdopciones exitosas: " + adopcionesExitosas +
               "\nReputacion: " + darReputacion();
    }
}
