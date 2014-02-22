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
import org.hectordam.practicaHector.base.Jugador;

public class TablaJugador extends JTable{

	private ArrayList<Jugador> jugadores;
	private DefaultTableModel modelo;
	private Connection conexion;
	
	public TablaJugador(){
		inicializar();
	}
	
	public void inicializar(){
		
		String [] columna = {"Nombre", "Apellido", "Pais"};
		
		modelo = new DefaultTableModel(columna, 0);
		
		setModel(modelo);
		
		this.jugadores = new ArrayList<Jugador>();
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
		
		Statement sentencia = this.conexion.createStatement();
		String consulta = "select * from jugadores";
		ResultSet resultado = sentencia.executeQuery(consulta);	
		
		Jugador jugador = null;
		for(int i = this.jugadores.size()-1; i >= 0; i--){
			this.jugadores.remove(i);
		}
		
		while (resultado.next()) {
			Object[] fila = new Object[]{resultado.getString(2), resultado.getString(3), resultado.getString(4)};
			modelo.addRow(fila);
			
			jugador = new Jugador();
			jugador.setId(resultado.getInt(1));
			jugador.setNombre(resultado.getString(2));
			jugador.setApellido(resultado.getString(3));
			jugador.setPais(resultado.getString(4));
			jugador.setEquipo(this.nombreEquipo(resultado.getInt(5)));
			
			this.jugadores.add(jugador);
		}
		
		if (sentencia != null)
			sentencia.close();
		
	}
	
	private Equipo nombreEquipo(int idEquipo) throws SQLException{
		
		Statement sentencia = this.conexion.createStatement();
		String consulta = "select * from equipo where id = "+ idEquipo;
		ResultSet resultado = sentencia.executeQuery(consulta);	
		
		if(resultado.first()){
			
			Equipo equipo = new Equipo();
			equipo.setId(resultado.getInt(1));
			equipo.setNombre(resultado.getString(2));
			equipo.setDeporte(resultado.getString(3));
			equipo.setLocalidad(resultado.getString(4));
			
			return equipo;
		}
		else{
			return null;
		}
	}
	
	public Equipo objetoEquipo(Equipo equiporecibido) throws SQLException{
		
		Statement sentencia = this.conexion.createStatement();
		String consulta = "select * from equipo where nombre = '"+ equiporecibido.getNombre() +"'";
		ResultSet resultado = sentencia.executeQuery(consulta);
		
		if(resultado.first()){
			
			Equipo equipo = new Equipo();
			equipo.setId(resultado.getInt(1));
			equipo.setNombre(resultado.getString(2));
			equipo.setDeporte(resultado.getString(3));
			equipo.setLocalidad(resultado.getString(4));
			
			return equipo;
		}
		else{
			return null;
		}
	}
	
	public Jugador filaSeleccionada(){
		int fila = this.getSelectedRow();
		
		if(fila == -1){
			return null;
		}
		
		String nombre = (String) this.getValueAt(fila, 0);
		String apellido = (String) this.getValueAt(fila, 1);
		String pais = (String) this.getValueAt(fila, 2);
		
		Jugador jugador = null;
		for(int i = 0; i < this.jugadores.size(); i++){
			jugador = this.jugadores.get(i);
			if(jugador.getNombre().equals(nombre) && jugador.getApellido().equals(apellido) || jugador.getPais().equals(pais)){
				return jugador;
			}
		}
		return null;
	}
	
	public void insertar(Jugador jugadores) throws SQLException{
		
		Jugador jugador = jugadores;
		
		if (jugador == null) 
			return;
		
		String sentenciaSql = "INSERT INTO jugadores (nombre, apellido, pais, id_equipo) VALUES (?, ?, ?, ?)";
		PreparedStatement sentencia = conexion.prepareStatement(sentenciaSql);
		sentencia.setString(1, jugador.getNombre());
		sentencia.setString(2, jugador.getApellido());
		sentencia.setString(3, jugador.getPais());
		sentencia.setInt(4, jugador.getEquipo().getId());
		sentencia.executeUpdate();
		
		if (sentencia != null)
			sentencia.close();
	}
	
	public void modificar(Jugador jugadores) throws SQLException{
		
		Jugador jugador = jugadores;
		
		if (jugador == null) 
			return;
		
		String sentenciaSql = "UPDATE jugadores SET nombre = ?, apellido = ?, pais = ?, id_equipo = ? WHERE id = ?";
		PreparedStatement sentencia = conexion.prepareStatement(sentenciaSql);
		sentencia.setString(1, jugador.getNombre());
		sentencia.setString(2, jugador.getApellido());
		sentencia.setString(3, jugador.getPais());
		sentencia.setInt(4, jugador.getEquipo().getId());
		sentencia.setInt(5, jugador.getId());
		
		sentencia.executeUpdate();
		
		if (sentencia != null)
			sentencia.close();
		
	}
	
	public void eliminar(Jugador jugadores){
		
		try {
			
			Jugador jugador = jugadores;
			
			if (jugador == null) 
				return;
			
			String consulta = "DELETE FROM jugadores WHERE id = ?";
			
			PreparedStatement sentencia = this.conexion.prepareStatement(consulta);
			sentencia.setInt(1, jugador.getId());
			sentencia.execute();
			
			if(sentencia != null)
				sentencia.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
}
