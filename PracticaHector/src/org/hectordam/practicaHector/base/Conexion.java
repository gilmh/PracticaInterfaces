package org.hectordam.practicaHector.base;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.hectordam.practicaHector.ConectarXml;

public class Conexion {

	private Connection conexion;
	private ConectarXml conecta;
	
	private void conectar(){
		
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conexion = DriverManager.getConnection("jdbc:mysql://" + conecta.getServidor() + ":3306/deporte", 
					conecta.getUsuario(), conecta.getContrasena());
			
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		this.setConexion(conexion);
	}
	
	public Conexion(ConectarXml conecta){
		this.conecta = conecta;
		conectar();
	}
	
	public Connection getConexion() {
		return conexion;
	}

	public void setConexion(Connection conexion) {
		this.conexion = conexion;
	}
	
}
