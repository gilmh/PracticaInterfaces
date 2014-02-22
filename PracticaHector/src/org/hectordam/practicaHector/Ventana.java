package org.hectordam.practicaHector;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

import org.hectordam.practicaHector.base.Conexion;
import org.hectordam.practicaHector.base.Filtros;

import javax.swing.JTabbedPane;
import javax.swing.JLayeredPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import org.hectordam.practicaHector.beans.TablaEquipo;
import org.hectordam.practicaHector.beans.TablaJugador;
import org.hectordam.practicaHector.beans.ComboEquipos;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JMenuItem;

public class Ventana {

	public JFrame frame;
	public JButton btnNuevoEquipo;
	public JButton btnCancelarEquipo;
	public JButton btnInsertarEquipo;
	public JButton btNuevoJugador;
	public JButton btCancelarJugador;
	public JButton btInsertarJugador;
	public JButton btModificarJugador;
	public JButton btEliminarJugador;
	public JButton btnModificarEquipo;
	public JButton btnEliminarEquipo;
	public JTextField txtNombreEquipo;
	public JLabel lblNombre;
	public JLabel lblLocalidad;
	public JLabel lblDeporte;
	public JTextField txtDeporteEquipo;
	public JTextField txtLocalidadEquipo;
	public JTextField txtNombreJugador;
	public JTextField txtApellidoJugador;
	public JTextField txtPaisJugador;
	public JPanel panel;
	
	private Connection conexion;
	private ConectarXml conecta;
	private Conexion conex;
	private TablaEquipo tablaEquipo;
	private TablaJugador tablaJugador;
	private SubVentana ventana;
	private ComboEquipos comboEquipos;
	private JLabel lblEquipo;
	private JMenuItem mntmUsuarios;
	
	
	private void mostrar1(){
		
		try {
			
			JasperReport report = (JasperReport) JRLoader.loadObject("report1Practica.jasper");
			JasperPrint jasperPrint = JasperFillManager.fillReport(report, null, conexion);
			
			JRExporter exporter = new JRPdfExporter();
			exporter.setParameter(JRExporterParameter.JASPER_PRINT,jasperPrint);
			exporter.setParameter(JRExporterParameter.OUTPUT_FILE,new java.io.File("reportPDF1.pdf"));
			exporter.exportReport();
			
			JasperViewer.viewReport(jasperPrint);
			
		} catch (JRException e) {
			e.printStackTrace();
		}		
		
	}
	
	public Action cancelar = new AbstractAction() { 
		
		private static final long serialVersionUID = 1L;
			
		@Override
		public void actionPerformed(ActionEvent evt) { 
			System.exit(0); 
		}
	};

	public Action aceptar = new AbstractAction() { 
		
		private static final long serialVersionUID = 1L;
			
		@Override
		public void actionPerformed(ActionEvent evt) { 
			
			ConectarXml conectar = new ConectarXml();
			conectar.aceptar();
			
			frame.setVisible(true);
		}
	};
	private JMenu mnPdf;
	private JMenuItem mntmMostrarEquipos;
	private JMenuItem mntmIreport;
	private JMenuItem mntmMostrar;
	
