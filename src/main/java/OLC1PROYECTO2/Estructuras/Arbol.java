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
import OLC1PROYECTO2.Arbol.Instrucciones;
import OLC1PROYECTO2.Arbol.TablaDeSimbolos;
import java.util.LinkedList;

public class Arbol {
    private LinkedList<Instrucciones> instrucciones;
    public LinkedList<Excepcion> errores;
    private String consola;
    private TablaDeSimbolos global;
    public LinkedList<TablaDeSimbolos> listaTablas;

    public Arbol(LinkedList<Instrucciones> instrucciones){
        this.instrucciones = instrucciones;
        this.consola = "";
        this.global = new TablaDeSimbolos();
        this.errores = new LinkedList<Excepcion>();
        this.listaTablas = new LinkedList<TablaDeSimbolos>();
    }

    public LinkedList<Instrucciones> getInstrucciones(){
        return this.instrucciones;
    }

    public void setInstrucciones(LinkedList<Instrucciones> instrucciones){
        this.instrucciones = instrucciones;
    }

    public String getConsola(){
        return this.consola;
    }

    public void setConsola(String consola){
        this.consola = consola;
    }

    public void updateConsola(String update){
        this.consola = this.consola + update + "\n";
    }

    public TablaDeSimbolos getGlobal(){
        return this.global;
    }

    public void setGlobal(TablaDeSimbolos tabla){
        this.global = tabla;
    }
}