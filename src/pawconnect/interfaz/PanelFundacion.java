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
import pawconnect.modelo.Donacion;
import pawconnect.modelo.Mascota;
import pawconnect.modelo.SolicitudVoluntariado;
import pawconnect.modelo.TipoActividad;
import pawconnect.modelo.Usuario;
import pawconnect.modelo.Voluntario;
import pawconnect.modelo.Zona;

public class PanelFundacion extends JPanel {

    private MainApp app;
    private JLabel etiquetaBienvenida;
    private JLabel etiquetaMensaje;

    private DefaultListModel<Mascota> modeloMascotas;
    private JList<Mascota> listaMascotas;
    private JTextField campoIdMascota;
    private JTextField campoNombreMascota;
    private JComboBox<Mascota.Especie> comboEspecieMascota;
    private JTextField campoRazaMascota;
    private JComboBox<Mascota.Sexo> comboSexoMascota;
    private JTextField campoValorEdadMascota;
    private JComboBox<Mascota.UnidadEdad> comboUnidadEdadMascota;
    private JComboBox<Mascota.Tamano> comboTamanoMascota;
    private JTextField campoSaludMascota;

    private DefaultListModel<Usuario> modeloInteresados;
    private JList<Usuario> listaInteresados;
    private JTextField campoIdMascotaConsulta;
    private JTextArea areaPerfilCandidato;

    private DefaultListModel<Campana> modeloCampanas;
    private JList<Campana> listaCampanas;
    private JTextField campoNombreCampana;
    private JTextField campoDescripcionCampana;
    private JTextField campoMetaCampana;
    private JTextField campoFechaInicioCampana;
    private JTextField campoFechaFinCampana;
    private JTextField campoIdCampanaAccion;
    private JTextField campoNuevaFechaCampana;

    private DefaultListModel<Donacion> modeloDonacionesRecibidas;
    private JList<Donacion> listaDonacionesRecibidas;
    private JLabel etiquetaTotalRecaudado;

    private DefaultListModel<SolicitudVoluntariado> modeloSolicitudes;
    private JList<SolicitudVoluntariado> listaSolicitudes;
    private JTextField campoDescripcionSolicitud;
    private JComboBox<Zona> comboZonaSolicitud;
    private JComboBox<TipoActividad> comboTipoSolicitud;
    private JTextField campoHorasSolicitud;
    private javax.swing.JCheckBox checkExperienciaSolicitud;
    private JTextField campoFechaInicioSolicitud;
    private JTextField campoFechaFinSolicitud;

    private JTextField campoIdSolicitudConsulta;
    private DefaultListModel<Voluntario> modeloPostulantes;
    private JList<Voluntario> listaPostulantes;

    public PanelFundacion( MainApp app ) {
        this.app = app;
        setBackground( MainApp.COLOR_FONDO );
        construirInterfaz();
    }

    private void construirInterfaz() {
        setLayout( new BorderLayout( 10, 10 ) );
        setBorder( BorderFactory.createEmptyBorder( 15, 20, 15, 20 ) );

        JPanel panelSuperior = new JPanel( new BorderLayout() );
        panelSuperior.setBackground( MainApp.COLOR_FONDO );
        etiquetaBienvenida = new JLabel( "Panel de fundacion" );
        etiquetaBienvenida.setFont( new Font( "SansSerif", Font.BOLD, 22 ) );
        etiquetaBienvenida.setForeground( MainApp.COLOR_PRIMARIO );
        panelSuperior.add( etiquetaBienvenida, BorderLayout.WEST );

        BotonEstilizado botonSalir = new BotonEstilizado( "Cerrar sesion", MainApp.COLOR_SECUNDARIO );
        botonSalir.addActionListener( e -> cerrarSesion() );
        panelSuperior.add( botonSalir, BorderLayout.EAST );
        add( panelSuperior, BorderLayout.NORTH );

        JTabbedPane pestanas = new JTabbedPane();
        pestanas.addTab( "Mascotas", construirPestanaMascotas() );
        pestanas.addTab( "Interesados", construirPestanaInteresados() );
        pestanas.addTab( "Campanas", construirPestanaCampanas() );
        pestanas.addTab( "Donaciones recibidas", construirPestanaDonacionesRecibidas() );
        pestanas.addTab( "Voluntariado", construirPestanaSolicitudes() );
        pestanas.addTab( "Postulantes", construirPestanaPostulantes() );
        add( pestanas, BorderLayout.CENTER );

        etiquetaMensaje = new JLabel( " " );
        etiquetaMensaje.setBorder( BorderFactory.createEmptyBorder( 8, 0, 0, 0 ) );
        add( etiquetaMensaje, BorderLayout.SOUTH );
    }

