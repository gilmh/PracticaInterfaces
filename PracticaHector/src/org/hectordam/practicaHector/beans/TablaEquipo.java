package org.hectordam.practicaHector.beans;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.hectordam.practicaHector.base.Equipo;

public class TablaEquipo extends JTable{

	private ArrayList<Equipo> equipos;
	private DefaultTableModel modelo;
	private Connection conexion;
	
	public TablaEquipo(){
		
		inicializar();
	}
	
	public void inicializar(){
		
		String [] columna = {"Nombre","Deporte","Localidad"};
		
		modelo = new DefaultTableModel(columna, 0){
			@Override
			public boolean isCellEditable(int fila, int columna) {
				return false;
			}			
		};
		this.setModel(modelo);
		
		this.equipos = new ArrayList<Equipo>();
	}
	
	public void conectar(Connection conectar){
		
		this.conexion = conectar;
	}
	
	public void listar() throws SQLException{
		
		modelo.setNumRows(0);
		
		if (conexion == null){
			return;
		}
		if (conexion.isClosed()){
			return;
		}
		
		for(int i = this.equipos.size()-1; i >= 0; i--){
			this.equipos.remove(i);
		}
		
		Statement sentencia = this.conexion.createStatement();
		String consulta = "select * from equipo";
		ResultSet resultado = sentencia.executeQuery(consulta);	
		
		
		Equipo equipo = null;
		
		while (resultado.next()) {
			Object[] fila = new Object[]{resultado.getString(2), resultado.getString(3), resultado.getString(4)};
			modelo.addRow(fila);
			
			equipo = new Equipo();
			equipo.setId(resultado.getInt(1));
			equipo.setNombre(resultado.getString(2));
			equipo.setDeporte(resultado.getString(3));
			equipo.setLocalidad(resultado.getString(4));
			this.equipos.add(equipo);
		}
		
		if (sentencia != null)
			sentencia.close();
		
	}
	
	public Equipo filaSeleccionada(){
		
		int fila = this.getSelectedRow();
		
		if(fila == -1){
			return null;
		}
		
		String nombre = (String) this.getValueAt(fila, 0);
		String deporte = (String) this.getValueAt(fila, 1);
		String localidad = (String) this.getValueAt(fila, 2);
		
		Equipo equipo = null;
		for(int i = 0; i < this.equipos.size(); i++){
			equipo = this.equipos.get(i);
			if(equipo.getNombre().equals(nombre) && equipo.getDeporte().equals(deporte) || equipo.getLocalidad().equals(localidad)){
				return equipo;
			}
		}
		return null;
	}
	
	
	public void insertar(Equipo equipos) throws SQLException{
		
		Equipo equipo = equipos;
		
		String sentenciaSql = "INSERT INTO equipo (nombre, deporte, localidad) VALUES (?, ?, ?)";
		PreparedStatement sentencia = conexion.prepareStatement(sentenciaSql);
		sentencia.setString(1, equipo.getNombre());
		sentencia.setString(2, equipo.getDeporte());
		sentencia.setString(3, equipo.getLocalidad());
		sentencia.executeUpdate();
		
		if (sentencia != null)
			sentencia.close();
		
	}
	
	public void modificar(Equipo equipos) throws SQLException{
		
		Equipo equipo = equipos;
		if (equipo == null) 
			return;
		
		String sentenciaSql = "UPDATE equipo SET nombre = ?, deporte = ?, localidad = ? WHERE id = ?";
		PreparedStatement sentencia = conexion.prepareStatement(sentenciaSql);
		sentencia.setString(1, equipo.getNombre());
		sentencia.setString(2, equipo.getDeporte());
		sentencia.setString(3, equipo.getLocalidad());
		sentencia.setInt(4, equipo.getId());
		
		
		sentencia.executeUpdate();
		
		if (sentencia != null)
			sentencia.close();
		
	}
	
	public void eliminar(Equipo equipos) throws SQLException{
		
		Equipo equipo = equipos;
		
		if (equipo == null) 
			return;
		
		String sentenciaSql = "DELETE FROM equipo WHERE id = ?";
		
		PreparedStatement sentencia = conexion.prepareStatement(sentenciaSql);
		sentencia.setInt(1, equipo.getId());
		sentencia.executeUpdate();
		
		this.equipos.remove(getSelectedRow());
		
		if (sentencia != null)
				sentencia.close();
		
	}
	
}
