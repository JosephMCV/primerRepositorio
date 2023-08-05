package controladores;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputMethodListener;
import java.awt.event.KeyEvent;
import static java.awt.event.KeyEvent.VK_BACK_QUOTE;
import java.awt.event.KeyListener;
import java.util.Stack;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.table.DefaultTableModel;
import modelos.AVLTree;
import modelos.Estudiante;
import modelos.Nodo;
import persistencia.SerializadoraEstudiantes;
import vistas.GestionVista;

/**
 *
 * @author Joseph && Jhovanny
 */
public class CtrlPrincipal implements ActionListener {

    private GestionVista ventana;
    private AVLTree<Estudiante> estudiantes;
    private Stack<Nodo<Estudiante>> pilaEstudiantes;

    /**
     * Controlador, cuando no hay datos existentes
     */
    public CtrlPrincipal(AVLTree<Estudiante> estudiantes) {
        this.estudiantes = estudiantes;
        ventana = new GestionVista();
        ventana.JBtnAgregar.addActionListener(this);
        ventana.JBtnActualizar.addActionListener(this);
        ventana.JBtnBuscar.addActionListener(this);
        ventana.JBtnPintar.addActionListener(this);
        ventana.JBtnPos.addActionListener(this);
        ventana.JBtnPre.addActionListener(this);
        
        ventana.jTxtBuscar.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                funcionBuscar(e);
            }
            @Override
            public void keyPressed(KeyEvent e) {}