    private JPanel construirPestanaMascotas() {
        JPanel contenedor = new JPanel( new BorderLayout( 5, 5 ) );
        contenedor.setBackground( MainApp.COLOR_FONDO );
        JLabel contexto = new JLabel( "<html><i>Administra las mascotas de tu fundacion: registra, consulta y elimina.</i></html>" );
        contenedor.add( contexto, BorderLayout.NORTH );

        JPanel panel = new JPanel( new GridLayout( 1, 2, 15, 0 ) );
        panel.setBackground( MainApp.COLOR_FONDO );

        modeloMascotas = new DefaultListModel<Mascota>();
        listaMascotas = new JList<Mascota>( modeloMascotas );
        JPanel panelLista = new JPanel( new BorderLayout( 5, 5 ) );
        panelLista.setBackground( MainApp.COLOR_FONDO );
        panelLista.setBorder( BorderFactory.createTitledBorder( "Mis mascotas registradas" ) );
        panelLista.add( new JScrollPane( listaMascotas ), BorderLayout.CENTER );

        BotonEstilizado botonEliminar = new BotonEstilizado( "Eliminar mascota seleccionada", MainApp.COLOR_ERROR );
        botonEliminar.addActionListener( e -> eliminarMascota() );
        panelLista.add( botonEliminar, BorderLayout.SOUTH );

        JPanel panelFormulario = new JPanel( new GridBagLayout() );
        panelFormulario.setBackground( MainApp.COLOR_FONDO );
        panelFormulario.setBorder( BorderFactory.createTitledBorder( "Registrar nueva mascota" ) );
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets( 4, 6, 4, 6 );
        gbc.fill = GridBagConstraints.HORIZONTAL;

        campoIdMascota = new JTextField( 15 );
        campoNombreMascota = new JTextField( 15 );
        comboEspecieMascota = new JComboBox<Mascota.Especie>( Mascota.Especie.values() );
        campoRazaMascota = new JTextField( 15 );
        comboSexoMascota = new JComboBox<Mascota.Sexo>( Mascota.Sexo.values() );
        campoValorEdadMascota = new JTextField( 6 );
        comboUnidadEdadMascota = new JComboBox<Mascota.UnidadEdad>( Mascota.UnidadEdad.values() );
        comboTamanoMascota = new JComboBox<Mascota.Tamano>( Mascota.Tamano.values() );
        campoSaludMascota = new JTextField( 15 );

        agregarCampo( panelFormulario, gbc, 0, "Identificador unico", campoIdMascota );
        agregarCampo( panelFormulario, gbc, 1, "Nombre", campoNombreMascota );
        agregarCampo( panelFormulario, gbc, 2, "Especie", comboEspecieMascota );
        agregarCampo( panelFormulario, gbc, 3, "Raza", campoRazaMascota );
        agregarCampo( panelFormulario, gbc, 4, "Sexo", comboSexoMascota );

        JPanel panelEdad = new JPanel( new BorderLayout( 4, 0 ) );
        panelEdad.setBackground( MainApp.COLOR_FONDO );
        panelEdad.add( campoValorEdadMascota, BorderLayout.WEST );
        panelEdad.add( comboUnidadEdadMascota, BorderLayout.CENTER );
        agregarCampo( panelFormulario, gbc, 5, "Edad", panelEdad );

        agregarCampo( panelFormulario, gbc, 6, "Tamano", comboTamanoMascota );
        agregarCampo( panelFormulario, gbc, 7, "Estado de salud", campoSaludMascota );

        BotonEstilizado botonAgregar = new BotonEstilizado( "Agregar mascota", MainApp.COLOR_PRIMARIO );
        botonAgregar.addActionListener( e -> agregarMascota() );
        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.gridwidth = 2;
        panelFormulario.add( botonAgregar, gbc );

        panel.add( panelLista );
        panel.add( panelFormulario );
        contenedor.add( panel, BorderLayout.CENTER );
        return contenedor;
    }

