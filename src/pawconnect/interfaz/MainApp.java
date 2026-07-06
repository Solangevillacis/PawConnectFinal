package pawconnect.interfaz;

import java.awt.CardLayout;
import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.UIManager;
import pawconnect.modelo.Fundacion;
import pawconnect.modelo.Usuario;
import pawconnect.modelo.Voluntario;
import pawconnect.negocio.GestorDonaciones;
import pawconnect.negocio.GestorFundaciones;
import pawconnect.negocio.GestorMascotas;
import pawconnect.negocio.GestorUsuarios;
import pawconnect.negocio.GestorVoluntarios;
import pawconnect.persistencia.ManejadorArchivos;

public class MainApp extends JFrame {

    public static final Color COLOR_FONDO = new Color( 245, 247, 245 );
    public static final Color COLOR_PRIMARIO = new Color( 46, 125, 90 );
    public static final Color COLOR_SECUNDARIO = new Color( 235, 168, 74 );
    public static final Color COLOR_TEXTO = new Color( 40, 40, 40 );
    public static final Color COLOR_ERROR = new Color( 178, 58, 58 );
    public static final Color COLOR_EXITO = new Color( 46, 125, 90 );

    private CardLayout cardLayout;
    private java.awt.Container contenedor;

    private GestorUsuarios gestorUsuarios;
    private GestorVoluntarios gestorVoluntarios;
    private GestorFundaciones gestorFundaciones;
    private GestorMascotas gestorMascotas;
    private GestorDonaciones gestorDonaciones;

    private Usuario usuarioActivo;
    private Voluntario voluntarioActivo;
    private Fundacion fundacionActiva;

    private PanelLogin panelLogin;
    private PanelRegistro panelRegistro;
    private PanelUsuario panelUsuario;
    private PanelFundacion panelFundacion;
    private PanelVoluntario panelVoluntario;

    public MainApp() {
        super( "PawConnect - Adopcion responsable" );
        inicializarGestores();
        cargarDatosIniciales();
        configurarVentana();
        construirPaneles();
    }

    private void inicializarGestores() {
        gestorUsuarios = new GestorUsuarios();
        gestorVoluntarios = new GestorVoluntarios();
        gestorFundaciones = new GestorFundaciones();
        gestorMascotas = new GestorMascotas();
        gestorDonaciones = new GestorDonaciones();
    }

    private void cargarDatosIniciales() {
        try {
            gestorUsuarios.establecerUsuarios( ManejadorArchivos.cargarUsuarios() );
            gestorVoluntarios.establecerVoluntarios( ManejadorArchivos.cargarVoluntarios() );
            gestorFundaciones.establecerFundaciones( ManejadorArchivos.cargarFundaciones() );
            gestorMascotas.establecerMascotas( ManejadorArchivos.cargarMascotas() );
            gestorDonaciones.establecerDonaciones( ManejadorArchivos.cargarDonaciones() );
            gestorDonaciones.establecerCampanas( ManejadorArchivos.cargarCampanas() );
            gestorVoluntarios.establecerSolicitudes( ManejadorArchivos.cargarSolicitudes() );
        } catch ( Exception e ) {
            System.out.println( "No se pudieron cargar datos previos: " + e.getMessage() );
        }
    }

    public void guardarTodo() {
        try {
            ManejadorArchivos.guardarUsuarios( gestorUsuarios.darUsuarios() );
            ManejadorArchivos.guardarVoluntarios( gestorVoluntarios.darVoluntarios() );
            ManejadorArchivos.guardarFundaciones( gestorFundaciones.darFundaciones() );
            ManejadorArchivos.guardarMascotas( gestorMascotas.darMascotas() );
            ManejadorArchivos.guardarDonaciones( gestorDonaciones.darDonaciones() );
            ManejadorArchivos.guardarCampanas( gestorDonaciones.darCampanas() );
            ManejadorArchivos.guardarSolicitudes( gestorVoluntarios.darSolicitudes() );
        } catch ( Exception e ) {
            System.out.println( "Error guardando datos: " + e.getMessage() );
        }
    }

    private void configurarVentana() {
        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        setSize( 950, 650 );
        setMinimumSize( new java.awt.Dimension( 900, 600 ) );
        setLocationRelativeTo( null );
        getContentPane().setBackground( COLOR_FONDO );

        cardLayout = new CardLayout();
        contenedor = getContentPane();
        contenedor.setLayout( cardLayout );

        addWindowListener( new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing( java.awt.event.WindowEvent e ) {
                guardarTodo();
            }
        } );
    }

    private void construirPaneles() {
        panelLogin = new PanelLogin( this );
        panelRegistro = new PanelRegistro( this );
        panelUsuario = new PanelUsuario( this );
        panelFundacion = new PanelFundacion( this );
        panelVoluntario = new PanelVoluntario( this );

        contenedor.add( panelLogin, "login" );
        contenedor.add( panelRegistro, "registro" );
        contenedor.add( panelUsuario, "usuario" );
        contenedor.add( panelFundacion, "fundacion" );
        contenedor.add( panelVoluntario, "voluntario" );

        cardLayout.show( contenedor, "login" );
    }

    public void mostrarPanel( String nombre ) {
        cardLayout.show( contenedor, nombre );
    }

    public GestorUsuarios darGestorUsuarios() {
        return gestorUsuarios;
    }

    public GestorVoluntarios darGestorVoluntarios() {
        return gestorVoluntarios;
    }

    public GestorFundaciones darGestorFundaciones() {
        return gestorFundaciones;
    }

    public GestorMascotas darGestorMascotas() {
        return gestorMascotas;
    }

    public GestorDonaciones darGestorDonaciones() {
        return gestorDonaciones;
    }

    public Usuario darUsuarioActivo() {
        return usuarioActivo;
    }

    public void establecerUsuarioActivo( Usuario u ) {
        this.usuarioActivo = u;
    }

    public Voluntario darVoluntarioActivo() {
        return voluntarioActivo;
    }

    public void establecerVoluntarioActivo( Voluntario v ) {
        this.voluntarioActivo = v;
    }

    public Fundacion darFundacionActiva() {
        return fundacionActiva;
    }

    public void establecerFundacionActiva( Fundacion f ) {
        this.fundacionActiva = f;
    }

    public void refrescarPanelUsuario() {
        panelUsuario.refrescar();
    }

    public void refrescarPanelFundacion() {
        panelFundacion.refrescar();
    }

    public void refrescarPanelVoluntario() {
        panelVoluntario.refrescar();
    }

    public static void main( String[] args ) {
        try {
            UIManager.setLookAndFeel( UIManager.getSystemLookAndFeelClassName() );
        } catch ( Exception e ) {
            System.out.println( "No se pudo aplicar el look and feel del sistema." );
        }

        javax.swing.SwingUtilities.invokeLater( new Runnable() {
            @Override
            public void run() {
                MainApp app = new MainApp();
                app.setVisible( true );
            }
        } );
    }
}
