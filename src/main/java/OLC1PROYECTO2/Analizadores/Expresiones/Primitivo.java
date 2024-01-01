/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OLC1PROYECTO2.Analizadores.Expresiones;

import OLC1PROYECTO2.Arbol.Instrucciones;
import OLC1PROYECTO2.Arbol.TablaDeSimbolos;
import OLC1PROYECTO2.Arbol.Tipo;
import OLC1PROYECTO2.Estructuras.Arbol;
import OLC1PROYECTO2.Estructuras.NodoAST;

/**
 *
 * @author javie
 */
public class Primitivo extends Instrucciones {
    private Object valor;

    public Primitivo(Tipo.tipos tipo, Object valor, int linea, int columna) {
        super(tipo, linea, columna);
        if (this.tipo == Tipo.tipos.BOOLEAN) {
            valor = "TRUE".equalsIgnoreCase((String) valor);
        }
        this.valor = valor;
    }

    @Override
    public NodoAST getNodo() {
        NodoAST nodo = new NodoAST("PRIMITIVO");
        nodo.agregarHijo(String.valueOf(this.valor));
        return nodo;
    }

    public Object ejecutar(Arbol tree, TablaDeSimbolos table) {
        return this.valor;
    }
}

