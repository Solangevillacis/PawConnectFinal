package pawconnect.interfaz;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import pawconnect.modelo.Fundacion;
import pawconnect.modelo.Usuario;
import pawconnect.modelo.Voluntario;

public class PanelLogin extends JPanel {

    private MainApp app;
    private JComboBox<String> comboTipo;
    private JTextField campoCedula;
    private JPasswordField campoClave;
    private JLabel etiquetaMensaje;

    public PanelLogin( MainApp app ) {
        this.app = app;
        setBackground( MainApp.COLOR_FONDO );
        construirInterfaz();
    }

    private void construirInterfaz() {
        setLayout( new GridBagLayout() );
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets( 10, 10, 10, 10 );
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titulo = new JLabel( "PawConnect" );
        titulo.setFont( new Font( "SansSerif", Font.BOLD, 32 ) );
        titulo.setForeground( MainApp.COLOR_PRIMARIO );
        gbc.gridy = 0;
        add( titulo, gbc );

        JLabel subtitulo = new JLabel( "Adopcion responsable en Quito" );
        subtitulo.setFont( new Font( "SansSerif", Font.PLAIN, 14 ) );
        subtitulo.setForeground( MainApp.COLOR_TEXTO );
        gbc.gridy = 1;
        add( subtitulo, gbc );

        JLabel etiquetaTipo = new JLabel( "Tipo de cuenta" );
        etiquetaTipo.setFont( new Font( "SansSerif", Font.PLAIN, 13 ) );
        gbc.gridy = 2;
        add( etiquetaTipo, gbc );

        comboTipo = new JComboBox<String>( new String[]{ "Usuario", "Voluntario", "Fundacion" } );
        comboTipo.setPreferredSize( new java.awt.Dimension( 260, 32 ) );
        gbc.gridy = 3;
        add( comboTipo, gbc );

        JLabel etiquetaCedula = new JLabel( "Cedula o RUC" );
        gbc.gridy = 4;
        add( etiquetaCedula, gbc );

        campoCedula = new JTextField();
        campoCedula.setPreferredSize( new java.awt.Dimension( 260, 32 ) );
        gbc.gridy = 5;
        add( campoCedula, gbc );

        JLabel etiquetaClave = new JLabel( "Clave" );
        gbc.gridy = 6;
        add( etiquetaClave, gbc );

        campoClave = new JPasswordField();
        campoClave.setPreferredSize( new java.awt.Dimension( 260, 32 ) );
        gbc.gridy = 7;
        add( campoClave, gbc );

        BotonEstilizado botonIngresar = new BotonEstilizado( "Iniciar sesion", MainApp.COLOR_PRIMARIO );
        botonIngresar.addActionListener( e -> iniciarSesion() );
        gbc.gridy = 8;
        add( botonIngresar, gbc );

        BotonEstilizado botonRegistro = new BotonEstilizado( "Crear una cuenta nueva", MainApp.COLOR_SECUNDARIO );
        botonRegistro.addActionListener( e -> {
            limpiarMensaje();
            app.mostrarPanel( "registro" );
        } );
        gbc.gridy = 9;
        add( botonRegistro, gbc );

        etiquetaMensaje = new JLabel( " " );
        etiquetaMensaje.setFont( new Font( "SansSerif", Font.PLAIN, 13 ) );
        etiquetaMensaje.setBorder( BorderFactory.createEmptyBorder( 10, 0, 0, 0 ) );
        gbc.gridy = 10;
        add( etiquetaMensaje, gbc );
    }

    private void iniciarSesion() {
        String tipo = (String) comboTipo.getSelectedItem();
        String cedula = campoCedula.getText().trim();
        String clave = new String( campoClave.getPassword() );

        try {
            if ( tipo.equals( "Usuario" ) ) {
                Usuario u = app.darGestorUsuarios().iniciarSesion( cedula, clave );
                app.establecerUsuarioActivo( u );
                app.refrescarPanelUsuario();
                app.mostrarPanel( "usuario" );
            } else if ( tipo.equals( "Voluntario" ) ) {
                Voluntario v = app.darGestorVoluntarios().iniciarSesion( cedula, clave );
                app.establecerVoluntarioActivo( v );
                app.refrescarPanelVoluntario();
                app.mostrarPanel( "voluntario" );
            } else {
                Fundacion f = app.darGestorFundaciones().iniciarSesion( cedula, clave );
                app.establecerFundacionActiva( f );
                app.refrescarPanelFundacion();
                app.mostrarPanel( "fundacion" );
            }
            limpiarCampos();
        } catch ( Exception ex ) {
            mostrarError( ex.getMessage() );
        }
    }

    private void limpiarCampos() {
        campoCedula.setText( "" );
        campoClave.setText( "" );
        limpiarMensaje();
    }

    private void limpiarMensaje() {
        etiquetaMensaje.setText( " " );
    }

    private void mostrarError( String mensaje ) {
        etiquetaMensaje.setForeground( MainApp.COLOR_ERROR );
        etiquetaMensaje.setText( mensaje );
    }
}
