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
import java.util.ArrayList;

public class NodoAST {
    private ArrayList<NodoAST> hijos;
    private String valor;

    public NodoAST(String valor) {
        this.valor = valor;
        this.hijos = new ArrayList<NodoAST>();
    }

    public void setHijos(ArrayList<NodoAST> hijos) {
        this.hijos = hijos;
    }

    public void agregarHijo(String cadena) {
        this.hijos.add(new NodoAST(cadena));
    }

    public void agregarHijos(ArrayList<NodoAST> hijos) {
        for(NodoAST m : hijos) {
            this.hijos.add(m);
        }
    }

    public void agregarHijoNodo(NodoAST hijo) {
        this.hijos.add(hijo);
    }

    public String getValor() {
        return this.valor;
    }

    public void setValor(String cadena) {
        this.valor = cadena;
    }
}