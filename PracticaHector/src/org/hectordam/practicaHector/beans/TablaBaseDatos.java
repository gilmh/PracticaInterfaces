package org.hectordam.practicaHector.beans;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class TablaBaseDatos extends JTable{

	private DefaultTableModel modelo;
	private Connection conexion;
	
	public TablaBaseDatos(){
		
	}
	
	public void inicializar(Connection conexion){
		this.conexion = conexion;
		
		String[] columnas = {"Bases de datos"};
		
		modelo = new DefaultTableModel(columnas, 0);
		
		this.setModel(modelo);
		
	}
	
	public void listar(){
		
		modelo.setNumRows(0);
		
		ResultSet resultado;
		try {
			resultado = conexion.getMetaData().getCatalogs();
			while(resultado.next()) {
				
				String[] fila = new String[] {
						resultado.getString(1)
				};
					
				modelo.addRow(fila);
			}
			
			resultado.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
}
