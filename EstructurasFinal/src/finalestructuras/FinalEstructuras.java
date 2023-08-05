package finalestructuras;

import controladores.CtrlPrincipal;
import modelos.AVLTree;
import modelos.Estudiante;
import modelos.Nodo;
import persistencia.SerializadoraEstudiantes;

/**
 *
 * @author Joseph && Jhovanny
 */
public class FinalEstructuras {

    /**
     * @param args the command line arguments
     */
    public static AVLTree<Estudiante> estudiantes;

    /**
     * Metodo principal para empezar el programa
     */
    public static void main(String[] args) {
        estudiantes = new SerializadoraEstudiantes().leer();
        CtrlPrincipal controlador = new CtrlPrincipal(estudiantes);
        controlador.iniciar();
    }

}
