package pawconnect.negocio;

import java.util.ArrayList;
import java.util.Iterator;
import pawconnect.modelo.Mascota;
import pawconnect.modelo.Usuario;

public class GestorMascotas {

    private ArrayList<Mascota> mascotas;

    public GestorMascotas() {
        mascotas = new ArrayList<Mascota>();
    }

    public ArrayList<Mascota> darMascotas() {
        return mascotas;
    }

    public void establecerMascotas( ArrayList<Mascota> lista ) {
        this.mascotas = lista;
    }

    public Mascota buscarMascota( String id ) {
        Mascota encontrada = null;
        Iterator<Mascota> it = mascotas.iterator();
        while ( it.hasNext() && encontrada == null ) {
            Mascota m = it.next();
            if ( m.darId().equals( id ) ) {
                encontrada = m;
            }
        }
        return encontrada;
    }

    public void registrarMascota( String id, String nombre, Mascota.Especie especie, String raza,
                                   Mascota.Sexo sexo, int valorEdad, Mascota.UnidadEdad unidadEdad,
                                   Mascota.Tamano tamano, String estadoSalud, String cedulaFundacion ) throws Exception {
        if ( id == null || id.trim().isEmpty() ) {
            throw new Exception( "El identificador de la mascota no puede estar vacio." );
        }
        if ( buscarMascota( id ) != null ) {
            throw new Exception( "Ya existe una mascota registrada con ese identificador." );
        }
        if ( nombre == null || nombre.trim().isEmpty() ) {
            throw new Exception( "El nombre de la mascota no puede estar vacio." );
        }
        if ( valorEdad < 0 ) {
            throw new Exception( "La edad de la mascota no puede ser negativa." );
        }

        Mascota nueva = new Mascota( id, nombre, especie, raza, sexo, valorEdad, unidadEdad, tamano,
                estadoSalud, cedulaFundacion );
        mascotas.add( nueva );
    }

    public void eliminarMascota( String id, String cedulaFundacion ) throws Exception {
        Mascota m = buscarMascota( id );
        if ( m == null ) {
            throw new Exception( "No existe una mascota con ese identificador." );
        }
        if ( !m.darCedulaFundacion().equals( cedulaFundacion ) ) {
            throw new Exception( "Esta mascota no pertenece a su fundacion." );
        }
        mascotas.remove( m );
    }

    public ArrayList<Mascota> listarDisponibles() {
        ArrayList<Mascota> disponibles = new ArrayList<Mascota>();
        for ( Mascota m : mascotas ) {
            if ( m.darEstado() == Mascota.EstadoAdopcion.DISPONIBLE ) {
                disponibles.add( m );
            }
        }
        return disponibles;
    }

    public ArrayList<Mascota> listarPorFundacion( String cedulaFundacion ) {
        ArrayList<Mascota> lista = new ArrayList<Mascota>();
        for ( int i = 0; i < mascotas.size(); i++ ) {
            Mascota m = mascotas.get( i );
            if ( m.darCedulaFundacion().equals( cedulaFundacion ) ) {
                lista.add( m );
            }
        }
        return lista;
    }

    /**
     * Filtra mascotas disponibles combinando criterios. Cualquier parametro puede ser null
     * (o vacio en el caso de texto) para indicar que ese filtro no se aplica.
     */
    public ArrayList<Mascota> filtrarMascotas( Mascota.Especie especie, Mascota.Tamano tamano,
                                                Mascota.Sexo sexo, String raza, String textoNombre,
                                                Integer edadMinimaMeses, Integer edadMaximaMeses,
                                                Boolean soloDisponibles ) {
        ArrayList<Mascota> resultado = new ArrayList<Mascota>();
        for ( Mascota m : mascotas ) {
            boolean cumple = true;

            if ( especie != null && m.darEspecie() != especie ) {
                cumple = false;
            }
            if ( tamano != null && m.darTamano() != tamano ) {
                cumple = false;
            }
            if ( sexo != null && m.darSexo() != sexo ) {
                cumple = false;
            }
            if ( raza != null && !raza.trim().isEmpty()
                    && !m.darRaza().toLowerCase().contains( raza.trim().toLowerCase() ) ) {
                cumple = false;
            }
            if ( textoNombre != null && !textoNombre.trim().isEmpty()
                    && !m.darNombre().toLowerCase().contains( textoNombre.trim().toLowerCase() ) ) {
                cumple = false;
            }
            if ( edadMinimaMeses != null && m.darEdadEnMeses() < edadMinimaMeses ) {
                cumple = false;
            }
            if ( edadMaximaMeses != null && m.darEdadEnMeses() > edadMaximaMeses ) {
                cumple = false;
            }
            if ( soloDisponibles != null && soloDisponibles && m.darEstado() != Mascota.EstadoAdopcion.DISPONIBLE ) {
                cumple = false;
            }

            if ( cumple ) {
                resultado.add( m );
            }
        }
        return resultado;
    }

    public void marcarInteres( String idMascota, String cedulaUsuario ) throws Exception {
        Mascota m = buscarMascota( idMascota );
        if ( m == null ) {
            throw new Exception( "No existe una mascota con ese identificador." );
        }
        m.agregarInteresado( cedulaUsuario );
    }

    public ArrayList<Usuario> listarInteresadosOrdenados( String idMascota, GestorUsuarios gestorUsuarios ) throws Exception {
        Mascota m = buscarMascota( idMascota );
        if ( m == null ) {
            throw new Exception( "No existe una mascota con ese identificador." );
        }
        return gestorUsuarios.ordenarPorPuntaje( m.darInteresados() );
    }

    /**
     * Acepta la adopcion de la mascota por parte del usuario elegido. El resto de interesados
     * reciben una notificacion de rechazo y el elegido una notificacion de aceptacion.
     */
    public void aceptarAdopcion( String idMascota, String cedulaUsuarioElegido, GestorUsuarios gestorUsuarios ) throws Exception {
        Mascota m = buscarMascota( idMascota );
        if ( m == null ) {
            throw new Exception( "No existe una mascota con ese identificador." );
        }
        if ( !m.darInteresados().contains( cedulaUsuarioElegido ) ) {
            throw new Exception( "El usuario elegido no se encuentra en la lista de interesados." );
        }
        m.marcarAdoptada();

        for ( String cedulaInteresado : m.darInteresados() ) {
            Usuario u = gestorUsuarios.buscarUsuario( cedulaInteresado );
            if ( u == null ) {
                continue;
            }
            if ( cedulaInteresado.equals( cedulaUsuarioElegido ) ) {
                u.agregarNotificacion( "Tu solicitud de adopcion para " + m.darNombre() + " fue aceptada." );
            } else {
                u.agregarNotificacion( "Tu solicitud de adopcion para " + m.darNombre() + " fue rechazada." );
            }
        }
    }
}
