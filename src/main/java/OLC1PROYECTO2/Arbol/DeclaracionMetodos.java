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
import OLC1PROYECTO2.Estructuras.Arbol;
import OLC1PROYECTO2.Estructuras.Excepcion;
import OLC1PROYECTO2.Estructuras.NodoAST;
import java.util.LinkedList;

public class DeclaracionMetodos extends Instrucciones {

    private LinkedList<Instrucciones> listaInstruccion;
    private LinkedList<Instrucciones> listaParametros;
    private String identificador;

    public DeclaracionMetodos(String identificador, LinkedList<Instrucciones> listaInstruccion, int linea, int columna, LinkedList<Instrucciones> listaParametros) {
        super(Tipo.tipos.METODO, linea, columna);
        this.listaInstruccion = listaInstruccion;
        this.identificador = identificador;
        this.listaParametros = listaParametros != null ? listaParametros : new LinkedList<>();
    }
    
     public DeclaracionMetodos(String identificador, LinkedList<Instrucciones> listaInstruccion, int linea, int columna) {
        super(Tipo.tipos.METODO, linea, columna);
        this.listaInstruccion = listaInstruccion;
        this.identificador = identificador;
    }


    @Override
    public NodoAST getNodo() {
        NodoAST nodo = new NodoAST("DECLARAR METODO");
        nodo.agregarHijo("void");
        nodo.agregarHijo(this.identificador);
        nodo.agregarHijo("(");
        if (this.listaParametros != null) {
            NodoAST nodito = new NodoAST("PARAMETROS");
            for (Instrucciones m : this.listaParametros) {
                nodito.agregarHijoNodo(m.getNodo());
            }
            nodo.agregarHijoNodo(nodito);
        }
        nodo.agregarHijo(")");
        nodo.agregarHijo("{");
        NodoAST noditoInstrucciones = new NodoAST("INSTRUCCIONES");
        for (Instrucciones m : this.listaInstruccion) {
            noditoInstrucciones.agregarHijoNodo(m.getNodo());
        }
        nodo.agregarHijoNodo(noditoInstrucciones);
        nodo.agregarHijo("}");
        return nodo;
    }

    @Override
    public Object ejecutar(Arbol tree, TablaDeSimbolos table) {
        LinkedList<Instrucciones> listaAguardar = new LinkedList<>(this.listaInstruccion);
        if (this.listaParametros != null) {
            listaAguardar.addAll(this.listaParametros);
        }
        int numeroParametros = this.listaParametros != null ? this.listaParametros.size() : 0;

        Simbolo metodo = new Simbolo(this.tipo, this.identificador, this.linea, this.columna, listaAguardar, numeroParametros);

        if (table.setVariable(metodo)) {
            return table.getVariable(this.identificador);
        } else {
            new Excepcion("Semántico", "El identificador del metodo ya existe", this.linea, this.columna);
        }
        return null;
    }
}
