package pawconnect.negocio;

import java.util.ArrayList;
import pawconnect.modelo.Usuario;

public class GestorUsuarios {

    private ArrayList<Usuario> usuarios;

    public GestorUsuarios() {
        usuarios = new ArrayList<Usuario>();
    }

    public ArrayList<Usuario> darUsuarios() {
        return usuarios;
    }

    public void establecerUsuarios( ArrayList<Usuario> lista ) {
        this.usuarios = lista;
    }

    public Usuario buscarUsuario( String cedula ) {
        Usuario encontrado = null;
        for ( Usuario u : usuarios ) {
            if ( u.darCedula().equals( cedula ) ) {
                encontrado = u;
            }
        }
        return encontrado;
    }

    public void registrarUsuario( String cedula, String nombre, String correo, String clave, String telefono,
                                   String direccion, double ingresosMensuales, boolean tienePatio,
                                   boolean tieneExperiencia, String tipoVivienda, int tamanoFamilia,
                                   double horasLibresDia ) throws Exception {
        if ( cedula == null || cedula.trim().isEmpty() ) {
            throw new Exception( "La cedula no puede estar vacia." );
        }
        if ( !ValidadorDatos.esCedulaValida( cedula ) ) {
            throw new Exception( "La cedula debe contener exactamente 10 digitos numericos." );
        }
        if ( nombre == null || nombre.trim().isEmpty() ) {
            throw new Exception( "El nombre no puede estar vacio." );
        }
        if ( !ValidadorDatos.esNombreValido( nombre ) ) {
            throw new Exception( "El nombre solo puede contener letras y espacios." );
        }
        if ( !ValidadorDatos.esCorreoValido( correo ) ) {
            throw new Exception( "Ingresa un correo electronico valido (ejemplo: usuario@gmail.com)." );
        }
        if ( !ValidadorDatos.esTelefonoValido( telefono ) ) {
            throw new Exception( "El telefono debe tener 10 digitos numericos (ejemplo: 0991234567)." );
        }
        if ( direccion == null || direccion.trim().isEmpty() ) {
            throw new Exception( "La direccion no puede estar vacia." );
        }
        if ( buscarUsuario( cedula ) != null ) {
            throw new Exception( "Ya existe un usuario registrado con esa cedula." );
        }
        if ( ingresosMensuales < 0 ) {
            throw new Exception( "Los ingresos mensuales no pueden ser negativos." );
        }
        if ( tamanoFamilia <= 0 ) {
            throw new Exception( "El tamano de familia debe ser mayor a cero." );
        }
        if ( horasLibresDia < 0 || horasLibresDia > 24 ) {
            throw new Exception( "Las horas libres al dia deben estar entre 0 y 24." );
        }

        Usuario nuevo = new Usuario( cedula, nombre, correo, clave, telefono, direccion, ingresosMensuales,
                tienePatio, tieneExperiencia, tipoVivienda, tamanoFamilia, horasLibresDia );
        usuarios.add( nuevo );
    }

    public Usuario iniciarSesion( String cedula, String clave ) throws Exception {
        Usuario u = buscarUsuario( cedula );
        if ( u == null ) {
            throw new Exception( "No existe un usuario registrado con esa cedula." );
        }
        if ( !u.darClave().equals( clave ) ) {
            throw new Exception( "La clave ingresada es incorrecta." );
        }
        return u;
    }

    public ArrayList<Usuario> ordenarPorPuntaje( ArrayList<String> cedulas ) {
        ArrayList<Usuario> lista = new ArrayList<Usuario>();
        for ( String cedula : cedulas ) {
            Usuario u = buscarUsuario( cedula );
            if ( u != null ) {
                lista.add( u );
            }
        }

        for ( int i = 0; i < lista.size() - 1; i++ ) {
            for ( int j = 0; j < lista.size() - 1 - i; j++ ) {
                if ( lista.get( j ).calcularPuntajeAdoptante() < lista.get( j + 1 ).calcularPuntajeAdoptante() ) {
                    Usuario temp = lista.get( j );
                    lista.set( j, lista.get( j + 1 ) );
                    lista.set( j + 1, temp );
                }
            }
        }
        return lista;
    }
}
