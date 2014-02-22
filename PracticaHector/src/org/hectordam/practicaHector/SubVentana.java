package org.hectordam.practicaHector;

import java.sql.SQLException;

import org.hectordam.practicaHector.base.Equipo;
import org.hectordam.practicaHector.base.Jugador;
import org.hectordam.practicaHector.beans.ComboEquipos;
import org.hectordam.practicaHector.beans.TablaEquipo;
import org.hectordam.practicaHector.beans.TablaJugador;

public class SubVentana {

	private Ventana ventana;
	private TablaEquipo tablaEquipo;
	private TablaJugador tablaJugador;
	private ComboEquipos comboEquipo;
	
	public SubVentana(Ventana ventana, TablaEquipo tablaEquipo, TablaJugador tablaJugador, ComboEquipos comboEquipo){
		this.ventana = ventana;
		this.tablaEquipo = tablaEquipo;
		this.tablaJugador = tablaJugador;
		this.comboEquipo = comboEquipo;
	}
	
	public void nuevoEquipo(){
		
		this.ventana.btnCancelarEquipo.setEnabled(true);
		this.ventana.btnNuevoEquipo.setEnabled(false);
		this.ventana.btnInsertarEquipo.setEnabled(true);
		this.ventana.btnModificarEquipo.setEnabled(false);
		this.ventana.btnEliminarEquipo.setEnabled(false);
		
		this.ventana.txtNombreEquipo.setEnabled(true);
		this.ventana.txtNombreEquipo.setText("");
		this.ventana.txtDeporteEquipo.setEnabled(true);
		this.ventana.txtDeporteEquipo.setText("");
		this.ventana.txtLocalidadEquipo.setEnabled(true);
		this.ventana.txtLocalidadEquipo.setText("");
	}
	
	public void cancelarEquipo(){
		
		this.ventana.btnCancelarEquipo.setEnabled(false);
		this.ventana.btnNuevoEquipo.setEnabled(true);
		this.ventana.btnInsertarEquipo.setEnabled(false);
		this.ventana.btnModificarEquipo.setEnabled(false);
		this.ventana.btnEliminarEquipo.setEnabled(false);
		
		this.ventana.txtNombreEquipo.setEnabled(false);
		this.ventana.txtNombreEquipo.setText("");
		this.ventana.txtDeporteEquipo.setEnabled(false);
		this.ventana.txtDeporteEquipo.setText("");
		this.ventana.txtLocalidadEquipo.setEnabled(false);
		this.ventana.txtLocalidadEquipo.setText("");
	}
	
	public void nuevoJugador(){
		
		this.ventana.btCancelarJugador.setEnabled(true);
		this.ventana.btNuevoJugador.setEnabled(false);
		this.ventana.btInsertarJugador.setEnabled(true);
		this.ventana.btModificarJugador.setEnabled(false);
		this.ventana.btEliminarJugador.setEnabled(false);
		this.comboEquipo.setEnabled(true);
		
		this.ventana.txtNombreJugador.setEnabled(true);
		this.ventana.txtNombreJugador.setText("");
		this.ventana.txtApellidoJugador.setEnabled(true);
		this.ventana.txtApellidoJugador.setText("");
		this.ventana.txtPaisJugador.setEnabled(true);
		this.ventana.txtPaisJugador.setText("");
		
	}
	
	public void cancelarJugador(){
		
		this.ventana.btCancelarJugador.setEnabled(false);
		this.ventana.btNuevoJugador.setEnabled(true);
		this.ventana.btInsertarJugador.setEnabled(false);
		this.ventana.btModificarJugador.setEnabled(false);
		this.ventana.btEliminarJugador.setEnabled(false);
		this.comboEquipo.setEnabled(false);
		
		this.ventana.txtNombreJugador.setEnabled(false);
		this.ventana.txtNombreJugador.setText("");
		this.ventana.txtApellidoJugador.setEnabled(false);
		this.ventana.txtApellidoJugador.setText("");
		this.ventana.txtPaisJugador.setEnabled(false);
		this.ventana.txtPaisJugador.setText("");
	}
	