    private JPanel construirPestanaInteresados() {
        JPanel panel = new JPanel( new BorderLayout( 8, 8 ) );
        panel.setBackground( MainApp.COLOR_FONDO );

        JLabel contexto = new JLabel( "<html><i>Consulta el ranking de interesados en cada mascota y revisa el perfil completo antes de decidir.</i></html>" );
        panel.add( contexto, BorderLayout.NORTH );

        JPanel panelCentral = new JPanel( new GridLayout( 1, 2, 10, 0 ) );
        panelCentral.setBackground( MainApp.COLOR_FONDO );

        JPanel panelIzquierdo = new JPanel( new BorderLayout( 8, 8 ) );
        panelIzquierdo.setBackground( MainApp.COLOR_FONDO );

        JPanel panelSuperior = new JPanel( new BorderLayout( 8, 8 ) );
        panelSuperior.setBackground( MainApp.COLOR_FONDO );
        campoIdMascotaConsulta = new JTextField();
        panelSuperior.add( new JLabel( "Identificador de la mascota: " ), BorderLayout.WEST );
        panelSuperior.add( campoIdMascotaConsulta, BorderLayout.CENTER );
        BotonEstilizado botonConsultar = new BotonEstilizado( "Ver interesados ordenados", MainApp.COLOR_PRIMARIO );
        botonConsultar.addActionListener( e -> consultarInteresados() );
        panelSuperior.add( botonConsultar, BorderLayout.EAST );
        panelIzquierdo.add( panelSuperior, BorderLayout.NORTH );

        modeloInteresados = new DefaultListModel<Usuario>();
        listaInteresados = new JList<Usuario>( modeloInteresados );
        listaInteresados.addListSelectionListener( e -> mostrarPerfilCandidatoSeleccionado() );
        listaInteresados.setCellRenderer( new javax.swing.ListCellRenderer<Usuario>() {
            @Override
            public java.awt.Component getListCellRendererComponent( JList<? extends Usuario> list, Usuario value,
                    int index, boolean isSelected, boolean cellHasFocus ) {
                JLabel etiqueta = new JLabel( ( index + 1 ) + ". " + value.darNombre() + " - Puntaje: "
                        + value.calcularPuntajeAdoptante() + "/100" );
                etiqueta.setOpaque( true );
                etiqueta.setBackground( isSelected ? MainApp.COLOR_SECUNDARIO : Color.WHITE );
                etiqueta.setBorder( BorderFactory.createEmptyBorder( 6, 8, 6, 8 ) );
                return etiqueta;
            }
        } );
        panelIzquierdo.add( new JScrollPane( listaInteresados ), BorderLayout.CENTER );

        BotonEstilizado botonAceptar = new BotonEstilizado( "Aceptar adopcion con seleccionado", MainApp.COLOR_PRIMARIO );
        botonAceptar.addActionListener( e -> aceptarAdopcion() );
        panelIzquierdo.add( botonAceptar, BorderLayout.SOUTH );

        JPanel panelDerecho = new JPanel( new BorderLayout( 5, 5 ) );
        panelDerecho.setBackground( MainApp.COLOR_FONDO );
        panelDerecho.setBorder( BorderFactory.createTitledBorder( "Perfil completo del candidato seleccionado" ) );
        areaPerfilCandidato = new JTextArea();
        areaPerfilCandidato.setEditable( false );
        areaPerfilCandidato.setFont( new Font( "Monospaced", Font.PLAIN, 13 ) );
        areaPerfilCandidato.setBackground( Color.WHITE );
        panelDerecho.add( new JScrollPane( areaPerfilCandidato ), BorderLayout.CENTER );

        panelCentral.add( panelIzquierdo );
        panelCentral.add( panelDerecho );
        panel.add( panelCentral, BorderLayout.CENTER );

        return panel;
    }

