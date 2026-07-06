package pawconnect.interfaz;

import java.awt.CardLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import pawconnect.modelo.Zona;

public class PanelRegistro extends JPanel {

    private MainApp app;
    private JComboBox<String> comboTipo;
    private CardLayout cardFormularios;
    private JPanel panelFormularios;
    private JLabel etiquetaMensaje;

    private JTextField campoCedulaU;
    private JTextField campoNombreU;
    private JTextField campoCorreoU;
    private JPasswordField campoClaveU;
    private JTextField campoTelefonoU;
    private JTextField campoDireccionU;
    private JTextField campoIngresosU;
    private JCheckBox checkPatioU;
    private JCheckBox checkExperienciaU;
    private JTextField campoViviendaU;
    private JTextField campoFamiliaU;
    private JTextField campoHorasU;

    private JTextField campoCedulaV;
    private JTextField campoNombreV;
    private JTextField campoCorreoV;
    private JPasswordField campoClaveV;
    private JComboBox<Zona> comboZonaV;
    private JTextField campoDisponibilidadV;
    private JCheckBox checkExperienciaV;

    private JTextField campoCedulaF;
    private JTextField campoNombreF;
    private JTextField campoCorreoF;
    private JPasswordField campoClaveF;
    private JTextField campoDireccionF;
    private JTextField campoCausaF;

    public PanelRegistro( MainApp app ) {
        this.app = app;
        setBackground( MainApp.COLOR_FONDO );
        construirInterfaz();
    }

    private void construirInterfaz() {
        setLayout( new GridBagLayout() );
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets( 8, 10, 8, 10 );
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titulo = new JLabel( "Crear cuenta en PawConnect" );
        titulo.setFont( new Font( "SansSerif", Font.BOLD, 24 ) );
        titulo.setForeground( MainApp.COLOR_PRIMARIO );
        gbc.gridy = 0;
        add( titulo, gbc );

        comboTipo = new JComboBox<String>( new String[]{ "Usuario", "Voluntario", "Fundacion" } );
        comboTipo.addActionListener( e -> {
            cardFormularios.show( panelFormularios, (String) comboTipo.getSelectedItem() );
            limpiarMensaje();
        } );
        gbc.gridy = 1;
        add( comboTipo, gbc );

        panelFormularios = new JPanel();
        cardFormularios = new CardLayout();
        panelFormularios.setLayout( cardFormularios );
        panelFormularios.setBackground( MainApp.COLOR_FONDO );
        panelFormularios.add( construirFormularioUsuario(), "Usuario" );
        panelFormularios.add( construirFormularioVoluntario(), "Voluntario" );
        panelFormularios.add( construirFormularioFundacion(), "Fundacion" );
        gbc.gridy = 2;
        add( panelFormularios, gbc );

        BotonEstilizado botonRegistrar = new BotonEstilizado( "Registrarme", MainApp.COLOR_PRIMARIO );
        botonRegistrar.addActionListener( e -> registrar() );
        gbc.gridy = 3;
        add( botonRegistrar, gbc );

        BotonEstilizado botonVolver = new BotonEstilizado( "Volver al inicio de sesion", MainApp.COLOR_SECUNDARIO );
        botonVolver.addActionListener( e -> {
            limpiarMensaje();
            app.mostrarPanel( "login" );
        } );
        gbc.gridy = 4;
        add( botonVolver, gbc );

        etiquetaMensaje = new JLabel( " " );
        etiquetaMensaje.setBorder( BorderFactory.createEmptyBorder( 10, 0, 0, 0 ) );
        gbc.gridy = 5;
        add( etiquetaMensaje, gbc );
    }

