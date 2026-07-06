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
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import pawconnect.modelo.Campana;
import pawconnect.modelo.Fundacion;
import pawconnect.modelo.Mascota;

public class PanelUsuario extends JPanel {

    private MainApp app;
    private JLabel etiquetaBienvenida;
    private JTextArea areaPerfil;
    private JTextArea areaNotificaciones;
    private DefaultListModel<Mascota> modeloMascotas;
    private JList<Mascota> listaMascotas;
    private JLabel etiquetaMensaje;
    private JTextField campoMontoDonacion;
    private JComboBox<Fundacion> comboFundacionDonar;
    private JComboBox<Object> comboCampanaDonar;
    private static final String OPCION_FONDO_GENERAL = "Sin campana (fondo general de la fundacion elegida)";

    private JComboBox<Object> comboFiltroEspecie;
    private JComboBox<Object> comboFiltroTamano;
    private JComboBox<Object> comboFiltroSexo;
    private JTextField campoFiltroRaza;
    private JTextField campoFiltroNombre;

    public PanelUsuario( MainApp app ) {
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

        JTabbedPane pestanas = new JTabbedPane();
        pestanas.addTab( "Mi perfil", construirPestanaPerfil() );
        pestanas.addTab( "Adopciones", construirPestanaAdopciones() );
        pestanas.addTab( "Donaciones", construirPestanaDonaciones() );
        add( pestanas, BorderLayout.CENTER );

        etiquetaMensaje = new JLabel( " " );
        etiquetaMensaje.setBorder( BorderFactory.createEmptyBorder( 8, 0, 0, 0 ) );
        add( etiquetaMensaje, BorderLayout.SOUTH );
    }

    private JPanel construirPestanaPerfil() {
        JPanel panel = new JPanel( new BorderLayout( 8, 8 ) );
        panel.setBackground( MainApp.COLOR_FONDO );

        JPanel panelDividido = new JPanel( new GridLayout( 1, 2, 15, 0 ) );
        panelDividido.setBackground( MainApp.COLOR_FONDO );

        JPanel panelPerfil = new JPanel( new BorderLayout( 5, 5 ) );
        panelPerfil.setBackground( MainApp.COLOR_FONDO );
        panelPerfil.setBorder( BorderFactory.createTitledBorder( "Mi perfil" ) );
        areaPerfil = new JTextArea();
        areaPerfil.setEditable( false );
        areaPerfil.setFont( new Font( "Monospaced", Font.PLAIN, 13 ) );
        areaPerfil.setBackground( Color.WHITE );
        panelPerfil.add( new JScrollPane( areaPerfil ), BorderLayout.CENTER );

        JPanel panelNotificaciones = new JPanel( new BorderLayout( 5, 5 ) );
        panelNotificaciones.setBackground( MainApp.COLOR_FONDO );
        panelNotificaciones.setBorder( BorderFactory.createTitledBorder( "Notificaciones" ) );
        areaNotificaciones = new JTextArea();
        areaNotificaciones.setEditable( false );
        areaNotificaciones.setFont( new Font( "SansSerif", Font.PLAIN, 13 ) );
        areaNotificaciones.setBackground( Color.WHITE );
        areaNotificaciones.setLineWrap( true );
        areaNotificaciones.setWrapStyleWord( true );
        panelNotificaciones.add( new JScrollPane( areaNotificaciones ), BorderLayout.CENTER );

        panelDividido.add( panelPerfil );
        panelDividido.add( panelNotificaciones );
        panel.add( panelDividido, BorderLayout.CENTER );

        return panel;
    }

