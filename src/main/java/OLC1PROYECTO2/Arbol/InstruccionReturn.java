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
public class InstruccionReturn extends Instrucciones {
    private Instrucciones retorno;

    public InstruccionReturn(int linea, int columna, Instrucciones retorno) {
        super(Tipo.tipos.RETORNO, linea, columna);
        this.retorno = retorno;
    }
    
    public InstruccionReturn(int linea, int columna) {
        super(Tipo.tipos.RETORNO, linea, columna);
        this.retorno = retorno;
    }

    public Instrucciones getRetorno() {
        return retorno;
    }
    
    

    @Override
    public NodoAST getNodo() {
        NodoAST nodo = new NodoAST("RETURN");
        nodo.agregarHijo("return");
        if (this.retorno != null) {
            nodo.agregarHijoNodo(this.retorno.getNodo());
        }
        return nodo;
    }

    @Override
    public Object ejecutar(Arbol tree, TablaDeSimbolos table) {
        return this;
    }
    
    
}