            @Override
            public void keyReleased(KeyEvent e) {}
        });
    }

    public void iniciar() {
        ventana.JTableEstudiante.getColumn("Codigo").setPreferredWidth(50);
        ventana.setVisible(true);
        ventana.setLocationRelativeTo(null);
        actualizarTabla();
    }

    public void agregarEstudiante(Estudiante estudiante) {
        try {
            estudiantes.insertar(estudiante);
        } catch (RuntimeException ex) {
            estudiante.setCodigo("" + (Math.random() * 101) + 1);
            agregarEstudiante(estudiante);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        if (e.getActionCommand().equalsIgnoreCase("Agregar")) {
            CtrlDetalle controlador = new CtrlDetalle(estudiantes);
            controlador.vistaAgregar();
//            ventana.setVisible(false);
//            ventana.dispose();
        } else if (e.getActionCommand().equalsIgnoreCase("Actualizar")) {
            actualizarTabla();
        } else if (e.getActionCommand().equalsIgnoreCase("Buscar")) {
            String buscar = JOptionPane.showInputDialog("Ingrese el código a buscar");
            Estudiante detalleBusqueda = estudiantes.buscar(new Estudiante(buscar));
            if (detalleBusqueda == null) {
                JOptionPane.showMessageDialog(null, "No se encontró un estudiante con este código");
            } else {
                CtrlDetalle controlador = new CtrlDetalle(estudiantes);
                controlador.vistaBuscar(detalleBusqueda);
                ventana.setVisible(false);
                ventana.dispose();
            }
        } else if (e.getActionCommand().equalsIgnoreCase("Pre Orden")) {
            actualizarTablaPreOrden();
        } else if (e.getActionCommand().equalsIgnoreCase("Post Orden")) {
            pilaEstudiantes = new Stack<>();
            insertarElementosEnStackPost(estudiantes.getRaiz());
            actualizarTablaPostOrden();
        } else if (e.getActionCommand().equalsIgnoreCase("Pintar")) {
            System.out.println(JDialog.getWindows());
            pintarArbol();
        }
        
    }
    

    public void actualizarTabla() {
        DefaultTableModel modelo = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                //all cells false
                return false;
            }
        };
        modelo.addColumn("Codigo");
        modelo.addColumn("Nombres");
        modelo.addColumn("Apellidos");
        modelo.addColumn("Cedula");
        modelo.addColumn("Carrera");
        modelo.addColumn("Semestre");
        modelo.addColumn("Promedio");
        agregarAModelo(modelo);
        ventana.JTableEstudiante.setModel(modelo);
        ventana.JTableEstudiante.getColumn("Codigo").setPreferredWidth(1);
        ventana.JTableEstudiante.getColumn("Nombres").setPreferredWidth(150);
        ventana.JTableEstudiante.getColumn("Apellidos").setPreferredWidth(150);
        ventana.JTableEstudiante.getColumn("Promedio").setPreferredWidth(1);
        ventana.JTableEstudiante.getColumn("Semestre").setPreferredWidth(1);
        ventana.JTableEstudiante.getColumn("Carrera").setPreferredWidth(150);

        ventana.JTableEstudiante.repaint();

        SerializadoraEstudiantes per = new SerializadoraEstudiantes();
        per.escribir(estudiantes);
    }

    private void actualizarTablaPreOrden() {
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("Codigo");
        modelo.addColumn("Nombres");
        modelo.addColumn("Apellidos");
        modelo.addColumn("Cedula");
        modelo.addColumn("Carrera");
        modelo.addColumn("Semestre");
        modelo.addColumn("Promedio");
        agregarAModeloPreOrden(modelo, estudiantes.getRaiz());
        ventana.JTableEstudiante.setModel(modelo);
        ventana.JTableEstudiante.repaint();
    }

    private void actualizarTablaPostOrden() {
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("Codigo");
        modelo.addColumn("Nombres");
        modelo.addColumn("Apellidos");
        modelo.addColumn("Cedula");
        modelo.addColumn("Carrera");
        modelo.addColumn("Semestre");
        modelo.addColumn("Promedio");
        agregarAModeloPostOrden(modelo, estudiantes.getRaiz());
        ventana.JTableEstudiante.setModel(modelo);
        ventana.JTableEstudiante.repaint();
    }

    private void pintarArbol() {
        System.out.println(pintarArbol(estudiantes.getRaiz(), "", 1));
    }

    private String pintarArbol(Nodo<Estudiante> root, String prefix, int dir) {
        if (root == null) {
            return "";
        }

        String space = " ".repeat(("" + root.getDato()).length());
        return pintarArbol(root.getHijoDer(), prefix + "│  ".charAt(dir) + space, 2)
                + prefix + "└ ┌".charAt(dir) + root.getDato()
                + " ┘┐┤".charAt((root.getHijoIzq() != null ? 2 : 0)
                        + (root.getHijoDer() != null ? 1 : 0)) + "\n"
                + pintarArbol(root.getHijoIzq(), prefix + "  │".charAt(dir) + space, 0);
    }

    private void agregarAModelo(DefaultTableModel modelo) {
        estudiantes.forEach((t) -> {
            modelo.addRow(t.toRow());
        });
    }

    private void agregarAModeloPreOrden(DefaultTableModel modelo, Nodo<Estudiante> nodo) {
        if (nodo != null) {
            modelo.addRow(nodo.getDato().toRow());
            agregarAModeloPreOrden(modelo, nodo.getHijoIzq());
            agregarAModeloPreOrden(modelo, nodo.getHijoDer());
        }
    }

    private void agregarAModeloPostOrden(DefaultTableModel modelo, Nodo<Estudiante> nodo) {
        while (!pilaEstudiantes.isEmpty()) {
            modelo.addRow(pilaEstudiantes.pop().getDato().toRow());
        }
    }
    
    private void insertarElementosEnStackPost(Nodo<Estudiante> raiz) {
        if (raiz != null) {
            pilaEstudiantes.add(raiz);
            insertarElementosEnStackPost(raiz.getHijoDer());
            insertarElementosEnStackPost(raiz.getHijoIzq());
        }
    }
    
    private void funcionBuscar(KeyEvent e) {
        ventana.jTxtBuscar.setText("VK_BACK_QUOTE");
    }
    
    private void actualizarTablaPorCodigoYCedula() {
        
    }
    
    private void actualizarTablaPorNombresYApellidos() {
        
    }

}