    private JPanel construirPestanaCampanas() {
        JPanel contenedor = new JPanel( new BorderLayout( 5, 5 ) );
        contenedor.setBackground( MainApp.COLOR_FONDO );
        JLabel contexto = new JLabel( "<html><i>Crea campanas de recaudacion con fecha limite. Al vencer, decide: eliminar, extender o continuar recaudando.</i></html>" );
        contenedor.add( contexto, BorderLayout.NORTH );

        JPanel panel = new JPanel( new GridLayout( 1, 2, 15, 0 ) );
        panel.setBackground( MainApp.COLOR_FONDO );

        modeloCampanas = new DefaultListModel<Campana>();
        listaCampanas = new JList<Campana>( modeloCampanas );
        listaCampanas.setCellRenderer( new javax.swing.ListCellRenderer<Campana>() {
            @Override
            public java.awt.Component getListCellRendererComponent( JList<? extends Campana> list, Campana value,
                    int index, boolean isSelected, boolean cellHasFocus ) {
                JLabel etiqueta = new JLabel( "<html>" + value.toString() + "<br/><i>" + value.darMensajeEstado()
                        + "</i></html>" );
                etiqueta.setOpaque( true );
                etiqueta.setBackground( isSelected ? MainApp.COLOR_SECUNDARIO : Color.WHITE );
                etiqueta.setBorder( BorderFactory.createEmptyBorder( 6, 8, 6, 8 ) );
                return etiqueta;
            }
        } );
        JPanel panelLista = new JPanel( new BorderLayout( 5, 5 ) );
        panelLista.setBackground( MainApp.COLOR_FONDO );
        panelLista.setBorder( BorderFactory.createTitledBorder( "Mis campanas de recaudacion" ) );
        panelLista.add( new JScrollPane( listaCampanas ), BorderLayout.CENTER );

        JPanel panelAccionesCampana = new JPanel( new GridBagLayout() );
        panelAccionesCampana.setBackground( MainApp.COLOR_FONDO );
        GridBagConstraints gbcAcc = new GridBagConstraints();
        gbcAcc.insets = new Insets( 3, 4, 3, 4 );
        gbcAcc.fill = GridBagConstraints.HORIZONTAL;

        campoIdCampanaAccion = new JTextField( 8 );
        campoNuevaFechaCampana = new JTextField( 10 );

        gbcAcc.gridx = 0; gbcAcc.gridy = 0;
        panelAccionesCampana.add( new JLabel( "ID campana" ), gbcAcc );
        gbcAcc.gridx = 1;
        panelAccionesCampana.add( campoIdCampanaAccion, gbcAcc );
        gbcAcc.gridx = 2;
        panelAccionesCampana.add( new JLabel( "Nueva fecha limite (aaaa-mm-dd)" ), gbcAcc );
        gbcAcc.gridx = 3;
        panelAccionesCampana.add( campoNuevaFechaCampana, gbcAcc );

        BotonEstilizado botonExtender = new BotonEstilizado( "Extender fecha limite", MainApp.COLOR_SECUNDARIO );
        botonExtender.addActionListener( e -> extenderCampana() );
        gbcAcc.gridx = 4;
        panelAccionesCampana.add( botonExtender, gbcAcc );

        BotonEstilizado botonEliminarCampana = new BotonEstilizado( "Eliminar campana", MainApp.COLOR_ERROR );
        botonEliminarCampana.addActionListener( e -> eliminarCampana() );
        gbcAcc.gridx = 5;
        panelAccionesCampana.add( botonEliminarCampana, gbcAcc );

        panelLista.add( panelAccionesCampana, BorderLayout.SOUTH );

        JPanel panelFormulario = new JPanel( new GridBagLayout() );
        panelFormulario.setBackground( MainApp.COLOR_FONDO );
        panelFormulario.setBorder( BorderFactory.createTitledBorder( "Crear nueva campana" ) );
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets( 4, 6, 4, 6 );
        gbc.fill = GridBagConstraints.HORIZONTAL;

        campoNombreCampana = new JTextField( 15 );
        campoDescripcionCampana = new JTextField( 15 );
        campoMetaCampana = new JTextField( 15 );
        campoFechaInicioCampana = new JTextField( 15 );
        campoFechaFinCampana = new JTextField( 15 );

        agregarCampo( panelFormulario, gbc, 0, "Nombre de la campana", campoNombreCampana );
        agregarCampo( panelFormulario, gbc, 1, "Descripcion", campoDescripcionCampana );
        agregarCampo( panelFormulario, gbc, 2, "Monto meta ($)", campoMetaCampana );
        agregarCampo( panelFormulario, gbc, 3, "Fecha inicio (aaaa-mm-dd)", campoFechaInicioCampana );
        agregarCampo( panelFormulario, gbc, 4, "Fecha limite (aaaa-mm-dd)", campoFechaFinCampana );

        BotonEstilizado botonCrear = new BotonEstilizado( "Crear campana", MainApp.COLOR_PRIMARIO );
        botonCrear.addActionListener( e -> crearCampana() );
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        panelFormulario.add( botonCrear, gbc );

        panel.add( panelLista );
        panel.add( panelFormulario );
        contenedor.add( panel, BorderLayout.CENTER );
        return contenedor;
    }

    private JPanel construirPestanaDonacionesRecibidas() {
        JPanel panel = new JPanel( new BorderLayout( 8, 8 ) );
        panel.setBackground( MainApp.COLOR_FONDO );

        etiquetaTotalRecaudado = new JLabel( "Total recaudado: $0.0" );
        etiquetaTotalRecaudado.setFont( new Font( "SansSerif", Font.BOLD, 16 ) );
        etiquetaTotalRecaudado.setForeground( MainApp.COLOR_PRIMARIO );
        etiquetaTotalRecaudado.setBorder( BorderFactory.createEmptyBorder( 0, 0, 8, 0 ) );
        panel.add( etiquetaTotalRecaudado, BorderLayout.NORTH );

        modeloDonacionesRecibidas = new DefaultListModel<Donacion>();
        listaDonacionesRecibidas = new JList<Donacion>( modeloDonacionesRecibidas );
        listaDonacionesRecibidas.setCellRenderer( new javax.swing.ListCellRenderer<Donacion>() {
            @Override
            public java.awt.Component getListCellRendererComponent( JList<? extends Donacion> list, Donacion value,
                    int index, boolean isSelected, boolean cellHasFocus ) {
                Usuario u = app.darGestorUsuarios().buscarUsuario( value.darCedulaUsuario() );
                String nombreDonante = u == null ? value.darCedulaUsuario() : u.darNombre();
                String textoCampana = value.darIdCampana() == null ? "Fondo general" : "Campana " + value.darIdCampana();
                JLabel etiqueta = new JLabel( nombreDonante + " - $" + value.darMonto() + " - " + value.darConcepto()
                        + " (" + textoCampana + ")" );
                etiqueta.setOpaque( true );
                etiqueta.setBackground( isSelected ? MainApp.COLOR_SECUNDARIO : Color.WHITE );
                etiqueta.setBorder( BorderFactory.createEmptyBorder( 6, 8, 6, 8 ) );
                return etiqueta;
            }
        } );
        panel.add( new JScrollPane( listaDonacionesRecibidas ), BorderLayout.CENTER );

        return panel;
    }

