/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OLC1PROYECTO2.Arbol;

import OLC1PROYECTO2.Estructuras.Arbol;
import OLC1PROYECTO2.Estructuras.Excepcion;
import OLC1PROYECTO2.Estructuras.NodoAST;

/**
 *
 * @author javie
 */
public class Asignacion extends Instrucciones {

    private Instrucciones contenido;
    private String identificador;

    public Asignacion(String nombre, int linea, int columna, Instrucciones contenido) {
        super(Tipo.tipos.STRING, linea, columna);
        this.contenido = contenido;
        this.identificador = nombre;
    }

    @Override
    public NodoAST getNodo() {
        NodoAST nodo = new NodoAST("ASIGNACION");
        nodo.agregarHijo(this.identificador);
        nodo.agregarHijo("=");
        if (this.contenido instanceof Instrucciones) {
            nodo.agregarHijoNodo(this.contenido.getNodo());
        } else {
            nodo.agregarHijo("Error");
        }
        return nodo;
    }

    @Override
    public Object ejecutar(Arbol tree, TablaDeSimbolos table) {
        Simbolo variable = table.getVariable(this.identificador);
        Object valorContenido = this.contenido.ejecutar(tree, table);

        if (variable == null) {
            new Excepcion("Semántico", "Referencia a una variable no declarada", this.linea, this.columna);
        }

        boolean opcion = false;
        if (variable.getTipo() == Tipo.tipos.DOUBLE && this.contenido.getTipo() == Tipo.tipos.INT) {
            opcion = true;
        } else if (variable.getTipo() != this.contenido.getTipo()) {
            new Excepcion("Semántico", "El tipo de la variable y la asignación no coinciden", this.linea, this.columna);
        } else {
            opcion = true;
        }

        if (opcion) {
            variable.setValor(valorContenido);
            return variable.getValor();
        }

        new Excepcion("Semántico", "El tipo de la variable y la asignación no coinciden", this.linea, this.columna);
        return null;
    }

}
