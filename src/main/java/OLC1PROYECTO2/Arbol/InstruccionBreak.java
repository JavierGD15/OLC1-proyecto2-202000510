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
public class InstruccionBreak extends Instrucciones {

    public InstruccionBreak(int linea, int columna) {
        super(Tipo.tipos.BREAK, linea, columna);
    }

    @Override
    public NodoAST getNodo() {
        NodoAST nodo = new NodoAST("BREAK");
        return nodo;
    }

    @Override
    public InstruccionBreak ejecutar(Arbol tree, TablaDeSimbolos table) {
        return new InstruccionBreak(this.linea, this.columna);
    }
}
