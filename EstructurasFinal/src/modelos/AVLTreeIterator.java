package modelos;

import java.util.Iterator;
import java.util.Stack;
import java.util.function.Consumer;

/**
 *
 * @author Joseph && Jhovanny
 */
public class AVLTreeIterator<T> implements Iterator {

    private Stack<Nodo<T>> stack = new Stack<>();

    /**
     * Constructor básico para In Order
     * @param raiz nodo donde inicia el iterador
     */
    public AVLTreeIterator(Nodo<T> raiz) {
        insertarElementosEnStack(raiz);
    }
    
    /**
     * Constructor para pre order y post order
     * @param raiz nodo donde inicia el iterador
     * @param n ingresa 1 si es PreOrden y 2 si es PostOrden
     */
    public AVLTreeIterator(Nodo<T> raiz, int n) {
        if (n == 1) {
            insertarElementosEnStackPre(raiz);
        } else if (n == 2) {
            insertarElementosEnStackPost(raiz);
        } else {
            throw new RuntimeException("Tipo de recorrido inválido");
        }
    }

    private void insertarElementosEnStack(Nodo<T> raiz) {
        if (raiz != null) {
            insertarElementosEnStack(raiz.getHijoDer());
            stack.add(raiz);
            insertarElementosEnStack(raiz.getHijoIzq());
        }
    }
    
    private void insertarElementosEnStackPre(Nodo<T> raiz) {
        if (raiz != null) {
            insertarElementosEnStack(raiz.getHijoDer());
            insertarElementosEnStack(raiz.getHijoIzq());
            stack.add(raiz);
        }
    }
    
    private void insertarElementosEnStackPost(Nodo<T> raiz) {
        if (raiz != null) {
            stack.add(raiz);
            insertarElementosEnStack(raiz.getHijoDer());
            insertarElementosEnStack(raiz.getHijoIzq());
        }
    }

    public void imprimir() {
        while (!stack.isEmpty()) {
            System.out.println("Elemento: " + stack.pop().getDato().toString());
        }
    }
    
    public void forEachRemainingPrePost(Consumer action) {
        while (hasNext())
            action.accept(next());
    }

    @Override
    public boolean hasNext() {
        return !stack.isEmpty();
    }

    @Override
    public Object next() {
        return stack.pop().getDato();
    }

    @Override
    public void forEachRemaining(Consumer action) {
        Iterator.super.forEachRemaining(action); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

    
}
