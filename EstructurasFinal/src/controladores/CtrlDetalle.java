package controladores;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import modelos.AVLTree;
import modelos.Estudiante;
import vistas.VistaRegistro;

/**
 *
 * @author Joseph && Jhovanny
 */
public class CtrlDetalle implements ActionListener {

    private AVLTree<Estudiante> estudiantes;
    private VistaRegistro ventana2;

    public CtrlDetalle(AVLTree<Estudiante> estudiantes) {
        this.estudiantes = estudiantes;
        ventana2 = new VistaRegistro();
        ventana2.JBtnGuardar.addActionListener(this);
        ventana2.JBtnEditar.addActionListener(this);
        ventana2.JBtnVolver.addActionListener(this);
    }

    /**
     * Método para mostrar la vista detallada cuando se quiere agregar un estudiante
     */
    public void vistaAgregar() {
        ventana2.JTxtCodigo.setText(crearCodigoAleatorio());
        ventana2.JBtnGuardar.setText("Guardar");
        ventana2.setVisible(true);
        ventana2.setLocationRelativeTo(null);
        ventana2.JBtnEditar.setVisible(false);
    }

    /**
     * Método para mostrar la vista detallada cuando se tiene un estudiante para detallar
     * @param estudiante El estudiante a representar
     */
    public void vistaBuscar(Estudiante estudiante) {
        ventana2.JTxtNombre.setText(estudiante.getNombres());
        ventana2.JTxtApellidos.setText(estudiante.getApellidos());
        ventana2.JTxtCedula.setText(estudiante.getCedula());
        ventana2.JTxtCodigo.setText(estudiante.getCodigo());
        ventana2.JTxtPromedio.setText(estudiante.getPromedio());
        switch (estudiante.getCarrera()) {
            case "Diseño Gráfico" -> ventana2.JCboCarrera.setSelectedIndex(0);
            case "Periodismo" -> ventana2.JCboCarrera.setSelectedIndex(1);
            case "Publicidad" -> ventana2.JCboCarrera.setSelectedIndex(2);
            case "Ingeniería de Software" -> ventana2.JCboCarrera.setSelectedIndex(3);
            case "Administración de Empresas" -> ventana2.JCboCarrera.setSelectedIndex(4);
            case "Hotelería y Turismo" -> ventana2.JCboCarrera.setSelectedIndex(5);
            case "Ingeniería Industiral" -> ventana2.JCboCarrera.setSelectedIndex(6);
            case "Contaduría Pública" -> ventana2.JCboCarrera.setSelectedIndex(7);
        }
        ventana2.setVisible(true);
        ventana2.setLocationRelativeTo(null);
        ventana2.JTxtCodigo.setEditable(false);
        ventana2.JTxtCedula.setEditable(false);
        ventana2.JBtnEditar.setVisible(true);
        ventana2.JBtnGuardar.setText("Eliminar");
    }

