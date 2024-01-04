/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OLC1PROYECTO2.Arbol;

import OLC1PROYECTO2.Estructuras.Arbol;
import OLC1PROYECTO2.Estructuras.NodoAST;

/**
 *
 * @author javie
 */
public class Exec extends Instrucciones {
    private Instrucciones funcion;

    public Exec(Instrucciones funcion, int linea, int columna) {
        super(Tipo.tipos.STRING, linea, columna);
        this.funcion = funcion;
    }

    @Override
    public NodoAST getNodo() {
        NodoAST nodo = new NodoAST("RUN");
        nodo.agregarHijo("run");
        nodo.agregarHijoNodo(this.funcion.getNodo());
        return nodo;
    }

    @Override
    public Object ejecutar(Arbol tree, TablaDeSimbolos table) {
        return this.funcion.ejecutar(tree, table);
    }
}
