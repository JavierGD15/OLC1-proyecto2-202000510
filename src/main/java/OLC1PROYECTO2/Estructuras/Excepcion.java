/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OLC1PROYECTO2.Estructuras;

/**
 *
 * @author javie
 */
public class Excepcion {
    private String tipo;
    private String descripcion;
    private int linea;
    private int columna;

    public Excepcion(String tipo, String descripcion, int linea, int columna) {
        this.tipo = tipo;
        this.descripcion = descripcion;
        this.linea = linea;
        this.columna = columna;
    }

    public String getTipo() {
        return this.tipo;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public int getLinea() {
        return this.linea;
    }

    public int getColumna() {
        return this.columna;
    }
    
    
}
