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

public class InstruccionIfElse extends Instrucciones {
    private Instrucciones condicion;
    private LinkedList<Instrucciones> listaInstruccionIf;
    private LinkedList<Instrucciones> listaInstruccionElse;

    public InstruccionIfElse(Instrucciones expresion, LinkedList<Instrucciones> listaIf, LinkedList<Instrucciones> listaElse, int linea, int columna) {
        super(Tipo.tipos.STRING, linea, columna);
        this.condicion = expresion;
        this.listaInstruccionIf = listaIf;
        this.listaInstruccionElse = listaElse;
    }

    @Override
    public NodoAST getNodo() {
        NodoAST nodo = new NodoAST("IF-ELSE");
        nodo.agregarHijo("if");
        nodo.agregarHijo("(");
        nodo.agregarHijoNodo(this.condicion.getNodo());
        nodo.agregarHijo(")");
        nodo.agregarHijo("{");
        NodoAST noditoIf = new NodoAST("INSTRUCCIONES-IF");
        for (Instrucciones m : this.listaInstruccionIf) {
            noditoIf.agregarHijoNodo(m.getNodo());
        }
        nodo.agregarHijoNodo(noditoIf);
        nodo.agregarHijo("}");
        nodo.agregarHijo("else");
        nodo.agregarHijo("{");
        NodoAST noditoElse = new NodoAST("INSTRUCCIONES-ELSE");
        for (Instrucciones m : this.listaInstruccionElse) {
            noditoElse.agregarHijoNodo(m.getNodo());
        }
        nodo.agregarHijoNodo(noditoElse);
        nodo.agregarHijo("}");
        return nodo;
    }

    @Override
    public Object ejecutar(Arbol tree, TablaDeSimbolos table) {
        Object result = this.condicion.ejecutar(tree, table);

        if (this.condicion.getTipo() != Tipo.tipos.BOOLEAN) {
           new Excepcion("Semántico", "Condición de tipo incorrecto", this.linea, this.columna);
        }

        TablaDeSimbolos nuevaTabla = new TablaDeSimbolos();
        nuevaTabla.setAnterior(table);
        tree.listaTablas.add(nuevaTabla);

        LinkedList<Instrucciones> listaEjecutar = result instanceof Boolean && (Boolean) result ? this.listaInstruccionIf : this.listaInstruccionElse;
        nuevaTabla.nombreEntorno = (result instanceof Boolean && (Boolean) result ? "If" : "Else");

        for (Instrucciones m : listaEjecutar) {
            Object resultInstruccion = m.ejecutar(tree, nuevaTabla);
            // Manejo de instrucciones break, continue, return o excepciones
            if (resultInstruccion instanceof InstruccionBreak || 
                resultInstruccion instanceof InstruccionContinue || 
                resultInstruccion instanceof InstruccionReturn || 
                resultInstruccion instanceof Excepcion) {
                return resultInstruccion;
            }
        }
        return result instanceof Boolean && (Boolean) result ? true : false;
    }
}