    private JPanel construirPestanaAdopciones() {
        JPanel panel = new JPanel( new BorderLayout( 8, 8 ) );
        panel.setBackground( MainApp.COLOR_FONDO );

        JLabel etiquetaContexto = new JLabel( "<html><i>En esta seccion podras encontrar mascotas que buscan un hogar responsable.</i></html>" );
        etiquetaContexto.setBorder( BorderFactory.createEmptyBorder( 0, 0, 8, 0 ) );
        panel.add( etiquetaContexto, BorderLayout.NORTH );

        JPanel panelFiltros = new JPanel( new GridBagLayout() );
        panelFiltros.setBackground( MainApp.COLOR_FONDO );
        panelFiltros.setBorder( BorderFactory.createTitledBorder( "Filtros de busqueda" ) );
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets( 3, 5, 3, 5 );
        gbc.fill = GridBagConstraints.HORIZONTAL;

        comboFiltroEspecie = new JComboBox<Object>();
        comboFiltroEspecie.addItem( "Cualquiera" );
        for ( Mascota.Especie e : Mascota.Especie.values() ) {
            comboFiltroEspecie.addItem( e );
        }
        comboFiltroTamano = new JComboBox<Object>();
        comboFiltroTamano.addItem( "Cualquiera" );
        for ( Mascota.Tamano t : Mascota.Tamano.values() ) {
            comboFiltroTamano.addItem( t );
        }
        comboFiltroSexo = new JComboBox<Object>();
        comboFiltroSexo.addItem( "Cualquiera" );
        for ( Mascota.Sexo s : Mascota.Sexo.values() ) {
            comboFiltroSexo.addItem( s );
        }
        campoFiltroRaza = new JTextField( 10 );
        campoFiltroNombre = new JTextField( 10 );

        gbc.gridx = 0; gbc.gridy = 0; panelFiltros.add( new JLabel( "Especie" ), gbc );
        gbc.gridx = 1; panelFiltros.add( comboFiltroEspecie, gbc );
        gbc.gridx = 2; panelFiltros.add( new JLabel( "Tamano" ), gbc );
        gbc.gridx = 3; panelFiltros.add( comboFiltroTamano, gbc );

        gbc.gridx = 0; gbc.gridy = 1; panelFiltros.add( new JLabel( "Sexo" ), gbc );
        gbc.gridx = 1; panelFiltros.add( comboFiltroSexo, gbc );
        gbc.gridx = 2; panelFiltros.add( new JLabel( "Raza" ), gbc );
        gbc.gridx = 3; panelFiltros.add( campoFiltroRaza, gbc );

        gbc.gridx = 0; gbc.gridy = 2; panelFiltros.add( new JLabel( "Nombre" ), gbc );
        gbc.gridx = 1; panelFiltros.add( campoFiltroNombre, gbc );

        BotonEstilizado botonFiltrar = new BotonEstilizado( "Aplicar filtros", MainApp.COLOR_PRIMARIO );
        botonFiltrar.addActionListener( e -> aplicarFiltros() );
        gbc.gridx = 2; gbc.gridy = 2; gbc.gridwidth = 2;
        panelFiltros.add( botonFiltrar, gbc );

        JPanel panelSuperior = new JPanel( new BorderLayout() );
        panelSuperior.setBackground( MainApp.COLOR_FONDO );
        panelSuperior.add( etiquetaContexto, BorderLayout.NORTH );
        panelSuperior.add( panelFiltros, BorderLayout.CENTER );

        modeloMascotas = new DefaultListModel<Mascota>();
        listaMascotas = new JList<Mascota>( modeloMascotas );
        listaMascotas.setFont( new Font( "SansSerif", Font.PLAIN, 13 ) );
        listaMascotas.setCellRenderer( new javax.swing.ListCellRenderer<Mascota>() {
            @Override
            public java.awt.Component getListCellRendererComponent( JList<? extends Mascota> list, Mascota value,
                    int index, boolean isSelected, boolean cellHasFocus ) {
                Fundacion f = app.darGestorFundaciones().buscarFundacion( value.darCedulaFundacion() );
                String nombreFundacion = f == null ? "Fundacion desconocida" : f.darNombre();
                String urgente = value.esUrgente() ? " [URGENTE]" : "";
                JLabel etiqueta = new JLabel( value.darNombre() + " - " + value.darEspecie() + " " + value.darRaza()
                        + " (" + value.darSexo() + ", " + value.darEdadTexto() + ", " + value.darTamano() + ")"
                        + " - Fundacion: " + nombreFundacion + urgente );
                etiqueta.setOpaque( true );
                etiqueta.setBackground( isSelected ? MainApp.COLOR_SECUNDARIO : Color.WHITE );
                etiqueta.setBorder( BorderFactory.createEmptyBorder( 6, 8, 6, 8 ) );
                return etiqueta;
            }
        } );

        JPanel panelListaYBoton = new JPanel( new BorderLayout( 5, 5 ) );
        panelListaYBoton.setBackground( MainApp.COLOR_FONDO );
        panelListaYBoton.add( new JScrollPane( listaMascotas ), BorderLayout.CENTER );

        BotonEstilizado botonInteres = new BotonEstilizado( "Mostrar interes en mascota seleccionada", MainApp.COLOR_PRIMARIO );
        botonInteres.addActionListener( e -> mostrarInteres() );
        panelListaYBoton.add( botonInteres, BorderLayout.SOUTH );

        panel.removeAll();
        panel.add( panelSuperior, BorderLayout.NORTH );
        panel.add( panelListaYBoton, BorderLayout.CENTER );

        return panel;
    }