    private JPanel construirPestanaSolicitudes() {
        JPanel contenedor = new JPanel( new BorderLayout( 5, 5 ) );
        contenedor.setBackground( MainApp.COLOR_FONDO );
        JLabel contexto = new JLabel( "<html><i>Publica solicitudes de voluntariado indicando periodo de vigencia, zona y tipo de actividad requerida.</i></html>" );
        contenedor.add( contexto, BorderLayout.NORTH );

        JPanel panel = new JPanel( new GridLayout( 1, 2, 15, 0 ) );
        panel.setBackground( MainApp.COLOR_FONDO );

        modeloSolicitudes = new DefaultListModel<SolicitudVoluntariado>();
        listaSolicitudes = new JList<SolicitudVoluntariado>( modeloSolicitudes );
        JPanel panelLista = new JPanel( new BorderLayout( 5, 5 ) );
        panelLista.setBackground( MainApp.COLOR_FONDO );
        panelLista.setBorder( BorderFactory.createTitledBorder( "Mis solicitudes de voluntariado" ) );
        panelLista.add( new JScrollPane( listaSolicitudes ), BorderLayout.CENTER );

        BotonEstilizado botonEliminarSolicitud = new BotonEstilizado( "Eliminar solicitud seleccionada", MainApp.COLOR_ERROR );
        botonEliminarSolicitud.addActionListener( e -> eliminarSolicitud() );
        panelLista.add( botonEliminarSolicitud, BorderLayout.SOUTH );

        JPanel panelFormulario = new JPanel( new GridBagLayout() );
        panelFormulario.setBackground( MainApp.COLOR_FONDO );
        panelFormulario.setBorder( BorderFactory.createTitledBorder( "Publicar solicitud" ) );
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets( 4, 6, 4, 6 );
        gbc.fill = GridBagConstraints.HORIZONTAL;

        campoDescripcionSolicitud = new JTextField( 15 );
        comboZonaSolicitud = new JComboBox<Zona>( Zona.values() );
        comboTipoSolicitud = new JComboBox<TipoActividad>( TipoActividad.values() );
        campoHorasSolicitud = new JTextField( 15 );
        checkExperienciaSolicitud = new javax.swing.JCheckBox( "Requiere experiencia previa" );
        campoFechaInicioSolicitud = new JTextField( 15 );
        campoFechaFinSolicitud = new JTextField( 15 );

        agregarCampo( panelFormulario, gbc, 0, "Descripcion", campoDescripcionSolicitud );
        agregarCampo( panelFormulario, gbc, 1, "Zona requerida", comboZonaSolicitud );
        agregarCampo( panelFormulario, gbc, 2, "Tipo de actividad", comboTipoSolicitud );
        agregarCampo( panelFormulario, gbc, 3, "Horas minimas por semana", campoHorasSolicitud );
        agregarCampo( panelFormulario, gbc, 4, "Fecha inicio (aaaa-mm-dd)", campoFechaInicioSolicitud );
        agregarCampo( panelFormulario, gbc, 5, "Fecha fin (aaaa-mm-dd)", campoFechaFinSolicitud );

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        panelFormulario.add( checkExperienciaSolicitud, gbc );

        BotonEstilizado botonPublicar = new BotonEstilizado( "Publicar solicitud", MainApp.COLOR_PRIMARIO );
        botonPublicar.addActionListener( e -> publicarSolicitud() );
        gbc.gridy = 7;
        panelFormulario.add( botonPublicar, gbc );

        panel.add( panelLista );
        panel.add( panelFormulario );
        contenedor.add( panel, BorderLayout.CENTER );
        return contenedor;
    }

