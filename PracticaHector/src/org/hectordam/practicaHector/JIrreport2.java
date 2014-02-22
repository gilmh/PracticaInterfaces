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

public class JIrreport2 extends JDialog {

	private final JPanel contentPanel = new JPanel();

	private VentanaUsuario.Accion accion;
	private Filtros filtro;
	
	private JLabel lblNombreJugador;
	private JTextField txtNombre;
	
	
	private void aceptar(){
		
		filtro = new Filtros();
		filtro.setNombre("%" + txtNombre.getText() + "%");
		
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
	public JIrreport2() {
		setModal(true);
		setBounds(100, 100, 242, 111);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		lblNombreJugador = new JLabel("Nombre Equipo");
		lblNombreJugador.setBounds(10, 11, 92, 14);
		contentPanel.add(lblNombreJugador);
		
		txtNombre = new JTextField();
		txtNombre.setBounds(112, 8, 108, 20);
		contentPanel.add(txtNombre);
		txtNombre.setColumns(10);
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
