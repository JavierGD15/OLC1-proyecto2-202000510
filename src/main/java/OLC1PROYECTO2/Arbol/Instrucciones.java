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
public abstract class Instrucciones {
    
     public Tipo.tipos tipo;
      public int linea;
     public int columna;
     public Tipo vectorLista;

    public Instrucciones(Tipo.tipos tipo, int linea, int columna, Tipo vectorLista) {
        this.tipo = tipo;
        this.linea = linea;
        this.columna = columna;
        this.vectorLista = vectorLista;
    }
    
    public Instrucciones(Tipo.tipos tipo, int linea, int columna) {
        this.tipo = tipo;
        this.linea = linea;
        this.columna = columna;
    }

   


    public Instrucciones(int linea, int columna) {
        this.linea = linea;
        this.columna = columna;
    }

    public Tipo.tipos getTipo() {
        return tipo;
    }

    public Tipo getVectorLista() {
        return vectorLista;
    }

    
    
    
    

    public abstract Object ejecutar(Arbol arbol,TablaDeSimbolos ts);
    
    public abstract NodoAST getNodo();


}
