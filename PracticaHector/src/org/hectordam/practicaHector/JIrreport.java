package org.hectordam.practicaHector;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JTextField;

import org.hectordam.practicaHector.base.Filtros;

public class JIrreport extends JDialog {

	private final JPanel contentPanel = new JPanel();

	private VentanaUsuario.Accion accion;
	private Filtros filtro;
	
	private JLabel lblNombreJugador;
	private JLabel lblApellidoJugador;
	private JTextField txtNombre;
	private JTextField txtApellido;
	
	
	private void aceptar(){
		
		filtro = new Filtros();
		filtro.setNombre("%" + txtNombre.getText() + "%");
		filtro.setApellido("%" + txtApellido.getText() + "%");
		
		accion = VentanaUsuario.Accion.ACEPTAR;
		setVisible(false);
	}
	
	private void cancelar(){
		
		accion = VentanaUsuario.Accion.CANCELAR;
		setVisible(false);
	}
	
	public VentanaUsuario.Accion mostrar(){
		
		setVisible(true);
		
		return accion;
	}

	/**
	 * Create the dialog.
	 */
	public JIrreport() {
		setModal(true);
		setBounds(100, 100, 249, 136);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		lblNombreJugador = new JLabel("Nombre Jugador");
		lblNombreJugador.setBounds(10, 11, 92, 14);
		contentPanel.add(lblNombreJugador);
		
		lblApellidoJugador = new JLabel("Apellido Jugador");
		lblApellidoJugador.setBounds(10, 36, 92, 14);
		contentPanel.add(lblApellidoJugador);
		
		txtNombre = new JTextField();
		txtNombre.setBounds(112, 8, 108, 20);
		contentPanel.add(txtNombre);
		txtNombre.setColumns(10);
		
		txtApellido = new JTextField();
		txtApellido.setBounds(112, 33, 108, 20);
		contentPanel.add(txtApellido);
		txtApellido.setColumns(10);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent arg0) {
						aceptar();
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
						cancelar();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}

	public Filtros getFiltro() {
		return filtro;
	}
	
}
