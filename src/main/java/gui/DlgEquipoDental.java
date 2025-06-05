package gui;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.EntityManager;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;


import model.Dentista;
import model.EquipoDental;

import utils.JPAUtil;

import javax.swing.JTextArea;
import java.awt.Font;

public class DlgEquipoDental extends JDialog implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel lblNroEquipo;
	private JLabel lblNombreEquipo;
	private JLabel lblCosto;
	private JLabel lblEstado;
	private JLabel lblFechaAdquisicion;
	private JLabel lblDentista;
	private JTextField txtNroEquipo;
	private JTextField txtNombre;
	private JTextField txtCosto;
	private JComboBox<String> cboEstados;
	private JTextField txtFechaAdquisicion;
	private JComboBox<Object> cboDentistas;
	private JButton btnBuscar;
	private JButton btnOK;
	private JButton btnOpciones;
	private JButton btnAdicionar;
	private JButton btnModificar;
	private JButton btnEliminar;
	private JButton btnListar;
	private JScrollPane scrollPane;
	private JTextArea txtSalida;

	// Tipo de operaci�n a procesar: Adicionar, Consultar, Modificar o Eliminar
	private int tipoOperacion;

	// Constantes para los tipos de operaciones
	public final static int ADICIONAR = 0;
	public final static int CONSULTAR = 1;
	public final static int MODIFICAR = 2;
	public final static int ELIMINAR = 3;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DlgEquipoDental dialog = new DlgEquipoDental();
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the dialog.
	 */
	public DlgEquipoDental() {
		setResizable(false);
		setTitle("Mantenimiento | Equipo Dental");
		setBounds(100, 100, 810, 604);
		getContentPane().setLayout(null);

		lblNroEquipo = new JLabel("Nro Equipo:");
		lblNroEquipo.setBounds(10, 10, 149, 23);
		getContentPane().add(lblNroEquipo);

		lblNombreEquipo = new JLabel("Nombre :");
		lblNombreEquipo.setBounds(10, 35, 149, 23);
		getContentPane().add(lblNombreEquipo);

		lblDentista = new JLabel("Dentista :");
		lblDentista.setBounds(10, 145, 149, 23);
		getContentPane().add(lblDentista);

		lblEstado = new JLabel("Estado :");
		lblEstado.setBounds(10, 88, 149, 23);
		getContentPane().add(lblEstado);

		txtNroEquipo = new JTextField();
		txtNroEquipo.setBounds(174, 10, 86, 23);
		getContentPane().add(txtNroEquipo);
		txtNroEquipo.setEditable(false);
		txtNroEquipo.setColumns(10);

		txtNombre = new JTextField();
		txtNombre.setBounds(174, 35, 251, 23);
		getContentPane().add(txtNombre);
		txtNombre.setColumns(10);
		
		lblCosto = new JLabel("Costo :");
		lblCosto.setBounds(10, 62, 149, 23);
		getContentPane().add(lblCosto);
		
		txtCosto = new JTextField();
		txtCosto.setEditable(false);
		txtCosto.setColumns(10);
		txtCosto.setBounds(174, 62, 86, 23);
		getContentPane().add(txtCosto);

		String[] estados = { "N", "A", "R", "S" };
		cboEstados = new JComboBox<String>();
		cboEstados.setBounds(174, 88, 86, 23);
		getContentPane().add(cboEstados);
		for (String estado : estados) {
			cboEstados.addItem(estado);
		}
		
		lblFechaAdquisicion = new JLabel("Fecha de adquisici\u00F3n:");
		lblFechaAdquisicion.setBounds(10, 116, 162, 20);
		getContentPane().add(lblFechaAdquisicion);

		txtFechaAdquisicion = new JTextField();
		txtFechaAdquisicion.setEditable(false);
		txtFechaAdquisicion.setBounds(174, 114, 146, 26);
		getContentPane().add(txtFechaAdquisicion);
		txtFechaAdquisicion.setColumns(10);
		
		cboDentistas = new JComboBox<Object>();
		cboDentistas.setBounds(174, 143, 251, 26);
		getContentPane().add(cboDentistas);

		btnBuscar = new JButton("Buscar");
		btnBuscar.addActionListener(this);
		btnBuscar.setEnabled(false);
		btnBuscar.setBounds(324, 10, 101, 23);
		getContentPane().add(btnBuscar);

		btnOK = new JButton("OK");
		btnOK.addActionListener(this);
		btnOK.setBounds(430, 145, 100, 23);
		getContentPane().add(btnOK);

		btnOpciones = new JButton("Opciones");
		btnOpciones.addActionListener(this);
		btnOpciones.setBounds(555, 10, 100, 75);
		getContentPane().add(btnOpciones);

		btnAdicionar = new JButton("Adicionar");
		btnAdicionar.addActionListener(this);
		btnAdicionar.setBounds(664, 10, 120, 23);
		getContentPane().add(btnAdicionar);

		btnModificar = new JButton("Modificar");
		btnModificar.addActionListener(this);
		btnModificar.setBounds(664, 36, 120, 23);
		getContentPane().add(btnModificar);

		btnEliminar = new JButton("Eliminar");
		btnEliminar.addActionListener(this);
		btnEliminar.setBounds(664, 62, 120, 23);
		getContentPane().add(btnEliminar);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 180, 775, 337);
		getContentPane().add(scrollPane);

		txtSalida = new JTextArea();
		txtSalida.setEditable(false);
		txtSalida.setFont(new Font("Monospaced", Font.PLAIN, 13));
		scrollPane.setViewportView(txtSalida);

		btnListar = new JButton("Listar");
		btnListar.addActionListener(this);
		btnListar.setBounds(345, 525, 115, 29);
		getContentPane().add(btnListar);

		habilitarEntradas(false);
		habilitarBotones(true);
		cargarDentistas();
	}

	public void actionPerformed(ActionEvent arg0) {
		if (arg0.getSource() == btnListar) {
			actionPerformedBtnListar(arg0);
		}
		if (arg0.getSource() == btnEliminar) {
			actionPerformedBtnEliminar(arg0);
		}
		if (arg0.getSource() == btnModificar) {
			actionPerformedBtnModificar(arg0);
		}
		if (arg0.getSource() == btnAdicionar) {
			actionPerformedBtnAdicionar(arg0);
		}
		if (arg0.getSource() == btnOpciones) {
			actionPerformedBtnOpciones(arg0);
		}
		if (arg0.getSource() == btnOK) {
			actionPerformedBtnOK(arg0);
		}
		if (arg0.getSource() == btnBuscar) {
			actionPerformedBtnBuscar(arg0);
		}
	}

	protected void actionPerformedBtnBuscar(ActionEvent arg0) {
		buscar();
	}

	protected void actionPerformedBtnOK(ActionEvent arg0) {
		switch (tipoOperacion) {
		case ADICIONAR:
			adicionar();
			break;
		case MODIFICAR:
			modificar();
			break;
		case ELIMINAR:
			eliminar();
		}
	}

	protected void actionPerformedBtnOpciones(ActionEvent arg0) {
		limpiar();
	}

	protected void actionPerformedBtnListar(ActionEvent arg0) {
		listar();
	}

	protected void actionPerformedBtnAdicionar(ActionEvent arg0) {
		tipoOperacion = ADICIONAR;
		habilitarEntradas(true);
		habilitarBotones(false);
		txtNombre.requestFocus();
	}

	protected void actionPerformedBtnModificar(ActionEvent arg0) {
		tipoOperacion = MODIFICAR;
		txtNroEquipo.setEditable(true);
		habilitarBotones(false);
		txtNroEquipo.requestFocus();
	}

	protected void actionPerformedBtnEliminar(ActionEvent arg0) {
		tipoOperacion = ELIMINAR;
		txtNroEquipo.setEditable(true);
		habilitarBotones(false);
		txtNroEquipo.requestFocus();
	}

	void cargarDentistas() {
		
EntityManager manager = JPAUtil.getEntityManager();
		
		try {
			String jpql = "select d from Dentista d ";
			List<Dentista> lstDentista = manager.createQuery(jpql, Dentista.class).getResultList();
			
			for (Dentista dentista : lstDentista) {
				cboDentistas.addItem(dentista);
			}
		} finally {
			manager.close();
		}

	}

	void listar() {

		String jpql = "SELECT e FROM EquipoDental e ORDER BY e.nroEquipo";
	    EntityManager manager = JPAUtil.getEntityManager();
	    List<EquipoDental> equipoDental = manager.createQuery(jpql, EquipoDental.class).getResultList();

	    txtSalida.setText(""); // Limpia el textArea

	    try {
	        for (EquipoDental equipD : equipoDental) {
	            imprimir("------------------------------------------------------------");
	            imprimir("Nro. Equipo...........: " + equipD.getNroEquipo());
	            imprimir("Nombre................: " + equipD.getNombre());
	            imprimir("Costo.................: " + equipD.getCosto());
	            imprimir("Fecha de adquisición..: " + equipD.getFechaAdquisicion());
	            imprimir("Dentista..............: " + equipD.getDentista().getNombreCompleto());
	            imprimir("Correo................: " + equipD.getDentista().getCorreo());
	            imprimir("Especialidad..........: " + equipD.getDentista().getEspecialidad());
	            imprimir("Estado................: " + equipD.getEstado());
	        }
	    } finally {
	        manager.close();
	    }
		
	}

	void adicionar() {
		
		String nombreEquip = txtNombre.getText();
		Dentista dentista = (Dentista) cboDentistas.getSelectedItem();
	    int costo =Integer.parseInt(txtCosto.getText());
	    String estado = (String) cboEstados.getSelectedItem();
	    LocalDate fechaRegistro = LocalDate.now(); 

	    
	    EquipoDental equipoDental = new EquipoDental();
	    equipoDental.
	    solicitud.setArchivoAdjunto(archivoAdjunto);
	    solicitud.setEstado(estado);
	    solicitud.setFechaRegistro(fechaRegistro);

	    EntityManager manager = JPAUtil.getEntityManager();

	    try {
	    	System.out.println( "actividad "+actividad );
	    	System.out.println( "archivoadjunto "+archivoAdjunto );
	    	System.out.println( "estado "+estado );
	    	System.out.println( "fecharegistro "+fechaRegistro );
	    	System.out.println( "query "+solicitud );
	        manager.getTransaction().begin();
	        manager.persist(solicitud); // Guarda la solicitud en la BD
	        manager.getTransaction().commit();

	        System.out.print(solicitud);
	        mensajeInfo("Solicitud registrada");
	        limpiar();

	    } catch (Exception e) {
	        mensajeError("Error al registrar el equipo: " + e.getMessage());
	        manager.getTransaction().rollback();
	    } finally {
	        manager.close();
	    }

	}
	
	void buscar() {
		
		String idEquipoStr = txtNroEquipo.getText().trim();
	    if (idEquipoStr.isEmpty()) {
	        mensajeError("Debe ingresar el número de solicitud.");
	        return;
	    }
	    int idEquipo = Integer.parseInt(idEquipoStr);

	    EntityManager manager = JPAUtil.getEntityManager();

	    try {
	        EquipoDental equipoD = manager.find(EquipoDental.class, idEquipo);

	        if (equipoD == null) {
	            mensajeError("Equipo no encontrado");
	            return;
	        }

	        // Aqui llena los textArea con los datos que estan en la base de datos
	        txtNombre.setText(equipoD.getNombre());
	        txtCosto.setText("" + equipoD.getCosto());
	        txtFechaAdquisicion.setText(String.valueOf(equipoD.getFechaAdquisicion()));
	        cboDentistas.setSelectedItem(equipoD.getNombre());
	        cboEstados.setSelectedItem(equipoD.getEstado());

	        habilitarOk();

	    } finally {
	        manager.close();
	    }

	}

	void modificar() {
		
		String idEquipoStr = txtNroEquipo.getText().trim();
	    if (idEquipoStr.isEmpty()) {
	        mensajeError("Debe ingresar el número deL EQUIPO.");
	        return;
	    }
	    int idEquipo = Integer.parseInt(idEquipoStr);

	    EntityManager manager = JPAUtil.getEntityManager();

	    try {
	        manager.getTransaction().begin();

	        
	        EquipoDental equipoD = manager.find(EquipoDental.class, idEquipo);

	        if (equipoD == null) {
	            mensajeError("Equipo no encontrado.");
	            manager.getTransaction().rollback();
	            return;
	        }

	       
	        Dentista dentista = (Dentista) cboDentistas.getSelectedItem();
	        String nombreEquipo = txtNombre.getText().trim();
	        int costo = Integer.parseInt(txtCosto.getText());
	        String estado = (String) cboEstados.getSelectedItem();


	        equipoD.setDentista(dentista);
	        equipoD.setNombre(nombreEquipo);
	        equipoD.setCosto(costo);
	        equipoD.setEstado(estado);
	        
	        manager.getTransaction().commit();

	        mensajeInfo("Equipo actualizado");
	        limpiar();

	    } catch (Exception e) {
	        mensajeError("Error al actualizar el equipo: " + e.getMessage());
	        e.printStackTrace();
	        manager.getTransaction().rollback();
	    } finally {
	        manager.close();
	    }

	}

	void eliminar() {
		
		// Aqui obtiene el ID escrito mediante el textArea
	    String idEquipoStr = txtNroEquipo.getText().trim();
	    if (idEquipoStr.isEmpty()) {
	        mensajeError("Debe ingresar el número del equipo.");
	        return;
	    }
	    int idEquipo = Integer.parseInt(idEquipoStr);

	    EntityManager manager = JPAUtil.getEntityManager();

	    try {
	        manager.getTransaction().begin();

	        // Busca la solicitud que ha generado el usuario mediante ID
	        EquipoDental equipoDe = manager.find(EquipoDental.class, idEquipo);

	        if (equipoDe == null) {
	            mensajeError("Equipo no encontrado.");
	            manager.getTransaction().rollback();
	            return;
	        }

	        // Aquí manda la solicitud para eliminar seguún el ID ingresado
	        manager.remove(equipoDe);
	        manager.getTransaction().commit();

	        mensajeInfo("Equipo eliminado");
	        limpiar();

	    } catch (Exception e) {
	        mensajeError("Error al eliminar el equipo: " + e.getMessage());
	        e.printStackTrace();
	        manager.getTransaction().rollback();
	    } finally {
	        manager.close();
	    }


	}

	// M�todos tipo void (con par�metros)
	void habilitarEntradas(boolean sino) {
		txtNombre.setEditable(sino);
		txtCosto.setEditable(sino);
		cboDentistas.setEnabled(sino);
		cboEstados.setEnabled(sino);
	}

	void habilitarBotones(boolean sino) {
		if (tipoOperacion == ADICIONAR)
			btnOK.setEnabled(!sino);
		else {
			btnBuscar.setEnabled(!sino);
			btnOK.setEnabled(false);
		}
		btnAdicionar.setEnabled(sino);
		btnModificar.setEnabled(sino);
		btnEliminar.setEnabled(sino);
		btnOpciones.setEnabled(!sino);
	}

	void habilitarOk() {
		if (tipoOperacion == MODIFICAR) {
			habilitarEntradas(true);
			txtNroEquipo.setEditable(false);
			txtFechaAdquisicion.setEditable(true);
			btnBuscar.setEnabled(false);
			btnOK.setEnabled(true);
			txtNombre.requestFocus();
		}
		if (tipoOperacion == ELIMINAR) {
			txtNroEquipo.setEditable(false);
			btnBuscar.setEnabled(false);
			btnOK.setEnabled(true);
		}
	}

	void mensajeInfo(String msj) {
		mensaje(msj, "INFO", JOptionPane.INFORMATION_MESSAGE);
	}

	void mensajeError(String msj) {
		mensaje(msj, "ERROR", JOptionPane.ERROR_MESSAGE);
	}

	void mensaje(String msj, String titulo, int tipo) {
		JOptionPane.showMessageDialog(this, msj, titulo, tipo);
	}

	void imprimir(String texto) {
		txtSalida.append(texto + "\n");
	}

	void imprimir() {
		imprimir("");
	}

	void limpiar() {
		txtNroEquipo.setText("");
		txtNombre.setText("");
		txtCosto.setText("");
		cboEstados.setSelectedIndex(0);
		txtFechaAdquisicion.setText("");
		if (cboDentistas.getItemCount() > 0)
			cboDentistas.setSelectedIndex(0);
		txtNroEquipo.setEditable(false);
		txtFechaAdquisicion.setEditable(false);
		habilitarEntradas(false);
		habilitarBotones(true);
	}
}