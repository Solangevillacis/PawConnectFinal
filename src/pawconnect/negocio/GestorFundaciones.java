package pawconnect.negocio;

import java.util.ArrayList;
import pawconnect.modelo.Fundacion;

public class GestorFundaciones {

    private ArrayList<Fundacion> fundaciones;

    public GestorFundaciones() {
        fundaciones = new ArrayList<Fundacion>();
    }

    public ArrayList<Fundacion> darFundaciones() {
        return fundaciones;
    }

    public void establecerFundaciones( ArrayList<Fundacion> lista ) {
        this.fundaciones = lista;
    }

    public Fundacion buscarFundacion( String cedula ) {
        Fundacion encontrada = null;
        for ( Fundacion f : fundaciones ) {
            if ( f.darCedula().equals( cedula ) ) {
                encontrada = f;
            }
        }
        return encontrada;
    }

    public void registrarFundacion( String cedula, String nombre, String correo, String clave,
                                     String direccion, String causa ) throws Exception {
        if ( cedula == null || cedula.trim().isEmpty() ) {
            throw new Exception( "El RUC de la fundacion no puede estar vacio." );
        }
        if ( !ValidadorDatos.esRucValido( cedula ) ) {
            throw new Exception( "El RUC debe contener exactamente 13 digitos numericos." );
        }
        if ( nombre == null || nombre.trim().isEmpty() ) {
            throw new Exception( "El nombre de la fundacion no puede estar vacio." );
        }
        if ( !ValidadorDatos.esCorreoValido( correo ) ) {
            throw new Exception( "Ingresa un correo electronico valido (ejemplo: contacto@fundacion.com)." );
        }
        if ( buscarFundacion( cedula ) != null ) {
            throw new Exception( "Ya existe una fundacion registrada con ese RUC." );
        }

        Fundacion nueva = new Fundacion( cedula, nombre, correo, clave, direccion, causa );
        fundaciones.add( nueva );
    }

    public Fundacion iniciarSesion( String cedula, String clave ) throws Exception {
        Fundacion f = buscarFundacion( cedula );
        if ( f == null ) {
            throw new Exception( "No existe una fundacion registrada con ese RUC." );
        }
        if ( !f.darClave().equals( clave ) ) {
            throw new Exception( "La clave ingresada es incorrecta." );
        }
        return f;
    }
}
