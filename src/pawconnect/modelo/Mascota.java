package pawconnect.modelo;

import java.util.ArrayList;

public class Mascota {

    public enum EstadoAdopcion {
        DISPONIBLE, ADOPTADA
    }

    public enum Especie {
        PERRO, GATO, OTRO
    }

    public enum Tamano {
        PEQUENO, MEDIANO, GRANDE
    }

    public enum Sexo {
        MACHO, HEMBRA
    }

    public enum UnidadEdad {
        MESES, ANIOS
    }

    public static final int DIAS_URGENTE = 30;

    private String id;
    private String nombre;
    private Especie especie;
    private String raza;
    private Sexo sexo;
    private int valorEdad;
    private UnidadEdad unidadEdad;
    private Tamano tamano;
    private String estadoSalud;
    private String cedulaFundacion;
    private int diasEnEspera;
    private EstadoAdopcion estado;
    private ArrayList<String> interesados;

    public Mascota( String id, String nombre, Especie especie, String raza, Sexo sexo, int valorEdad,
                     UnidadEdad unidadEdad, Tamano tamano, String estadoSalud, String cedulaFundacion ) {
        this.id = id;
        this.nombre = nombre;
        this.especie = especie;
        this.raza = raza;
        this.sexo = sexo;
        this.valorEdad = valorEdad;
        this.unidadEdad = unidadEdad;
        this.tamano = tamano;
        this.estadoSalud = estadoSalud;
        this.cedulaFundacion = cedulaFundacion;
        this.diasEnEspera = 0;
        this.estado = EstadoAdopcion.DISPONIBLE;
        this.interesados = new ArrayList<String>();
    }

    public String darId() {
        return id;
    }

    public String darNombre() {
        return nombre;
    }

    public Especie darEspecie() {
        return especie;
    }

    public String darRaza() {
        return raza;
    }

    public Sexo darSexo() {
        return sexo;
    }

    public int darValorEdad() {
        return valorEdad;
    }

    public UnidadEdad darUnidadEdad() {
        return unidadEdad;
    }

    public int darEdadEnMeses() {
        return unidadEdad == UnidadEdad.ANIOS ? valorEdad * 12 : valorEdad;
    }

    public Tamano darTamano() {
        return tamano;
    }

    public String darEstadoSalud() {
        return estadoSalud;
    }

    public String darCedulaFundacion() {
        return cedulaFundacion;
    }

    public int darDiasEnEspera() {
        return diasEnEspera;
    }

    public EstadoAdopcion darEstado() {
        return estado;
    }

    public ArrayList<String> darInteresados() {
        return interesados;
    }

    public void aumentarDiasEnEspera( int dias ) {
        diasEnEspera += dias;
    }

    public boolean esUrgente() {
        return diasEnEspera >= DIAS_URGENTE && estado == EstadoAdopcion.DISPONIBLE;
    }

    public void agregarInteresado( String cedulaUsuario ) throws Exception {
        if ( estado == EstadoAdopcion.ADOPTADA ) {
            throw new Exception( "Esta mascota ya fue adoptada, no se puede mostrar interes." );
        }
        if ( interesados.contains( cedulaUsuario ) ) {
            throw new Exception( "Ya mostraste interes en esta mascota." );
        }
        interesados.add( cedulaUsuario );
    }

    public void marcarAdoptada() throws Exception {
        if ( estado == EstadoAdopcion.ADOPTADA ) {
            throw new Exception( "La mascota ya se encuentra adoptada." );
        }
        estado = EstadoAdopcion.ADOPTADA;
    }

    public String darEdadTexto() {
        String unidadTexto = unidadEdad == UnidadEdad.MESES ? ( valorEdad == 1 ? "mes" : "meses" )
                : ( valorEdad == 1 ? "año" : "años" );
        return valorEdad + " " + unidadTexto;
    }

    @Override
    public String toString() {
        String urgente = esUrgente() ? " [URGENTE]" : "";
        return id + " - " + nombre + " (" + especie + ", " + raza + ", " + darEdadTexto() + ", " + sexo
                + ", " + tamano + ") - " + estado + urgente;
    }
}
