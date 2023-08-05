package modelos;

import java.io.Serializable;
import java.util.Iterator;
import java.util.function.Consumer;
import javax.swing.JOptionPane;
/**
 *
 * @author Joseph && Jhovanny
 */
public class AVLTree<T extends Comparable<T>> implements Iterable<T>, Serializable{

    private Nodo<T> raiz;

    public AVLTree() {
        this.raiz = null;
    }

    public Nodo<T> getRaiz() {
        return raiz;
    }

    public void insertar(T dato) {
        raiz = insertar(dato, raiz);
        //return this; se supone que retorna elmismo arbol
    }

    //INSERTAR BASADO EN EL CODIGO DEL ESTUDIANTE
    private Nodo<T> insertar(T dato, Nodo<T> nodo) {
        if (nodo == null) {
            return new Nodo<>(dato);
        }
        if (dato.compareTo(nodo.getDato()) == 0) {
            JOptionPane.showMessageDialog(null, "El codigo ya existe");
            throw new RuntimeException("Ya existe");
        } else if (dato.compareTo(nodo.getDato()) < 0) {
            nodo.setHijoIzq(insertar(dato, nodo.getHijoIzq()));
        } else if (dato.compareTo(nodo.getDato()) > 0) {
            nodo.setHijoDer(insertar(dato, nodo.getHijoDer()));
        } else {
            return nodo;
        }
        actualizarAltura(nodo);
        return aplicarRotacion(nodo);
    }

    public void borrar(T dato) {
        raiz = borrar(dato, raiz);
    }

    private Nodo<T> borrar(T dato, Nodo<T> nodo) {
        if (nodo == null) {
            return null;
        }
        if (dato.compareTo(nodo.getDato()) < 0) {
            nodo.setHijoIzq(borrar(dato, nodo.getHijoIzq()));
        } else if (dato.compareTo(nodo.getDato()) > 0) {
            nodo.setHijoDer(borrar(dato, nodo.getHijoDer()));
        } else {
            // One Child or Leaf Node (no children)
            if (nodo.getHijoIzq() == null) {
                return nodo.getHijoDer();
            } else if (nodo.getHijoDer() == null) {
                return nodo.getHijoIzq();
            }
            // Two Children
            nodo.setDato((T) maximoValor(nodo.getHijoIzq()));
            nodo.setHijoIzq(borrar(nodo.getDato(), nodo.getHijoIzq()));
        }
        actualizarAltura(nodo);
        return aplicarRotacion(nodo);
    }

    public T buscar(T busqueda) {
        return buscar(busqueda, raiz);
    }

    private T buscar(T busqueda, Nodo<T> raiz) {
        if (raiz != null) {
            if (busqueda.compareTo(raiz.getDato()) > 0) {
                return (T) buscar(busqueda, raiz.getHijoDer());
            } else if (busqueda.compareTo(raiz.getDato()) < 0) {
                return (T) buscar(busqueda, raiz.getHijoIzq());
            } else {
                return raiz.getDato();
            }
        }
        return null;
    }

    public void modificar(T dato) {
        Nodo<T> editar = buscarNodo(dato, raiz);
        if (editar == null) {
            throw new RuntimeException("Error al modificar nodo (nodo null - no encontrado)");
        } else {
            editar.setDato(dato);
        }
    }
    
    private Nodo<T> buscarNodo(T busqueda, Nodo<T> raiz) {
        if (raiz != null) {
            if (busqueda.compareTo(raiz.getDato()) > 0) {
                return buscarNodo(busqueda, raiz.getHijoDer());
            } else if (busqueda.compareTo(raiz.getDato()) < 0) {
                return buscarNodo(busqueda, raiz.getHijoIzq());
            } else {
                return raiz;
            }
        }
        return null;
    }

    public void recorridoInOrder() {
        recorridoInOrder(raiz);
    }

    private void recorridoInOrder(Nodo<T> nodo) {
        if (nodo != null) {
            recorridoInOrder(nodo.getHijoIzq());
            System.out.println(nodo.getDato().toString());
            recorridoInOrder(nodo.getHijoDer());
        }
    }

    public T maximoValor() {
        if (estaVacio()) {
            return null;
        }
        return maximoValor(raiz);
    }

    private T maximoValor(Nodo<T> nodo) {
        if (nodo.getHijoDer() != null) {
            return (T) maximoValor(nodo.getHijoDer());
        }
        return nodo.getDato();
    }

    public T minimoValor() {
        if (estaVacio()) {
            return null;
        }
        return minimoValor(raiz);
    }

    private T minimoValor(Nodo<T> nodo) {
        if (nodo.getHijoIzq() != null) {
            return (T) minimoValor(nodo.getHijoIzq());
        }
        return nodo.getDato();
    }

    public boolean estaVacio() {
        return raiz == null;
    }

    private Nodo<T> aplicarRotacion(Nodo<T> nodo) {
        int balance = balance(nodo);
        if (balance > 1) {
            if (balance(nodo.getHijoIzq()) < 0) {
                nodo.setHijoIzq(rotacionIzquierda(nodo.getHijoIzq()));
            }
            return rotacionDerecha(nodo);
        }
        if (balance < -1) {
            if (balance(nodo.getHijoDer()) > 0) {
                nodo.setHijoDer(rotacionDerecha(nodo.getHijoDer()));
            }
            return rotacionIzquierda(nodo);
        }
        return nodo;
    }

    private Nodo<T> rotacionDerecha(Nodo<T> nodo) {
        Nodo<T> nodoIzq = nodo.getHijoIzq();
        Nodo<T> nodoCentral = nodoIzq.getHijoDer();
        nodoIzq.setHijoDer(nodo);
        nodo.setHijoIzq(nodoCentral);
        actualizarAltura(nodo);
        actualizarAltura(nodoIzq);
        return nodoIzq;
    }

    private Nodo<T> rotacionIzquierda(Nodo<T> nodo) {
        Nodo<T> nodoDer = nodo.getHijoDer();
        Nodo<T> nodoCentral = nodoDer.getHijoIzq();
        nodoDer.setHijoIzq(nodo);
        nodo.setHijoDer(nodoCentral);
        actualizarAltura(nodo);
        actualizarAltura(nodoDer);
        return nodoDer;
    }

    private void actualizarAltura(Nodo<T> nodo) {
        int alturaMax = Math.max(altura(nodo.getHijoIzq()), altura(nodo.getHijoDer()));
        nodo.setAltura(alturaMax + 1);
    }

    private int balance(Nodo<T> nodo) {
        return nodo != null ? altura(nodo.getHijoIzq()) - altura(nodo.getHijoDer()) : 0;
    }

    private int altura(Nodo<T> nodo) {
        return nodo != null ? nodo.getAltura() : 0;
    }

    public void limpiar() {
        raiz = null;
    }

    @Override
    public Iterator<T> iterator() {
        return new AVLTreeIterator<>(raiz);
    }

    @Override
    public void forEach(Consumer<? super T> action) {
        Iterable.super.forEach(action); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

}
