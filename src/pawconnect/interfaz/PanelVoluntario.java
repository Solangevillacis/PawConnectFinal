package pawconnect.interfaz;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import pawconnect.modelo.Fundacion;
import pawconnect.modelo.SolicitudVoluntariado;
import pawconnect.modelo.TipoActividad;
import pawconnect.modelo.Zona;

public class PanelVoluntario extends JPanel {

    private MainApp app;
    private JLabel etiquetaBienvenida;
    private JTextArea areaPerfil;
    private DefaultListModel<SolicitudVoluntariado> modeloSolicitudes;
    private JList<SolicitudVoluntariado> listaSolicitudes;
    private JLabel etiquetaMensaje;
    private JTextField campoHoras;
    private JComboBox<TipoActividad> comboTipoHoras;
    private JComboBox<Object> comboFiltroZona;
    private JComboBox<Object> comboFiltroTipo;

    public PanelVoluntario( MainApp app ) {
        this.app = app;
        setBackground( MainApp.COLOR_FONDO );
        construirInterfaz();
    }

    private void construirInterfaz() {
        setLayout( new BorderLayout( 10, 10 ) );
        setBorder( BorderFactory.createEmptyBorder( 15, 20, 15, 20 ) );

        JPanel panelSuperior = new JPanel( new BorderLayout() );
        panelSuperior.setBackground( MainApp.COLOR_FONDO );
        etiquetaBienvenida = new JLabel( "Bienvenido" );
        etiquetaBienvenida.setFont( new Font( "SansSerif", Font.BOLD, 22 ) );
        etiquetaBienvenida.setForeground( MainApp.COLOR_PRIMARIO );
        panelSuperior.add( etiquetaBienvenida, BorderLayout.WEST );

        BotonEstilizado botonSalir = new BotonEstilizado( "Cerrar sesion", MainApp.COLOR_SECUNDARIO );
        botonSalir.addActionListener( e -> cerrarSesion() );
        panelSuperior.add( botonSalir, BorderLayout.EAST );
        add( panelSuperior, BorderLayout.NORTH );

        JLabel etiquetaContexto = new JLabel( "<html><i>En esta seccion podras registrarte como voluntario y ayudar a mejorar la vida de las mascotas.</i></html>" );

        JPanel panelCentral = new JPanel( new GridLayout( 1, 2, 15, 0 ) );
        panelCentral.setBackground( MainApp.COLOR_FONDO );

        JPanel panelPerfil = new JPanel( new BorderLayout( 5, 5 ) );
        panelPerfil.setBackground( MainApp.COLOR_FONDO );
        panelPerfil.setBorder( BorderFactory.createTitledBorder( "Mi perfil" ) );
        areaPerfil = new JTextArea();
        areaPerfil.setEditable( false );
        areaPerfil.setFont( new Font( "Monospaced", Font.PLAIN, 13 ) );
        areaPerfil.setBackground( Color.WHITE );
        panelPerfil.add( new JScrollPane( areaPerfil ), BorderLayout.CENTER );

        JPanel panelHoras = new JPanel( new GridBagLayout() );
        panelHoras.setBackground( MainApp.COLOR_FONDO );
        GridBagConstraints gbcHoras = new GridBagConstraints();
        gbcHoras.insets = new Insets( 3, 5, 3, 5 );
        gbcHoras.fill = GridBagConstraints.HORIZONTAL;

        comboTipoHoras = new JComboBox<TipoActividad>( TipoActividad.values() );
        campoHoras = new JTextField( 8 );

        gbcHoras.gridx = 0; gbcHoras.gridy = 0;
        panelHoras.add( new JLabel( "Tipo de actividad" ), gbcHoras );
        gbcHoras.gridx = 1;
        panelHoras.add( comboTipoHoras, gbcHoras );
        gbcHoras.gridx = 0; gbcHoras.gridy = 1;
        panelHoras.add( new JLabel( "Horas cumplidas" ), gbcHoras );
        gbcHoras.gridx = 1;
        panelHoras.add( campoHoras, gbcHoras );

        BotonEstilizado botonHoras = new BotonEstilizado( "Registrar horas", MainApp.COLOR_PRIMARIO );
        botonHoras.addActionListener( e -> registrarHoras() );
        gbcHoras.gridx = 0; gbcHoras.gridy = 2; gbcHoras.gridwidth = 2;
        panelHoras.add( botonHoras, gbcHoras );

        panelPerfil.add( panelHoras, BorderLayout.SOUTH );

        JPanel panelSolicitudes = new JPanel( new BorderLayout( 5, 5 ) );
        panelSolicitudes.setBackground( MainApp.COLOR_FONDO );
        panelSolicitudes.setBorder( BorderFactory.createTitledBorder( "Solicitudes de voluntariado disponibles" ) );

        JPanel panelFiltros = new JPanel( new GridBagLayout() );
        panelFiltros.setBackground( MainApp.COLOR_FONDO );
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets( 3, 5, 3, 5 );
        gbc.fill = GridBagConstraints.HORIZONTAL;

        comboFiltroZona = new JComboBox<Object>();
        comboFiltroZona.addItem( "Cualquiera" );
        for ( Zona z : Zona.values() ) {
            comboFiltroZona.addItem( z );
        }
        comboFiltroTipo = new JComboBox<Object>();
        comboFiltroTipo.addItem( "Cualquiera" );
        for ( TipoActividad t : TipoActividad.values() ) {
            comboFiltroTipo.addItem( t );
        }

        gbc.gridx = 0; gbc.gridy = 0;
        panelFiltros.add( new JLabel( "Zona" ), gbc );
        gbc.gridx = 1;
        panelFiltros.add( comboFiltroZona, gbc );
        gbc.gridx = 2;
        panelFiltros.add( new JLabel( "Tipo" ), gbc );
        gbc.gridx = 3;
        panelFiltros.add( comboFiltroTipo, gbc );

        BotonEstilizado botonFiltrar = new BotonEstilizado( "Filtrar", MainApp.COLOR_PRIMARIO );
        botonFiltrar.addActionListener( e -> aplicarFiltros() );
        gbc.gridx = 4;
        panelFiltros.add( botonFiltrar, gbc );

        panelSolicitudes.add( panelFiltros, BorderLayout.NORTH );

        modeloSolicitudes = new DefaultListModel<SolicitudVoluntariado>();
        listaSolicitudes = new JList<SolicitudVoluntariado>( modeloSolicitudes );
        listaSolicitudes.setFont( new Font( "SansSerif", Font.PLAIN, 13 ) );
        listaSolicitudes.setCellRenderer( new javax.swing.ListCellRenderer<SolicitudVoluntariado>() {
            @Override
            public java.awt.Component getListCellRendererComponent( JList<? extends SolicitudVoluntariado> list,
                    SolicitudVoluntariado value, int index, boolean isSelected, boolean cellHasFocus ) {
                Fundacion f = app.darGestorFundaciones().buscarFundacion( value.darCedulaFundacion() );
                String nombreFundacion = f == null ? "Fundacion desconocida" : f.darNombre();
                String estadoPersonal = value.darEstadoDe( app.darVoluntarioActivo().darCedula() );
                String sufijoPersonal = estadoPersonal == null ? "" : " - Tu postulacion: [" + estadoPersonal + "]";
                String texto = value.darId() + " - " + nombreFundacion + ": " + value.darDescripcion()
                        + " (" + value.darTipoActividad() + " - " + value.darZonaRequerida() + ") - Vigencia: "
                        + value.darFechaInicio() + " al " + value.darFechaFin() + " - Estado: [" + value.darEstado()
                        + "]" + sufijoPersonal;
                JLabel etiqueta = new JLabel( texto );
                etiqueta.setOpaque( true );
                etiqueta.setBackground( isSelected ? MainApp.COLOR_SECUNDARIO : Color.WHITE );
                etiqueta.setBorder( BorderFactory.createEmptyBorder( 6, 8, 6, 8 ) );
                return etiqueta;
            }
        } );
        panelSolicitudes.add( new JScrollPane( listaSolicitudes ), BorderLayout.CENTER );

        BotonEstilizado botonAplicar = new BotonEstilizado( "Aplicar a solicitud seleccionada", MainApp.COLOR_PRIMARIO );
        botonAplicar.addActionListener( e -> aplicarSolicitud() );
        panelSolicitudes.add( botonAplicar, BorderLayout.SOUTH );

        panelCentral.add( panelPerfil );
        panelCentral.add( panelSolicitudes );

        JPanel panelConContexto = new JPanel( new BorderLayout( 8, 8 ) );
        panelConContexto.setBackground( MainApp.COLOR_FONDO );
        panelConContexto.add( etiquetaContexto, BorderLayout.NORTH );
        panelConContexto.add( panelCentral, BorderLayout.CENTER );

        add( panelConContexto, BorderLayout.CENTER );

        etiquetaMensaje = new JLabel( " " );
        etiquetaMensaje.setBorder( BorderFactory.createEmptyBorder( 8, 0, 0, 0 ) );
        add( etiquetaMensaje, BorderLayout.SOUTH );
    }

