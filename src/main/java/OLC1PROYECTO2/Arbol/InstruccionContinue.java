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
public class InstruccionContinue extends Instrucciones {

    public InstruccionContinue(int linea, int columna) {
        super(Tipo.tipos.CONTINUE, linea, columna);
    }

    @Override
    public NodoAST getNodo() {
        NodoAST nodo = new NodoAST("CONTINUE");
        return nodo;
    }

    @Override
    public InstruccionContinue ejecutar(Arbol tree, TablaDeSimbolos table) {
        return new InstruccionContinue(this.linea, this.columna);
    }
}
