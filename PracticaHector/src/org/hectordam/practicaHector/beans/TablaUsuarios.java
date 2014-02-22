package org.hectordam.practicaHector.beans;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class TablaUsuarios extends JTable{

	private Connection conexion;
	private DefaultTableModel modelo;
	
	public TablaUsuarios(){
		
	}
	
	public void inicializar(Connection conexion){
		this.conexion = conexion;
		
		String[] columnas = {"Usuario", "Password"};
		
		modelo = new DefaultTableModel(columnas, 0){
			@Override
			public boolean isCellEditable(int fila, int columna) {
				return false;
			}			
		};
		this.setModel(modelo);
		
	}
	
	public void listar() throws SQLException{
		
		modelo.setNumRows(0);
		
		Statement sentencia = conexion.createStatement();
		String consulta = "select User, Password from mysql.user ";
		ResultSet resultado = sentencia.executeQuery(consulta);
		
		while(resultado.next()){
			
			String[] fila = {resultado.getString(1), resultado.getString(2)};
			
			modelo.addRow(fila);
		}
		
		if (sentencia != null)
			sentencia.close();
	}
	
}
