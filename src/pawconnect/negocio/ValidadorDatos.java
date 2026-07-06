package pawconnect.negocio;

import java.util.regex.Pattern;

public class ValidadorDatos {

    private static final Pattern PATRON_CORREO = Pattern.compile( "^[\\w.+-]+@[\\w-]+\\.[a-zA-Z]{2,}$" );
    private static final Pattern PATRON_NOMBRE = Pattern.compile( "^[a-zA-ZÁÉÍÓÚÑáéíóúñ ]+$" );
    private static final Pattern PATRON_CEDULA = Pattern.compile( "^\\d{10}$" );
    private static final Pattern PATRON_RUC = Pattern.compile( "^\\d{13}$" );
    private static final Pattern PATRON_TELEFONO = Pattern.compile( "^\\d{10}$" );

    public static boolean esCorreoValido( String correo ) {
        return correo != null && PATRON_CORREO.matcher( correo.trim() ).matches();
    }

    public static boolean esNombreValido( String nombre ) {
        return nombre != null && !nombre.trim().isEmpty() && PATRON_NOMBRE.matcher( nombre.trim() ).matches();
    }

    public static boolean esCedulaValida( String cedula ) {
        return cedula != null && PATRON_CEDULA.matcher( cedula.trim() ).matches();
    }

    public static boolean esRucValido( String ruc ) {
        return ruc != null && PATRON_RUC.matcher( ruc.trim() ).matches();
    }

    public static boolean esTelefonoValido( String telefono ) {
        return telefono != null && PATRON_TELEFONO.matcher( telefono.trim() ).matches();
    }
}
