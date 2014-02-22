package org.hectordam.practicaHector;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import org.swixml.SwingEngine;

public class ConectarXml extends JFrame{

	public JTextField txtServidor;
	public JTextField txtUsuario;
	public JTextField txtContrasena;
	
	private String usuario;
	private String contrasena;
	private String servidor;
	
	
	public ConectarXml(){
		
		try {
			SwingEngine eng = new SwingEngine(this);
			eng.render("Ventana.xml").setVisible(true);
			
		} catch (Exception e) {
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
			
			aceptar();
		}
	};
	
	public void aceptar(){
		
		if(txtUsuario.getText().equalsIgnoreCase("") || txtServidor.getText().equalsIgnoreCase("")){
			JOptionPane.showMessageDialog(null, "Error", "Error al introducir los datos", JOptionPane.ERROR_MESSAGE);
		}
		
		else{
			this.setUsuario(this.txtUsuario.getText());
			if(this.txtContrasena.getText().equals("")){
				this.setContrasena("");
			}
			this.setServidor(this.txtServidor.getText());
			this.setVisible(false);
			
			Ventana window = new Ventana(this);
			window.frame.setVisible(true);
		}
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					ConectarXml conectar = new ConectarXml();
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getContrasena() {
		return contrasena;
	}

	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}

	public String getServidor() {
		return servidor;
	}

	public void setServidor(String servidor) {
		this.servidor = servidor;
	}
}