	public void insertarEquipo(){
		
		try {
			
			Equipo equipo = new Equipo();
			
			equipo.setNombre(this.ventana.txtNombreEquipo.getText());
			equipo.setDeporte(this.ventana.txtDeporteEquipo.getText());
			equipo.setLocalidad(this.ventana.txtLocalidadEquipo.getText());
			
			this.tablaEquipo.insertar(equipo);
			
			this.tablaEquipo.listar();
			this.comboEquipo.listar();
			cancelarEquipo();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void modificarEquipo(){
		
		try {
			
			Equipo equipo = this.tablaEquipo.filaSeleccionada();
			
			equipo.setNombre(this.ventana.txtNombreEquipo.getText());
			equipo.setDeporte(this.ventana.txtDeporteEquipo.getText());
			equipo.setLocalidad(this.ventana.txtLocalidadEquipo.getText());
			
			this.tablaEquipo.modificar(equipo);
			
			this.tablaEquipo.listar();
			this.comboEquipo.listar();
			
			cancelarEquipo();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void eliminarEquipo(){
		
		try {
			Equipo equipo = this.tablaEquipo.filaSeleccionada();
			
			this.tablaEquipo.eliminar(equipo);
			
			this.tablaEquipo.listar();
			this.comboEquipo.listar();
			
			cancelarEquipo();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}		
	}
	
	public void equipoSeleccionado(){
		
		Equipo equipo = this.tablaEquipo.filaSeleccionada();
		
		this.ventana.txtNombreEquipo.setText(equipo.getNombre());
		this.ventana.txtDeporteEquipo.setText(equipo.getDeporte());
		this.ventana.txtLocalidadEquipo.setText(equipo.getLocalidad());
		
		this.ventana.btnModificarEquipo.setEnabled(true);
		this.ventana.btnEliminarEquipo.setEnabled(true);
		
		this.ventana.txtNombreEquipo.setEnabled(true);
		this.ventana.txtDeporteEquipo.setEnabled(true);
		this.ventana.txtLocalidadEquipo.setEnabled(true);
	}
	
	public void insertarJugador(){
		
		try {
			Jugador jugador = new Jugador();
			
			jugador.setNombre(this.ventana.txtNombreJugador.getText());
			jugador.setApellido(this.ventana.txtApellidoJugador.getText());
			jugador.setPais(this.ventana.txtPaisJugador.getText());
			jugador.setEquipo(tablaJugador.objetoEquipo(comboEquipo.equipoSeleccionado()));
			
			this.tablaJugador.insertar(jugador);
			
			this.tablaJugador.listar();
			
			cancelarJugador();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public void modificarJugador(){
		
		try {
			Jugador jugador = this.tablaJugador.filaSeleccionada();
			
			jugador.setNombre(this.ventana.txtNombreJugador.getText());
			jugador.setApellido(this.ventana.txtApellidoJugador.getText());
			jugador.setPais(this.ventana.txtPaisJugador.getText());
		
			this.tablaJugador.modificar(jugador);
			
			this.tablaJugador.listar();
			
			cancelarJugador();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public void eliminarJugador(){
		
		try {
			Jugador jugador = this.tablaJugador.filaSeleccionada();
			
			this.tablaJugador.eliminar(jugador);
			
			this.tablaJugador.listar();
			
			cancelarJugador();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public void jugadorSeleccionado(){
		
		Jugador jugador = this.tablaJugador.filaSeleccionada();
		
		this.ventana.txtNombreJugador.setText(jugador.getNombre());
		this.ventana.txtApellidoJugador.setText(jugador.getApellido());
		this.ventana.txtPaisJugador.setText(jugador.getPais());
		this.comboEquipo.setSelectedItem(jugador.getEquipo().getNombre());
		
		this.ventana.txtNombreJugador.setEnabled(true);
		this.ventana.txtApellidoJugador.setEnabled(true);
		this.ventana.txtPaisJugador.setEnabled(true);
		
		this.ventana.btModificarJugador.setEnabled(true);
		this.ventana.btEliminarJugador.setEnabled(true);
	}
}