	private void inicializar(){
		
		conex = new Conexion(conecta);
		conexion = conex.getConexion();
		
		this.tablaEquipo.conectar(conexion);
		this.tablaJugador.conectar(conexion);
		this.comboEquipos.conectar(conexion);
		
		try {
			this.tablaEquipo.listar();
			this.tablaJugador.listar();
			this.comboEquipos.listar();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		ventana = new SubVentana(this, this.tablaEquipo, this.tablaJugador, this.comboEquipos);
	}
	
	/**
	 * Create the application.
	 */
	public Ventana(ConectarXml conectar) {
		this.conecta = conectar;
		
		initialize();
		inicializar();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 556, 383);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JMenu mnUsuarios = new JMenu("Usuarios");
		mnUsuarios.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
			}
		});
		menuBar.add(mnUsuarios);
		
		mntmUsuarios = new JMenuItem("Usuarios");
		mntmUsuarios.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				VentanaUsuario ventanaUsuario = new VentanaUsuario();
				ventanaUsuario.conectar(conexion);
			}
		});
		mnUsuarios.add(mntmUsuarios);
		
		mnPdf = new JMenu("PDF");
		menuBar.add(mnPdf);
		
		mntmMostrarEquipos = new JMenuItem("Mostrar Equipos");
		mntmMostrarEquipos.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				mostrar1();
			}
		});
		mnPdf.add(mntmMostrarEquipos);
		
		mntmIreport = new JMenuItem("Mostrar 2");
		mntmIreport.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JIrreport ireport = new JIrreport();
				
				if(ireport.mostrar() == VentanaUsuario.Accion.CANCELAR){
					return;
				}
				
				Filtros filtro = ireport.getFiltro();
				
				HashMap<String, Object> mapa = new HashMap<String, Object>();
				mapa.put("Nombre_Jugador", filtro.getNombre());
				mapa.put("Apellido_Jugador", filtro.getApellido());
				
				try {
					JasperReport report = (JasperReport) JRLoader.loadObject("report2Practica.jasper");
					JasperPrint jasperPrint = JasperFillManager.fillReport(report, mapa, conexion);
				
					JRExporter exporter = new JRPdfExporter();
					exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
					exporter.setParameter(JRExporterParameter.OUTPUT_FILE,new java.io.File("reportPDF2.pdf"));
					exporter.exportReport();
					
					JasperViewer.viewReport(jasperPrint);
							
				} catch (JRException e1) {
					e1.printStackTrace();
				}		
			}
		});
		mnPdf.add(mntmIreport);
		
		mntmMostrar = new JMenuItem("Mostrar 3");
		mntmMostrar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JIrreport2 ireport = new JIrreport2();
				
				if(ireport.mostrar() == VentanaUsuario.Accion.CANCELAR){
					return;
				}
				
				Filtros filtro = ireport.getFiltro();
				
				HashMap<String, Object> mapa = new HashMap<String, Object>();
				mapa.put("Nombre_Equipo", filtro.getNombre());
				
				try {
					JasperReport report = (JasperReport) JRLoader.loadObject("report3Practica.jasper");
					JasperPrint jasperPrint = JasperFillManager.fillReport(report, mapa, conexion);
				
					JRExporter exporter = new JRPdfExporter();
					exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
					exporter.setParameter(JRExporterParameter.OUTPUT_FILE,new java.io.File("reportPDF3.pdf"));
					exporter.exportReport();
					
					JasperViewer.viewReport(jasperPrint);
							
				} catch (JRException e1) {
					e1.printStackTrace();
				}
			}
		});
		mnPdf.add(mntmMostrar);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(new BorderLayout(0, 0));
		
		JTabbedPane tabbedPane = new JTabbedPane(SwingConstants.TOP);
		panel.add(tabbedPane, BorderLayout.CENTER);
		
		JLayeredPane layeredPane = new JLayeredPane();
		tabbedPane.addTab("Equipo", null, layeredPane, null);
		
		btnNuevoEquipo = new JButton("Nuevo");
		btnNuevoEquipo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				ventana.nuevoEquipo();
			}
		});
		btnNuevoEquipo.setBounds(10, 157, 89, 23);
		layeredPane.add(btnNuevoEquipo);
		
		btnCancelarEquipo = new JButton("Cancelar");
		btnCancelarEquipo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ventana.cancelarEquipo();
			}
		});
		btnCancelarEquipo.setEnabled(false);
		btnCancelarEquipo.setBounds(10, 191, 89, 23);
		layeredPane.add(btnCancelarEquipo);
		
		btnInsertarEquipo = new JButton("Insertar");
		btnInsertarEquipo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				ventana.insertarEquipo();
			}
		});
		btnInsertarEquipo.setEnabled(false);
		btnInsertarEquipo.setBounds(143, 157, 89, 23);
		layeredPane.add(btnInsertarEquipo);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(242, 11, 283, 274);
		layeredPane.add(scrollPane);
		
		tablaEquipo = new TablaEquipo();
		tablaEquipo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				ventana.equipoSeleccionado();
			}
		});
		scrollPane.setViewportView(tablaEquipo);
		
		btnEliminarEquipo = new JButton("Eliminar");
		btnEliminarEquipo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				ventana.eliminarEquipo();
			}
		});
		btnEliminarEquipo.setEnabled(false);
		btnEliminarEquipo.setBounds(143, 225, 89, 23);
		layeredPane.add(btnEliminarEquipo);
		
		btnModificarEquipo = new JButton("Modificar");
		btnModificarEquipo.setEnabled(false);
		btnModificarEquipo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				ventana.modificarEquipo();
			}
		});
		btnModificarEquipo.setBounds(143, 191, 89, 23);
		layeredPane.add(btnModificarEquipo);
		
		txtNombreEquipo = new JTextField();
		txtNombreEquipo.setEnabled(false);
		txtNombreEquipo.setBounds(84, 27, 148, 20);
		layeredPane.add(txtNombreEquipo);
		txtNombreEquipo.setColumns(10);
		
		lblNombre = new JLabel("Nombre");
		lblNombre.setBounds(10, 30, 64, 14);
		layeredPane.add(lblNombre);
		
		lblLocalidad = new JLabel("Localidad");
		lblLocalidad.setBounds(10, 105, 64, 14);
		layeredPane.add(lblLocalidad);
		
		lblDeporte = new JLabel("Deporte");
		lblDeporte.setBounds(10, 66, 64, 14);
		layeredPane.add(lblDeporte);
		
		txtDeporteEquipo = new JTextField();
		txtDeporteEquipo.setEnabled(false);
		txtDeporteEquipo.setBounds(84, 63, 148, 20);
		layeredPane.add(txtDeporteEquipo);
		txtDeporteEquipo.setColumns(10);
		
		txtLocalidadEquipo = new JTextField();
		txtLocalidadEquipo.setEnabled(false);
		txtLocalidadEquipo.setBounds(84, 102, 148, 20);
		layeredPane.add(txtLocalidadEquipo);
		txtLocalidadEquipo.setColumns(10);
		
		JLayeredPane layeredPane_1 = new JLayeredPane();
		tabbedPane.addTab("Jugador", null, layeredPane_1, null);
		
		JLabel label = new JLabel("Nombre");
		label.setBounds(10, 35, 64, 14);
		layeredPane_1.add(label);
		
		txtNombreJugador = new JTextField();
		txtNombreJugador.setEnabled(false);
		txtNombreJugador.setColumns(10);
		txtNombreJugador.setBounds(84, 32, 148, 20);
		layeredPane_1.add(txtNombreJugador);
		
		txtApellidoJugador = new JTextField();
		txtApellidoJugador.setEnabled(false);
		txtApellidoJugador.setColumns(10);
		txtApellidoJugador.setBounds(84, 63, 148, 20);
		layeredPane_1.add(txtApellidoJugador);
		
		txtPaisJugador = new JTextField();
		txtPaisJugador.setEnabled(false);
		txtPaisJugador.setColumns(10);
		txtPaisJugador.setBounds(84, 94, 148, 20);
		layeredPane_1.add(txtPaisJugador);
		
		JLabel lblPais = new JLabel("Pais");
		lblPais.setBounds(10, 97, 64, 14);
		layeredPane_1.add(lblPais);
		
		JLabel lblApellido = new JLabel("Apellido");
		lblApellido.setBounds(10, 66, 64, 14);
		layeredPane_1.add(lblApellido);
		
		btNuevoJugador = new JButton("Nuevo");
		btNuevoJugador.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ventana.nuevoJugador();
			}
		});
		btNuevoJugador.setBounds(10, 156, 89, 23);
		layeredPane_1.add(btNuevoJugador);
		
		btCancelarJugador = new JButton("Cancelar");
		btCancelarJugador.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ventana.cancelarJugador();
			}
		});
		btCancelarJugador.setEnabled(false);
		btCancelarJugador.setBounds(10, 190, 89, 23);
		layeredPane_1.add(btCancelarJugador);
		
		btEliminarJugador = new JButton("Eliminar");
		btEliminarJugador.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ventana.eliminarJugador();
			}
		});
		btEliminarJugador.setEnabled(false);
		btEliminarJugador.setBounds(143, 224, 89, 23);
		layeredPane_1.add(btEliminarJugador);
		
		btModificarJugador = new JButton("Modificar");
		btModificarJugador.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ventana.modificarJugador();
			}
		});
		btModificarJugador.setEnabled(false);
		btModificarJugador.setBounds(143, 190, 89, 23);
		layeredPane_1.add(btModificarJugador);
		
		btInsertarJugador = new JButton("Insertar");
		btInsertarJugador.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				ventana.insertarJugador();
			}
		});
		btInsertarJugador.setEnabled(false);
		btInsertarJugador.setBounds(143, 156, 89, 23);
		layeredPane_1.add(btInsertarJugador);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(242, 11, 283, 274);
		layeredPane_1.add(scrollPane_1);
		
		tablaJugador = new TablaJugador();
		tablaJugador.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				ventana.jugadorSeleccionado();
			}
		});
		scrollPane_1.setViewportView(tablaJugador);
		
		comboEquipos = new ComboEquipos();
		comboEquipos.setEnabled(false);
		comboEquipos.setBounds(84, 125, 148, 20);
		layeredPane_1.add(comboEquipos);
		
		lblEquipo = new JLabel("Equipo");
		lblEquipo.setBounds(10, 128, 46, 14);
		layeredPane_1.add(lblEquipo);
	}
}
