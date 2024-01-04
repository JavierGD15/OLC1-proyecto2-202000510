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
import java.util.ArrayList;

public class InstruccionIfElseIf extends Instrucciones {
    private Instrucciones condicion;
    private ArrayList<Instrucciones> listaInstruccionIf;
    private Instrucciones instruccionElseIf;

    public InstruccionIfElseIf(Instrucciones expresion, ArrayList<Instrucciones> listaIf, Instrucciones elseif, int linea, int columna) {
        super(Tipo.tipos.STRING, linea, columna);
        this.condicion = expresion;
        this.listaInstruccionIf = listaIf;
        this.instruccionElseIf = elseif;
    }

    @Override
    public NodoAST getNodo() {
        NodoAST nodo = new NodoAST("IF");
        nodo.agregarHijo("if");
        nodo.agregarHijo("(");
        nodo.agregarHijoNodo(this.condicion.getNodo());
        nodo.agregarHijo(")");
        nodo.agregarHijo("{");
        NodoAST nodito = new NodoAST("INSTRUCCIONES");
        for (Instrucciones m : this.listaInstruccionIf) {
            nodito.agregarHijoNodo(m.getNodo());
        }
        nodo.agregarHijoNodo(nodito);
        nodo.agregarHijo("}");
        nodo.agregarHijo("ELSE");
        nodo.agregarHijoNodo(this.instruccionElseIf.getNodo());
        return nodo;
    }

    @Override
    public Object ejecutar(Arbol tree, TablaDeSimbolos table) {
        Object result = this.condicion.ejecutar(tree, table);

        if (this.condicion.getTipo() != Tipo.tipos.BOOLEAN) {
             new Excepcion("Semántico", "Condición de tipo incorrecto", this.linea, this.columna);
        }

        if ((Boolean) result) {
            TablaDeSimbolos nuevaTabla = new TablaDeSimbolos();
            nuevaTabla.nombreEntorno = "If";
            tree.listaTablas.add(nuevaTabla);
            nuevaTabla.setAnterior(table);

            for (Instrucciones m : this.listaInstruccionIf) {
                Object resultInstruccion = m.ejecutar(tree, nuevaTabla);
                // Manejo de instrucciones break, continue, return o excepciones
                if (resultInstruccion instanceof InstruccionBreak || 
                    resultInstruccion instanceof InstruccionContinue || 
                    resultInstruccion instanceof InstruccionReturn || 
                    resultInstruccion instanceof Excepcion) {
                    return resultInstruccion;
                }
            }
            return true;
        } else {
            return this.instruccionElseIf.ejecutar(tree, table);
        }
    }
}