    private JPanel construirPestanaDonaciones() {
        JPanel panel = new JPanel( new BorderLayout( 8, 8 ) );
        panel.setBackground( MainApp.COLOR_FONDO );

        JLabel etiquetaContexto = new JLabel( "<html><i>En esta seccion podras apoyar a las fundaciones mediante donaciones.</i></html>" );
        etiquetaContexto.setBorder( BorderFactory.createEmptyBorder( 0, 0, 8, 0 ) );
        panel.add( etiquetaContexto, BorderLayout.NORTH );

        JPanel panelDonarFormulario = new JPanel( new GridBagLayout() );
        panelDonarFormulario.setBackground( MainApp.COLOR_FONDO );
        panelDonarFormulario.setBorder( BorderFactory.createTitledBorder( "Hacer una donacion" ) );
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets( 4, 6, 4, 6 );
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        panelDonarFormulario.add( new JLabel( "Fundacion destino" ), gbc );
        comboFundacionDonar = new JComboBox<Fundacion>();
        gbc.gridx = 1;
        panelDonarFormulario.add( comboFundacionDonar, gbc );

        gbc.gridx = 0;
        gbc.gridy = 1;
        panelDonarFormulario.add( new JLabel( "Campana (opcional)" ), gbc );
        comboCampanaDonar = new JComboBox<Object>();
        comboCampanaDonar.setRenderer( new javax.swing.ListCellRenderer<Object>() {
            @Override
            public java.awt.Component getListCellRendererComponent( JList<?> list, Object value, int index,
                    boolean isSelected, boolean cellHasFocus ) {
                String texto;
                if ( value instanceof Campana ) {
                    Campana c = (Campana) value;
                    Fundacion f = app.darGestorFundaciones().buscarFundacion( c.darCedulaFundacion() );
                    String nombreFundacion = f == null ? "Fundacion desconocida" : f.darNombre();
                    texto = c.darNombre() + " - Fundacion: " + nombreFundacion + " ($" + c.darMontoRecaudado()
                            + "/$" + c.darMontoMeta() + ") - Limite: " + c.darFechaFin();
                } else {
                    texto = String.valueOf( value );
                }
                JLabel etiqueta = new JLabel( texto );
                etiqueta.setOpaque( true );
                etiqueta.setBackground( isSelected ? MainApp.COLOR_SECUNDARIO : Color.WHITE );
                etiqueta.setBorder( BorderFactory.createEmptyBorder( 4, 6, 4, 6 ) );
                return etiqueta;
            }
        } );
        gbc.gridx = 1;
        panelDonarFormulario.add( comboCampanaDonar, gbc );

        gbc.gridx = 0;
        gbc.gridy = 2;
        panelDonarFormulario.add( new JLabel( "Monto a donar ($)" ), gbc );
        campoMontoDonacion = new JTextField( 10 );
        gbc.gridx = 1;
        panelDonarFormulario.add( campoMontoDonacion, gbc );

        BotonEstilizado botonDonar = new BotonEstilizado( "Donar", MainApp.COLOR_PRIMARIO );
        botonDonar.addActionListener( e -> donar() );
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        panelDonarFormulario.add( botonDonar, gbc );

        panel.add( panelDonarFormulario, BorderLayout.CENTER );
        return panel;
    }

