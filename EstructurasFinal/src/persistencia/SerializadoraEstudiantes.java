package persistencia;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import modelos.AVLTree;
import modelos.Estudiante;

/**
 *
 * @author Joseph && Jhovanny
 */
public class SerializadoraEstudiantes {

    public SerializadoraEstudiantes() {

    }

    public void escribir(AVLTree<Estudiante> estudiante) {
        try {
            FileOutputStream archivo
                    = new FileOutputStream("estudiantes.dat");
            ObjectOutputStream escritor
                    = new ObjectOutputStream(archivo);
            escritor.writeObject(estudiante);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public AVLTree<Estudiante> leer() {
        try {
            FileInputStream archivo
                    = new FileInputStream("estudiantes.dat");
            ObjectInputStream lector
                    = new ObjectInputStream(archivo);
            return (AVLTree<Estudiante>) lector.readObject();

        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
            return new AVLTree<Estudiante>();
        }
    }
    
    public void borrar() {
        AVLTree<Estudiante> limpio = new AVLTree<>();
        try {
            FileOutputStream archivo
                    = new FileOutputStream("estudiantes.dat");
            ObjectOutputStream escritor
                    = new ObjectOutputStream(archivo);
            escritor.writeObject(limpio);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