    /**
     * Action Performed que recibe los eventos de los botones de la vista detallada
     * @param e Evento efectuado por algún botón
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equalsIgnoreCase("Guardar")) {
            guardarEstudiante();

            CtrlPrincipal controlador = new CtrlPrincipal(estudiantes);
            controlador.iniciar();
            ventana2.setVisible(false);
            ventana2.dispose();
        } else if (e.getActionCommand().equalsIgnoreCase("Volver")) {
            CtrlPrincipal controlador = new CtrlPrincipal(estudiantes);
            controlador.iniciar();
            ventana2.setVisible(false);
            ventana2.dispose();

        } else if (e.getActionCommand().equalsIgnoreCase("Editar")) {
            modificarEstudiante();
            JOptionPane.showMessageDialog(null, "Editado con exito");

            CtrlPrincipal controlador = new CtrlPrincipal(estudiantes);
            controlador.iniciar();
            ventana2.setVisible(false);
            ventana2.dispose();
        } else if (e.getActionCommand().equalsIgnoreCase("Eliminar")) {
            if (JOptionPane.showConfirmDialog(null, "Seguro que desea borrar el estudiante?", "", 0, 2)== 0) {
                borrarEstudiante();
                JOptionPane.showMessageDialog(null, "Borrado con exito");
                CtrlPrincipal controlador = new CtrlPrincipal(estudiantes);
                controlador.iniciar();
                ventana2.setVisible(false);
                ventana2.dispose();
            }
        }
    }

    /**
     * Método para guardar el estudiante en el momento de presionar el botón "Guardar"
     */
    private void guardarEstudiante() {
        String nombres = ventana2.JTxtNombre.getText();
        String apellidos = ventana2.JTxtApellidos.getText();
        String codigo = ventana2.JTxtCodigo.getText();
        String carrera = ventana2.JCboCarrera.getSelectedItem().toString();
        String semestre = ventana2.JCboSemestre.getSelectedItem().toString();
        String cedula = ventana2.JTxtCedula.getText();
        String promedio = ventana2.JTxtPromedio.getText();

        if (ventana2.JTxtNombre.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Faltan campos por llenar");
            return;
        }

        if (ventana2.JTxtApellidos.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Faltan campos por llenar");
            return;
        }

        if (ventana2.JTxtCodigo.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Faltan campos por llenar");
            return;
        }
        if (ventana2.JCboCarrera.getSelectedItem().toString().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Faltan campos por llenar");
            return;
        }
        if (ventana2.JCboSemestre.getSelectedItem().toString().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Faltan campos por llenar");
            return;
        }
        if (ventana2.JTxtCedula.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Faltan campos por llenar");
            return;
        }

        Estudiante estudiante = new Estudiante(nombres, apellidos, codigo, cedula, semestre, carrera, promedio);

        estudiantes.insertar(estudiante);

    }

    /**
     * Método para modificar el estudiante cuando se presiona el botón "Editar"
     */
    private void modificarEstudiante() {
        String nombres = ventana2.JTxtNombre.getText();
        String apellidos = ventana2.JTxtApellidos.getText();
        String codigo = ventana2.JTxtCodigo.getText();
        String carrera = ventana2.JCboCarrera.getSelectedItem().toString();
        String semestre = ventana2.JCboSemestre.getSelectedItem().toString();
        String cedula = ventana2.JTxtCedula.getText();
        String promedio = ventana2.JTxtPromedio.getText();

        Estudiante estudiante = new Estudiante(nombres, apellidos, codigo, cedula, semestre, carrera, promedio);

        estudiantes.modificar(estudiante);
    }

    /**
     * Método para borrar un estudiante en el momento de presionar el botón "Eliminar" luego de haber detallado un
     * estudiante
     */
    private void borrarEstudiante() {
        String nombres = ventana2.JTxtNombre.getText();
        String apellidos = ventana2.JTxtApellidos.getText();
        String codigo = ventana2.JTxtCodigo.getText();
        String carrera = ventana2.JCboCarrera.getSelectedItem().toString();
        String semestre = ventana2.JCboSemestre.getSelectedItem().toString();
        String cedula = ventana2.JTxtCedula.getText();
        String promedio = ventana2.JTxtPromedio.getText();

        Estudiante estudiante = new Estudiante(nombres, apellidos, codigo, cedula, semestre, carrera, promedio);

        estudiantes.borrar(estudiante);
    }

    /**
     * Método para agregar un código aleatorio que no exista anteriormente al estudiante
     * @return
     */
    private String crearCodigoAleatorio() {
        String codigoAleatorio;
        Estudiante auxEst;
        do {
            codigoAleatorio = "" + (int) ((Math.random() * 100 - 11) + 10);
            auxEst = estudiantes.buscar(new Estudiante(codigoAleatorio));
        } while (auxEst != null);
        return codigoAleatorio;
    }

}