    public void refrescar() {
        if ( app.darVoluntarioActivo() == null ) {
            return;
        }
        etiquetaBienvenida.setText( "Hola, " + app.darVoluntarioActivo().darNombre() );
        areaPerfil.setText( app.darVoluntarioActivo().mostrarPerfil() );
        aplicarFiltros();
        limpiarMensaje();
    }

    private void aplicarFiltros() {
        Object zonaSel = comboFiltroZona.getSelectedItem();
        Object tipoSel = comboFiltroTipo.getSelectedItem();
        Zona zona = zonaSel instanceof Zona ? (Zona) zonaSel : null;
        TipoActividad tipo = tipoSel instanceof TipoActividad ? (TipoActividad) tipoSel : null;

        modeloSolicitudes.clear();
        for ( SolicitudVoluntariado s : app.darGestorVoluntarios().filtrarSolicitudes(
                app.darVoluntarioActivo().darCedula(), zona, tipo ) ) {
            modeloSolicitudes.addElement( s );
        }
    }

    private void aplicarSolicitud() {
        SolicitudVoluntariado seleccionada = listaSolicitudes.getSelectedValue();
        if ( seleccionada == null ) {
            mostrarError( "Selecciona una solicitud de la lista." );
            return;
        }
        try {
            app.darGestorVoluntarios().aplicarASolicitud( seleccionada.darId(),
                    app.darVoluntarioActivo().darCedula() );
            listaSolicitudes.repaint();
            mostrarExito( "Tu postulacion fue enviada. Espera la respuesta de la fundacion." );
        } catch ( Exception ex ) {
            mostrarError( ex.getMessage() );
        }
    }

    private void registrarHoras() {
        try {
            double horas = Double.parseDouble( campoHoras.getText().trim() );
            TipoActividad tipo = (TipoActividad) comboTipoHoras.getSelectedItem();
            app.darGestorVoluntarios().acumularHoras( app.darVoluntarioActivo().darCedula(), tipo, horas );
            areaPerfil.setText( app.darVoluntarioActivo().mostrarPerfil() );
            campoHoras.setText( "" );
            mostrarExito( "Horas registradas con exito." );
        } catch ( NumberFormatException nfe ) {
            mostrarError( "Ingresa un numero valido de horas." );
        } catch ( Exception ex ) {
            mostrarError( ex.getMessage() );
        }
    }

    private void cerrarSesion() {
        app.guardarTodo();
        app.establecerVoluntarioActivo( null );
        app.mostrarPanel( "login" );
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