    private JPanel construirFormularioUsuario() {
        JPanel panel = new JPanel( new GridBagLayout() );
        panel.setBackground( MainApp.COLOR_FONDO );
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets( 4, 6, 4, 6 );
        gbc.fill = GridBagConstraints.HORIZONTAL;

        campoCedulaU = new JTextField( 18 );
        campoNombreU = new JTextField( 18 );
        campoCorreoU = new JTextField( 18 );
        campoClaveU = new JPasswordField( 18 );
        campoTelefonoU = new JTextField( 18 );
        campoDireccionU = new JTextField( 18 );
        campoIngresosU = new JTextField( 18 );
        checkPatioU = new JCheckBox( "Tiene patio" );
        checkExperienciaU = new JCheckBox( "Tiene experiencia con mascotas" );
        campoViviendaU = new JTextField( 18 );
        campoFamiliaU = new JTextField( 18 );
        campoHorasU = new JTextField( 18 );

        agregarCampo( panel, gbc, 0, "Cedula (10 digitos)", campoCedulaU );
        agregarCampo( panel, gbc, 1, "Nombre completo", campoNombreU );
        agregarCampo( panel, gbc, 2, "Correo", campoCorreoU );
        agregarCampo( panel, gbc, 3, "Clave", campoClaveU );
        agregarCampo( panel, gbc, 4, "Telefono", campoTelefonoU );
        agregarCampo( panel, gbc, 5, "Direccion", campoDireccionU );
        agregarCampo( panel, gbc, 6, "Ingresos mensuales ($)", campoIngresosU );
        agregarCampo( panel, gbc, 7, "Tipo de vivienda", campoViviendaU );
        agregarCampo( panel, gbc, 8, "Tamano de familia", campoFamiliaU );
        agregarCampo( panel, gbc, 9, "Horas libres al dia", campoHorasU );

        gbc.gridx = 0;
        gbc.gridy = 10;
        gbc.gridwidth = 2;
        panel.add( checkPatioU, gbc );
        gbc.gridy = 11;
        panel.add( checkExperienciaU, gbc );

        return panel;
    }

    private JPanel construirFormularioVoluntario() {
        JPanel panel = new JPanel( new GridBagLayout() );
        panel.setBackground( MainApp.COLOR_FONDO );
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets( 4, 6, 4, 6 );
        gbc.fill = GridBagConstraints.HORIZONTAL;

        campoCedulaV = new JTextField( 18 );
        campoNombreV = new JTextField( 18 );
        campoCorreoV = new JTextField( 18 );
        campoClaveV = new JPasswordField( 18 );
        comboZonaV = new JComboBox<Zona>( Zona.values() );
        campoDisponibilidadV = new JTextField( 18 );
        checkExperienciaV = new JCheckBox( "Tiene experiencia con animales" );

        agregarCampo( panel, gbc, 0, "Cedula (10 digitos)", campoCedulaV );
        agregarCampo( panel, gbc, 1, "Nombre completo", campoNombreV );
        agregarCampo( panel, gbc, 2, "Correo", campoCorreoV );
        agregarCampo( panel, gbc, 3, "Clave", campoClaveV );
        agregarCampo( panel, gbc, 4, "Zona de Quito", comboZonaV );
        agregarCampo( panel, gbc, 5, "Disponibilidad (horas/semana)", campoDisponibilidadV );

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        panel.add( checkExperienciaV, gbc );

        return panel;
    }

    private JPanel construirFormularioFundacion() {
        JPanel panel = new JPanel( new GridBagLayout() );
        panel.setBackground( MainApp.COLOR_FONDO );
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets( 4, 6, 4, 6 );
        gbc.fill = GridBagConstraints.HORIZONTAL;

        campoCedulaF = new JTextField( 18 );
        campoNombreF = new JTextField( 18 );
        campoCorreoF = new JTextField( 18 );
        campoClaveF = new JPasswordField( 18 );
        campoDireccionF = new JTextField( 18 );
        campoCausaF = new JTextField( 18 );

        agregarCampo( panel, gbc, 0, "RUC (13 digitos)", campoCedulaF );
        agregarCampo( panel, gbc, 1, "Nombre de la fundacion", campoNombreF );
        agregarCampo( panel, gbc, 2, "Correo", campoCorreoF );
        agregarCampo( panel, gbc, 3, "Clave", campoClaveF );
        agregarCampo( panel, gbc, 4, "Direccion", campoDireccionF );
        agregarCampo( panel, gbc, 5, "Causa que atiende", campoCausaF );

        return panel;
    }

