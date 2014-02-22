package org.hectordam.practicaHector.beans;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JComboBox;

import org.hectordam.practicaHector.base.Equipo;

public class ComboEquipos extends JComboBox<String>{

	private Connection conexion;
	private ArrayList<Equipo> equipos;
	
	public ComboEquipos(){
		equipos = new ArrayList<Equipo>();
	}
	
	public void conectar(Connection conexion){
		this.conexion = conexion;
	}
	
	public void listar() throws SQLException{
		
		if (conexion == null){
			this.removeAllItems();
			return;
		}
		if (conexion.isClosed()){
			this.removeAllItems();
			return;
		}
		this.removeAllItems();
		
		for(int i = this.equipos.size()-1; i >= 0; i--){
			this.equipos.remove(i);
		}
		
		Statement sentencia = this.conexion.createStatement();
		String consulta = "select * from equipo";
		ResultSet resultado = sentencia.executeQuery(consulta);	
		
		Equipo equipo = null;
		while (resultado.next()) {
			equipo = new Equipo();
			
			equipo.setId(resultado.getInt(1));
			equipo.setNombre(resultado.getString(2));
			equipo.setDeporte(resultado.getString(3));
			equipo.setLocalidad(resultado.getString(4));
			
			this.addItem(equipo.getNombre());
			
			this.equipos.add(equipo);
		}
		
		if (sentencia != null)
			sentencia.close();
		
	}
	
	public Equipo equipoSeleccionado(){
		
		return this.equipos.get(this.getSelectedIndex());
	}
	
}