    private JPanel construirPestanaPostulantes() {
        JPanel panel = new JPanel( new BorderLayout( 8, 8 ) );
        panel.setBackground( MainApp.COLOR_FONDO );

        JLabel contexto = new JLabel( "<html><i>Revisa quienes se postularon a tus solicitudes de voluntariado, incluyendo sus horas de experiencia previa.</i></html>" );
        panel.add( contexto, BorderLayout.NORTH );

        JPanel panelCuerpo = new JPanel( new BorderLayout( 8, 8 ) );
        panelCuerpo.setBackground( MainApp.COLOR_FONDO );

        JPanel panelSuperior = new JPanel( new BorderLayout( 8, 8 ) );
        panelSuperior.setBackground( MainApp.COLOR_FONDO );
        campoIdSolicitudConsulta = new JTextField();
        panelSuperior.add( new JLabel( "Identificador de la solicitud: " ), BorderLayout.WEST );
        panelSuperior.add( campoIdSolicitudConsulta, BorderLayout.CENTER );
        BotonEstilizado botonConsultar = new BotonEstilizado( "Ver postulantes pendientes", MainApp.COLOR_PRIMARIO );
        botonConsultar.addActionListener( e -> consultarPostulantes() );
        panelSuperior.add( botonConsultar, BorderLayout.EAST );
        panelCuerpo.add( panelSuperior, BorderLayout.NORTH );

        modeloPostulantes = new DefaultListModel<Voluntario>();
        listaPostulantes = new JList<Voluntario>( modeloPostulantes );
        listaPostulantes.setCellRenderer( new javax.swing.ListCellRenderer<Voluntario>() {
            @Override
            public java.awt.Component getListCellRendererComponent( JList<? extends Voluntario> list, Voluntario value,
                    int index, boolean isSelected, boolean cellHasFocus ) {
                SolicitudVoluntariado sol = app.darGestorVoluntarios().buscarSolicitud( campoIdSolicitudConsulta.getText().trim() );
                boolean cumple = sol != null && value.cumpleRequisitos( sol.darZonaRequerida(),
                        sol.darHorasMinimasSemana(), sol.darExperienciaRequerida() );
                String indicador = cumple ? " [Cumple requisitos]" : " [No cumple todos los requisitos]";
                String horasTexto = value.darResumenHorasPorTipo().replace( "\n", " | " );
                String textoHtml = "<html>" + value.darNombre() + " - Zona: " + value.darZona() + " - Nivel: "
                        + value.darNivel() + indicador + "<br/><i>" + horasTexto + "</i></html>";
                JLabel etiqueta = new JLabel( textoHtml );
                etiqueta.setOpaque( true );
                etiqueta.setBackground( isSelected ? MainApp.COLOR_SECUNDARIO : Color.WHITE );
                etiqueta.setBorder( BorderFactory.createEmptyBorder( 6, 8, 6, 8 ) );
                return etiqueta;
            }
        } );
        panelCuerpo.add( new JScrollPane( listaPostulantes ), BorderLayout.CENTER );

        JPanel panelBotones = new JPanel( new GridLayout( 1, 2, 10, 0 ) );
        panelBotones.setBackground( MainApp.COLOR_FONDO );
        BotonEstilizado botonAceptar = new BotonEstilizado( "Aceptar voluntario seleccionado", MainApp.COLOR_PRIMARIO );
        botonAceptar.addActionListener( e -> responderPostulante( true ) );
        BotonEstilizado botonRechazar = new BotonEstilizado( "Rechazar voluntario seleccionado", MainApp.COLOR_ERROR );
        botonRechazar.addActionListener( e -> responderPostulante( false ) );
        panelBotones.add( botonAceptar );
        panelBotones.add( botonRechazar );
        panelCuerpo.add( panelBotones, BorderLayout.SOUTH );

        panel.add( panelCuerpo, BorderLayout.CENTER );
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

    public void refrescar() {
        if ( app.darFundacionActiva() == null ) {
            return;
        }
        etiquetaBienvenida.setText( "Panel de " + app.darFundacionActiva().darNombre() );
        refrescarListaMascotas();
        refrescarListaCampanas();
        refrescarListaDonacionesRecibidas();
        refrescarListaSolicitudes();
        modeloInteresados.clear();
        modeloPostulantes.clear();
        areaPerfilCandidato.setText( "" );
        limpiarMensaje();
    }

    private void refrescarListaMascotas() {
        modeloMascotas.clear();
        for ( Mascota m : app.darGestorMascotas().listarPorFundacion( app.darFundacionActiva().darCedula() ) ) {
            modeloMascotas.addElement( m );
        }
    }

    private void refrescarListaCampanas() {
        modeloCampanas.clear();
        for ( Campana c : app.darGestorDonaciones().listarCampanasPorFundacion( app.darFundacionActiva().darCedula() ) ) {
            modeloCampanas.addElement( c );
        }
    }

    private void refrescarListaDonacionesRecibidas() {
        modeloDonacionesRecibidas.clear();
        String cedulaFundacion = app.darFundacionActiva().darCedula();
        for ( Donacion d : app.darGestorDonaciones().darDonaciones() ) {
            if ( d.darCedulaFundacion().equals( cedulaFundacion ) ) {
                modeloDonacionesRecibidas.addElement( d );
            }
        }
        double total = app.darGestorDonaciones().calcularTotalRecaudadoPorFundacion( cedulaFundacion );
        etiquetaTotalRecaudado.setText( "Total recaudado: $" + total );
    }

    private void refrescarListaSolicitudes() {
        modeloSolicitudes.clear();
        for ( SolicitudVoluntariado s : app.darGestorVoluntarios().listarSolicitudesPorFundacion( app.darFundacionActiva().darCedula() ) ) {
            modeloSolicitudes.addElement( s );
        }
    }

    private void agregarMascota() {
        try {
            int valorEdad = Integer.parseInt( campoValorEdadMascota.getText().trim() );
            app.darGestorMascotas().registrarMascota( campoIdMascota.getText().trim(), campoNombreMascota.getText().trim(),
                    (Mascota.Especie) comboEspecieMascota.getSelectedItem(), campoRazaMascota.getText().trim(),
                    (Mascota.Sexo) comboSexoMascota.getSelectedItem(), valorEdad,
                    (Mascota.UnidadEdad) comboUnidadEdadMascota.getSelectedItem(),
                    (Mascota.Tamano) comboTamanoMascota.getSelectedItem(), campoSaludMascota.getText().trim(),
                    app.darFundacionActiva().darCedula() );
            refrescarListaMascotas();
            limpiarCamposMascota();
            mostrarExito( "Mascota agregada con exito." );
        } catch ( NumberFormatException nfe ) {
            mostrarError( "La edad debe ser un numero entero." );
        } catch ( Exception ex ) {
            mostrarError( ex.getMessage() );
        }
    }

    private void limpiarCamposMascota() {
        campoIdMascota.setText( "" );
        campoNombreMascota.setText( "" );
        campoRazaMascota.setText( "" );
        campoValorEdadMascota.setText( "" );
        campoSaludMascota.setText( "" );
    }

    private void eliminarMascota() {
        Mascota seleccionada = listaMascotas.getSelectedValue();
        if ( seleccionada == null ) {
            mostrarError( "Selecciona una mascota para eliminar." );
            return;
        }
        try {
            app.darGestorMascotas().eliminarMascota( seleccionada.darId(), app.darFundacionActiva().darCedula() );
            refrescarListaMascotas();
            mostrarExito( "Mascota eliminada con exito." );
        } catch ( Exception ex ) {
            mostrarError( ex.getMessage() );
        }
    }

    private void consultarInteresados() {
        try {
            modeloInteresados.clear();
            for ( Usuario u : app.darGestorMascotas().listarInteresadosOrdenados(
                    campoIdMascotaConsulta.getText().trim(), app.darGestorUsuarios() ) ) {
                modeloInteresados.addElement( u );
            }
            areaPerfilCandidato.setText( "" );
            if ( modeloInteresados.isEmpty() ) {
                mostrarError( "Esta mascota aun no tiene interesados." );
            } else {
                mostrarExito( "Interesados ordenados de mejor a peor candidato. Selecciona uno para ver su perfil completo." );
            }
        } catch ( Exception ex ) {
            mostrarError( ex.getMessage() );
        }
    }

    private void mostrarPerfilCandidatoSeleccionado() {
        Usuario seleccionado = listaInteresados.getSelectedValue();
        if ( seleccionado == null ) {
            areaPerfilCandidato.setText( "" );
            return;
        }
        areaPerfilCandidato.setText( seleccionado.mostrarPerfil() );
    }

    private void aceptarAdopcion() {
        Usuario seleccionado = listaInteresados.getSelectedValue();
        if ( seleccionado == null ) {
            mostrarError( "Selecciona un interesado de la lista." );
            return;
        }
        try {
            app.darGestorMascotas().aceptarAdopcion( campoIdMascotaConsulta.getText().trim(), seleccionado.darCedula(),
                    app.darGestorUsuarios() );
            app.darFundacionActiva().registrarAdopcionExitosa();
            refrescarListaMascotas();
            modeloInteresados.clear();
            areaPerfilCandidato.setText( "" );
            mostrarExito( "Adopcion confirmada con " + seleccionado.darNombre() + ". Se notifico a todos los interesados." );
        } catch ( Exception ex ) {
            mostrarError( ex.getMessage() );
        }
    }

    private void consultarPostulantes() {
        try {
            modeloPostulantes.clear();
            for ( Voluntario v : app.darGestorVoluntarios().listarPostulantes( campoIdSolicitudConsulta.getText().trim() ) ) {
                modeloPostulantes.addElement( v );
            }
            if ( modeloPostulantes.isEmpty() ) {
                mostrarError( "Esta solicitud aun no tiene postulantes pendientes." );
            } else {
                mostrarExito( "Postulantes pendientes de revision." );
            }
        } catch ( Exception ex ) {
            mostrarError( ex.getMessage() );
        }
    }

    private void responderPostulante( boolean aceptar ) {
        Voluntario seleccionado = listaPostulantes.getSelectedValue();
        if ( seleccionado == null ) {
            mostrarError( "Selecciona un postulante de la lista." );
            return;
        }
        try {
            app.darGestorVoluntarios().responderPostulante( campoIdSolicitudConsulta.getText().trim(),
                    seleccionado.darCedula(), aceptar );
            modeloPostulantes.removeElement( seleccionado );
            String resultado = aceptar ? "aceptado" : "rechazado";
            mostrarExito( "El voluntario " + seleccionado.darNombre() + " fue " + resultado + "." );
        } catch ( Exception ex ) {
            mostrarError( ex.getMessage() );
        }
    }

    private void crearCampana() {
        try {
            double meta = Double.parseDouble( campoMetaCampana.getText().trim() );
            app.darGestorDonaciones().crearCampana( app.darFundacionActiva().darCedula(),
                    campoNombreCampana.getText().trim(), campoDescripcionCampana.getText().trim(), meta,
                    campoFechaInicioCampana.getText(), campoFechaFinCampana.getText() );
            refrescarListaCampanas();
            campoNombreCampana.setText( "" );
            campoDescripcionCampana.setText( "" );
            campoMetaCampana.setText( "" );
            campoFechaInicioCampana.setText( "" );
            campoFechaFinCampana.setText( "" );
            mostrarExito( "Campana creada con exito." );
        } catch ( NumberFormatException nfe ) {
            mostrarError( "El monto meta debe ser un numero valido." );
        } catch ( Exception ex ) {
            mostrarError( ex.getMessage() );
        }
    }

    private void extenderCampana() {
        try {
            app.darGestorDonaciones().extenderCampana( campoIdCampanaAccion.getText().trim(),
                    app.darFundacionActiva().darCedula(), campoNuevaFechaCampana.getText() );
            refrescarListaCampanas();
            mostrarExito( "Fecha limite extendida con exito." );
        } catch ( Exception ex ) {
            mostrarError( ex.getMessage() );
        }
    }

    private void eliminarCampana() {
        try {
            app.darGestorDonaciones().eliminarCampana( campoIdCampanaAccion.getText().trim(),
                    app.darFundacionActiva().darCedula() );
            refrescarListaCampanas();
            mostrarExito( "Campana eliminada con exito." );
        } catch ( Exception ex ) {
            mostrarError( ex.getMessage() );
        }
    }

    private void eliminarSolicitud() {
        SolicitudVoluntariado seleccionada = listaSolicitudes.getSelectedValue();
        if ( seleccionada == null ) {
            mostrarError( "Selecciona una solicitud para eliminar." );
            return;
        }
        try {
            app.darGestorVoluntarios().eliminarSolicitud( seleccionada.darId(), app.darFundacionActiva().darCedula() );
            refrescarListaSolicitudes();
            mostrarExito( "Solicitud eliminada con exito." );
        } catch ( Exception ex ) {
            mostrarError( ex.getMessage() );
        }
    }

    private void publicarSolicitud() {
        try {
            int horas = Integer.parseInt( campoHorasSolicitud.getText().trim() );
            app.darGestorVoluntarios().publicarSolicitud( app.darFundacionActiva().darCedula(),
                    campoDescripcionSolicitud.getText().trim(), (Zona) comboZonaSolicitud.getSelectedItem(),
                    (TipoActividad) comboTipoSolicitud.getSelectedItem(), horas,
                    checkExperienciaSolicitud.isSelected(), campoFechaInicioSolicitud.getText(),
                    campoFechaFinSolicitud.getText() );
            refrescarListaSolicitudes();
            campoDescripcionSolicitud.setText( "" );
            campoHorasSolicitud.setText( "" );
            campoFechaInicioSolicitud.setText( "" );
            campoFechaFinSolicitud.setText( "" );
            mostrarExito( "Solicitud de voluntariado publicada." );
        } catch ( NumberFormatException nfe ) {
            mostrarError( "Las horas minimas deben ser un numero entero." );
        } catch ( Exception ex ) {
            mostrarError( ex.getMessage() );
        }
    }

    private void cerrarSesion() {
        app.guardarTodo();
        app.establecerFundacionActiva( null );
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