    private void agregarCampo( JPanel panel, GridBagConstraints gbc, int fila, String etiqueta, java.awt.Component campo ) {
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = fila;
        panel.add( new JLabel( etiqueta ), gbc );
        gbc.gridx = 1;
        panel.add( campo, gbc );
    }

    private void registrar() {
        String tipo = (String) comboTipo.getSelectedItem();
        try {
            if ( tipo.equals( "Usuario" ) ) {
                registrarUsuario();
            } else if ( tipo.equals( "Voluntario" ) ) {
                registrarVoluntario();
            } else {
                registrarFundacion();
            }
            mostrarExito( "Cuenta creada con exito. Ahora puedes iniciar sesion." );
            app.mostrarPanel( "login" );
        } catch ( Exception ex ) {
            mostrarError( ex.getMessage() );
        }
    }

    private void registrarUsuario() throws Exception {
        double ingresos = parsearDouble( campoIngresosU.getText(), "Los ingresos deben ser un numero valido." );
        int familia = parsearEntero( campoFamiliaU.getText(), "El tamano de familia debe ser un numero entero valido." );
        double horas = parsearDouble( campoHorasU.getText(), "Las horas libres deben ser un numero valido." );

        app.darGestorUsuarios().registrarUsuario( campoCedulaU.getText().trim(), campoNombreU.getText().trim(),
                campoCorreoU.getText().trim(), new String( campoClaveU.getPassword() ),
                campoTelefonoU.getText().trim(), campoDireccionU.getText().trim(), ingresos,
                checkPatioU.isSelected(), checkExperienciaU.isSelected(), campoViviendaU.getText().trim(),
                familia, horas );
    }

    private void registrarVoluntario() throws Exception {
        int disponibilidad = parsearEntero( campoDisponibilidadV.getText(), "La disponibilidad debe ser un numero entero valido." );

        app.darGestorVoluntarios().registrarVoluntario( campoCedulaV.getText().trim(), campoNombreV.getText().trim(),
                campoCorreoV.getText().trim(), new String( campoClaveV.getPassword() ),
                (Zona) comboZonaV.getSelectedItem(), disponibilidad, checkExperienciaV.isSelected() );
    }

    private void registrarFundacion() throws Exception {
        app.darGestorFundaciones().registrarFundacion( campoCedulaF.getText().trim(), campoNombreF.getText().trim(),
                campoCorreoF.getText().trim(), new String( campoClaveF.getPassword() ),
                campoDireccionF.getText().trim(), campoCausaF.getText().trim() );
    }

    private double parsearDouble( String texto, String mensajeError ) throws Exception {
        try {
            return Double.parseDouble( texto.trim() );
        } catch ( NumberFormatException e ) {
            throw new Exception( mensajeError );
        }
    }

    private int parsearEntero( String texto, String mensajeError ) throws Exception {
        try {
            return Integer.parseInt( texto.trim() );
        } catch ( NumberFormatException e ) {
            throw new Exception( mensajeError );
        }
    }

    private void limpiarMensaje() {
        etiquetaMensaje.setText( " " );
    }

    private void mostrarError( String mensaje ) {
        etiquetaMensaje.setForeground( MainApp.COLOR_ERROR );
        etiquetaMensaje.setText( mensaje );
    }

    private void mostrarExito( String mensaje ) {
        etiquetaMensaje.setForeground( MainApp.COLOR_EXITO );
        etiquetaMensaje.setText( mensaje );
    }
}
