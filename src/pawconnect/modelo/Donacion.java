package pawconnect.modelo;

public class Donacion {

    private String id;
    private String cedulaUsuario;
    private String cedulaFundacion;
    private double monto;
    private String concepto;
    private String idCampana;

    public Donacion( String id, String cedulaUsuario, String cedulaFundacion, double monto,
                      String concepto, String idCampana ) {
        this.id = id;
        this.cedulaUsuario = cedulaUsuario;
        this.cedulaFundacion = cedulaFundacion;
        this.monto = monto;
        this.concepto = concepto;
        this.idCampana = idCampana;
    }

    public String darId() {
        return id;
    }

    public String darCedulaUsuario() {
        return cedulaUsuario;
    }

    public String darCedulaFundacion() {
        return cedulaFundacion;
    }

    public double darMonto() {
        return monto;
    }

    public String darConcepto() {
        return concepto;
    }

    public String darIdCampana() {
        return idCampana;
    }

    @Override
    public String toString() {
        return concepto + " - $" + monto + " (" + cedulaUsuario + ")";
    }
}