    public void refrescar() {
        if ( app.darUsuarioActivo() == null ) {
            return;
        }
        etiquetaBienvenida.setText( "Hola, " + app.darUsuarioActivo().darNombre() );
        areaPerfil.setText( app.darUsuarioActivo().mostrarPerfil() );

        if ( app.darUsuarioActivo().darNotificaciones().isEmpty() ) {
            areaNotificaciones.setText( "No tienes notificaciones nuevas." );
        } else {
            StringBuilder texto = new StringBuilder();
            for ( String mensaje : app.darUsuarioActivo().darNotificaciones() ) {
                texto.append( "• " ).append( mensaje ).append( "\n\n" );
            }
            areaNotificaciones.setText( texto.toString() );
            app.darUsuarioActivo().limpiarNotificaciones();
        }

        aplicarFiltros();

        comboFundacionDonar.removeAllItems();
        for ( Fundacion f : app.darGestorFundaciones().darFundaciones() ) {
            comboFundacionDonar.addItem( f );
        }

        comboCampanaDonar.removeAllItems();
        comboCampanaDonar.addItem( OPCION_FONDO_GENERAL );
        for ( Campana c : app.darGestorDonaciones().listarCampanasDisponiblesParaDonar() ) {
            comboCampanaDonar.addItem( c );
        }

        limpiarMensaje();
    }

    private void aplicarFiltros() {
        Object especieSel = comboFiltroEspecie.getSelectedItem();
        Object tamanoSel = comboFiltroTamano.getSelectedItem();
        Object sexoSel = comboFiltroSexo.getSelectedItem();
        Mascota.Especie especie = especieSel instanceof Mascota.Especie ? (Mascota.Especie) especieSel : null;
        Mascota.Tamano tamano = tamanoSel instanceof Mascota.Tamano ? (Mascota.Tamano) tamanoSel : null;
        Mascota.Sexo sexo = sexoSel instanceof Mascota.Sexo ? (Mascota.Sexo) sexoSel : null;
        String raza = campoFiltroRaza.getText();
        String nombre = campoFiltroNombre.getText();

        modeloMascotas.clear();
        for ( Mascota m : app.darGestorMascotas().filtrarMascotas( especie, tamano, sexo, raza, nombre,
                null, null, true ) ) {
            modeloMascotas.addElement( m );
        }
    }

    private void mostrarInteres() {
        Mascota seleccionada = listaMascotas.getSelectedValue();
        if ( seleccionada == null ) {
            mostrarError( "Selecciona una mascota de la lista." );
            return;
        }
        try {
            app.darGestorMascotas().marcarInteres( seleccionada.darId(), app.darUsuarioActivo().darCedula() );
            mostrarExito( "Mostraste interes en " + seleccionada.darNombre() + " correctamente." );
        } catch ( Exception ex ) {
            mostrarError( ex.getMessage() );
        }
    }

    private void donar() {
        Object seleccionCampana = comboCampanaDonar.getSelectedItem();
        String cedulaFundacionDestino;
        String idCampana = null;
        String nombreDestino;
        String concepto;

        if ( seleccionCampana instanceof Campana ) {
            Campana c = (Campana) seleccionCampana;
            cedulaFundacionDestino = c.darCedulaFundacion();
            idCampana = c.darId();
            Fundacion f = app.darGestorFundaciones().buscarFundacion( cedulaFundacionDestino );
            nombreDestino = f == null ? "la fundacion" : f.darNombre();
            concepto = "Aporte a campana: " + c.darNombre();
        } else {
            Fundacion fundacionSeleccionada = (Fundacion) comboFundacionDonar.getSelectedItem();
            if ( fundacionSeleccionada == null ) {
                mostrarError( "No hay fundaciones registradas todavia para donar." );
                return;
            }
            cedulaFundacionDestino = fundacionSeleccionada.darCedula();
            nombreDestino = fundacionSeleccionada.darNombre();
            concepto = "Donacion general";
        }

        try {
            double monto = Double.parseDouble( campoMontoDonacion.getText().trim() );
            app.darGestorDonaciones().registrarDonacion( app.darUsuarioActivo(), cedulaFundacionDestino,
                    monto, concepto, idCampana );
            campoMontoDonacion.setText( "" );
            areaPerfil.setText( app.darUsuarioActivo().mostrarPerfil() );
            comboCampanaDonar.removeAllItems();
            comboCampanaDonar.addItem( OPCION_FONDO_GENERAL );
            for ( Campana c : app.darGestorDonaciones().listarCampanasDisponiblesParaDonar() ) {
                comboCampanaDonar.addItem( c );
            }
            mostrarExito( "Donacion registrada con exito para " + nombreDestino + ". Gracias por tu apoyo." );
        } catch ( NumberFormatException nfe ) {
            mostrarError( "Ingresa un monto valido." );
        } catch ( Exception ex ) {
            mostrarError( ex.getMessage() );
        }
    }

    private void cerrarSesion() {
        app.guardarTodo();
        app.establecerUsuarioActivo( null );
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
