/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OLC1PROYECTO2.Arbol;

/**
 *
 * @author javie
 */
public abstract class Instrucciones {
    
     Tipo tipo;
     int linea;
     int columna;
     Tipo vectorLista;

    public Instrucciones(Tipo tipo, int linea, int columna, Tipo vectorLista) {
        this.tipo = tipo;
        this.linea = linea;
        this.columna = columna;
        this.vectorLista = vectorLista;
    }

    public Instrucciones(Tipo tipo, int linea, int columna) {
        this.tipo = tipo;
        this.linea = linea;
        this.columna = columna;
    }

    public Instrucciones() {
    }
    
  
    public abstract Object ejecutar(TablaDeSimbolos ts);


}
