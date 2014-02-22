package org.hectordam.practicaHector;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import org.hectordam.practicaHector.beans.TablaUsuarios;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import org.hectordam.practicaHector.beans.TablaBaseDatos;

public class VentanaUsuario extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JScrollPane scrollPane;
	private TablaUsuarios tablaUsuarios;
	
	private Connection conexion;
	private JLabel lblUsuario;
	private JLabel lblPassword;
	private JTextField txtcontrasena;
	private JTextField txtusuario;
	private JScrollPane scrollPane_1;
	private JButton btnInsertar;
	private JButton btnEliminar;
	private TablaBaseDatos tablaBaseDatos;
	
	
	public static enum Accion{
		CANCELAR, ACEPTAR
	}
	
	private void insertar(){
		
		if(txtusuario.getText().equals("")){
			JOptionPane.showMessageDialog(null, "el usuario no esta rellenado", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		int[] basedatos = tablaBaseDatos.getSelectedRows();
		
		for(int i = 0; i < basedatos.length; i++) {
			String query = "grant all on " + (String)tablaBaseDatos.getModel().getValueAt(basedatos[i], 0) + ".* to " + 
					txtusuario.getText() + " identified by '" + txtcontrasena.getText() + "';";
			
			try {
				
				Statement sentencia = conexion.createStatement();		
				sentencia.executeQuery(query);
				
				if (sentencia != null)
					sentencia.close();
				
				this.tablaUsuarios.listar();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	private void eliminar(){
		
		if(this.tablaUsuarios.getSelectedRow() == 0){
			return;
		}
		
		String usuario = (String) tablaUsuarios.getModel().getValueAt(tablaUsuarios.getSelectedRow(), 0);
		
		String query = "delete from mysql.user where User = '" + usuario + "'";
		
		try {
			
			Statement sentencia = conexion.createStatement();		
			sentencia.executeUpdate(query);
			
			if (sentencia != null)
				sentencia.close();
			
			this.tablaUsuarios.listar();
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void conectar(Connection conexion){
		this.conexion = conexion;
		
		try {
			
			tablaUsuarios.inicializar(conexion);
			tablaUsuarios.listar();
			
			tablaBaseDatos.inicializar(conexion);
			tablaBaseDatos.listar();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		this.setVisible(true);
		
	}
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			VentanaUsuario dialog = new VentanaUsuario();
			dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public VentanaUsuario() {
		setModal(true);
		
		setBounds(100, 100, 450, 380);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(218, 11, 206, 253);
		contentPanel.add(scrollPane);
		
		tablaUsuarios = new TablaUsuarios();
		scrollPane.setViewportView(tablaUsuarios);
		
		lblUsuario = new JLabel("Usuario:");
		lblUsuario.setBounds(10, 31, 64, 14);
		contentPanel.add(lblUsuario);
		
		lblPassword = new JLabel("Password:");
		lblPassword.setBounds(10, 56, 64, 14);
		contentPanel.add(lblPassword);
		
		txtcontrasena = new JTextField();
		txtcontrasena.setBounds(84, 53, 124, 20);
		contentPanel.add(txtcontrasena);
		txtcontrasena.setColumns(10);
		
		txtusuario = new JTextField();
		txtusuario.setBounds(84, 28, 124, 20);
		contentPanel.add(txtusuario);
		txtusuario.setColumns(10);
		
		scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 81, 198, 183);
		contentPanel.add(scrollPane_1);
		
		tablaBaseDatos = new TablaBaseDatos();
		scrollPane_1.setViewportView(tablaBaseDatos);
		
		btnInsertar = new JButton("Insertar");
		btnInsertar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				insertar();
			}
		});
		btnInsertar.setBounds(71, 275, 89, 23);
		contentPanel.add(btnInsertar);
		
		btnEliminar = new JButton("Eliminar");
		btnEliminar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				eliminar();
			}
		});
		btnEliminar.setBounds(286, 275, 89, 23);
		contentPanel.add(btnEliminar);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						setVisible(false);
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						setVisible(false);
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
}
