package pawconnect.modelo;

public abstract class Cuenta {

    private String cedula;
    private String nombre;
    private String correo;
    private String clave;

    public Cuenta( String cedula, String nombre, String correo, String clave ) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.correo = correo;
        this.clave = clave;
    }

    public String darCedula() {
        return cedula;
    }

    public String darNombre() {
        return nombre;
    }

    public String darCorreo() {
        return correo;
    }

    public String darClave() {
        return clave;
    }

    public abstract String mostrarPerfil();

    @Override
    public String toString() {
        return cedula + " - " + nombre;
    }
}
